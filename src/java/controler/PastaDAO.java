package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Pasta;

public class PastaDAO extends DAO<Pasta> {

    public List<Pasta> pastasUsuario(Integer idUsuario) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Pasta> query = manager.createQuery("from PASTA p where p.idUsuario = :idUsuario", Pasta.class);
            query.setParameter("idUsuario", idUsuario);
            return query.getResultList();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
