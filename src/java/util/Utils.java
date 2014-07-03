package util;

import controler.InscricaoDAO;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Inscricao;

public class Utils {

    public static final String USUARIO = "usuario";

    public static String encriptar(String valor) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(valor.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            return hash.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return valor;
    }

    public static boolean decriptar(String valorEncriptado, String valor) {
        if (valorEncriptado.equals(encriptar(valor))) {
            return true;
        }
        return false;
    }

    public static HttpSession retornaSessao() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }

    public static void atualizarInscricoes() {
        for (Inscricao inscricao : new InscricaoDAO().listarTodos()) {
            try {
                ParseFeed.converterFeed(inscricao);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                atualizarInscricoes();
            }
        }, 0, 60000 * 30);
    }
}
