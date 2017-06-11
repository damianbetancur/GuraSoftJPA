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
import model.CategoriaDeCatalogo;
import model.JPAController.exceptions.NonexistentEntityException;
import model.ListaDePrecio;
import model.Proveedor;

/**
 *
 * @author Ariel
 */
public class ArticuloJpaController implements Serializable {

    public ArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Articulo articulo) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaDeCatalogo unCategoriaDeArticulos = articulo.getUnCategoriaDeCatalogo();
            if (unCategoriaDeArticulos != null) {
                unCategoriaDeArticulos = em.getReference(unCategoriaDeArticulos.getClass(), unCategoriaDeArticulos.getId());
                articulo.setUnCategoriaDeCatalogo(unCategoriaDeArticulos);
            }
            Proveedor unProveedor = articulo.getUnProveedor();
            if (unProveedor != null) {
                unProveedor = em.getReference(unProveedor.getClass(), unProveedor.getId());
                articulo.setUnProveedor(unProveedor);
            }
            em.persist(articulo);
            if (unCategoriaDeArticulos != null) {
                unCategoriaDeArticulos.getListaDeArticulos().add(articulo);
                unCategoriaDeArticulos = em.merge(unCategoriaDeArticulos);
            }
            if (unProveedor != null) {
                unProveedor.getListaDeArticulos().add(articulo);
                unProveedor = em.merge(unProveedor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Articulo articulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Articulo persistentArticulo = em.find(Articulo.class, articulo.getId());
            CategoriaDeCatalogo unCategoriaDeArticulosOld = persistentArticulo.getUnCategoriaDeCatalogo();
            CategoriaDeCatalogo unCategoriaDeArticulosNew = articulo.getUnCategoriaDeCatalogo();
            Proveedor unProveedorOld = persistentArticulo.getUnProveedor();
            Proveedor unProveedorNew = articulo.getUnProveedor();
            if (unCategoriaDeArticulosNew != null) {
                unCategoriaDeArticulosNew = em.getReference(unCategoriaDeArticulosNew.getClass(), unCategoriaDeArticulosNew.getId());
                articulo.setUnCategoriaDeCatalogo(unCategoriaDeArticulosNew);
            }
            if (unProveedorNew != null) {
                unProveedorNew = em.getReference(unProveedorNew.getClass(), unProveedorNew.getId());
                articulo.setUnProveedor(unProveedorNew);
            }
            articulo = em.merge(articulo);
            if (unCategoriaDeArticulosOld != null && !unCategoriaDeArticulosOld.equals(unCategoriaDeArticulosNew)) {
                unCategoriaDeArticulosOld.getListaDeArticulos().remove(articulo);
                unCategoriaDeArticulosOld = em.merge(unCategoriaDeArticulosOld);
            }
            if (unCategoriaDeArticulosNew != null && !unCategoriaDeArticulosNew.equals(unCategoriaDeArticulosOld)) {
                unCategoriaDeArticulosNew.getListaDeArticulos().add(articulo);
                unCategoriaDeArticulosNew = em.merge(unCategoriaDeArticulosNew);
            }
            if (unProveedorOld != null && !unProveedorOld.equals(unProveedorNew)) {
                unProveedorOld.getListaDeArticulos().remove(articulo);
                unProveedorOld = em.merge(unProveedorOld);
            }
            if (unProveedorNew != null && !unProveedorNew.equals(unProveedorOld)) {
                unProveedorNew.getListaDeArticulos().add(articulo);
                unProveedorNew = em.merge(unProveedorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = articulo.getId();
                if (findArticulo(id) == null) {
                    throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.");
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
            Articulo articulo;
            try {
                articulo = em.getReference(Articulo.class, id);
                articulo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The articulo with id " + id + " no longer exists.", enfe);
            }
            CategoriaDeCatalogo unCategoriaDeArticulos = articulo.getUnCategoriaDeCatalogo();
            if (unCategoriaDeArticulos != null) {
                unCategoriaDeArticulos.getListaDeArticulos().remove(articulo);
                unCategoriaDeArticulos = em.merge(unCategoriaDeArticulos);
            }
            Proveedor unProveedor = articulo.getUnProveedor();
            if (unProveedor != null) {
                unProveedor.getListaDeArticulos().remove(articulo);
                unProveedor = em.merge(unProveedor);
            }
            em.remove(articulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Articulo> findArticuloEntities() {
        return findArticuloEntities(true, -1, -1);
    }

    public List<Articulo> findArticuloEntities(int maxResults, int firstResult) {
        return findArticuloEntities(false, maxResults, firstResult);
    }

    private List<Articulo> findArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Articulo.class));
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

    public Articulo findArticulo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Articulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Articulo> rt = cq.from(Articulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
  
}
