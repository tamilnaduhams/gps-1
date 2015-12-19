/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;


import com.gps.entities.Sms;
import com.gps.facades.local.SmsFacadeLocal;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Amine Sagaama
 */
@Stateless
public class SMSFacade extends AbstractFacade implements SmsFacadeLocal, Serializable {
    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Sms> findAllSms() {
        return findAll(Sms.class, null, "");
    }

    
    
    
    
    
    
}
