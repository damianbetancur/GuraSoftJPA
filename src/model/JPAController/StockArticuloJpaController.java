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
import model.Articulo;
import model.Deposito;
import model.JPAController.exceptions.NonexistentEntityException;
import model.JPAController.exceptions.PreexistingEntityException;
import model.StockArticulo;

/**
 *
 * @author Ariel
 */
public class StockArticuloJpaController implements Serializable {

    public StockArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(StockArticulo stockArticulo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(stockArticulo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findStockArticulo(stockArticulo.getId_Deposito()) != null) {
                throw new PreexistingEntityException("StockArticulo " + stockArticulo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(StockArticulo stockArticulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            stockArticulo = em.merge(stockArticulo);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = stockArticulo.getId_Deposito();
                if (findStockArticulo(id) == null) {
                    throw new NonexistentEntityException("The stockArticulo with id " + id + " no longer exists.");
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
            StockArticulo stockArticulo;
            try {
                stockArticulo = em.getReference(StockArticulo.class, id);
                stockArticulo.getId_Deposito();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The stockArticulo with id " + id + " no longer exists.", enfe);
            }
            em.remove(stockArticulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<StockArticulo> findStockArticuloEntities() {
        return findStockArticuloEntities(true, -1, -1);
    }

    public List<StockArticulo> findStockArticuloEntities(int maxResults, int firstResult) {
        return findStockArticuloEntities(false, maxResults, firstResult);
    }

    private List<StockArticulo> findStockArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(StockArticulo.class));
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

    public StockArticulo findStockArticulo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(StockArticulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getStockArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<StockArticulo> rt = cq.from(StockArticulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    
    /**
     * Busca el precio de un articulo
     * @param unArticulo que se busca
     * @param unDeposito donde se busca
     * @return articulo encontrado
     */
    public StockArticulo buscarStockDeArticuloEnDeposito(Articulo unArticulo, Deposito unDeposito){
        EntityManager em = getEntityManager();
        StockArticulo stockArticulo = null;
        String consulta;
        try {
            consulta ="FROM StockArticulo stkArt WHERE stkArt.id_articulo = ?1 AND stkArt.id_Deposito = ?2";
            Query query = em.createQuery(consulta);
            query.setParameter(1, unArticulo.getId());
            query.setParameter(2, unDeposito.getId());
            
            
            List <StockArticulo> lista = query.getResultList();
            if (!lista.isEmpty()) {
                stockArticulo = lista.get(0);
            }
        } catch (Exception e) {
            throw e;
        } finally{
            em.close();
        }
        return stockArticulo;
    }
    
    
}
