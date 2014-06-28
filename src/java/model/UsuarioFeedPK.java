package model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UsuarioFeedPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Feed feed;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            } else {
                if (!(obj instanceof UsuarioFeedPK)) {
                    return false;
                }
            }
        }

        UsuarioFeedPK that = (UsuarioFeedPK) obj;

        if (this.usuario != null ? !this.usuario.equals(that.usuario)
                : that.usuario != null) {
            return false;
        } else {
            if (this.feed != null ? !this.feed.equals(that.feed)
                    : that.feed != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (this.usuario != null ? this.usuario.hashCode() : 0);
        result = 31 * result + (this.feed != null ? this.feed.hashCode() : 0);
        return result;
    }

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

}
