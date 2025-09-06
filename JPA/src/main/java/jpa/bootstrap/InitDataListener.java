package jpa.bootstrap;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import jpa.configs.JPAConfigs;  
import jpa.entity.User;

@WebListener
public class InitDataListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        EntityManager em = JPAConfigs.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1) Nếu chưa có admin thì tạo
            Long countAdmin = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :u", Long.class)
                .setParameter("u", "admin")
                .getSingleResult();

            if (countAdmin == 0) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword("123");   // demo; thực tế nên dùng mật khẩu đã mã hoá
                admin.setRoleId(3);         // 3 = admin
                admin.setFullname("Administrator");
                admin.setEmail("admin@gmail.com");
                admin.setStatus(true);
                em.persist(admin);
            }

            // 2) (tuỳ chọn) seed sẵn 1 manager + 1 user để test
            Long countManager = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :u", Long.class)
                .setParameter("u", "manager")
                .getSingleResult();
            if (countManager == 0) {
                User m = new User();
                m.setUsername("manager");
                m.setPassword("123");
                m.setRoleId(2);
                m.setFullname("Manager");
                m.setEmail("manager@gmail.com");
                m.setStatus(true);
                em.persist(m);
            }

            Long countUser = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :u", Long.class)
                .setParameter("u", "user")
                .getSingleResult();
            if (countUser == 0) {
                User u = new User();
                u.setUsername("user");
                u.setPassword("123");
                u.setRoleId(1);
                u.setFullname("User");
                u.setEmail("user@gmail.com");
                u.setStatus(true);
                em.persist(u);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) { /* no-op */ }
}
