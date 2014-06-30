package view.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import model.Tag;
import model.Usuario;
import util.Utils;

@FacesConverter(value = "tagConverter", forClass = Tag.class)
public class TagConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String idString) {
        Usuario usuario = (Usuario) Utils.retornaSessao().getAttribute(Utils.USUARIO);

        if (usuario != null) {
            for (Tag tag : usuario.getListaTag()) {
                if (tag.getId() == Integer.parseInt(idString)) {
                    return tag;
                }
            }
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o) {
        return ((Tag) o).getId() + "";
    }

}
