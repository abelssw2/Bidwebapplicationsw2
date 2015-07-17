package it.polimi.guessbid.Bean;

import it.polimi.guessbid.Entity.Goods;
import it.polimi.guessbid.Bean.util.JsfUtil;
import it.polimi.guessbid.Bean.util.JsfUtil.PersistAction;
import it.polimi.guessbid.Controler.GoodsFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named("goodsController")
@SessionScoped
public class GoodsController implements Serializable {

    @EJB
    private it.polimi.guessbid.Controler.GoodsFacade ejbFacade;
    private List<Goods> items = null;
    private Goods selected;

    public GoodsController() {
    }

    public Goods getSelected() {
        return selected;
    }

    public void setSelected(Goods selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private GoodsFacade getFacade() {
        return ejbFacade;
    }

    public Goods prepareCreate() {
        selected = new Goods();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/bid").getString("GoodsCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/bid").getString("GoodsUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/bid").getString("GoodsDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Goods> getItems() {
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

    public Goods getGoods(java.lang.Integer id) {
        return getFacade().find(id);
    }

    public List<Goods> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Goods> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    

}