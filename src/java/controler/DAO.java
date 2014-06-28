package controler;

import javax.persistence.EntityManager;

public class DAO <T> {
    
    public void persistir(T objeto) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
    
    public void atualizar(T objeto) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
            JpaUtil.close();
        }      
    }
    
    public void excluir(T objeto) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            manager.getTransaction().begin();
            manager.remove(objeto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
        } finally {
            manager.close();
            JpaUtil.close();
        }  
    }
}
