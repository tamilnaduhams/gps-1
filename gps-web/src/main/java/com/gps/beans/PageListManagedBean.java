/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "pagelistmanagedbean")
@ApplicationScoped
public class PageListManagedBean {

	private String pageUriRequest, pageUri;

	public PageListManagedBean() {
	}

	@PostConstruct
	public void initRequestUri() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		this.pageUriRequest = (String) ((HttpServletRequest) request).getRequestURI().toString().subSequence(0, 7);
	}

	public String prepareProfil() {
		this.pageUri = this.pageUriRequest.concat("/pages/configuration/Profil.xhtml");
		return "/pages/configuration/Profil.xhtml";
	}

	public String prepareUtilisateur() {
		this.pageUri = this.pageUriRequest.concat("/pages/configuration/Utilisateurs.xhtml");
		return "/pages/configuration/Utilisateurs.xhtml";
	}

	public String prepareVehicules() {
		this.pageUri = this.pageUriRequest.concat("/pages/vehicules/vehicules.xhtml");
		return "/pages/vehicules/vehicules.xhtml";
	}

	public String prepareEntretiens() {
		this.pageUri = this.pageUriRequest.concat("/pages/entretiens/entretiens.xhtml");
		return "/pages/entretiens/entretiens.xhtml";
	}

	public String prepareCarteVehicules() {
		this.pageUri = this.pageUriRequest.concat("/pages/carte/vehicules.xhtml");
		return "/pages/carte/vehicules.xhtml";
	}

	public String prepareItinerairesVoitures() {
		this.pageUri = this.pageUriRequest.concat("/pages/carte/itinerairesvehicules.xhtml");
		return "/pages/carte/itinerairesvehicules.xhtml";
	}

	public String prepareBoitiers() {
		this.pageUri = this.pageUriRequest.concat("/pages/boitiers/boitiers.xhtml");
		return "/pages/boitiers/boitiers.xhtml";
	}

	public String prepareChauffeurs() {
		this.pageUri = this.pageUriRequest.concat("/pages/chauffeurs/chauffeurs.xhtml");
		return "/pages/chauffeurs/chauffeurs.xhtml";
	}

	public String prepareGeneralSettings() {
		this.pageUri = this.pageUriRequest.concat("/pages/configuration/GeneralSettings.xhtml");
		return "/pages/configuration/GeneralSettings.xhtml";
	}

	public String prepareFonctionnalites() {
		this.pageUri = this.pageUriRequest.concat("/pages/configuration/Fonctionnalites.xhtml");
		return "/pages/configuration/Fonctionnalites.xhtml";
	}

	public String getPageUri() {
		return pageUri;
	}

	public void setPageUri(String pageUri) {
		this.pageUri = pageUri;
	}
}
