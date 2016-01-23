/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Boitier;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = BoitierFacadeLocal.class)
public interface BoitierFacadeLocal {

	void create(Object authorities);

	void edit(Object authorities);

	void remove(Object authorities);

	Object find(Class entityClass, Object id);

	List<Boitier> findAllBoitiers();

	List<Boitier> findBoitierByNumBoitier(String numBoitier);
}
