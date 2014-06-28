package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String senha;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date evento;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoAcesso;
    @Column(nullable = false)
    private boolean assinante;
    /* Lembrar de utilizar (já que não existe auto-relacionamento)
     * 
     * usuarioSeguidor.setListaEuSigo(usuarioSeguidos);
     * usuarioSeguidos[i].addUsuarioMeSegue(usuarioSeguidor);
     * 
     * ou, quando for apenas um
     * 
     * usuarioSeguidor.addUsuarioEuSigo(usuarioSeguido);
     * usuarioSeguido.addUsuarioMeSegue(usuarioSeguidor);
     * 
     * o mesmo ocorre para Pastas:
     * 
     * usuario.addPasta(pasta);
     * pasta.setUsuario(usuario);
     * 
     * ou a relação UsuarioFeed:
     * 
     * usuario.addUsuarioFeed(usuarioFeed);
     * feed.addUsuarioFeed(usuarioFeed);
     * 
     */

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Pasta> listaPasta;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioFeedPK.usuario",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UsuarioFeed> listaUsuarioFeed;

    @OneToMany(mappedBy = "usuario")
    private List<Tag> listaTag;

    public Usuario() {
    }

    public Usuario(String nome, String email, String senha, Date evento) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.evento = evento;
    }

    public void addPasta(Pasta pasta) {
        if (this.listaPasta == null) {
            this.listaPasta = new ArrayList<Pasta>();
        }
        this.listaPasta.add(pasta);
    }

    public void addUsuarioFeed(UsuarioFeed usuarioFeed) {
        if (this.listaUsuarioFeed == null) {
            this.listaUsuarioFeed = new ArrayList<UsuarioFeed>();
        }
        this.listaUsuarioFeed.add(usuarioFeed);
    }

    public void addTag(Tag tag) {
        if (this.listaTag == null) {
            this.listaTag = new ArrayList<Tag>();
        }
        this.listaTag.add(tag);
    }

    public Pasta getPastaDefault() {
        for (Pasta pasta : this.getListaPasta()) {
            if (pasta.isPastaDefault()) {
                return pasta;
            }
        }
        return null;
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Date getEvento() {
        return this.evento;
    }

    public void setEvento(Date evento) {
        this.evento = evento;
    }

    public Date getUltimoAcesso() {
        return ultimoAcesso;
    }

    public void setUltimoAcesso(Date ultimoAcesso) {
        this.ultimoAcesso = ultimoAcesso;
    }

    public List<Pasta> getListaPasta() {
        if (listaPasta == null) {
            return Collections.emptyList();
        } else {
            Collections.sort(listaPasta);
            return listaPasta;
        }
    }

    public void setListaPasta(List<Pasta> listaPasta) {
        this.listaPasta = listaPasta;
    }

    public List<UsuarioFeed> getListaUsuarioFeed() {
        if (listaUsuarioFeed == null) {
            return Collections.emptyList();
        } else {
            Collections.sort(listaUsuarioFeed);
            return listaUsuarioFeed;
        }
    }

    public void setListaTag(List<Tag> listaTag) {
        this.listaTag = listaTag;
    }

    public List<Tag> getListaTag() {
        if (listaTag == null) {
            return Collections.emptyList();
        } else {
            Collections.sort(listaTag);
            return listaTag;
        }
    }

    public void setListaUsuarioFeed(List<UsuarioFeed> listaUsuarioFeed) {
        this.listaUsuarioFeed = listaUsuarioFeed;
    }
}
