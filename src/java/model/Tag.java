package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

@Entity
public class Tag implements Comparable<Tag>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTag")
    private int id;
    
    @Column(nullable=false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;

    @ManyToMany(mappedBy = "listaTag")
    private List<UsuarioFeed> listaUsuarioFeed;
	
    public Tag() {
    }
    
    public Tag(String nome) {
    	this.nome = nome;
    }

    public void addUsuarioFeed(UsuarioFeed usuarioFeed) {
    	if (this.listaUsuarioFeed == null) {
            this.listaUsuarioFeed = new ArrayList<UsuarioFeed>();
        }
        this.listaUsuarioFeed.add(usuarioFeed);
    }

	public List<UsuarioFeed> getListaUsuarioFeed() {
        if (listaUsuarioFeed == null) {
            return Collections.emptyList();
        } else {
            return listaUsuarioFeed;
        }
    }
    
    public void setListaUsuarioFeed(List<UsuarioFeed> listaUsuarioFeed) {
        this.listaUsuarioFeed = listaUsuarioFeed;
    }
    
    public Usuario getUsuario() {
		return usuario;
	}
    
    public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
    
	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int compareTo(Tag o) {
		if (this.nome.compareTo(o.getNome()) > 0) {
			return 1;
		}

		if (this.nome.compareTo(o.getNome()) < 0) {
			return -1;
		}
		return 0;
	}

}
