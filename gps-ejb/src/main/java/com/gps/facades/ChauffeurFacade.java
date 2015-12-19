/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import com.gps.entities.Boitier;
import com.gps.entities.Chauffeur;
import com.gps.facades.local.BoitierFacadeLocal;
import com.gps.facades.local.ChauffeurFacadeLocal;
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
public class ChauffeurFacade extends AbstractFacade implements ChauffeurFacadeLocal, Serializable {
    
    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    
            
    @Override
    public List<Chauffeur> findAllChauffeurs() {
        return findAll(Chauffeur.class, null, "");
    }

}
