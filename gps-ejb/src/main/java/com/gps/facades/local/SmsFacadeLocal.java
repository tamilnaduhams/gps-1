/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.Sms;

/**
 *
 * @author Amine Sagaama
 */
@Local(value = SmsFacadeLocal.class)
public interface SmsFacadeLocal {

	void create(Object c);

	void edit(Object c);

	void remove(Object c);

	public boolean deleteItems(Sms[] items);

	List<Sms> findAllSms();

}
