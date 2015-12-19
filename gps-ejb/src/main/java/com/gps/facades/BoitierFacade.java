/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import com.gps.entities.Boitier;
import com.gps.facades.local.BoitierFacadeLocal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless
public class BoitierFacade extends AbstractFacade implements BoitierFacadeLocal, Serializable {
    
    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
            
    @Override
    public List<Boitier> findAllBoitiers() {
        return findAll(Boitier.class, null, "");
    }
    

    @Override
    public List<Boitier> findBoitierByNumBoitier(String numBoitier) {
        Map condition = new HashMap();
        condition.put("numBoitier", " = '" + numBoitier + "'");
        return findByCriteria(condition, Boitier.class, "", "");
    }


}
