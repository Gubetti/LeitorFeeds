package services;

import controler.UsuarioFeedDAO;
import java.util.List;
import javax.jws.WebService;
import model.UsuarioFeed;

@WebService
public class FeedsService {
    
    public List<UsuarioFeed> getFeedsCompartilhadosPeloUsuario(String usuario) {
        UsuarioFeedDAO usuarioFeedDAO = new UsuarioFeedDAO();
        return usuarioFeedDAO.compartilhadosPeloUsuario(usuario);
    }
}