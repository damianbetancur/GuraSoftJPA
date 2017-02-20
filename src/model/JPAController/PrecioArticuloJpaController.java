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
import model.JPAController.exceptions.PreexistingEntityException;
import model.PrecioArticulo;

/**
 *
 * @author Ariel
 */
public class PrecioArticuloJpaController implements Serializable {

    public PrecioArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(PrecioArticulo precioArticulo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(precioArticulo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPrecioArticulo(precioArticulo.getId_articulo()) != null) {
                throw new PreexistingEntityException("PrecioArticulo " + precioArticulo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(PrecioArticulo precioArticulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            precioArticulo = em.merge(precioArticulo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = precioArticulo.getId_articulo();
                if (findPrecioArticulo(id) == null) {
                    throw new NonexistentEntityException("The precioArticulo with id " + id + " no longer exists.");
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
            PrecioArticulo precioArticulo;
            try {
                precioArticulo = em.getReference(PrecioArticulo.class, id);
                precioArticulo.getId_articulo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The precioArticulo with id " + id + " no longer exists.", enfe);
            }
            em.remove(precioArticulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<PrecioArticulo> findPrecioArticuloEntities() {
        return findPrecioArticuloEntities(true, -1, -1);
    }

    public List<PrecioArticulo> findPrecioArticuloEntities(int maxResults, int firstResult) {
        return findPrecioArticuloEntities(false, maxResults, firstResult);
    }

    private List<PrecioArticulo> findPrecioArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(PrecioArticulo.class));
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

    public PrecioArticulo findPrecioArticulo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(PrecioArticulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getPrecioArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<PrecioArticulo> rt = cq.from(PrecioArticulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    //Buscar Cliente por DNI
    public PrecioArticulo buscarPrecioArticulo(PrecioArticulo unPrecioArticulo){
        EntityManager em = getEntityManager();
        PrecioArticulo precioArticulo = null;
        String consulta;
        try {
            consulta ="FROM PrecioArticulo preArt WHERE preArt.id_articulo = ?1 AND preArt.id_listaDePrecio = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, unPrecioArticulo.getId_articulo());
            query.setParameter(2, unPrecioArticulo.getId_listaDePrecio());
            
            
            List <PrecioArticulo> lista = query.getResultList();
            if (!lista.isEmpty()) {
                precioArticulo = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        } finally{
            em.close();
        }
        return precioArticulo;
    }
    
}
