/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPAController;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.JPAController.exceptions.NonexistentEntityException;
import model.TipoEmpleado;
import model.Unidad;

/**
 *
 * @author Ariel
 */
public class TipoEmpleadoJpaController implements Serializable {

    public TipoEmpleadoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TipoEmpleado tipoEmpleado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tipoEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TipoEmpleado tipoEmpleado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tipoEmpleado = em.merge(tipoEmpleado);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = tipoEmpleado.getId();
                if (findTipoEmpleado(id) == null) {
                    throw new NonexistentEntityException("The tipoEmpleado with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoEmpleado tipoEmpleado;
            try {
                tipoEmpleado = em.getReference(TipoEmpleado.class, id);
                tipoEmpleado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tipoEmpleado with id " + id + " no longer exists.", enfe);
            }
            em.remove(tipoEmpleado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TipoEmpleado> findTipoEmpleadoEntities() {
        return findTipoEmpleadoEntities(true, -1, -1);
    }

    public List<TipoEmpleado> findTipoEmpleadoEntities(int maxResults, int firstResult) {
        return findTipoEmpleadoEntities(false, maxResults, firstResult);
    }

    private List<TipoEmpleado> findTipoEmpleadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TipoEmpleado.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public TipoEmpleado findTipoEmpleado(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TipoEmpleado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTipoEmpleadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TipoEmpleado> rt = cq.from(TipoEmpleado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Consulta JPQL 
    @SuppressWarnings("unchecked")
    public List<TipoEmpleado> buscarTipoEmpleadoPorUnidad(Unidad u) {
        EntityManager em = getEntityManager();
        
        try {
            String queryString = "FROM TipoEmpleado te WHERE te.unidad = :unidadParametro";
            Query query = em.createQuery(queryString);
            
            query.setParameter("unidadParametro",u);            
            
            return query.getResultList();
            
        } finally {
            em.close();
        }
    }
    
}
