/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Menu;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = MenuFacadeLocal.class)
public interface MenuFacadeLocal {

	void create(Object menu);

	void edit(Object menu);

	void remove(Object menu);

	Object find(Class entityClass, Object id);

	List<Menu> findAllMenus();

	public Menu findActiveMenu();

}
