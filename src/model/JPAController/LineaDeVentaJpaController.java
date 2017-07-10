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
import model.LineaDeVenta;

/**
 *
 * @author Ariel
 */
public class LineaDeVentaJpaController implements Serializable {

    public LineaDeVentaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(LineaDeVenta lineaDeVenta) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(lineaDeVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(LineaDeVenta lineaDeVenta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            lineaDeVenta = em.merge(lineaDeVenta);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = lineaDeVenta.getId();
                if (findLineaDeVenta(id) == null) {
                    throw new NonexistentEntityException("The lineaDeVenta with id " + id + " no longer exists.");
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
            LineaDeVenta lineaDeVenta;
            try {
                lineaDeVenta = em.getReference(LineaDeVenta.class, id);
                lineaDeVenta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lineaDeVenta with id " + id + " no longer exists.", enfe);
            }
            em.remove(lineaDeVenta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<LineaDeVenta> findLineaDeVentaEntities() {
        return findLineaDeVentaEntities(true, -1, -1);
    }

    public List<LineaDeVenta> findLineaDeVentaEntities(int maxResults, int firstResult) {
        return findLineaDeVentaEntities(false, maxResults, firstResult);
    }

    private List<LineaDeVenta> findLineaDeVentaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(LineaDeVenta.class));
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

    public LineaDeVenta findLineaDeVenta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(LineaDeVenta.class, id);
        } finally {
            em.close();
        }
    }

    public int getLineaDeVentaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<LineaDeVenta> rt = cq.from(LineaDeVenta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
