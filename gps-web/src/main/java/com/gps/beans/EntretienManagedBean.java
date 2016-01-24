package com.gps.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.gps.entities.EntretienVoiture;
import com.gps.facades.local.EntretienFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */
@ManagedBean(name = "entretienManagedBean")
@ViewScoped
public class EntretienManagedBean implements Serializable {

	private static final Logger logger = Logger.getLogger(EntretienManagedBean.class);
	@EJB
	private EntretienFacadeLocal entretienFacade;
	private EntretienVoiture selectedEntretien;
	private List<EntretienVoiture> listEntretien;

	@ManagedProperty("#{login}")
	private Login login;

	public EntretienManagedBean() {
	}

	@PostConstruct
	public void init() {
		findAllEntretiens();
		setSelectedEntretien(new EntretienVoiture());
	}

	public String prepareList() {
		findAllEntretiens();
		setSelectedEntretien(new EntretienVoiture());
		return "/pages/entretiens/entretiens.xhtml";
	}

	public void prepareCreate() {
		setSelectedEntretien(new EntretienVoiture());
	}

	public void create() {
		try {
			entretienFacade.create(getSelectedEntretien());
			login.calculateNearTimeEntretiens();
			JsfUtil.addSuccessMessage("Boitier créé avec succès");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Echec de création du entretien");
		}
	}

	public void save() {
		try {
			if (getSelectedEntretien().getId() == null) {
				entretienFacade.create(getSelectedEntretien());
				JsfUtil.addSuccessMessage("Création d'un nouveau entretien effectuée avec succès !");
				getListEntretien().add(getSelectedEntretien());
				JsfUtil.executeScript("createEntretienWidget.hide()");
				JsfUtil.updateScript(":formEntretien:listEntretien");
			} else {
				entretienFacade.edit(getSelectedEntretien());
				JsfUtil.addSuccessMessage("Mise à jour du entretien effectuée avec succès !");
				JsfUtil.executeScript("createEntretienWidget.hide()");
				JsfUtil.updateScript(":formEntretien:listEntretien");
			}
			login.calculateNearTimeEntretiens();
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour du entretien !!!");
			getLogger().error(e);
		}
	}

	public String prepareEdit() {
		return "Edit";
	}

	public void update() {
		try {
			entretienFacade.edit(getSelectedEntretien());
			JsfUtil.addSuccessMessage("Mise à jour du entretien effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de mise à jour du entretien !!!");
		}
	}

	public void destroy() {
		try {
			entretienFacade.remove(getSelectedEntretien());
			getListEntretien().remove(getSelectedEntretien());
			JsfUtil.addSuccessMessage("Suppression du entretien effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de suppression du entretien !!!");
		}
	}

	public void findAllEntretiens() {
		try {
			setListEntretien(entretienFacade.findAllEntretiens());
		} catch (Exception ex) {
			getLogger().error(ex);
		}
	}

	public EntretienFacadeLocal getEntretienFacade() {
		return entretienFacade;
	}

	public void setEntretienFacade(EntretienFacadeLocal entretienFacade) {
		this.entretienFacade = entretienFacade;
	}

	public EntretienVoiture getSelectedEntretien() {
		return selectedEntretien;
	}

	public void setSelectedEntretien(EntretienVoiture selectedEntretien) {
		this.selectedEntretien = selectedEntretien;
	}

	public List<EntretienVoiture> getListEntretien() {
		return listEntretien;
	}

	public void setListEntretien(List<EntretienVoiture> listEntretien) {
		this.listEntretien = listEntretien;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}
}
