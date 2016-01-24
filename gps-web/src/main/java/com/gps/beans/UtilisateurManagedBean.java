package com.gps.beans;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import com.gps.entities.Utilisateur;
import com.gps.facades.local.UtilisateurFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "utilisateurManagedBean")
@ViewScoped
public class UtilisateurManagedBean implements Serializable {

	private static final Logger logger = Logger.getLogger(UtilisateurManagedBean.class);

	@EJB
	private UtilisateurFacadeLocal utilisateurFacade;

	@ManagedProperty(value = "#{login}")
	private Login login;
	@ManagedProperty(value = "#{page}")
	private PageUri page;

	private Utilisateur selectedUtilisateur;
	private List<Utilisateur> listUtilisateur;
	private String newPassWord, confPassWord;

	public UtilisateurManagedBean() {
	}

	@PostConstruct
	public void init() {
		setSelectedUtilisateur(new Utilisateur());
		findAllUtilisateurs();
	}

	public void prepareCreate() {
		newPassWord = null;
		confPassWord = null;
		setSelectedUtilisateur(new Utilisateur());
	}

	public void prepareEdit(Utilisateur selectedUtilisateur) {
		setSelectedUtilisateur(selectedUtilisateur);
	}

	public void create() {
		if (confPassWord != null && newPassWord != null && !confPassWord.isEmpty() && !newPassWord.isEmpty()
				&& confPassWord.equals(newPassWord)) {
			getSelectedUtilisateur().setMotPasse(encodePassword(newPassWord));
			try {
				if (checkIdentifiantUnicity(getSelectedUtilisateur().getIdentifiant())
						&& checkMailUnicity(getSelectedUtilisateur().getEmail())
						&& checkMobileUnicity(getSelectedUtilisateur().getMobile())) {
					getSelectedUtilisateur().setGrowl(2_000);
					getSelectedUtilisateur().setAvatar("default.png");
					getUtilisateurFacade().create(getSelectedUtilisateur());
					listUtilisateur.add(selectedUtilisateur);
					JsfUtil.addSuccessMessage(
							"L'opération de création d'un nouveau utilisateur a été effectuée avec succès !");
				}
			} catch (Exception e) {
				JsfUtil.addErrorMessage("L'opération de création d'un nouveau utilisateur a échoué !!!");
				logger.error(e);
			}
		} else {
			JsfUtil.addErrorMessage("Les deux nouveaux mots de passes que vous avez inséré ne se correspondent pas !!");
		}
	}

	public void update() {
		try {
			if (checkIdentifiantUnicityUpdate(getSelectedUtilisateur())
					&& checkMailUnicityUpdate(getSelectedUtilisateur())
					&& checkMobileUnicityUpdate(getSelectedUtilisateur())) {
				getUtilisateurFacade().edit(getSelectedUtilisateur());
				JsfUtil.addSuccessMessage("La mise à jour de l'utilisateur a été effectuée avec succès !");
			}
		} catch (Exception e) {
			JsfUtil.addErrorMessage("L'opération de mise à jour de l'utilisateur a échoué !!!");
			logger.error("Les deux nouveaux mots de passes que vous avez inséré ne se correspondent pas !!");
		}
	}

	public void update(Utilisateur connectedCollaborateur) {
		try {
			getUtilisateurFacade().edit(connectedCollaborateur);
			login.setConnectedUser(connectedCollaborateur);
			JsfUtil.addSuccessMessage("Mise à jour collaborateur avec succès.");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Error lors de mise à jour de collaborateur!");
			logger.error("Error lors de mise à jour de collaborateur!");
		}
	}

