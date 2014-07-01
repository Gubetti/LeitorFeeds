package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class PastaInscricao implements Comparable<PastaInscricao> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPastaInscricao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idInscricao")
    private Inscricao inscricao;

    @ManyToOne
    @JoinColumn(name = "idPasta")
    private Pasta pasta;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date evento;

    public PastaInscricao() {
    }

    public PastaInscricao(Pasta pasta, Inscricao inscricao, Date evento) {
       this.pasta = pasta;
        this.inscricao = inscricao;
        this.evento = evento;
    }

    public Integer getId() {
        return id;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    public Pasta getPasta() {
        return pasta;
    }

    public void setPasta(Pasta pasta) {
        this.pasta = pasta;
    }

    public Date getEvento() {
        return evento;
    }

    public void setEvento(Date evento) {
        this.evento = evento;
    }

    @Override
    public int compareTo(PastaInscricao o) {
        if (this.inscricao.getNome()
                .compareTo(o.inscricao.getNome()) > 0) {
            return 1;
        }

        if (this.inscricao.getNome()
                .compareTo(o.inscricao.getNome()) < 0) {
            return -1;
        }

        return 0;
    }

}
