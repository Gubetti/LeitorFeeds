package model;

import java.util.Date;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@AssociationOverrides({
        @AssociationOverride(name = "pastaInscricaoPK.pasta", joinColumns = @JoinColumn(name = "idPasta")),
        @AssociationOverride(name = "pastaInscricaoPK.inscricao", joinColumns = @JoinColumn(name = "idInscricao")) })
public class PastaInscricao implements Comparable<PastaInscricao>{

    @EmbeddedId
    private PastaInscricaoPK pastaInscricaoPK;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date evento;
    
    public PastaInscricao() {
	}

	public PastaInscricao(Date evento, PastaInscricaoPK pastaInscricaoPK) {
		this.evento = evento;
		this.pastaInscricaoPK = pastaInscricaoPK;
	}

	public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
        }

        PastaInscricao that = (PastaInscricao) obj;

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
    public Pasta getPasta() {
        return getPk().getPasta();
    }

    @Transient
    public Inscricao getInscricao() {
        return getPk().getInscricao();
    }

    public PastaInscricaoPK getPk() {
		return pastaInscricaoPK;
	}    

	public Date getEvento() {
		return evento;
	}

	public void setEvento(Date evento) {
		this.evento = evento;
	}
	
	public PastaInscricaoPK getPastaInscricaoPK() {
		return pastaInscricaoPK;
	}

	public void setPastaInscricaoPK(PastaInscricaoPK pastaInscricaoPK) {
		this.pastaInscricaoPK = pastaInscricaoPK;
	}

	@Override
	public int compareTo(PastaInscricao o) {
		if (this.pastaInscricaoPK.getInscricao().getNome()
				.compareTo(o.pastaInscricaoPK.getInscricao().getNome()) > 0) {
			return 1;
		}

		if (this.pastaInscricaoPK.getInscricao().getNome()
				.compareTo(o.pastaInscricaoPK.getInscricao().getNome()) < 0) {
			return -1;
		}

		return 0;
	}

}
