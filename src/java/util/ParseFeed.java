package util;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.Feed;
import model.Inscricao;
import model.Pasta;
import model.PastaInscricao;
import model.TipoFeed;
import model.Usuario;
import model.UsuarioFeed;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import controler.DAO;
import controler.InscricaoDAO;

public class ParseFeed {

    public static void converterFeed(Inscricao inscricao) throws IOException {
        URL url = new URL(inscricao.getCaminhoURL());
        XmlReader reader = null;
        InscricaoDAO inscricaoDAO = new InscricaoDAO();
        DAO<Feed> daoFeed = new DAO<Feed>();
        try {
            reader = new XmlReader(url);
            SyndFeed syndFeed = new SyndFeedInput().build(reader);
            if (inscricao.getNome().equalsIgnoreCase("Inscrição")) {
                inscricao.setNome(syndFeed.getTitle());
            }

            int j = 0;
            for (@SuppressWarnings("rawtypes") Iterator i = syndFeed.getEntries().iterator(); i.hasNext();) {
                j++;
                SyndEntry entry = (SyndEntry) i.next();
                Feed feed = null;//existeFeed(inscricao, entry.getUri(), entry.getPublishedDate().getTime());
                if (feed == null) {
                    Date pubDate = entry.getPublishedDate();
                    if (entry.getUpdatedDate() != null && entry.getUpdatedDate().after(entry.getPublishedDate())) {
                        pubDate = entry.getUpdatedDate();
                    }
                    feed = new Feed(entry.getTitle(), entry.getUri(), entry.getAuthor(), entry.getDescription().getValue(), pubDate);
                    inscricao.addFeed(feed);
                    feed.setInscricao(inscricao);
                    daoFeed.persistir(feed);
                } else {
                    if (entry.getUpdatedDate() != null && entry.getUpdatedDate().after(feed.getPubDate())) {
                        feed.setPubDate(entry.getUpdatedDate());
                        feed.setTitulo(entry.getTitle());
                        feed.setAutor(entry.getAuthor());
                        feed.setConteudo(entry.getDescription().getValue());
                        daoFeed.atualizar(feed);
                    }
                }
                if (j == 20) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        inscricaoDAO.atualizar(inscricao);
    }

    public static boolean validarUrl(String caminho) {
        try {
            XmlReader reader = new XmlReader(new URL(caminho));
            new SyndFeedInput().build(reader);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
