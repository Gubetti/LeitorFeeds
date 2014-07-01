package controler;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Pasta;

public class PastaDAO extends DAO<Pasta> {

    public Pasta existePasta(Integer idUsuario, String nomePasta) {
        EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Pasta> query = manager.createQuery("select p from Pasta p where p.usuario.id = :idUsuario and p.nome like :nome", Pasta.class);
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("nome", nomePasta);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
