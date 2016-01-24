/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gps.constants.GeneralSettingsConstants;
import com.gps.entities.Droit;
import com.gps.entities.DroitFonctionnalite;
import com.gps.entities.EntretienVoiture;
import com.gps.entities.GeneralSettings;
import com.gps.entities.Utilisateur;
import com.gps.facades.local.EntretienFacadeLocal;
import com.gps.facades.local.GeneralSettingsFacadeLocal;
import com.gps.facades.local.UtilisateurFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "login")
@SessionScoped
public class Login implements Serializable {

	private static final Logger logger = Logger.getLogger(Login.class);

	@EJB
	private UtilisateurFacadeLocal utilisateurFacade;
	@EJB
	private GeneralSettingsFacadeLocal generalSettingsFacade;

	@EJB
	private EntretienFacadeLocal entretienFacade;

	private Utilisateur connectedUser;
	private Map<String, String> userRolesMap;
	private String langueLocale, dir, ancienPassWord, newPassWord, confPassWord,
			urlAvatar = "/resources/images/avatar/default.png";
	private String longitude = GeneralSettingsConstants.LONGITUDE;
	private String latitude = GeneralSettingsConstants.LATITUDE;

	private List<EntretienVoiture> nearTimeEntretiens;

	@PostConstruct
	public void init() {
		dir = "ltr";
		langueLocale = "Fr";
		try {
			resolveConnectedUser();
			GeneralSettings gsLongitude = getGeneralSettingsFacade().findGeneralSettingsBySettingsCode("Longitude");
			GeneralSettings gsLatitude = getGeneralSettingsFacade().findGeneralSettingsBySettingsCode("Latitude");
			if (gsLongitude != null)
				setLongitude(gsLongitude.getSettingValue());
			if (gsLatitude != null)
				setLatitude(gsLatitude.getSettingValue());
			calculateNearTimeEntretiens();
		} catch (Exception ex) {
			logger.error(ex);
		}
	}

	public void calculateNearTimeEntretiens() {
		nearTimeEntretiens = entretienFacade.findAllNearEntreiens();
	}

	public void resolveConnectedUser() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();

