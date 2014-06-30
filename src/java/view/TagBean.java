package view;

import controler.TagDAO;
import controler.UsuarioDAO;
import controler.UsuarioFeedDAO;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.Tag;
import model.Usuario;
import model.UsuarioFeed;

import util.Utils;

@ManagedBean(name = "tagBean")
@SessionScoped
@ViewScoped
public class TagBean {
    
    private String novaTag;
    private Tag tagSelecionada;
    
    public String getNovaTag() {
        return novaTag;
    }
    
    public void setNovaTag(String novaTag) {
        this.novaTag = novaTag;
    }
    
    public Tag getTagSelecionada() {
        return tagSelecionada;
    }
    
    public void setTagSelecionada(Tag tagSelecionada) {
        this.tagSelecionada = tagSelecionada;
    }

    /**
     * Método que pesquisa o texto digitado pelo usuário na sua lista de Tags.
     *
     * @param query Valor do texto digitado pelo usuário.
     * @return Lista de instâncias da classe Tag.
     */
    public List<Tag> autoList(String query) {
        return new TagDAO().retornaBuscaTag(((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getId(), query);
    }

    /**
     * Método que insere uma nova Tag (elimina duplicatas) para o UsuarioFeed
     * selecionado.
     *
     * @param usuarioFeed UsuarioFeed referente ao componente.
     */
    public void inserirTag(UsuarioFeed usuarioFeed) {
        usuarioFeed.setListaTag(new ArrayList<Tag>(new HashSet<Tag>(usuarioFeed.getListaTag())));
        atualizarTag(usuarioFeed);
    }

    /**
     * Método que remove uma nova Tag do UsuarioFeed selecionado.
     *
     * @param usuarioFeed UsuarioFeed referente ao componente.
     */
    public void removerTag(UsuarioFeed usuarioFeed) {
        atualizarTag(usuarioFeed);
    }
    
    private void atualizarTag(UsuarioFeed usuarioFeed) {
        new UsuarioFeedDAO().atualizar(usuarioFeed);
    }

    /**
     * Método que cria uma nova Tag (elimina duplicatas) para o Usuario atual.
     */
    public void criarTag() {
        if (novaTag != null && novaTag.trim().length() > 0) {
            Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);
            for (Tag t : usuario.getListaTag()) {
                if (t.getNome().equalsIgnoreCase(novaTag)) {
                    novaTag = "";
                    return;
                }
            }
            
            TagDAO tagDAO = new TagDAO();
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Tag tag = new Tag(novaTag);
            tag.setUsuario(usuario);
            tagDAO.persistir(tag);
            usuario.addTag(tag);
            usuarioDAO.atualizar(usuario);
            novaTag = "";
        }
    }
}
