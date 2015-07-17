/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid;

/**
 *
 * @author abel
 */
import com.sun.glass.ui.View;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import it.polimi.guessbid.util.Util;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.swing.JOptionPane;

@ManagedBean(name = "loginBean")

/**
 *
 * @author abel
 */
@ApplicationScoped
public class LoginBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String password;
    private String message, uname;
    private transient Logger logger;

    @ApplicationScoped
    boolean seller = true;
    boolean buyer = true;

    Integer capacity;
    String email;
    String messaged;

    public boolean isSeller() {
        setheader();
        return seller;
    }

    public void setSeller(boolean seller) {
        this.seller = seller;
    }

    public boolean isBuyer() {
        setheader();
        return buyer;
    }

    public void setBuyer(boolean buyer) {
        this.buyer = buyer;
    }

    public String getMessage() {
        return message;
    }

    public Integer getCapacity() {

        return capacity;
    }

    public void setCapacity(Integer capacity) {

        this.capacity = capacity;
    }

    public String getEmail() {
        return email;
    }

    public String getMessaged() {
        return messaged;
    }

    public void setMessaged(String messaged) {
        this.messaged = messaged;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    @ApplicationScoped
    public void setheader() {
        Users j = new Users();
        HttpSession session = Util.getSession();
        j.selectcreadie(session.getAttribute("username").toString(), session.getAttribute("password").toString());

        capacity = j.capacity;
        email = j.email;
        // setEmail("sdfsd");
        System.out.println(capacity);
        System.out.println(email);
        
        

        if ("Seller".equals(j.role)) {
            seller = false;

        }
        if ("Buyer".equals(j.role)) {
            buyer = false;

        }
        System.out.println(seller);

    }

    public String loginProject() {

        boolean result = Users.login(uname, password);

        if (result) {
            // get Http Session and store username
            HttpSession session = Util.getSession();
            session.setAttribute("username", uname);
            session.setAttribute("password", password);
            setheader();
            return "home";
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "error logging in", "Error logging in"));

//          
            return null;
        }
    }

    public void logout() {

        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        logger.log(Level.INFO, "User ({0}) loging out #" + Util.getCurrentDateTime(), request.getUserPrincipal().getName());
        if (session != null) {
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/Login.xhtml?faces-redirect=true");
    }

//    public String logout() {
//      HttpSession session = Util.getSession();
//      session.invalidate();
//      return "logout";
//   }
//    
    public String creataccount() {
        setUname("");
        setPassword("");
        return "user";
    }

}
