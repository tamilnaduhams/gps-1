/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.gps.entities.Droit;
import com.gps.facades.local.DroitFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless
public class DroitFacade extends AbstractFacade implements DroitFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Droit> findAllDroits() {
		return findAll(Droit.class, null, "");
	}

	@Override
	public Droit findDroitByIdDroit(Integer iDro) {
		return (Droit) find(Droit.class, iDro);
	}

	@Override
	public List<Droit> findDroitByLibDroit(String dro) {
		Map condition = new HashMap();
		condition.put("libelle", " = '" + dro + "'");
		return findByCriteria(condition, Droit.class, "", "");
	}

}