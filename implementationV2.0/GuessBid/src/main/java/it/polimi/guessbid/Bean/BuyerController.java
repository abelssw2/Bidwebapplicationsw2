package it.polimi.guessbid.Bean;

import it.polimi.guessbid.Entity.Buyer;
import it.polimi.guessbid.Bean.util.JsfUtil;
import it.polimi.guessbid.Bean.util.JsfUtil.PersistAction;
import it.polimi.guessbid.Controler.BuyerFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@Named("buyerController")
@SessionScoped
public class BuyerController implements Serializable {


    @EJB private it.polimi.guessbid.Controler.BuyerFacade ejbFacade;
    private List<Buyer> items = null;
    private Buyer selected;

    public BuyerController() {
    }

    public Buyer getSelected() {
        return selected;
    }

    public void setSelected(Buyer selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BuyerFacade getFacade() {
        return ejbFacade;
    }

    public Buyer prepareCreate() {
        selected = new Buyer();
        initializeEmbeddableKey();
        selected.setCapacity(100);
        return selected;
    }

    public void create() {
        
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bid").getString("BuyerCreated"));
        if (!JsfUtil.isValidationFailed()) {
            System.out.println("invalid");
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bid").getString("BuyerUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bid").getString("BuyerDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Buyer> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bid").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/bid").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Buyer getBuyer(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Buyer> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Buyer> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=Buyer.class)
    public static class BuyerControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BuyerController controller = (BuyerController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "buyerController");
            return controller.getBuyer(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Buyer) {
                Buyer o = (Buyer) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Buyer.class.getName()});
                return null;
            }
        }

    }

}
