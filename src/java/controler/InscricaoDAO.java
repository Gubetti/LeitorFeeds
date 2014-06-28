package controler;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Inscricao;

public class InscricaoDAO {
        public List<Inscricao> listarTodos() {
       EntityManager manager = JpaUtil.getEntityManager();
        try {
            TypedQuery<Inscricao> query = manager.createQuery("from Inscricao", Inscricao.class);
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
           TypedQuery<Inscricao> query = manager.createQuery("from Inscricao i where i.caminhoURL like :caminhoURL", Inscricao.class);
           query.setParameter("caminhoURL", caminhoURL);
            return query.getSingleResult();
        } finally {
            manager.close();
            JpaUtil.close();
        }
    }
}
