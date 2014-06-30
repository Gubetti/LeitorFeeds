package view;

import controler.DAO;
import controler.PastaDAO;
import controler.UsuarioDAO;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import model.Inscricao;
import model.Pasta;
import model.PastaInscricao;
import model.Usuario;
import util.Utils;

@ManagedBean(name = "pastaBean")
@SessionScoped
@ViewScoped
public class PastaBean {

    private List<Pasta> pastas;

    private Pasta pastaSelecionada;
    private PastaInscricao pastaInscricaoSelecionada;

    private String novaPasta;

    public PastaInscricao getPastaInscricaoSelecionada() {
        return pastaInscricaoSelecionada;
    }

    public void setPastaInscricaoSelecionada(PastaInscricao pastaInscricaoSelecionada) {
        this.pastaInscricaoSelecionada = pastaInscricaoSelecionada;
    }

    public Pasta getPastaSelecionada() {
        return pastaSelecionada;
    }

    /**
     * Método set que move uma inscrição da pasta atual para a pasta
     * selecionada.
     *
     * @param pastaSelecionada Pasta para onde a inscrição é movida.
     */
    public void setPastaSelecionada(Pasta pastaSelecionada) {
        this.pastaSelecionada = pastaSelecionada;

        PastaDAO pastaDAO = new PastaDAO();
        DAO<PastaInscricao> daoPastaInscricao = new DAO<PastaInscricao>();
        
        Inscricao inscricao = pastaInscricaoSelecionada.getInscricao();

        daoPastaInscricao.excluir(pastaInscricaoSelecionada);

        PastaInscricao pastaInscricao = new PastaInscricao(inscricao, new Date());
        this.pastaSelecionada.addPastaInscricao(pastaInscricao);
        pastaDAO.atualizar(pastaSelecionada);
        
        daoPastaInscricao.persistir(pastaInscricao);
    }

    /**
     * Método get que carrega as pastas do usuário logado.
     *
     * @return Retorna a lista de pastas do usuário logado.
     */
    public List<Pasta> getPastas() {
        carregarPastas();
        return pastas;
    }

    public void setPastas(List<Pasta> pastas) {
        this.pastas = pastas;
    }

    private void carregarPastas() {
        pastas = ((Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO)).getListaPasta();
    }

    /**
     * Método que cria uma nova pasta para o usuário logado.
     */
    public void addPasta() {
        PastaDAO pastaDAO = new PastaDAO();
        if (novaPasta != null && novaPasta.trim().length() > 0) {
           Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);
            if(pastaDAO.existePasta(usuario.getId(), novaPasta) != null) {
                
                return;
            }
            
            Pasta pasta = new Pasta();
            pasta.setNome(novaPasta);
            pasta.setUsuario(usuario);

            pastaDAO.persistir(pasta);
            usuario.addPasta(pasta);
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.atualizar(usuario);
            pastas = usuario.getListaPasta();
            novaPasta = "";
        }
    }

    public String getNovaPasta() {
        return novaPasta;
    }

    public void setNovaPasta(String novaPasta) {
        this.novaPasta = novaPasta;
    }
}