	public void disableUtilisateur(Utilisateur utilis) {
		try {
			getUtilisateurFacade().edit(utilis);
			if (utilis.getEnabled())
				JsfUtil.addSuccessMessage("L'activation du collaborateur a été effectuée avec succès !");
			else
				JsfUtil.addSuccessMessage("La désactivation du collaborateur a été effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("Error lors de l'opération d'activation/désactivation du collaborateur !!!");
			logger.error("Error lors de désactivation du collaborateur!" + e);
		}
	}

	public void destroy() {
		try {
			getUtilisateurFacade().remove(getSelectedUtilisateur());
			listUtilisateur.remove(selectedUtilisateur);
			JsfUtil.addSuccessMessage("La suppression du collaborateur a été effectuée avec succès !");
		} catch (Exception e) {
			JsfUtil.addErrorMessage("La suppression du collaborateur a échoué !!!");
			logger.error("Erreur au niveau de destroy()");
		}
	}

	public void findEnabledCollaborateurs() {
		try {
			setListUtilisateur(utilisateurFacade.findEnabledUtilisateurs());
		} catch (Exception ex) {
			logger.error("Erreur au niveau de findEnabledCollaborateurs()");
		}
	}

	public void findAllUtilisateurs() {
		try {
			setListUtilisateur(getUtilisateurFacade().findAllUtilisateurs());
		} catch (Exception ex) {
			logger.error("Erreur au niveau de findALLCollaborateurs()");
		}
	}

	public String findAvatar(Utilisateur utilisateur) throws MalformedURLException, IOException {

		String path = page.requestPath();
		String imgUrl;
		if (utilisateur != null && !StringUtils.isBlank(utilisateur.getAvatar())) {
			imgUrl = path + "/static/images/avatar/" + utilisateur.getAvatar();
			URL url = new URL(imgUrl);
			HttpURLConnection http = (HttpURLConnection) url.openConnection();
			if (http.getResponseCode() == 200) {
				return imgUrl;
			}
		}
		return "/resources/images/default.jpg";

	}

	public Boolean checkIdentifiantUnicity(String username) throws Exception {
		if (username != null && !username.isEmpty()
				&& getUtilisateurFacade().findUtilisateurByIdentifiant(username) != null) {
			if (!utilisateurFacade.findUtilisateurByIdentifiant(username).equals(selectedUtilisateur)) {
				JsfUtil.addErrorMessage("Cet identifiant existe déjà !!");
				return false;
			}
		}
		return true;
	}

	public Boolean checkMobileUnicity(String mobile) throws Exception {
		if (mobile != null && !mobile.isEmpty() && !utilisateurFacade.findUtilisateursByMobile(mobile).isEmpty()) {
			if (!utilisateurFacade.findUtilisateursByMobile(mobile).get(0).equals(selectedUtilisateur)) {
				JsfUtil.addErrorMessage("Mobile déjà existant !!");
				return false;
			}
		}
		return true;
	}

	public Boolean checkMailUnicity(String mail) throws Exception {
		if (mail != null && !mail.isEmpty() && !utilisateurFacade.findUtilisateursByEmail(mail).isEmpty()) {
			if (!utilisateurFacade.findUtilisateursByEmail(mail).get(0).equals(selectedUtilisateur)) {
				JsfUtil.addErrorMessage("Email déjà existant !!");
				return false;
			}
		}
		return true;
	}

	public Boolean checkIdentifiantUnicityUpdate(Utilisateur selectedUtilisateur) throws Exception {
		if (selectedUtilisateur.getIdentifiant() != null && !selectedUtilisateur.getIdentifiant().isEmpty()) {
			Utilisateur utilisateur = getUtilisateurFacade()
					.findUtilisateurByIdentifiant(selectedUtilisateur.getIdentifiant());
			if (utilisateur != null && utilisateur.getIdUtilisateur() != selectedUtilisateur.getIdUtilisateur()) {
				JsfUtil.addErrorMessage("Identifiant déjà existant !!");
				return false;
			}
		}
		return true;
	}

	public Boolean checkMobileUnicityUpdate(Utilisateur selectedCollaborateur) throws Exception {
		if (selectedCollaborateur.getMobile() != null && !selectedCollaborateur.getMobile().isEmpty()) {
			List<Utilisateur> listUtilisateurs = utilisateurFacade
					.findUtilisateursByMobile(selectedCollaborateur.getMobile());
			if (!listUtilisateurs.isEmpty()
					&& listUtilisateurs.get(0).getIdUtilisateur() != selectedCollaborateur.getIdUtilisateur()) {
				JsfUtil.addErrorMessage("Mobile déjà existant");
				return false;
			}
		}
		return true;
	}

	public Boolean checkMailUnicityUpdate(Utilisateur selectedCollaborateur) throws Exception {
		if (selectedCollaborateur.getEmail() != null && !selectedCollaborateur.getEmail().isEmpty()) {
			List<Utilisateur> listUtilisateurs = utilisateurFacade
					.findUtilisateursByEmail(selectedCollaborateur.getEmail());
			if (!listUtilisateurs.isEmpty()
					&& listUtilisateurs.get(0).getIdUtilisateur() != selectedCollaborateur.getIdUtilisateur()) {
				JsfUtil.addErrorMessage("Email déjà existant");
				return false;
			}
		}
		return true;
	}

	public String encodePassword(String password) {
		PasswordEncoder encoder = new Md5PasswordEncoder();
		return encoder.encodePassword(password, null);
	}

	/**
	 * @return the login
	 */
	public Login getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(Login login) {
		this.login = login;
	}

	/**
	 * @return the page
	 */
	public PageUri getPage() {
		return page;
	}

	/**
	 * @param page
	 *            the page to set
	 */
	public void setPage(PageUri page) {
		this.page = page;
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
	 * @return the selectedUtilisateur
	 */
	public Utilisateur getSelectedUtilisateur() {
		return selectedUtilisateur;
	}

	/**
	 * @param selectedUtilisateur
	 *            the selectedUtilisateur to set
	 */
	public void setSelectedUtilisateur(Utilisateur selectedUtilisateur) {
		this.selectedUtilisateur = selectedUtilisateur;
	}

	/**
	 * @return the listUtilisateur
	 */
	public List<Utilisateur> getListUtilisateur() {
		return listUtilisateur;
	}

	/**
	 * @param listUtilisateur
	 *            the listUtilisateur to set
	 */
	public void setListUtilisateur(List<Utilisateur> listUtilisateur) {
		this.listUtilisateur = listUtilisateur;
	}

	@FacesConverter(forClass = Utilisateur.class)
	public static class CollaborateurManagedBeanConverter implements Converter {

		@Override
		public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
			if (value == null || value.length() == 0) {
				return null;
			}
			UtilisateurManagedBean ManagedBean = (UtilisateurManagedBean) facesContext.getApplication().getELResolver()
					.getValue(facesContext.getELContext(), null, "utilisateurManagedBean");
			return ManagedBean.getUtilisateurFacade().find(Utilisateur.class, getKey(value));
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
			if (object instanceof Utilisateur) {
				Utilisateur o = (Utilisateur) object;
				return getStringKey(o.getIdUtilisateur());
			} else {
				throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName()
						+ "; expected type: " + Utilisateur.class.getName());
			}
		}
	}
}
