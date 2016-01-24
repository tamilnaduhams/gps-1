package com.gps.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.gps.entities.Boitier;
import com.gps.facades.local.BoitierFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "boitierManagedBean")
@ViewScoped
public class BoitierManagedBean implements Serializable {

	private static final Logger logger = Logger.getLogger(BoitierManagedBean.class);

	@EJB
	private BoitierFacadeLocal boitierFacade;
	private Boitier selectedBoitier;
	private List<Boitier> listBoitier;

	public BoitierManagedBean() {
	}

	@PostConstruct
	public void init() {
		findAllBoitiers();
		setSelectedBoitier(new Boitier());
	}

	public String prepareList() {
		findAllBoitiers();
		setSelectedBoitier(new Boitier());
		return "/pages/boitiers/boitiers.xhtml";
	}

	public void prepareCreate() {
		setSelectedBoitier(new Boitier());
	}

	public void create() {
		try {
			boitierFacade.create(getSelectedBoitier());
			JsfUtil.addSuccessMessage("Boitier créé avec succès");
		} catch (Exception e) {
			JsfUtil.addErrorMessage(e, "Echec de création du boitier");
		}
	}

	public void save() {
		try {
			if (getSelectedBoitier().getNumBoitier() != null && !selectedBoitier.getNumBoitier().isEmpty()) {
				if (getSelectedBoitier().getId() == null) {
					List<Boitier> boitiers = boitierFacade
							.findBoitierByNumBoitier(getSelectedBoitier().getNumBoitier());
					if (boitiers == null || boitiers.isEmpty()) {
						boitierFacade.create(getSelectedBoitier());
						JsfUtil.addSuccessMessage("Création d'un nouveau boitier effectuée avec succès !");
						getListBoitier().add(getSelectedBoitier());
						JsfUtil.executeScript("createBoitierWidget.hide()");
						JsfUtil.updateScript(":formBoitier:listBoitier");

					} else {
						JsfUtil.addErrorMessage("Le numéro du boitier à ajouter existe déjà !!!");
					}
				} else {
					if (!checkBoitierUnicity(selectedBoitier)) {
						boitierFacade.edit(getSelectedBoitier());
						JsfUtil.addSuccessMessage("Mise à jour du boitier effectuée avec succès !");
						JsfUtil.executeScript("createBoitierWidget.hide()");
						JsfUtil.updateScript(":formBoitier:listBoitier");

					} else {
						JsfUtil.addErrorMessage("Le numéro du boitier à mettre à jour existe déjà !!!");
					}
				}
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour du boitier !!!");
			getLogger().error(e);
		}
	}

	public Boolean checkBoitierUnicity(Boitier veh) throws Exception {

		if (veh.getNumBoitier() != null && !veh.getNumBoitier().isEmpty()) {

			List<Boitier> listVeh = boitierFacade.findBoitierByNumBoitier(veh.getNumBoitier());

			if (listVeh != null && !listVeh.isEmpty() && listVeh.get(0).getId() != veh.getId()) {
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
			boitierFacade.edit(getSelectedBoitier());
			JsfUtil.addSuccessMessage("Mise à jour du boitier effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de mise à jour du boitier !!!");
		}
	}

	public void destroy() {
		try {
			boitierFacade.remove(getSelectedBoitier());
			getListBoitier().remove(getSelectedBoitier());
			JsfUtil.addSuccessMessage("Suppression du boitier effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Echec de l'opération de suppression du boitier !!!");
		}
	}

	public void findAllBoitiers() {
		try {
			setListBoitier(boitierFacade.findAllBoitiers());
		} catch (Exception ex) {
			getLogger().error(ex);
		}
	}

	public Boitier getSelectedBoitier() {
		return selectedBoitier;
	}

	public void setSelectedBoitier(Boitier selectedBoitier) {
		this.selectedBoitier = selectedBoitier;
	}

	public List<Boitier> getListBoitier() {
		return listBoitier;
	}

	public void setListBoitier(List<Boitier> listBoitier) {
		this.listBoitier = listBoitier;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

}
