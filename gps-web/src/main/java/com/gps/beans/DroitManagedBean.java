package com.gps.beans;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;

import com.gps.entities.Droit;
import com.gps.facades.local.DroitFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "droitManagedBean")
@ViewScoped
public class DroitManagedBean implements Serializable {

	private static final Logger logger = Logger.getLogger(DroitManagedBean.class);

	@EJB
	private DroitFacadeLocal droitFacade;
	private Droit selectedDroit;
	private List<Droit> listDroit;

	public DroitManagedBean() {
	}

	@PostConstruct
	public void init() {
		findAllAuthoritiess();
		setSelectedDroit(new Droit());
	}

	public String prepareList() {
		findAllAuthoritiess();
		setSelectedDroit(new Droit());
		return "/pages/configuration/Roles.xhtml";
	}

	public void prepareCreate() {
		setSelectedDroit(new Droit());
	}

	public void create() {
		try {
			getDroitFacade().create(getSelectedDroit());
			JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("AuthoritiesCreated"));
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
		}
	}

	public void save() {
		try {
			if (getSelectedDroit().getLibelle() != null && !selectedDroit.getLibelle().isEmpty()) {
				if (getSelectedDroit().getIdDroit() == null) {
					if (getDroitFacade().findDroitByLibDroit(getSelectedDroit().getLibelle()).isEmpty()) {

						getDroitFacade().create(getSelectedDroit());
						JsfUtil.addSuccessMessage("Création d'un nouveau rôle effectuée avec succès !");
						getListDroit().add(getSelectedDroit());
						JsfUtil.executeScript("createRoleWidget.hide()");
						JsfUtil.updateScript(":formRole:listRole");

					} else {
						JsfUtil.addErrorMessage("Le libellé du rôle à ajouter existe déjà !!!");
					}
				} else {
					if (!checkRoleUnicity(selectedDroit)) {

						getDroitFacade().edit(getSelectedDroit());
						JsfUtil.addSuccessMessage("Mise à jour du rôle effectuée avec succès !");
						JsfUtil.executeScript("createRoleWidget.hide()");
						JsfUtil.updateScript(":formRole:listRole");

					} else {
						JsfUtil.addErrorMessage("Le libellé du rôle à mettre à jour existe déjà !!!");
					}
				}
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour du rôle !!!");
			getLogger().error(e);
		}
	}

	public Boolean checkRoleUnicity(Droit dro) throws Exception {

		if (dro.getLibelle() != null && !dro.getLibelle().isEmpty()) {

			List<Droit> listAuths = getDroitFacade().findDroitByLibDroit(dro.getLibelle());

			if (!listAuths.isEmpty() && listAuths.get(0).getIdDroit() != dro.getIdDroit()) {
				return true;
			}
		}
		return false;
	}

	public String prepareEdit() {
		return "Edit";
	}

	public void update() {
		try {
			getDroitFacade().edit(getSelectedDroit());
			JsfUtil.addSuccessMessage("Mise à jour du rôle effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de mise à jour du rôle !!!");
		}
	}

	public void destroy() {
		try {
			getDroitFacade().remove(getSelectedDroit());
			getListDroit().remove(getSelectedDroit());
			JsfUtil.addSuccessMessage("Suppression du rôle effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de suppression du rôle !!!");
		}
	}

	public void findAllAuthoritiess() {
		try {
			setListDroit(getDroitFacade().findAllDroits());
		} catch (Exception ex) {
			getLogger().error(ex);
		}
	}

	/**
	 * @return the droitFacade
	 */
	public DroitFacadeLocal getDroitFacade() {
		return droitFacade;
	}

	/**
	 * @param droitFacade
	 *            the droitFacade to set
	 */
	public void setDroitFacade(DroitFacadeLocal droitFacade) {
		this.droitFacade = droitFacade;
	}

	/**
	 * @return the selectedDroit
	 */
	public Droit getSelectedDroit() {
		return selectedDroit;
	}

	/**
	 * @param selectedDroit
	 *            the selectedDroit to set
	 */
	public void setSelectedDroit(Droit selectedDroit) {
		this.selectedDroit = selectedDroit;
	}

	/**
	 * @return the listDroit
	 */
	public List<Droit> getListDroit() {
		return listDroit;
	}

	/**
	 * @param listDroit
	 *            the listDroit to set
	 */
	public void setListDroit(List<Droit> listDroit) {
		this.listDroit = listDroit;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

	@FacesConverter(forClass = Droit.class)
	public static class AuthoritiesManagedBeanConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			DroitManagedBean ManagedBean = (DroitManagedBean) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "droitManagedBean");
			return ManagedBean.getDroitFacade().find(Droit.class, getKey(value));
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
			if (object instanceof Droit) {
				Droit o = (Droit) object;
				return getStringKey(o.getIdDroit());
			} else {
				throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
						+ "; expected type: " + Droit.class.getName());
			}
		}
	}
}
