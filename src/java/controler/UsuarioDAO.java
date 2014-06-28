package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Usuario;

public class UsuarioDAO extends DAO<Usuario>{
    
    public List<Usuario> listarTodos() {
       EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Usuario> query = manager.createQuery("from Usuario", Usuario.class);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
    
}
