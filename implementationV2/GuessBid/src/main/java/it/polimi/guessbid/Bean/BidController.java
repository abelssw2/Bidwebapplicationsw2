package it.polimi.guessbid.Bean;

import it.polimi.guessbid.Entity.Bid;
import it.polimi.guessbid.Bean.util.JsfUtil;
import it.polimi.guessbid.Bean.util.JsfUtil.PersistAction;
import it.polimi.guessbid.Controler.BidFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;


@Named("bidController")
@ApplicationScoped
public class BidController implements Serializable {
 
    @EJB private it.polimi.guessbid.Controler.BidFacade ejbFacade;
    
    private List<Bid> items = null;
     
    private Bid selected;

    public BidController() {
    }

    public Bid getSelected() {
        return selected;
    }

    public void setSelected(Bid selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private BidFacade getFacade() {
        return ejbFacade;
    }

    public Bid prepareCreate() {
        selected = new Bid();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        System.out.println(selected.getBidPrice());
      
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bid").getString("BidCreated"));
       
        if (!JsfUtil.isValidationFailed()) {
             System.out.println("invalid");
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bid").getString("BidUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bid").getString("BidDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Bid> getItems() {
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
                     System.out.println("1111111111111111 saved");
                    
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                System.out.println(ex);
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
        }else{
             System.out.println("not savvedd");
        }
    }

    public Bid getBid(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Bid> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Bid> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass=Bid.class)
    public static class BidControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            BidController controller = (BidController)facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "bidController");
            return controller.getBid(getKey(value));
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
            if (object instanceof Bid) {
                Bid o = (Bid) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Bid.class.getName()});
                return null;
            }
        }

    }

}
