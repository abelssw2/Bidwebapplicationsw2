package it.polimi.guessbid.Bean;

import it.polimi.guessbid.Entity.Useraccount;
import it.polimi.guessbid.Bean.util.JsfUtil;
import it.polimi.guessbid.Bean.util.JsfUtil.PersistAction;
import it.polimi.guessbid.Controler.UseraccountFacade;
import it.polimi.guessbid.Users;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("useraccountController")
@SessionScoped
public class UseraccountController implements Serializable {

    @EJB
    private it.polimi.guessbid.Controler.UseraccountFacade ejbFacade;
    private List<Useraccount> items = null;
    private Useraccount selected = new Useraccount();

    String confirmpassword;

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public UseraccountController() {
    }

    public Useraccount getSelected() {
        return selected;
    }

    public void setSelected(Useraccount selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UseraccountFacade getFacade() {
        return ejbFacade;
    }

    public Useraccount prepareCreate() {
        selected = new Useraccount();
        initializeEmbeddableKey();
        return selected;
    }

    public Useraccount createg() {

        selected.setId(null);

        System.out.println(selected.getPassword());

        // prepareCreate();
        if (getConfirmpassword() == null ? selected.getPassword() != null : getConfirmpassword().equals(selected.getPassword())) {

            selected.setPassword(Users.encrypt(selected.getPassword()));
            persist(PersistAction.CREATE, ResourceBundle.getBundle("/bid").getString("UseraccountCreated"));
            if (!JsfUtil.isValidationFailed()) {
                items = null;    // Invalidate list of items to trigger re-query.
            }
            System.out.println("In action");
            FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml?faces-redirect=true");
        }else{
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password does not match", "Password does not match"));
        }

        return prepareCreate();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bid").getString("UseraccountCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bid").getString("UseraccountUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bid").getString("UseraccountDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Useraccount> getItems() {
        if (items == null) {
            items = getFacade().findAllbuyer();
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

    public Useraccount getUseraccount(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Useraccount> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Useraccount> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
     public List<Useraccount> getItemssellerSelectOne() {
        return getFacade().findAllseller();
    }
     public List<Useraccount> getItemsBuyerSelectOne() {
        return getFacade().findAllbuyer();
    }
    
    
     public List<Useraccount> getSellerItems() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Useraccount.class)
    public static class UseraccountControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UseraccountController controller = (UseraccountController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "useraccountController");
            return controller.getUseraccount(getKey(value));
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
            if (object instanceof Useraccount) {
                Useraccount o = (Useraccount) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Useraccount.class.getName()});
                return null;
            }
        }

    }

}
