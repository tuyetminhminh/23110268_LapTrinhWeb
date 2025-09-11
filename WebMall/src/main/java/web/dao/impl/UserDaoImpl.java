package web.dao.impl;

import jakarta.persistence.EntityManager;
import web.configs.JPAConfigs;
import web.dao.UserDao;
import web.entity.User;

public class UserDaoImpl implements UserDao {

    @Override
    public void update(User user) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public User findByUsername(String username) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                     .setParameter("username", username)
                     .getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(User user) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
