/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Account;
import com.gps.entities.Service;

/**
 *
 * @author Amine Sagaama
 */
@Local(value = ServiceFacadeLocal.class)
public interface ServiceFacadeLocal {

	void create(Object c);

	void edit(Object c);

	void remove(Object c);

	List<Service> findAllServices();

	List<Account> getAllAccountByService(Service service);

	Object find(Class entityClass, Object id);

	public List<Service> findServiceByTypeService(String typeService);

	public List<Service> findSortService();

}
