package view;

import controler.PastaDAO;
import controler.UsuarioDAO;
import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Pasta;
import model.Usuario;

import util.ParseFeed;
import util.Utils;

@ManagedBean(name = "loginBean")
@SessionScoped
@ViewScoped
public class LoginBean {

    private String email;
    private String senha;
    private String confirmeSenha;
    private String nome;
    private String senhaAtual;

    /**
     * Método que cadastra um novo usuário no sistema.
     */
    public void cadastrar() {
        if (!senha.equalsIgnoreCase(confirmeSenha)) {
            FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Campos de senha com valores diferentes.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            setSenha("");
            setConfirmeSenha("");
            return;
        }

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        if (usuarioDAO.buscarUsuario(email) != null) {
            FacesMessage erro = new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Email já existente.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            setSenha("");
            setConfirmeSenha("");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(Utils.encriptar(senha));
        usuario.setNome(nome);
        usuario.setEvento(new Date());

        Pasta pasta = new Pasta();
        pasta.setNome("Geral");
        pasta.setPastaDefault(true);
        pasta.setUsuario(usuario);
        usuario.addPasta(pasta);
        usuarioDAO.persistir(usuario);

        setEmail("");
        setSenha("");
        setConfirmeSenha("");
        setNome("");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                "Cadastrado efetuado com sucesso.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    /**
     * Método que autentica um usuário no sistema.
     *
     * @return Verdadeiro se usuário foi autenticado, falso se o contrário.
     */
    public boolean autenticar() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.buscarUsuario(email);

        if (usuario != null) {
            if (Utils.decriptar(usuario.getSenha(), senha)) {
                Utils.retornaSessao().setAttribute(Utils.USUARIO, usuario);
                ParseFeed.atualizarFeedUsuario();
                try {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("principal.jsf");
                    usuario.setUltimoAcesso(new Date());
                    usuarioDAO.atualizar(usuario);
                    setNome(usuario.getNome());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }
            FacesMessage erro = new FacesMessage(
                    FacesMessage.SEVERITY_FATAL, "Senha incorreta.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            setSenha("");
            return false;

        }
        FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                "Usuário inválido.", "");
        FacesContext.getCurrentInstance().addMessage(null, erro);
        setEmail("");
        setSenha("");
        return false;
    }

    /**
     * Método que altera os dados cadastrais de um usuário no sistema.
     */
    public void alterarDados() {
        Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        if (Utils.decriptar(usuario.getSenha(), senhaAtual)) {
            Usuario usu = usuarioDAO.buscarUsuario(email);
            if (usu != null && usuario.getId() != usu.getId()) {
                FacesMessage erro = new FacesMessage(
                        FacesMessage.SEVERITY_FATAL, "Email já existente.",
                        "");
                FacesContext.getCurrentInstance().addMessage(null, erro);
                setSenhaAtual("");
                return;
            }
            usuario.setEmail(email);
            usuario.setNome(nome);
            usuarioDAO.atualizar(usuario);
            Utils.retornaSessao().setAttribute(Utils.USUARIO, usuario);

            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Dados alterados com sucesso.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Senha incorreta.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
        }

        setSenhaAtual("");
    }

    /**
     * Método que altera a senha de um usuário no sistema.
     */
    public void alterarSenha() {
        if (!senha.equals(confirmeSenha)) {
            FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Campos de nova senha com valores diferentes.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            setSenha("");
            setConfirmeSenha("");
            setSenhaAtual("");
            return;
        }

        Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);
        if (Utils.decriptar(usuario.getSenha(), senhaAtual)) {
            usuario.setSenha(Utils.encriptar(senha));
            new UsuarioDAO().atualizar(usuario);

            setSenha("");
            setConfirmeSenha("");
            setSenhaAtual("");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Senha alterada com sucesso.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Senha atual incorreta.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            setSenhaAtual("");
        }
    }

    /**
     * Método chamado para realizar o logout do sistema.
     *
     * @return Retorna a página a ser redirecionada, neste caso, a página index.
     */
    public String sair() {
        Utils.retornaSessao().invalidate();
        return "/index?faces-redirect=true";
    }

    public void perfil() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("perfil.jsf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmeSenha() {
        return confirmeSenha;
    }

    public void setConfirmeSenha(String confirmeSenha) {
        this.confirmeSenha = confirmeSenha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }
}
