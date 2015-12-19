/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import com.gps.entities.Fonctionnalite;
import com.gps.facades.local.FonctionnaliteFacadeLocal;
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
public class FonctionnaliteFacade extends AbstractFacade implements FonctionnaliteFacadeLocal, Serializable {

    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Fonctionnalite> findAllFonctionnalites() {
        return findAll(Fonctionnalite.class, null, "");
    }
    
    @Override
    public List<Fonctionnalite> findFonctionnalitesByMenu(Integer mnu) {
        Map condition = new HashMap();
        condition.put("idMenu.idMenu", " = " + mnu);
        return findByCriteria(condition, Fonctionnalite.class, "", "");
    }
    
    @Override
    public List<Fonctionnalite> findFonctionnalitesByURL(String url) {
        Map condition = new HashMap();
        condition.put("url", " = '" + url + "'");
        return findByCriteria(condition, Fonctionnalite.class, "", "");
    }
}