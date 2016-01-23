/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Utilisateur;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = UtilisateurFacadeLocal.class)
public interface UtilisateurFacadeLocal {

	void create(Object collaborateur);

	void edit(Object collaborateur);

	void remove(Object collaborateur);

	Object find(Class entityClass, Object id);

	List<Utilisateur> findAllUtilisateurs();

	Utilisateur findUtilisateurByIdentifiant(String identif);

	List<Utilisateur> findEnabledUtilisateurs();

	List<Utilisateur> findUtilisateursByMobile(String mobile);

	List<Utilisateur> findUtilisateursByEmail(String email);

}
