package controler;

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
    
}
