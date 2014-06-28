package controler;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

    private static EntityManagerFactory factory = null;
    private static final String NOME_BASE = "DBMY_167651";
    
    private JpaUtil() {
    }
    
    public static EntityManager getEntityManager() {
        if (factory == null || !factory.isOpen()) {
            factory = Persistence.createEntityManagerFactory(NOME_BASE);
        }
        return factory.createEntityManager();
    }
    
    public static void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
    
    
}
