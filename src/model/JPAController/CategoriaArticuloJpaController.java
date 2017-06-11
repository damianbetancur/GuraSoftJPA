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
import model.Catalogo;
import model.Articulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CategoriaDeCatalogo;
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

    public void create(CategoriaDeCatalogo categoriaArticulo) {
        if (categoriaArticulo.getListaDeArticulos() == null) {
            categoriaArticulo.setListaDeArticulos(new ArrayList<Articulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalogo unCatalogoDeArticulos = categoriaArticulo.getUnCatalogo();
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos = em.getReference(unCatalogoDeArticulos.getClass(), unCatalogoDeArticulos.getId());
                categoriaArticulo.setUnCatalogo(unCatalogoDeArticulos);
            }
            List<Articulo> attachedListaDeArticulos = new ArrayList<Articulo>();
            for (Articulo listaDeArticulosArticuloToAttach : categoriaArticulo.getListaDeArticulos()) {
                listaDeArticulosArticuloToAttach = em.getReference(listaDeArticulosArticuloToAttach.getClass(), listaDeArticulosArticuloToAttach.getId());
                attachedListaDeArticulos.add(listaDeArticulosArticuloToAttach);
            }
            categoriaArticulo.setListaDeArticulos(attachedListaDeArticulos);
            em.persist(categoriaArticulo);
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos.getListaCategoriaCatalogo().add(categoriaArticulo);
                unCatalogoDeArticulos = em.merge(unCatalogoDeArticulos);
            }
            for (Articulo listaDeArticulosArticulo : categoriaArticulo.getListaDeArticulos()) {
                CategoriaDeCatalogo oldUnCategoriaDeArticulosOfListaDeArticulosArticulo = listaDeArticulosArticulo.getUnCategoriaDeCatalogo();
                listaDeArticulosArticulo.setUnCategoriaDeCatalogo(categoriaArticulo);
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

    public void edit(CategoriaDeCatalogo categoriaArticulo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CategoriaDeCatalogo persistentCategoriaArticulo = em.find(CategoriaDeCatalogo.class, categoriaArticulo.getId());
            Catalogo unCatalogoDeArticulosOld = persistentCategoriaArticulo.getUnCatalogo();
            Catalogo unCatalogoDeArticulosNew = categoriaArticulo.getUnCatalogo();
            List<Articulo> listaDeArticulosOld = persistentCategoriaArticulo.getListaDeArticulos();
            List<Articulo> listaDeArticulosNew = categoriaArticulo.getListaDeArticulos();
            if (unCatalogoDeArticulosNew != null) {
                unCatalogoDeArticulosNew = em.getReference(unCatalogoDeArticulosNew.getClass(), unCatalogoDeArticulosNew.getId());
                categoriaArticulo.setUnCatalogo(unCatalogoDeArticulosNew);
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
                unCatalogoDeArticulosOld.getListaCategoriaCatalogo().remove(categoriaArticulo);
                unCatalogoDeArticulosOld = em.merge(unCatalogoDeArticulosOld);
            }
            if (unCatalogoDeArticulosNew != null && !unCatalogoDeArticulosNew.equals(unCatalogoDeArticulosOld)) {
                unCatalogoDeArticulosNew.getListaCategoriaCatalogo().add(categoriaArticulo);
                unCatalogoDeArticulosNew = em.merge(unCatalogoDeArticulosNew);
            }
            for (Articulo listaDeArticulosOldArticulo : listaDeArticulosOld) {
                if (!listaDeArticulosNew.contains(listaDeArticulosOldArticulo)) {
                    listaDeArticulosOldArticulo.setUnCategoriaDeCatalogo(null);
                    listaDeArticulosOldArticulo = em.merge(listaDeArticulosOldArticulo);
                }
            }
            for (Articulo listaDeArticulosNewArticulo : listaDeArticulosNew) {
                if (!listaDeArticulosOld.contains(listaDeArticulosNewArticulo)) {
                    CategoriaDeCatalogo oldUnCategoriaDeArticulosOfListaDeArticulosNewArticulo = listaDeArticulosNewArticulo.getUnCategoriaDeCatalogo();
                    listaDeArticulosNewArticulo.setUnCategoriaDeCatalogo(categoriaArticulo);
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
            CategoriaDeCatalogo categoriaArticulo;
            try {
                categoriaArticulo = em.getReference(CategoriaDeCatalogo.class, id);
                categoriaArticulo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoriaArticulo with id " + id + " no longer exists.", enfe);
            }
            Catalogo unCatalogoDeArticulos = categoriaArticulo.getUnCatalogo();
            if (unCatalogoDeArticulos != null) {
                unCatalogoDeArticulos.getListaCategoriaCatalogo().remove(categoriaArticulo);
                unCatalogoDeArticulos = em.merge(unCatalogoDeArticulos);
            }
            List<Articulo> listaDeArticulos = categoriaArticulo.getListaDeArticulos();
            for (Articulo listaDeArticulosArticulo : listaDeArticulos) {
                listaDeArticulosArticulo.setUnCategoriaDeCatalogo(null);
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

    public List<CategoriaDeCatalogo> findCategoriaArticuloEntities() {
        return findCategoriaArticuloEntities(true, -1, -1);
    }

    public List<CategoriaDeCatalogo> findCategoriaArticuloEntities(int maxResults, int firstResult) {
        return findCategoriaArticuloEntities(false, maxResults, firstResult);
    }

    private List<CategoriaDeCatalogo> findCategoriaArticuloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CategoriaDeCatalogo.class));
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

    public CategoriaDeCatalogo findCategoriaArticulo(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CategoriaDeCatalogo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaArticuloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CategoriaDeCatalogo> rt = cq.from(CategoriaDeCatalogo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
