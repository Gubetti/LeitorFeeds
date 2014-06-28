package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Pasta implements Comparable<Pasta>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idPasta")
    private Integer id;

    @Column(nullable=false)
    private String nome;

    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;
    
    @Column(nullable=false)
    private boolean pastaDefault;
    
    public Pasta() {
    }

    public Pasta(String nome, Usuario usuario) {
        this.nome = nome;
        this.usuario = usuario;
        this.pastaDefault = false;
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

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public boolean isPastaDefault() {
		return pastaDefault;
	}

	public void setPastaDefault(boolean pastaDefault) {
		this.pastaDefault = pastaDefault;
	}


    @Override
    public int compareTo(Pasta o) {
        if (o.isPastaDefault() || this.nome.compareTo(o.getNome()) > 0) {
            return 1;
        }

        if (this.nome.compareTo(o.getNome()) < 0) {
            return -1;
        }

        return 0;
    }

}
