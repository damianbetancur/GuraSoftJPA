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
import model.CategoriaDeCatalogo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.Catalogo;
import model.JPAController.exceptions.NonexistentEntityException;

/**
 *
 * @author Ariel
 */
public class CatalogoArticuloJpaController implements Serializable {

    public CatalogoArticuloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Catalogo catalogoDeArticulos) {
        if (catalogoDeArticulos.getListaCategoriaCatalogo() == null) {
            catalogoDeArticulos.setListaCategoriaCatalogo(new ArrayList<CategoriaDeCatalogo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CategoriaDeCatalogo> attachedListaCatalogoDeArticulos = new ArrayList<CategoriaDeCatalogo>();
            for (CategoriaDeCatalogo listaCatalogoDeArticulosCategoriaArticuloToAttach : catalogoDeArticulos.getListaCategoriaCatalogo()) {
                listaCatalogoDeArticulosCategoriaArticuloToAttach = em.getReference(listaCatalogoDeArticulosCategoriaArticuloToAttach.getClass(), listaCatalogoDeArticulosCategoriaArticuloToAttach.getId());
                attachedListaCatalogoDeArticulos.add(listaCatalogoDeArticulosCategoriaArticuloToAttach);
            }
            catalogoDeArticulos.setListaCategoriaCatalogo(attachedListaCatalogoDeArticulos);
            em.persist(catalogoDeArticulos);
            for (CategoriaDeCatalogo listaCatalogoDeArticulosCategoriaArticulo : catalogoDeArticulos.getListaCategoriaCatalogo()) {
                Catalogo oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo = listaCatalogoDeArticulosCategoriaArticulo.getUnCatalogo();
                listaCatalogoDeArticulosCategoriaArticulo.setUnCatalogo(catalogoDeArticulos);
                listaCatalogoDeArticulosCategoriaArticulo = em.merge(listaCatalogoDeArticulosCategoriaArticulo);
                if (oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo != null) {
                    oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo.getListaCategoriaCatalogo().remove(listaCatalogoDeArticulosCategoriaArticulo);
                    oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo = em.merge(oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Catalogo catalogoDeArticulos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Catalogo persistentCatalogoDeArticulos = em.find(Catalogo.class, catalogoDeArticulos.getId());
            List<CategoriaDeCatalogo> listaCatalogoDeArticulosOld = persistentCatalogoDeArticulos.getListaCategoriaCatalogo();
            List<CategoriaDeCatalogo> listaCatalogoDeArticulosNew = catalogoDeArticulos.getListaCategoriaCatalogo();
            List<CategoriaDeCatalogo> attachedListaCatalogoDeArticulosNew = new ArrayList<CategoriaDeCatalogo>();
            for (CategoriaDeCatalogo listaCatalogoDeArticulosNewCategoriaArticuloToAttach : listaCatalogoDeArticulosNew) {
                listaCatalogoDeArticulosNewCategoriaArticuloToAttach = em.getReference(listaCatalogoDeArticulosNewCategoriaArticuloToAttach.getClass(), listaCatalogoDeArticulosNewCategoriaArticuloToAttach.getId());
                attachedListaCatalogoDeArticulosNew.add(listaCatalogoDeArticulosNewCategoriaArticuloToAttach);
            }
            listaCatalogoDeArticulosNew = attachedListaCatalogoDeArticulosNew;
            catalogoDeArticulos.setListaCategoriaCatalogo(listaCatalogoDeArticulosNew);
            catalogoDeArticulos = em.merge(catalogoDeArticulos);
            for (CategoriaDeCatalogo listaCatalogoDeArticulosOldCategoriaArticulo : listaCatalogoDeArticulosOld) {
                if (!listaCatalogoDeArticulosNew.contains(listaCatalogoDeArticulosOldCategoriaArticulo)) {
                    listaCatalogoDeArticulosOldCategoriaArticulo.setUnCatalogo(null);
                    listaCatalogoDeArticulosOldCategoriaArticulo = em.merge(listaCatalogoDeArticulosOldCategoriaArticulo);
                }
            }
            for (CategoriaDeCatalogo listaCatalogoDeArticulosNewCategoriaArticulo : listaCatalogoDeArticulosNew) {
                if (!listaCatalogoDeArticulosOld.contains(listaCatalogoDeArticulosNewCategoriaArticulo)) {
                    Catalogo oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo = listaCatalogoDeArticulosNewCategoriaArticulo.getUnCatalogo();
                    listaCatalogoDeArticulosNewCategoriaArticulo.setUnCatalogo(catalogoDeArticulos);
                    listaCatalogoDeArticulosNewCategoriaArticulo = em.merge(listaCatalogoDeArticulosNewCategoriaArticulo);
                    if (oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo != null && !oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo.equals(catalogoDeArticulos)) {
                        oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo.getListaCategoriaCatalogo().remove(listaCatalogoDeArticulosNewCategoriaArticulo);
                        oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo = em.merge(oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = catalogoDeArticulos.getId();
                if (findCatalogoDeArticulos(id) == null) {
                    throw new NonexistentEntityException("The catalogoDeArticulos with id " + id + " no longer exists.");
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
            Catalogo catalogoDeArticulos;
            try {
                catalogoDeArticulos = em.getReference(Catalogo.class, id);
                catalogoDeArticulos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalogoDeArticulos with id " + id + " no longer exists.", enfe);
            }
            List<CategoriaDeCatalogo> listaCatalogoDeArticulos = catalogoDeArticulos.getListaCategoriaCatalogo();
            for (CategoriaDeCatalogo listaCatalogoDeArticulosCategoriaArticulo : listaCatalogoDeArticulos) {
                listaCatalogoDeArticulosCategoriaArticulo.setUnCatalogo(null);
                listaCatalogoDeArticulosCategoriaArticulo = em.merge(listaCatalogoDeArticulosCategoriaArticulo);
            }
            em.remove(catalogoDeArticulos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Catalogo> findCatalogoDeArticulosEntities() {
        return findCatalogoDeArticulosEntities(true, -1, -1);
    }

    public List<Catalogo> findCatalogoDeArticulosEntities(int maxResults, int firstResult) {
        return findCatalogoDeArticulosEntities(false, maxResults, firstResult);
    }

    private List<Catalogo> findCatalogoDeArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Catalogo.class));
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

    public Catalogo findCatalogoDeArticulos(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Catalogo.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogoDeArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Catalogo> rt = cq.from(Catalogo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
