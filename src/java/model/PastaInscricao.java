package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PastaInscricao implements Comparable<PastaInscricao> {

    @Id
    @Column(name = "idPastaInscricao")
    private Integer id;

    @ManyToOne
    private Pasta pasta;

    @ManyToOne
    private Inscricao inscricao;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date evento;

    public Integer getId() {
        return id;
    }
    
    public Pasta getPasta() {
        return pasta;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public Date getEvento() {
        return evento;
    }

    public void setEvento(Date evento) {
        this.evento = evento;
    }

    @Override
    public int compareTo(PastaInscricao o) {
        if (this.getInscricao().getNome()
                .compareTo(o.getInscricao().getNome()) > 0) {
            return 1;
        }

        if (this.getInscricao().getNome()
                .compareTo(o.getInscricao().getNome()) < 0) {
            return -1;
        }

        return 0;
    }

}
