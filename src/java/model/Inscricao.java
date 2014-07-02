package model;

import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Inscricao implements Serializable, CycleRecoverable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInscricao")
    private Integer id;

    @Column(nullable = false)
    private String caminhoURL;

    @Column(nullable = false)
    private String nome;

    @OneToMany(mappedBy = "inscricao", fetch = FetchType.LAZY)
    private List<Feed> listaFeed;

    public Inscricao() {
    }

    public Inscricao(String caminhoURL, String nome) {
        this.caminhoURL = caminhoURL;
        this.nome = nome;
    }

    public void addFeed(Feed feed) {
        if (this.listaFeed == null) {
            this.listaFeed = new ArrayList<Feed>();
        }
        this.listaFeed.add(feed);
    }

    public int getId() {
        return this.id;
    }

    public String getCaminhoURL() {
        return this.caminhoURL;
    }

    public void setCaminhoURL(String caminhoURL) {
        this.caminhoURL = caminhoURL;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Feed> getListaFeed() {
        if (listaFeed == null) {
            return Collections.emptyList();
        } else {
            Collections.sort(listaFeed);
            return listaFeed;
        }
    }

    public void setListaFeed(List<Feed> listaFeed) {
        this.listaFeed = listaFeed;
    }

    @Override
    public Object onCycleDetected(Context cntxt) {
        Inscricao inscricao = new Inscricao();
        inscricao.id = this.id;
        return inscricao;
    }
}
