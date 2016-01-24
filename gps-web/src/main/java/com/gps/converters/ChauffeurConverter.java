/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.converters;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.gps.entities.Chauffeur;
import com.gps.facades.local.ChauffeurFacadeLocal;

@ManagedBean
@FacesConverter(forClass = Chauffeur.class, value = "chauffeurConverter")
public class ChauffeurConverter implements Converter {

	@EJB
	private ChauffeurFacadeLocal chauffeurFacade;

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
		if (value == null || value.length() == 0) {
			return null;
		}
		return chauffeurFacade.find(Chauffeur.class, getKey(value));
	}

	java.lang.Integer getKey(String value) {
		java.lang.Integer key;
		key = Integer.valueOf(value);
		return key;
	}

	String getStringKey(java.lang.Integer value) {
		StringBuilder sb = new StringBuilder();
		sb.append(value);
		return sb.toString();
	}

	@Override
	public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
		if (object == null) {
			return null;
		}
		if (object instanceof Chauffeur) {
			Chauffeur o = (Chauffeur) object;
			return getStringKey(o.getId());
		} else {
			throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
					+ "; expected type: " + Chauffeur.class.getName());
		}
	}
}
