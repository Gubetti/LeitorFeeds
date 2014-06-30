package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Feed;
import model.Inscricao;

public class InscricaoDAO extends DAO<Inscricao> {

    public List<Inscricao> listarTodos() {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Inscricao> query = manager.createQuery("from INSCRICAO", Inscricao.class);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public Inscricao buscarInscricao(Integer id) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            return manager.find(Inscricao.class, id);
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public Inscricao existeInscricao(String caminhoURL) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Inscricao> query = manager.createQuery("from INSCRICAO i where i.caminhoURL like :caminhoURL", Inscricao.class);
            query.setParameter("caminhoURL", caminhoURL.toLowerCase());
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }

    public Feed existeFeed(Inscricao inscricao, String caminhoURL) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Feed> query = manager.createQuery("from FEED f where f.inscricao.id = :idInscricao AND f.caminhoURL like :caminhoURL", Feed.class);
            query.setParameter("idInscricao", inscricao.getId());
            query.setParameter("caminhoURL", caminhoURL);
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
