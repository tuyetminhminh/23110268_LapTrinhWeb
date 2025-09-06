package jpa.configs;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAConfigs {
    private static final String PERSISTENCE_UNIT_NAME = "dataSource";
    private static EntityManagerFactory factory;

    static {
        try {
            factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            if (factory == null) {
                throw new RuntimeException("Không thể tạo EntityManagerFactory cho đơn vị kiên trì: " + PERSISTENCE_UNIT_NAME);
            }
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khởi tạo EntityManagerFactory: " + e.getMessage(), e);
        }
    }

    public static EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            throw new RuntimeException("EntityManagerFactory không khả dụng");
        }
        return factory.createEntityManager();
    }

    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}