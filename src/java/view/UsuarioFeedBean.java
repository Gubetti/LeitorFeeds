package view;

import controler.UsuarioFeedDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Inscricao;
import model.Tag;
import model.TipoFeed;
import model.Usuario;
import model.UsuarioFeed;

import util.Utils;

@ManagedBean(name = "usuarioFeedBean")
@SessionScoped
public class UsuarioFeedBean {

    private List<UsuarioFeed> usuarioFeeds;
    private UsuarioFeed usuarioFeedSelecionado;
    private Inscricao inscricaoAtual;
    private String tituloFeed;
    private Tag tagSelecionada;
    private String notaSelecionada;
    private boolean favoritos;

    public UsuarioFeedBean() {
        usuarioFeeds = new ArrayList<UsuarioFeed>();
        mostrarTodos();
    }

    /**
     * Método chamado quando o botão compartilhar é pressionado. Atribuí ao
     * UsuarioFeed selecionado a categoria compartilhado.
     */
    public void compartilhar() {
        if (usuarioFeedSelecionado.getCompartilhado() == null) {
            usuarioFeedSelecionado.setCompartilhado(new Date());
        } else {
            usuarioFeedSelecionado.setCompartilhado(null);
        }

        new UsuarioFeedDAO().atualizar(usuarioFeedSelecionado);
    }

    /**
     * Método chamado quando o botão curtir é pressionado. Atribuí ao
     * UsuarioFeed selecionado a categoria curtido.
     */
    public void curtir() {
        if (usuarioFeedSelecionado.getCurtido() == null) {
            usuarioFeedSelecionado.setCurtido(new Date());
        } else {
            usuarioFeedSelecionado.setCurtido(null);
        }
        new UsuarioFeedDAO().atualizar(usuarioFeedSelecionado);
    }

    /**
     * Método chamado quando o botão Favoritar é pressionado. Atribuí ao
     * UsuarioFeed selecionado a categoria favoritado, junto com sua respectiva
     * nota.
     *
     * @param usuarioFeed
     */
    public void favoritar(UsuarioFeed usuarioFeed) {
        new UsuarioFeedDAO().atualizar(usuarioFeed);
    }

    /**
     * Método chamado quando o botão Lido é pressionado. Atribuí ao UsuarioFeed
     * selecionado a categoria lido.
     */
    public void ler() {
        if (usuarioFeedSelecionado.getLido() == null) {
            usuarioFeedSelecionado.setLido(new Date());
        } else {
            usuarioFeedSelecionado.setLido(null);
        }

        new UsuarioFeedDAO().atualizar(usuarioFeedSelecionado);
    }

    /**
     * Método que filtra o UsuarioFeed para a categoria NAOLIDO.
     * @return 
     */
    public String mostrarTodos() {
        favoritos = false;
        setarFiltro(TipoFeed.NAOLIDO);
        return "principal?faces-redirect=true";
    }

    /**
     * Método que filtra o UsuarioFeed para a categoria COMPARTILHADO.
     * @return 
     */
    public String mostrarCompartilhados() {
        favoritos = false;
        setarFiltro(TipoFeed.COMPARTILHADO);
        return "feeds/principal?faces-redirect=true";
    }

    /**
     * Método que filtra o UsuarioFeed para a categoria CURTIDO.
     * @return 
     */
    public String mostrarCurtidos() {
        favoritos = false;
        setarFiltro(TipoFeed.CURTIDO);
        return "feeds/principal?faces-redirect=true";
    }

    /**
     * Método que filtra o UsuarioFeed para a categoria FAVORITADO.
     */
    public String mostrarFavoritados() {
        favoritos = true;
        notaSelecionada = "0";
        setarFiltro(TipoFeed.FAVORITADO);
        return "feeds/principal?faces-redirect=true";
    }