		if (userName != null && !userName.isEmpty()) {
			setConnectedUser(utilisateurFacade.findUtilisateurByIdentifiant(userName));
			if (getConnectedUser() != null) {
				Droit userRoles = getConnectedUser().getIdDroit();
				if (userRoles != null) {
					urlAvatar = getConnectedUser().getAvatar() == null ? "/resources/images/avatar/default.jpg"
							: "/static/images/avatar/" + getConnectedUser().getAvatar();
					userRolesMap = new HashMap<>();
					if (userRoles.getLibelle() != null && StringUtils.isNotBlank(userRoles.getLibelle())) {
						userRolesMap.put(userRoles.getLibelle(), userRoles.getLibelle());
					}
				}
			}
		}
	}

	public boolean connectedUserHasRole(String roleName) {
		if (StringUtils.isNotBlank(roleName) && userRolesMap != null && !userRolesMap.isEmpty()
				&& userRolesMap.containsKey(roleName)) {
			return true;
		}
		return false;
	}

	public void savePassWord() {
		if (ancienPassWord != null && encodePassword(ancienPassWord).equals(getConnectedUser().getMotPasse())) {
			if (confPassWord != null && newPassWord != null && confPassWord.equals(newPassWord)) {
				getConnectedUser().setMotPasse(encodePassword(newPassWord));
				try {
					utilisateurFacade.edit(getConnectedUser());
					JsfUtil.addSuccessMessage("Votre mot de passe a été mis à jour avec succès !");
					JsfUtil.executeScript("profil.hide()");
				} catch (Exception ex) {
					logger.error(ex);
				}
			} else {
				JsfUtil.addErrorMessage("Les deux mots de passe que vous avez inséré ne se correspondent pas !!");
			}
		} else {
			JsfUtil.addErrorMessage("L'ancien mot de passe que vous avez inséré n'est pas correct !!");
		}
	}

	public void saveLifeTimeMessage() {
		utilisateurFacade.edit(getConnectedUser());
		JsfUtil.addSuccessMessage(
				"La durée d'affichage des notifications a été mise à jour, le changement prendra effet à partir de la prochaine reconnexion !");
	}

	public String changeAvatar(String avatar) {
		try {
			getConnectedUser().setAvatar(avatar);
			utilisateurFacade.edit(getConnectedUser());
			urlAvatar = "/static/images/avatar/" + avatar;
			JsfUtil.addSuccessMessage("Votre photo de profil a été mise à jour avec succès !");
		} catch (Exception ex) {
			JsfUtil.addSuccessMessage("La mise à jour de votre photo de profil a échouée !!!");
			logger.error(ex);
			logger.error("Erreur : Photo de profil");
		}
		return null;
	}

	public String encodePassword(String password) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(password, null);
	}

	public Boolean isConnectedUserAllowed(String url) {
		if (getConnectedUser() != null) {
			for (DroitFonctionnalite droitFonctionnalite : getConnectedUser().getIdDroit()
					.getDroitFonctionnaliteList()) {
				if (droitFonctionnalite.getIdFonctionnalite().getUrl().equals(url) && droitFonctionnalite.getActive()) {
					return true;
				}
			}
		}
		return false;
	}

	public String redirectionPanneauTravail() {
		return "/pages/carte/vehicules.xhtml";
	}

	public Map<String, String> getUserRolesMap() {
		return userRolesMap;
	}

	public void setUserRolesMap(Map<String, String> userRolesMap) {
		this.userRolesMap = userRolesMap;
	}

	public String getLangueLocale() {
		return langueLocale;
	}

	public void setLangueLocale(String langueLocale) {
		this.langueLocale = langueLocale;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void cancelModif() {
	}

	public String getAncienPassWord() {
		return ancienPassWord;
	}

	public void setAncienPassWord(String ancienPassWord) {
		this.ancienPassWord = ancienPassWord;
	}

	public String getNewPassWord() {
		return newPassWord;
	}

	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}

	public String getConfPassWord() {
		return confPassWord;
	}

	public void setConfPassWord(String confPassWord) {
		this.confPassWord = confPassWord;
	}

	public String getUrlAvatar() {
		return urlAvatar;
	}

	public void setUrlAvatar(String urlAvatar) {
		this.urlAvatar = urlAvatar;
	}

	/**
	 * @return the utilisateurFacade
	 */
	public UtilisateurFacadeLocal getUtilisateurFacade() {
		return utilisateurFacade;
	}

	/**
	 * @param utilisateurFacade
	 *            the utilisateurFacade to set
	 */
	public void setUtilisateurFacade(UtilisateurFacadeLocal utilisateurFacade) {
		this.utilisateurFacade = utilisateurFacade;
	}

	/**
	 * @return the connectedUser
	 */
	public Utilisateur getConnectedUser() {
		return connectedUser;
	}

	/**
	 * @param connectedUser
	 *            the connectedUser to set
	 */
	public void setConnectedUser(Utilisateur connectedUser) {
		this.connectedUser = connectedUser;
	}

	/**
	 * @return the generalSettingsFacade
	 */
	public GeneralSettingsFacadeLocal getGeneralSettingsFacade() {
		return generalSettingsFacade;
	}

	/**
	 * @param generalSettingsFacade
	 *            the generalSettingsFacade to set
	 */
	public void setGeneralSettingsFacade(GeneralSettingsFacadeLocal generalSettingsFacade) {
		this.generalSettingsFacade = generalSettingsFacade;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude
	 *            the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude
	 *            the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public List<EntretienVoiture> getNearTimeEntretiens() {
		return nearTimeEntretiens;
	}

	public void setNearTimeEntretiens(List<EntretienVoiture> nearTimeEntretiens) {
		this.nearTimeEntretiens = nearTimeEntretiens;
	}

}
