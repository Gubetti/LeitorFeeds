package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Tag;

public class TagDAO extends DAO<Tag> {

    public List<Tag> retornaBuscaTag(int idUsuario, String busca) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Tag> query = manager.createQuery("select t from Tag t where t.usuario.id = :idUsuario AND t.nome like :busca", Tag.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("busca", busca + "%");
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
