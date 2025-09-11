package web.configs;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import web.dao.UserDao;
import web.dao.impl.UserDaoImpl;
import web.entity.User;

@WebListener
public class AdminSeeder implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            UserDao userDao = new UserDaoImpl();
            User admin = userDao.findByUsername("admin");
            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // Đơn giản để đúng với LoginServlet hiện tại
                admin.setFullname("Administrator");
                admin.setPhone("0900000000");
                admin.setRole("admin");
                admin.setImage(null);
                userDao.save(admin);
                System.out.println("[Seeder] Created default admin: admin/admin123");
            }
        } catch (Exception e) {
            System.err.println("[Seeder] Admin seeding error: " + e.getMessage());
        }
    }
}
