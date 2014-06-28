package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "usuarioFeedPK.usuario", joinColumns = @JoinColumn(name = "idUsuario")),
        @AssociationOverride(name = "usuarioFeedPK.feed", joinColumns = @JoinColumn(name = "idFeed")) })
public class UsuarioFeed implements Comparable<UsuarioFeed>{

    @EmbeddedId
    private UsuarioFeedPK usuarioFeedPK;

    private Date lido;
	private Date compartilhado;
	private Date curtido;
	private int notaFavoritado;

    @ManyToMany
    @JoinTable(name = "UsuarioFeedTag")
    private List<Tag> listaTag;

    public UsuarioFeed() {
	}

	public UsuarioFeed(UsuarioFeedPK usuarioFeedPK) {
		this.usuarioFeedPK = usuarioFeedPK;
	}

	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
        }

        UsuarioFeed that = (UsuarioFeed) obj;

        if (getPk() != null ? !getPk().equals(that.getPk())
                : that.getPk() != null) {
            return false;
        }

        return true;
    }

    public int hashCode() {
        return (getPk() != null ? getPk().hashCode() : 0);
    }

    @Transient
    public Usuario getUsuario() {
        return getPk().getUsuario();
    }

    @Transient
    public Feed getFeed() {
        return getPk().getFeed();
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

    public UsuarioFeedPK getPk() {
		return usuarioFeedPK;
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
		 if (usuarioFeedPK.getFeed().getPubDate().before(o.getFeed().getPubDate())) {
			 return 1;
	     }
		 if (usuarioFeedPK.getFeed().getPubDate().after(o.getFeed().getPubDate())) {
			 return -1;
	     }
		return 0;
	}

}
