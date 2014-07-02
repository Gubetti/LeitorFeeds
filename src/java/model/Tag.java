package model;

import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Tag implements Comparable<Tag>, Serializable, CycleRecoverable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTag")
    private Integer id;
    
    @Column(nullable=false)
    private String nome;
    
    @ManyToOne
    @JoinColumn(name="idUsuario")
    private Usuario usuario;
	
    public Tag() {
    }
    
    public Tag(String nome) {
    	this.nome = nome;
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

    @Override
    public Object onCycleDetected(Context cntxt) {
        Tag tag = new Tag();
        tag.id=this.id;
        return id;
    }

}
