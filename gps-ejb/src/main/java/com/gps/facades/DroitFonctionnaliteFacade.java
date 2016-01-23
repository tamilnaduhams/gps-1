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

import com.gps.entities.DroitFonctionnalite;
import com.gps.facades.local.DroitFonctionnaliteFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless
public class DroitFonctionnaliteFacade extends AbstractFacade implements DroitFonctionnaliteFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<DroitFonctionnalite> findAllDroitFonctionnalites() {
		return findAll(DroitFonctionnalite.class, null, "");
	}

	@Override
	public List<DroitFonctionnalite> findDroitFonctionnalitesByFonctionnalite(Integer dro, Integer mn) {
		Map condition = new HashMap();
		condition.put("idDroit.idDroit", " = " + dro);
		condition.put("idFonctionnalite.idMenu.idMenu", " = " + mn);
		return findByCriteria(condition, DroitFonctionnalite.class, "", "");
	}

	@Override
	public List<DroitFonctionnalite> findDroitFonctionnalitesByDroit(Integer dro) {
		Map condition = new HashMap();
		condition.put("idDroit.idDroit", " = " + dro);
		return findByCriteria(condition, DroitFonctionnalite.class, "", "");
	}

	@Override
	public List<DroitFonctionnalite> findDroitFonctionnalitesByFct(Integer fct) {
		Map condition = new HashMap();
		condition.put("idFonctionnalite.idFonctionnalite", " = " + fct);
		return findByCriteria(condition, DroitFonctionnalite.class, "", "");
	}

	@Override
	public List<DroitFonctionnalite> findDroitFonctionnalitesByMenu(Integer menu) {
		Map condition = new HashMap();
		condition.put("idFonctionnalite.idMenu.idMenu", " = " + menu);
		return findByCriteria(condition, DroitFonctionnalite.class, "", "");
	}

	@Override
	public List<DroitFonctionnalite> findDroitFonctionnalitesByMenuAndDroit(Integer menu, Integer dro) {
		List<DroitFonctionnalite> droitFonctionnalite = em
				.createQuery(
						"SELECT p FROM DroitFonctionnalite p WHERE p.idFonctionnalite.idMenu.idMenu = ?1 AND p.idDroit.idDroit = ?2 ORDER BY p.idFonctionnalite.libelle ASC")
				.setParameter(1, menu).setParameter(2, dro).getResultList();
		if (droitFonctionnalite.isEmpty()) {
			return null;
		}
		return droitFonctionnalite;
	}

	@Override
	public boolean findEtatByFonctionnaliteAndDroit(Integer fct, Integer dro) {
		List<DroitFonctionnalite> droitFonctionnalite = em.createQuery("SELECT p FROM DroitFonctionnalite p WHERE "
				+ "p.idFonctionnalite.idFonctionnalite = ?1 " + "AND p.idDroit.idDroit = ?2 ").setParameter(1, fct)
				.setParameter(2, dro).getResultList();
		if (droitFonctionnalite.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public DroitFonctionnalite findDroitFonctionnaliteByFonctionnaliteAndDroit(Integer fct, Integer dro) {
		List<DroitFonctionnalite> droitFonctionnalite = em.createQuery("SELECT p FROM DroitFonctionnalite p WHERE "
				+ "p.idFonctionnalite.idFonctionnalite = ?1 " + "AND p.idDroit.idDroit = ?2 ").setParameter(1, fct)
				.setParameter(2, dro).getResultList();
		if (droitFonctionnalite.isEmpty()) {
			return null;
		}
		return droitFonctionnalite.get(0);
	}

}