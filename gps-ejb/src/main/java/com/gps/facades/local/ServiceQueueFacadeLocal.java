/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Servicequeue;

/**
 *
 * @author Amine
 */
@Local(value = ServiceQueueFacadeLocal.class)
public interface ServiceQueueFacadeLocal {

	void create(Object c);

	void edit(Object c);

	void remove(Object c);

	List<Servicequeue> findAllServicesQ();

	Servicequeue findServicequeueByOrder(Integer order);

}
