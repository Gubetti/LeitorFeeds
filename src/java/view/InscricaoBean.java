package view;

import controler.DAO;
import controler.InscricaoDAO;
import java.io.IOException;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import model.Inscricao;
import model.Pasta;
import model.PastaInscricao;
import model.Usuario;

import util.ParseFeed;
import util.Utils;

@ManagedBean(name = "inscricaoBean")
@SessionScoped
@ViewScoped
public class InscricaoBean {

    private String url;

    /**
     * Método que inclui uma nova inscrição para o usuário logado.
     */
    public void incluirInscricao() {
        Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);
        if(!usuario.isAssinante() && new InscricaoDAO().listaPastaInscricoes(usuario.getPastaDefault().getId()).size() > 3) {
            return;
        }
        
        if (!url.startsWith("http://")) {
            url = "http://" + url;
        }
        if (!ParseFeed.validarUrl(url)) {
            FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Endereço informado inválido.", "");
            FacesContext.getCurrentInstance().addMessage(null, erro);
            return;
        }

        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        Inscricao inscricao = inscricaoDAO.existeInscricao(url);
        if (inscricao == null) {
            inscricao = new Inscricao(url.toLowerCase(), "Inscrição");
            inscricaoDAO.persistir(inscricao);
            try {
                ParseFeed.converterFeed(inscricao);
            } catch (IOException e) {
                FacesMessage erro = new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "O seguinte erro ocorreu durante a adição da inscrição: " + e.getMessage(), "");
                FacesContext.getCurrentInstance().addMessage(null, erro);
                e.printStackTrace();
                return;
            }
        }

        Pasta pastaDefault = usuario.getPastaDefault();

        PastaInscricao pastaInscricao = new PastaInscricao(pastaDefault, inscricao, new Date());
        DAO<PastaInscricao> daoPastaInscricao = new DAO<PastaInscricao>();
        daoPastaInscricao.persistir(pastaInscricao);

        ParseFeed.atualizarFeedUsuario();
        url = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
