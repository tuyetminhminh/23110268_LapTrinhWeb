package web.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfigs {

    private static final String PERSISTENCE_UNIT_NAME = "dataSource";
    private static EntityManagerFactory factory;

    private static synchronized void init() {
        if (factory == null || !factory.isOpen()) {
            try {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            } catch (Exception e) {
                throw new RuntimeException("Không khởi tạo được JPA EMF: " + e.getMessage(), e);
            }
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            init();
        }
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}
