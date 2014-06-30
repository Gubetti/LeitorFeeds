package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Feed;
import model.Usuario;
import model.UsuarioFeed;

public class UsuarioFeedDAO extends DAO<UsuarioFeed>{
    
    public UsuarioFeed retornaUsuarioFeed(Usuario usuario, Feed feed) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from USUARIOFEED u where u.idusuario = :idUsuario and u.idFeed = :idFeed", UsuarioFeed.class);
            query.setParameter("idUsuario", usuario.getId());
            query.setParameter("idFeed", feed.getId());
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
    
    public List<UsuarioFeed> compartilhadosPeloUsuario(String email) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from UsuarioFeed u "
                    + "where u.compartilhado is not null and u.usuario.email = :email",
                    UsuarioFeed.class);
            query.setParameter("email", email);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
