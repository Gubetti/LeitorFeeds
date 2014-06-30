package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Pasta;

public class PastaDAO extends DAO<Pasta> {

    public Pasta existePasta(Integer idUsuario, String nomePasta) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Pasta> query = manager.createQuery("from PASTA p where p.usuario.id = :idUsuario and p.nome like :nome", Pasta.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("nome", nomePasta);
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
