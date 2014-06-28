package model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class PastaInscricaoPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Pasta pasta;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Inscricao inscricao;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            } else {
                if (!(obj instanceof PastaInscricaoPK)) {
                    return false;
                }
            }
        }

        PastaInscricaoPK that = (PastaInscricaoPK) obj;

        if (this.pasta != null ? !this.pasta.equals(that.pasta)
                : that.pasta != null) {
            return false;
        } else {
            if (this.inscricao != null ? !this.inscricao.equals(that.inscricao)
                    : that.inscricao != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        result = (this.pasta != null ? this.pasta.hashCode() : 0);
        result = 31 * result + (this.inscricao != null ? this.inscricao.hashCode() : 0);
        return result;
    }

	public Pasta getPasta() {
		return pasta;
	}

	public void setPasta(Pasta pasta) {
		this.pasta = pasta;
	}

	public Inscricao getInscricao() {
		return inscricao;
	}

	public void setInscricao(Inscricao inscricao) {
		this.inscricao = inscricao;
	}

}
