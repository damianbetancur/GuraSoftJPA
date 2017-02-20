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
import model.ListaDePrecio;

/**
 *
 * @author Ariel
 */
public class ListaDePrecioJpaController implements Serializable {

    public ListaDePrecioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ListaDePrecio listaDePrecio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(listaDePrecio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ListaDePrecio listaDePrecio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            listaDePrecio = em.merge(listaDePrecio);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = listaDePrecio.getId();
                if (findListaDePrecio(id) == null) {
                    throw new NonexistentEntityException("The listaDePrecio with id " + id + " no longer exists.");
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
            ListaDePrecio listaDePrecio;
            try {
                listaDePrecio = em.getReference(ListaDePrecio.class, id);
                listaDePrecio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The listaDePrecio with id " + id + " no longer exists.", enfe);
            }
            em.remove(listaDePrecio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ListaDePrecio> findListaDePrecioEntities() {
        return findListaDePrecioEntities(true, -1, -1);
    }

    public List<ListaDePrecio> findListaDePrecioEntities(int maxResults, int firstResult) {
        return findListaDePrecioEntities(false, maxResults, firstResult);
    }

    private List<ListaDePrecio> findListaDePrecioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ListaDePrecio.class));
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

    public ListaDePrecio findListaDePrecio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ListaDePrecio.class, id);
        } finally {
            em.close();
        }
    }

    public int getListaDePrecioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ListaDePrecio> rt = cq.from(ListaDePrecio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
}
