package controler;

import java.util.Date;
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
    
    public Usuario buscarUsuario(Integer id) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            return manager.find(Usuario.class, id);
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
    
    
    public static void main(String args[]) {
        Usuario usuario = new Usuario("TESTE", "email", "**", new Date());
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.persistir(usuario);
    }
}
