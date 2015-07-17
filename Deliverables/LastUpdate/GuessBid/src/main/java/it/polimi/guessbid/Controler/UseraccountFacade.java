/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.Controler;

import it.polimi.guessbid.Entity.Useraccount;
import it.polimi.guessbid.util.Util;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpSession;

/**
 *
 * @author abel
 */
@Stateless
public class UseraccountFacade extends AbstractFacade<Useraccount> {

    @PersistenceContext(unitName = "com.mycompany_GuessBid_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public List<Useraccount> findAllseller() {
//        Query sellrfil = getEntityManager().createNamedQuery("Useraccount.findByRole");
//
//        sellrfil.setParameter("role", "Seller");
        
         HttpSession session = Util.getSession();
           
        Query sellrfil = getEntityManager().createNamedQuery("Useraccount.findByUsername");

        sellrfil.setParameter("username",session.getAttribute("username").toString());
        return sellrfil.getResultList();
    }
    
    
      public List<Useraccount> findAllbuyer() {
          
          
            HttpSession session = Util.getSession();
           
        Query sellrfil = getEntityManager().createNamedQuery("Useraccount.findByUsername");

        sellrfil.setParameter("username",session.getAttribute("username").toString());
        return sellrfil.getResultList();
    }

    public UseraccountFacade() {
        super(Useraccount.class);
    }

}
