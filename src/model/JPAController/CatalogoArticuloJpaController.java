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
import model.CategoriaArticulo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import model.CatalogoArticulos;
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

    public void create(CatalogoArticulos catalogoDeArticulos) {
        if (catalogoDeArticulos.getListaCatalogoDeArticulos() == null) {
            catalogoDeArticulos.setListaCatalogoDeArticulos(new ArrayList<CategoriaArticulo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<CategoriaArticulo> attachedListaCatalogoDeArticulos = new ArrayList<CategoriaArticulo>();
            for (CategoriaArticulo listaCatalogoDeArticulosCategoriaArticuloToAttach : catalogoDeArticulos.getListaCatalogoDeArticulos()) {
                listaCatalogoDeArticulosCategoriaArticuloToAttach = em.getReference(listaCatalogoDeArticulosCategoriaArticuloToAttach.getClass(), listaCatalogoDeArticulosCategoriaArticuloToAttach.getId());
                attachedListaCatalogoDeArticulos.add(listaCatalogoDeArticulosCategoriaArticuloToAttach);
            }
            catalogoDeArticulos.setListaCatalogoDeArticulos(attachedListaCatalogoDeArticulos);
            em.persist(catalogoDeArticulos);
            for (CategoriaArticulo listaCatalogoDeArticulosCategoriaArticulo : catalogoDeArticulos.getListaCatalogoDeArticulos()) {
                CatalogoArticulos oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo = listaCatalogoDeArticulosCategoriaArticulo.getUnCatalogoDeArticulos();
                listaCatalogoDeArticulosCategoriaArticulo.setUnCatalogoDeArticulos(catalogoDeArticulos);
                listaCatalogoDeArticulosCategoriaArticulo = em.merge(listaCatalogoDeArticulosCategoriaArticulo);
                if (oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo != null) {
                    oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosCategoriaArticulo.getListaCatalogoDeArticulos().remove(listaCatalogoDeArticulosCategoriaArticulo);
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

    public void edit(CatalogoArticulos catalogoDeArticulos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CatalogoArticulos persistentCatalogoDeArticulos = em.find(CatalogoArticulos.class, catalogoDeArticulos.getId());
            List<CategoriaArticulo> listaCatalogoDeArticulosOld = persistentCatalogoDeArticulos.getListaCatalogoDeArticulos();
            List<CategoriaArticulo> listaCatalogoDeArticulosNew = catalogoDeArticulos.getListaCatalogoDeArticulos();
            List<CategoriaArticulo> attachedListaCatalogoDeArticulosNew = new ArrayList<CategoriaArticulo>();
            for (CategoriaArticulo listaCatalogoDeArticulosNewCategoriaArticuloToAttach : listaCatalogoDeArticulosNew) {
                listaCatalogoDeArticulosNewCategoriaArticuloToAttach = em.getReference(listaCatalogoDeArticulosNewCategoriaArticuloToAttach.getClass(), listaCatalogoDeArticulosNewCategoriaArticuloToAttach.getId());
                attachedListaCatalogoDeArticulosNew.add(listaCatalogoDeArticulosNewCategoriaArticuloToAttach);
            }
            listaCatalogoDeArticulosNew = attachedListaCatalogoDeArticulosNew;
            catalogoDeArticulos.setListaCatalogoDeArticulos(listaCatalogoDeArticulosNew);
            catalogoDeArticulos = em.merge(catalogoDeArticulos);
            for (CategoriaArticulo listaCatalogoDeArticulosOldCategoriaArticulo : listaCatalogoDeArticulosOld) {
                if (!listaCatalogoDeArticulosNew.contains(listaCatalogoDeArticulosOldCategoriaArticulo)) {
                    listaCatalogoDeArticulosOldCategoriaArticulo.setUnCatalogoDeArticulos(null);
                    listaCatalogoDeArticulosOldCategoriaArticulo = em.merge(listaCatalogoDeArticulosOldCategoriaArticulo);
                }
            }
            for (CategoriaArticulo listaCatalogoDeArticulosNewCategoriaArticulo : listaCatalogoDeArticulosNew) {
                if (!listaCatalogoDeArticulosOld.contains(listaCatalogoDeArticulosNewCategoriaArticulo)) {
                    CatalogoArticulos oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo = listaCatalogoDeArticulosNewCategoriaArticulo.getUnCatalogoDeArticulos();
                    listaCatalogoDeArticulosNewCategoriaArticulo.setUnCatalogoDeArticulos(catalogoDeArticulos);
                    listaCatalogoDeArticulosNewCategoriaArticulo = em.merge(listaCatalogoDeArticulosNewCategoriaArticulo);
                    if (oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo != null && !oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo.equals(catalogoDeArticulos)) {
                        oldUnCatalogoDeArticulosOfListaCatalogoDeArticulosNewCategoriaArticulo.getListaCatalogoDeArticulos().remove(listaCatalogoDeArticulosNewCategoriaArticulo);
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
            CatalogoArticulos catalogoDeArticulos;
            try {
                catalogoDeArticulos = em.getReference(CatalogoArticulos.class, id);
                catalogoDeArticulos.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The catalogoDeArticulos with id " + id + " no longer exists.", enfe);
            }
            List<CategoriaArticulo> listaCatalogoDeArticulos = catalogoDeArticulos.getListaCatalogoDeArticulos();
            for (CategoriaArticulo listaCatalogoDeArticulosCategoriaArticulo : listaCatalogoDeArticulos) {
                listaCatalogoDeArticulosCategoriaArticulo.setUnCatalogoDeArticulos(null);
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

    public List<CatalogoArticulos> findCatalogoDeArticulosEntities() {
        return findCatalogoDeArticulosEntities(true, -1, -1);
    }

    public List<CatalogoArticulos> findCatalogoDeArticulosEntities(int maxResults, int firstResult) {
        return findCatalogoDeArticulosEntities(false, maxResults, firstResult);
    }

    private List<CatalogoArticulos> findCatalogoDeArticulosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CatalogoArticulos.class));
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

    public CatalogoArticulos findCatalogoDeArticulos(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CatalogoArticulos.class, id);
        } finally {
            em.close();
        }
    }

    public int getCatalogoDeArticulosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CatalogoArticulos> rt = cq.from(CatalogoArticulos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
