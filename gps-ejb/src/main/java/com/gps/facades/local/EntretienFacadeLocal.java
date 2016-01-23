/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.EntretienVoiture;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = EntretienFacadeLocal.class)
public interface EntretienFacadeLocal {

	void create(Object entretien);

	void edit(Object entretien);

	void remove(Object entretien);

	Object find(Class entityClass, Object id);

	List<EntretienVoiture> findAllEntretiens();

	List<EntretienVoiture> findAllNearEntreiens();
}
