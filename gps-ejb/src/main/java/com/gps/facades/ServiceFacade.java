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
import javax.persistence.Query;

import com.gps.entities.Account;
import com.gps.entities.Service;
import com.gps.facades.local.ServiceFacadeLocal;

/**
 *
 * @author 21590495
 */
@Stateless
public class ServiceFacade extends AbstractFacade implements ServiceFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;
	Map conditions = new HashMap();

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Service> findAllServices() {
		return findAll(Service.class, null, "");
	}

	@Override
	public List<Account> getAllAccountByService(Service service) {
		conditions = new HashMap();
		conditions.put("idService.idService", "=" + service.getIdService());
		return this.findByCriteria(conditions, Account.class, "", "");

	}

	@Override
	public List<Service> findServiceByTypeService(String typeService) {
		Map condition = new HashMap();
		condition.put("typeService", " = '" + typeService + "'");
		return findByCriteria(condition, Service.class, "", "");
	}

	@Override
	public List<Service> findSortService() {
		Query requete = em.createQuery(
				"select s from Service s where s.servicequeue.ordre IS NOT NULL order by s.servicequeue.ordre");
		return requete.getResultList();
	}

}
