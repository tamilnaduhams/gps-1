/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.log4j.Logger;
import org.primefaces.event.ToggleEvent;

import com.gps.entities.Fonctionnalite;
import com.gps.entities.Menu;
import com.gps.facades.local.MenuFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "menuManagedBean")
@ViewScoped
public class MenuManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(Menu.class);
	@EJB
	private MenuFacadeLocal menuFacade;
	private List<Menu> listMenu;
	private List<Fonctionnalite> listFonctionnalite;
	private Menu selectedMenu;

	public MenuManagedBean() {
	}

	@PostConstruct
	public void init() {
		findAllMenu();
		setSelectedMenu(new Menu());
	}

	public String prepareList() {
		findAllMenu();
		setSelectedMenu(new Menu());
		return "/pages/configuration/Menus.xhtml";
	}

	public void prepareCreate() {
		selectedMenu = new Menu();
	}

	public void save() {
		try {
			if (selectedMenu.getLibelle() != null && !selectedMenu.getLibelle().isEmpty()) {
				if (selectedMenu.getIdMenu() == null) {
					if (checkLibelleUnicity(selectedMenu)) {
						menuFacade.create(selectedMenu);
						listMenu.add(selectedMenu);
						JsfUtil.addSuccessMessage("Création d'un nouveau menu effectuée avec succès !");
						JsfUtil.executeScript("createMenuWidget.hide()");
						JsfUtil.updateScript(":formMenu:listMenu");
					} else {
						JsfUtil.addErrorMessage("Le libellé du menu à ajouter existe déjà !!!");
					}
				} else {
					if (checkLibelleUnicity(selectedMenu)) {
						menuFacade.edit(selectedMenu);
						JsfUtil.addSuccessMessage("Mise à jour du menu effectuée avec succès !");
						JsfUtil.executeScript("createMenuWidget.hide()");
						JsfUtil.updateScript(":formMenu:listMenu");
					} else {
						JsfUtil.addErrorMessage("Le libellé du menu à mettre à jour existe déjà !!!");
					}
				}
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour !!!");
			LOGGER.error(e);
		}
	}

	public Boolean checkLibelleUnicity(Menu selectedMenu) {
		if (selectedMenu.getLibelle() != null && !selectedMenu.getLibelle().isEmpty()) {
			for (Menu contrat : listMenu) {
				if (contrat.getIdMenu() != selectedMenu.getIdMenu()
						&& contrat.getLibelle().equalsIgnoreCase(selectedMenu.getLibelle())) {
					return Boolean.FALSE;
				}
			}
		}
		return Boolean.TRUE;
	}

	public void destroy() {
		try {
			menuFacade.remove(selectedMenu);
			listMenu.remove(selectedMenu);
			JsfUtil.addSuccessMessage("Suppression du menu effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addSuccessMessage("Opération de suppression du menu échouée !!!");
			LOGGER.error("Opération de suppression du menu échouée !!!");
			LOGGER.error(e);
		}
	}

	public void findAllMenu() {
		listMenu = getMenuFacade().findAllMenus();
	}

	public void onRowToggle(ToggleEvent event) throws Exception {
		if (event != null) {
			Menu menu = (Menu) event.getData();
			if (menu != null) {
				listFonctionnalite = menu.getFonctionnaliteList();
			}
		} else {
			throw new Exception("onRowToggle NoteFraisJustifManagedBean error");
		}
	}

	/**
	 * @return the menuFacade
	 */
	public MenuFacadeLocal getMenuFacade() {
		return menuFacade;
	}

	/**
	 * @param menuFacade
	 *            the menuFacade to set
	 */
	public void setMenuFacade(MenuFacadeLocal menuFacade) {
		this.menuFacade = menuFacade;
	}

	/**
	 * @return the listMenu
	 */
	public List<Menu> getListMenu() {
		return listMenu;
	}

	/**
	 * @param listMenu
	 *            the listMenu to set
	 */
	public void setListMenu(List<Menu> listMenu) {
		this.listMenu = listMenu;
	}

	/**
	 * @return the selectedMenu
	 */
	public Menu getSelectedMenu() {
		return selectedMenu;
	}

	/**
	 * @param selectedMenu
	 *            the selectedMenu to set
	 */
	public void setSelectedMenu(Menu selectedMenu) {
		this.selectedMenu = selectedMenu;
	}

	/**
	 * @return the listFonctionnalite
	 */
	public List<Fonctionnalite> getListFonctionnalite() {
		return listFonctionnalite;
	}

	/**
	 * @param listFonctionnalite
	 *            the listFonctionnalite to set
	 */
	public void setListFonctionnalite(List<Fonctionnalite> listFonctionnalite) {
		this.listFonctionnalite = listFonctionnalite;
	}

	@FacesConverter(forClass = Menu.class)
	public static class MenuManagedBeanConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			MenuManagedBean ManagedBean = (MenuManagedBean) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "menuManagedBean");
			return ManagedBean.menuFacade.find(Menu.class, getKey(value));
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
			if (object instanceof Menu) {
				Menu o = (Menu) object;
				return getStringKey(o.getIdMenu());
			} else {
				throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
						+ "; expected type: " + Menu.class.getName());
			}
		}
	}
}