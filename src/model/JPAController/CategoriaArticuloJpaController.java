/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPAController;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.CatalogoArticulos;
import model.Articulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CategoriaArticulo;
import model.JPAController.exceptions.NonexistentEntityException;

/**
 *
 * @author Ariel
 */
public class CategoriaArticuloJpaController implements Serializable {

    public CategoriaArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CategoriaArticulo categoriaArticulo) {
        if (categoriaArticulo.getListaDeArticulos() == null) {
            categoriaArticulo.setListaDeArticulos(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatalogoArticulos unCatalogoDeArticulos = categoriaArticulo.getUnCatalogoDeArticulos();
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos = em.getReference(unCatalogoDeArticulos.getClass(), unCatalogoDeArticulos.getId());
                categoriaArticulo.setUnCatalogoDeArticulos(unCatalogoDeArticulos);
            }
            List<Articulo> attachedListaDeArticulos = new ArrayList<Articulo>();
            for (Articulo listaDeArticulosArticuloToAttach : categoriaArticulo.getListaDeArticulos()) {
                listaDeArticulosArticuloToAttach = em.getReference(listaDeArticulosArticuloToAttach.getClass(), listaDeArticulosArticuloToAttach.getId());
                attachedListaDeArticulos.add(listaDeArticulosArticuloToAttach);
            }
            categoriaArticulo.setListaDeArticulos(attachedListaDeArticulos);
            em.persist(categoriaArticulo);
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos.getListaCatalogoDeArticulos().add(categoriaArticulo);
                unCatalogoDeArticulos = em.merge(unCatalogoDeArticulos);
            }
            for (Articulo listaDeArticulosArticulo : categoriaArticulo.getListaDeArticulos()) {
                CategoriaArticulo oldUnCategoriaDeArticulosOfListaDeArticulosArticulo = listaDeArticulosArticulo.getUnCategoriaDeArticulos();
                listaDeArticulosArticulo.setUnCategoriaDeArticulos(categoriaArticulo);
                listaDeArticulosArticulo = em.merge(listaDeArticulosArticulo);
                if (oldUnCategoriaDeArticulosOfListaDeArticulosArticulo != null) {
                    oldUnCategoriaDeArticulosOfListaDeArticulosArticulo.getListaDeArticulos().remove(listaDeArticulosArticulo);
                    oldUnCategoriaDeArticulosOfListaDeArticulosArticulo = em.merge(oldUnCategoriaDeArticulosOfListaDeArticulosArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CategoriaArticulo categoriaArticulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaArticulo persistentCategoriaArticulo = em.find(CategoriaArticulo.class, categoriaArticulo.getId());
            CatalogoArticulos unCatalogoDeArticulosOld = persistentCategoriaArticulo.getUnCatalogoDeArticulos();
            CatalogoArticulos unCatalogoDeArticulosNew = categoriaArticulo.getUnCatalogoDeArticulos();
            List<Articulo> listaDeArticulosOld = persistentCategoriaArticulo.getListaDeArticulos();
            List<Articulo> listaDeArticulosNew = categoriaArticulo.getListaDeArticulos();
            if (unCatalogoDeArticulosNew != null) {
                unCatalogoDeArticulosNew = em.getReference(unCatalogoDeArticulosNew.getClass(), unCatalogoDeArticulosNew.getId());
                categoriaArticulo.setUnCatalogoDeArticulos(unCatalogoDeArticulosNew);
            }
            List<Articulo> attachedListaDeArticulosNew = new ArrayList<Articulo>();
            for (Articulo listaDeArticulosNewArticuloToAttach : listaDeArticulosNew) {
                listaDeArticulosNewArticuloToAttach = em.getReference(listaDeArticulosNewArticuloToAttach.getClass(), listaDeArticulosNewArticuloToAttach.getId());
                attachedListaDeArticulosNew.add(listaDeArticulosNewArticuloToAttach);
            }
            listaDeArticulosNew = attachedListaDeArticulosNew;
            categoriaArticulo.setListaDeArticulos(listaDeArticulosNew);
            categoriaArticulo = em.merge(categoriaArticulo);
            if (unCatalogoDeArticulosOld != null && !unCatalogoDeArticulosOld.equals(unCatalogoDeArticulosNew)) {
                unCatalogoDeArticulosOld.getListaCatalogoDeArticulos().remove(categoriaArticulo);
                unCatalogoDeArticulosOld = em.merge(unCatalogoDeArticulosOld);
            }
            if (unCatalogoDeArticulosNew != null && !unCatalogoDeArticulosNew.equals(unCatalogoDeArticulosOld)) {
                unCatalogoDeArticulosNew.getListaCatalogoDeArticulos().add(categoriaArticulo);
                unCatalogoDeArticulosNew = em.merge(unCatalogoDeArticulosNew);
            }
            for (Articulo listaDeArticulosOldArticulo : listaDeArticulosOld) {
                if (!listaDeArticulosNew.contains(listaDeArticulosOldArticulo)) {
                    listaDeArticulosOldArticulo.setUnCategoriaDeArticulos(null);
                    listaDeArticulosOldArticulo = em.merge(listaDeArticulosOldArticulo);
                }
            }
            for (Articulo listaDeArticulosNewArticulo : listaDeArticulosNew) {
                if (!listaDeArticulosOld.contains(listaDeArticulosNewArticulo)) {
                    CategoriaArticulo oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo = listaDeArticulosNewArticulo.getUnCategoriaDeArticulos();
                    listaDeArticulosNewArticulo.setUnCategoriaDeArticulos(categoriaArticulo);
                    listaDeArticulosNewArticulo = em.merge(listaDeArticulosNewArticulo);
                    if (oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo != null && !oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo.equals(categoriaArticulo)) {
                        oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo.getListaDeArticulos().remove(listaDeArticulosNewArticulo);
                        oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo = em.merge(oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = categoriaArticulo.getId();
                if (findCategoriaArticulo(id) == null) {
                    throw new NonexistentEntityException("The categoriaArticulo with id " + id + " no longer exists.");
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
            CategoriaArticulo categoriaArticulo;
            try {
                categoriaArticulo = em.getReference(CategoriaArticulo.class, id);
                categoriaArticulo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaArticulo with id " + id + " no longer exists.", enfe);
            }
            CatalogoArticulos unCatalogoDeArticulos = categoriaArticulo.getUnCatalogoDeArticulos();
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos.getListaCatalogoDeArticulos().remove(categoriaArticulo);
                unCatalogoDeArticulos = em.merge(unCatalogoDeArticulos);
            }
            List<Articulo> listaDeArticulos = categoriaArticulo.getListaDeArticulos();
            for (Articulo listaDeArticulosArticulo : listaDeArticulos) {
                listaDeArticulosArticulo.setUnCategoriaDeArticulos(null);
                listaDeArticulosArticulo = em.merge(listaDeArticulosArticulo);
            }
            em.remove(categoriaArticulo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CategoriaArticulo> findCategoriaArticuloEntities() {
        return findCategoriaArticuloEntities(true, -1, -1);
    }

    public List<CategoriaArticulo> findCategoriaArticuloEntities(int maxResults, int firstResult) {
        return findCategoriaArticuloEntities(false, maxResults, firstResult);
    }

    private List<CategoriaArticulo> findCategoriaArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaArticulo.class));
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

    public CategoriaArticulo findCategoriaArticulo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaArticulo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaArticulo> rt = cq.from(CategoriaArticulo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
