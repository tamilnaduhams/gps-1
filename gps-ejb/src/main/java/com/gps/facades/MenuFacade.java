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

import com.gps.entities.Menu;
import com.gps.facades.local.MenuFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless
public class MenuFacade extends AbstractFacade implements MenuFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Menu> findAllMenus() {
		return findAll(Menu.class, null, "");
	}

	@Override
	public Menu findActiveMenu() {
		List<Menu> menu = em.createQuery("SELECT p FROM Menu p WHERE p.active IS " + true).getResultList();
		if (menu.isEmpty()) {
			return null;
		}
		return menu.get(0);
	}
}