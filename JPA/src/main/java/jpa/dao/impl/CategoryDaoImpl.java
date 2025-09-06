package jpa.dao.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jpa.configs.JPAConfigs;
import jpa.dao.CategoryDao;
import jpa.entity.Category;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public void create(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(category);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Category category) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(category); // dùng merge thay vì persist
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(int id) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Category c = em.find(Category.class, id);
            if (c != null) em.remove(c);
            trans.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (trans.isActive()) trans.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public Category findById(int id) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.find(Category.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findAll() {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findAll", Category.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Category> findByUserId(int userId) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<Category> query = em.createNamedQuery("Category.findByUser", Category.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}
