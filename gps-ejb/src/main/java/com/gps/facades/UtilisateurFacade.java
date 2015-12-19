/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import com.gps.entities.Utilisateur;
import com.gps.facades.local.UtilisateurFacadeLocal;
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
public class UtilisateurFacade extends AbstractFacade implements UtilisateurFacadeLocal, Serializable {
    
    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    @Override
    public List<Utilisateur> findAllUtilisateurs() {
        return findAll(Utilisateur.class, null, "");
    }
    
    @Override
    public Utilisateur findUtilisateurByIdentifiant(String identif) {
        return (Utilisateur) findByAttribute(Utilisateur.class, "identifiant", identif);
    }
    
    @Override
    public List<Utilisateur> findEnabledUtilisateurs() {
        Map condition = new HashMap();
        condition.put("enabled", "='" + true + "'");
        return findByCriteria(condition, Utilisateur.class, "", "");
    }
    
    @Override
    public List<Utilisateur> findUtilisateursByMobile(String mobile) {
        Map condition = new HashMap();
        condition.put("mobile", " = '" + mobile + "'");

        return findByCriteria(condition, Utilisateur.class, "", "");
    }
    
    @Override
    public List<Utilisateur> findUtilisateursByEmail(String email) {
        Map condition = new HashMap();
        condition.put("email", " = '" + email + "'");

        return findByCriteria(condition, Utilisateur.class, "", "");
    }
    
}
