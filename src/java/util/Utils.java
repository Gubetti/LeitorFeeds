package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
}
