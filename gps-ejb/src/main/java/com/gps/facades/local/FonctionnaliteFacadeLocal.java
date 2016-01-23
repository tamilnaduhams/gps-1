/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Fonctionnalite;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = FonctionnaliteFacadeLocal.class)
public interface FonctionnaliteFacadeLocal {

	void create(Object fonctionnalite);

	void edit(Object fonctionnalite);

	void remove(Object fonctionnalite);

	Object find(Class entityClass, Object id);

	List<Fonctionnalite> findAllFonctionnalites();

	List<Fonctionnalite> findFonctionnalitesByMenu(Integer menu);

	List<Fonctionnalite> findFonctionnalitesByURL(String url);
}
