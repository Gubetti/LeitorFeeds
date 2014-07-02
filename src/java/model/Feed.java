package model;

import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
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
public class Feed implements Comparable<Feed>, Serializable, CycleRecoverable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFeed")
    private Integer id;
    private String titulo;
    @Column(nullable = false)
    private String caminhoURL; // além de ser utilizado como link para o usuário, será usado para verificar se o Feed já existe
    private String autor;
    @Column(length = 5000)
    private String conteudo;
    @Temporal(TemporalType.DATE)
    private Date pubDate;

    @ManyToOne
    @JoinColumn(name = "idInscricao")
    private Inscricao inscricao;

    public Feed() {
    }

    public Feed(String titulo, String caminhoURL, String autor, String conteudo, Date pubDate) {
        this.titulo = titulo;
        this.caminhoURL = caminhoURL;
        this.autor = autor;
        this.conteudo = conteudo;
        this.pubDate = pubDate;
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCaminhoURL() {
        return caminhoURL;
    }

    public void setCaminhoURL(String caminhoURL) {
        this.caminhoURL = caminhoURL;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public Inscricao getInscricao() {
        return inscricao;
    }

    public void setInscricao(Inscricao inscricao) {
        this.inscricao = inscricao;
    }

    @Override
    public int compareTo(Feed o) {
        if (pubDate.before(o.getPubDate())) {
            return 1;
        }
        if (pubDate.after(o.getPubDate())) {
            return -1;
        }
        return 0;
    }

    @Override
    public Object onCycleDetected(Context cntxt) {
        Feed feed = new Feed();
        feed.id = this.id;
        return feed;
    }
}
