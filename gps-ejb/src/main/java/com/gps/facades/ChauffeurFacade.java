/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gps.entities.Chauffeur;
import com.gps.facades.local.ChauffeurFacadeLocal;

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
