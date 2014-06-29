package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class UsuarioFeed implements Comparable<UsuarioFeed> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuarioFeed")
    private Integer id;

    @ManyToOne
    private Feed feed;

    @ManyToOne
    private Usuario usuario;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lido;
    @Temporal(TemporalType.TIMESTAMP)
    private Date compartilhado;
    @Temporal(TemporalType.TIMESTAMP)
    private Date curtido;
    private int notaFavoritado;

    @ManyToMany
    @JoinTable(name = "UsuarioFeedTag")
    private List<Tag> listaTag;

    public UsuarioFeed() {
    }

    public UsuarioFeed(Usuario usuario, Feed feed) {
        this.usuario = usuario;
        this.feed = feed;
    }

    public void addTag(Tag tag) {
        if (this.listaTag == null) {
            this.listaTag = new ArrayList<Tag>();
        }
        this.listaTag.add(tag);
    }

    public List<Tag> getListaTag() {
        if (listaTag == null) {
            listaTag = new ArrayList<Tag>();
        } else {
            Collections.sort(listaTag);

        }
        return listaTag;
    }

    public void setListaTag(List<Tag> listaTag) {
        this.listaTag = listaTag;
    }

    public Integer getId() {
        return id;
    }

    public Feed getFeed() {
        return feed;
    }

    public void setFeed(Feed feed) {
        this.feed = feed;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getLido() {
        return lido;
    }

    public void setLido(Date lido) {
        this.lido = lido;
    }

    public Date getCompartilhado() {
        return compartilhado;
    }

    public void setCompartilhado(Date compartilhado) {
        this.compartilhado = compartilhado;
    }

    public Date getCurtido() {
        return curtido;
    }

    public void setCurtido(Date curtido) {
        this.curtido = curtido;
    }

    public int getNotaFavoritado() {
        return notaFavoritado;
    }

    public void setNotaFavoritado(int notaFavoritado) {
        this.notaFavoritado = notaFavoritado;
    }

    // Ordenando por data, dos mais recentes para os mais antigos
    @Override
    public int compareTo(UsuarioFeed o) {
        if (feed.getPubDate().before(o.feed.getPubDate())) {
            return 1;
        }
        if (feed.getPubDate().after(o.feed.getPubDate())) {
            return -1;
        }
        return 0;
    }

}
