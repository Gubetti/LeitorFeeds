package view;

import controler.UsuarioDAO;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Usuario;

@ManagedBean
@SessionScoped
public class LoginBean {

public void teste() {
    Usuario usuario = new Usuario("TESTE", "email", "**", new Date());
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.persistir(usuario);
}
    
}
