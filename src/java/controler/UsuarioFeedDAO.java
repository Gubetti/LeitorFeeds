package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.TipoFeed;
import model.UsuarioFeed;

public class UsuarioFeedDAO extends DAO<UsuarioFeed> {

    public UsuarioFeed retornaUsuarioFeed(Integer idUsuario, Integer idFeed) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from USUARIOFEED u where u.usuario.id = :idUsuario AND u.feed.id = :idFeed", UsuarioFeed.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idFeed", idFeed);
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public List<UsuarioFeed> usuarioFeedsTipo(Integer idUsuario, TipoFeed tipoFeed) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            String select = "from USUARIOFEED u where u.usuario.id = :idUsuario";
            switch (tipoFeed) {
                case NAOLIDO:
                    select += " AND u.lido IS NULL";
                    break;
                case COMPARTILHADO:
                    select += " AND u.compartilhado IS NOT NULL";
                    break;
                case CURTIDO:
                    select += " AND u.curtido IS NOT NULL";
                    break;
                case FAVORITADO:
                    select += " AND u.notaFavoritado > 0";
            }
            TypedQuery<UsuarioFeed> query = manager.createQuery(select, UsuarioFeed.class);
            query.setParameter("idUsuario", idUsuario);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public List<UsuarioFeed> usuarioFeedsInscricao(Integer idUsuario, Integer idInscricao) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from USUARIOFEED u where u.usuario.id = :idUsuario AND u.feed.inscricao.id = :idInscricao", UsuarioFeed.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idFeed", idInscricao);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public List<UsuarioFeed> usuarioFeedsNota(Integer idUsuario, int nota) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from USUARIOFEED u where u.usuario.id = :idUsuario AND u.notafavoritado = :nota", UsuarioFeed.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("nota", nota);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public List<UsuarioFeed> usuarioFeedsTag(Integer idUsuario, Integer idTag) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<UsuarioFeed> query = manager.createQuery("from USUARIOFEED u JOIN FETCH u.listaTag lt where u.usuario.id = :idUsuario AND lt.id = :idTag", UsuarioFeed.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idTag", idTag);
            return query.getResultList();
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
