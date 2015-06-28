/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.polimi.guessbid.Controler;

import it.polimi.guessbid.Entity.Winer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author abel
 */
@Stateless
public class WinerFacade extends AbstractFacade<Winer> {
    @PersistenceContext(unitName = "com.mycompany_GuessBid_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WinerFacade() {
        super(Winer.class);
    }
    
}
