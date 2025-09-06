package jpa.services.impl;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa.configs.JPAConfigs;
import jpa.dao.CategoryDao;
import jpa.dao.impl.CategoryDaoImpl;
import jpa.entity.Category;
import jpa.services.CategoryService;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao dao = new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery("SELECT c FROM Category c", Category.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(Integer id) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void insert(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(category);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public List<Category> findByUserId(Integer id) {
        return dao.findByUserId(id);
    }
}
