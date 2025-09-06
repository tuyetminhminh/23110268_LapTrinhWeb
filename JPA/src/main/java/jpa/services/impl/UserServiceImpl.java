package jpa.services.impl;

import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jpa.configs.JPAConfigs;
import jpa.dao.UserDao;
import jpa.dao.impl.UserDaoImpl;
import jpa.entity.User;
import jpa.services.UserService;

public class UserServiceImpl implements UserService {
    private final UserDao dao = new UserDaoImpl();

    @Override
    public void insert(User user) { dao.create(user); }

    @Override
    public void update(User user) { dao.update(user); }

    @Override
    public List<User> findAll() {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách user: " + e.getMessage(), e);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    @Override
    public User findById(Integer id) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }

    @Override
    public User findByUsername(String username) { return dao.findByUsername(username); }

    @Override
    public User findByEmail(String email) { return dao.findByEmail(email); }

    @Override
    public User checkLogin(String username, String password) { return dao.checkLogin(username, password); }

    @Override
    public List<User> findUsersByRoleOrSelf(int roleId, int selfId) {
        EntityManager em = JPAConfigs.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery("SELECT u FROM User u WHERE u.roleId = :roleId OR u.id = :selfId", User.class);
            query.setParameter("roleId", roleId);
            query.setParameter("selfId", selfId);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) em.close();
        }
    }
}