    /**
     * Método que filtra o UsuarioFeed com uma Tag em específico.
     * @return 
     */
    public String filtrarTag() {
        favoritos = false;
        tituloFeed = "Feeds com a Tag: " + tagSelecionada.getNome();
        usuarioFeeds = new UsuarioFeedDAO().usuarioFeedsTag(
                ((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(),
                tagSelecionada.getId());
        if (usuarioFeeds == null) {
            usuarioFeeds = new ArrayList<UsuarioFeed>();
        }
        tagSelecionada = null;
        return "feeds/principal?faces-redirect=true";
    }

    /**
     * Método que filtra o UsuarioFeed somente de uma Inscricao selecionada.
     * @return 
     */
    public String filtrarInscricao() {
        favoritos = false;
        tituloFeed = inscricaoAtual.getNome();
        usuarioFeeds = new UsuarioFeedDAO().usuarioFeedsInscricao(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), inscricaoAtual.getId());
        if (usuarioFeeds == null) {
            usuarioFeeds = new ArrayList<UsuarioFeed>();
        }
        return "feeds/principal?faces-redirect=true";
    }

    public void filtrarNota() {
        UsuarioFeedDAO usuarioFeedDAO = new UsuarioFeedDAO();
        switch (Integer.parseInt(notaSelecionada)) {
            case 0:
                mostrarFavoritados();
                break;
            case 1:
                usuarioFeeds = usuarioFeedDAO.usuarioFeedsNota(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), 1);
                break;
            case 2:
                usuarioFeeds = usuarioFeedDAO.usuarioFeedsNota(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), 2);
                break;
            case 3:
                usuarioFeeds = usuarioFeedDAO.usuarioFeedsNota(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), 3);
                break;
            case 4:
                usuarioFeeds = usuarioFeedDAO.usuarioFeedsNota(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), 4);
                break;
            case 5:
                usuarioFeeds = usuarioFeedDAO.usuarioFeedsNota(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), 5);
        }
        if (usuarioFeeds == null) {
            usuarioFeeds = new ArrayList<UsuarioFeed>();
        }
    }

    private void setarFiltro(TipoFeed tipo) {
        switch (tipo) {
            case NAOLIDO:
                tituloFeed = "Todos os Feeds";
                break;
            case COMPARTILHADO:
                tituloFeed = "Feeds Compartilhados";
                break;
            case CURTIDO:
                tituloFeed = "Feeds Curtidos";
                break;
            case FAVORITADO:
                tituloFeed = "Feeds Favoritados";
        }
        usuarioFeeds = new UsuarioFeedDAO().usuarioFeedsTipo(
                ((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(),
                tipo);
        if (usuarioFeeds == null) {
            usuarioFeeds = new ArrayList<UsuarioFeed>();
        }

    }

    public List<UsuarioFeed> getUsuarioFeeds() {
        return usuarioFeeds;
    }

    public void setUsuarioFeeds(List<UsuarioFeed> usuarioFeeds) {
        this.usuarioFeeds = usuarioFeeds;
    }

    public UsuarioFeed getUsuarioFeedSelecionado() {
        return usuarioFeedSelecionado;
    }

    public void setUsuarioFeedSelecionado(UsuarioFeed usuarioFeedSelecionado) {
        this.usuarioFeedSelecionado = usuarioFeedSelecionado;
    }

    public Inscricao getInscricaoAtual() {
        return inscricaoAtual;
    }

    public void setInscricaoAtual(Inscricao inscricaoAtual) {
        this.inscricaoAtual = inscricaoAtual;
    }

    public String getTituloFeed() {
        return tituloFeed;
    }

    public void setTituloFeed(String tituloFeed) {
        this.tituloFeed = tituloFeed;
    }

    public Tag getTagSelecionada() {
        return tagSelecionada;
    }

    public void setTagSelecionada(Tag tagSelecionada) {
        this.tagSelecionada = tagSelecionada;
    }

    public String getNotaSelecionada() {
        return notaSelecionada;
    }

    public void setNotaSelecionada(String notaSelecionada) {
        this.notaSelecionada = notaSelecionada;
    }

    public boolean isFavoritos() {
        return favoritos;
    }

    public void setFavoritos(boolean favoritos) {
        this.favoritos = favoritos;
    }
}
