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
import model.CuentaCorriente;
import model.JPAController.exceptions.NonexistentEntityException;

/**
 *
 * @author Ariel
 */
public class CuentaCorrienteJpaController implements Serializable {

    public CuentaCorrienteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CuentaCorriente cuentaCorriente) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(cuentaCorriente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CuentaCorriente cuentaCorriente) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            cuentaCorriente = em.merge(cuentaCorriente);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = cuentaCorriente.getId();
                if (findCuentaCorriente(id) == null) {
                    throw new NonexistentEntityException("The cuentaCorriente with id " + id + " no longer exists.");
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
            CuentaCorriente cuentaCorriente;
            try {
                cuentaCorriente = em.getReference(CuentaCorriente.class, id);
                cuentaCorriente.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cuentaCorriente with id " + id + " no longer exists.", enfe);
            }
            em.remove(cuentaCorriente);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CuentaCorriente> findCuentaCorrienteEntities() {
        return findCuentaCorrienteEntities(true, -1, -1);
    }

    public List<CuentaCorriente> findCuentaCorrienteEntities(int maxResults, int firstResult) {
        return findCuentaCorrienteEntities(false, maxResults, firstResult);
    }

    private List<CuentaCorriente> findCuentaCorrienteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CuentaCorriente.class));
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

    public CuentaCorriente findCuentaCorriente(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CuentaCorriente.class, id);
        } finally {
            em.close();
        }
    }

    public int getCuentaCorrienteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CuentaCorriente> rt = cq.from(CuentaCorriente.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
