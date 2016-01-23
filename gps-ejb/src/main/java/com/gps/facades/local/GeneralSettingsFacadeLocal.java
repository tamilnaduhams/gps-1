/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import java.util.List;

import javax.ejb.Local;

import com.gps.entities.GeneralSettings;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = GeneralSettingsFacadeLocal.class)
public interface GeneralSettingsFacadeLocal {

	void create(Object c);

	void edit(Object c);

	void remove(Object c);

	public Object find(Class entityClass, Object id);

	public GeneralSettings findGeneralSettingsBySettingsCode(String settingsCode);

	List<GeneralSettings> findAllGeneralSettings();

	int findGeneralSettingsBySettingsCode(String settingsCode, int defaultValue);

	String findGeneralSettingsBySettingsCode(String settingsCode, String defaultValue);

}
