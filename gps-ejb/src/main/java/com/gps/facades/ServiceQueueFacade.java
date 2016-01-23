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

import com.gps.entities.Servicequeue;
import com.gps.facades.local.ServiceQueueFacadeLocal;

/**
 *
 * @author Amine
 */
@Stateless
public class ServiceQueueFacade extends AbstractFacade implements ServiceQueueFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Servicequeue> findAllServicesQ() {
		return findAll(Servicequeue.class, null, "");
	}

	@Override
	public Servicequeue findServicequeueByOrder(Integer order) {
		return (Servicequeue) findByAttribute(Servicequeue.class, "ordre", order);
	}

}
