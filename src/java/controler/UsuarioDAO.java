package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Usuario;

public class UsuarioDAO extends DAO<Usuario> {

    public List<Usuario> listarTodos() {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u", Usuario.class);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public Usuario buscarUsuario(String email) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = manager.createQuery("select u from Usuario u where u.email like :email", Usuario.class);
            query.setParameter("email", email.toLowerCase());
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
