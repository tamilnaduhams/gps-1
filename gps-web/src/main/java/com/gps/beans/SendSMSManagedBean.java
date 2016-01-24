/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import com.gps.entities.Account;
import com.gps.entities.Service;
import com.gps.entities.Sms;
import com.gps.facades.local.AccountFacadeLocal;
import com.gps.facades.local.ServiceFacadeLocal;
import com.gps.facades.local.SmsFacadeLocal;
import com.gps.sms.modem.SendModem;
import com.gps.sms.ws.SendNexmo;
import com.gps.sms.ws.SendRedOxygen;
import com.gps.util.JsfUtil;

/**
 *
 * @author Amine Sagaama
 */
@ManagedBean
@ViewScoped
public class SendSMSManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(SendSMSManagedBean.class);
	@EJB
	private ServiceFacadeLocal serviceFacade;
	@EJB
	private AccountFacadeLocal accountFacade;
	@EJB
	private SmsFacadeLocal smsFacade;
	private Service selectedService;
	private List<Service> listService;
	private List<Service> listSortService = new ArrayList<>();
	private List<Account> listAccount;
	private List<Sms> listSms;
	private SendModem sendModem = new SendModem();
	private String texte;
	private Account selectedAccount;
	private SendNexmo sendNexmo = new SendNexmo();
	private SendRedOxygen sendRedOxygen = new SendRedOxygen();
	private String destinataires;
	private String description;
	private Sms sms = new Sms();

	@PostConstruct
	public void init() {
		setSms(new Sms());
		findAllSms();
		setSelectedService(new Service());
		findServiceByType();
		getSortListService();

	}

	public int getTailleSms(String str) {
		int i, j;
		j = 0;
		String str2 = "";
		for (i = 0; i < str.length(); i++) {
			str2 = str2 + str.charAt(i);
			if (str2.length() == 160) {
				j++;
				str2 = "";
			}
		}
		if (str2.length() < 160) {
			j++;
		}
		return j;
	}

	public List<Service> getSortListService() {
		try {
			setListSortService(serviceFacade.findSortService());
			return listSortService;

		} catch (Exception e) {
			return null;
		}
	}

	public Account getCompteDispo(Service service) {
		int i = 0;
		while (i <= service.getAccountList().size() && !service.getAccountList().isEmpty()) {
			if (("0.00000000".equals(service.getAccountList().get(i).getBalance())
					&& "Nexmo".equals(service.getNomService()))) {
				i++;
			} else {
				System.out.println("compte dispo+++" + service.getAccountList().get(i));
				return service.getAccountList().get(i);
			}
		}
		return null;

	}

	public void sendSMS() {
		boolean test = true;
		int i = 0;
		List<Service> list = new ArrayList<Service>();
		list = null;
		Service s = new Service();
		Account compte = new Account();
		compte = null;
		list = getListSortService();
		System.out.println("++++" + list);
		while (test && !list.isEmpty()) {
			s = list.get(i);
			if (getCompteDispo(s) != null) {
				compte = getCompteDispo(s);
			}
			if (compte != null) {
				test = false;
			} else {
				list.remove(list.get(i));
			}
		}

		System.out.println("Compte-----" + compte);
		if (compte != null) {
			System.out.println("Compte-----" + compte);
			if ("Modem".equals(compte.getIdService().getNomService())) {
				sendModem.setModemPort(compte.getModemPort());
				sendModem.setnPort(compte.getNumPort());
				sendModem.setNomConstructeur(compte.getNomConstructeur());
				sendModem.setVitesseMaxPort(compte.getVitesseMaxPort());
				sendModem.setnTelephone(destinataires);
				sendModem.setMessage(texte);
				try {
					sendModem.doIt();
					JsfUtil.executeScript("carDialog.hide();");
					FacesContext.getCurrentInstance().addMessage("Successfull", new FacesMessage(
							FacesMessage.SEVERITY_INFO, "Succés d'envoi Modem", "Succés d'envoi Modem"));
					sms.setDestinataires(destinataires);
					sms.setMessage(texte);
					sms.setDateEnvoi(new Date());
					sms.setAccountId(compte);
					sms.setStatut("Succès");
					sms.setDescription(description);
					sms.setNbreMessages(getTailleSms(texte));
					sms.setCoutSms(compte.getCoutSms());
					smsFacade.create(sms);
				} catch (Exception e) {
					FacesContext.getCurrentInstance().addMessage("Echec", new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Echec d'envoi Modem", "Echec d'envoi Modem"));
					sms.setDestinataires(destinataires);
					sms.setMessage(texte);
					sms.setDateEnvoi(new Date());
					sms.setAccountId(compte);
					sms.setStatut("Echec");
					sms.setDescription(description);
					smsFacade.create(sms);

				}

			}

			else {
				if ("Nexmo".equals(compte.getIdService().getNomService())) {
					sendNexmo.setApiKey(compte.getAuthToken());
					sendNexmo.setApiSecret(compte.getCompteWsSecret());
					sendNexmo.setSmsFrom(compte.getNumTel());
					sendNexmo.setSmsTo(destinataires);
					sendNexmo.setSmsText(texte);

					try {
						sendNexmo.send();
						if (sendNexmo.getCompte() != null) {
							compte.setBalance(sendNexmo.getCompte());
							accountFacade.edit(compte);
							JsfUtil.executeScript("carDialog.hide();");
							FacesContext.getCurrentInstance().addMessage("Successfull", new FacesMessage(
									FacesMessage.SEVERITY_INFO, "Succés d'envoi Nexmo", "Succés d'envoi Nexmo"));
							sms.setDestinataires(destinataires);
							sms.setMessage(texte);
							sms.setDateEnvoi(new Date());
							sms.setAccountId(compte);
							sms.setStatut("Succès");
							sms.setDescription(description);
							sms.setCoutSms(sendNexmo.getCoutSms());
							sms.setNbreMessages(getTailleSms(texte));
							smsFacade.create(sms);
						} else {
							FacesContext.getCurrentInstance().addMessage("Echec", new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Echec d'envoi 1", "Echec d'envoi 1"));
							sms.setDestinataires(destinataires);
							sms.setMessage(texte);
							sms.setDateEnvoi(new Date());
							sms.setAccountId(compte);
							sms.setStatut("Echec");
							sms.setDescription(description);
							sms.setCoutSms(sendNexmo.getCoutSms());
							smsFacade.create(sms);
						}
					} catch (Exception e) {
						FacesContext.getCurrentInstance().addMessage("Echec",
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec d'envoi", "Echec d'envoi"));
						sms.setDestinataires(destinataires);
						sms.setMessage(texte);
						sms.setDateEnvoi(new Date());
						sms.setAccountId(compte);
						sms.setStatut("Echec");
						sms.setDescription(description);
						sms.setCoutSms(sendNexmo.getCoutSms());
						smsFacade.create(sms);
					}
				} else {
					if ("RedOxygen".equals(compte.getIdService().getNomService())) {
						sendRedOxygen.setEmail(compte.getLogin());
						sendRedOxygen.setPassword(compte.getPassword());
						sendRedOxygen.setAccountId(compte.getAuthToken());
						sendRedOxygen.setMessage(sms.getMessage());
						sendRedOxygen.setRecipient(sms.getDestinataires());
						try {
							sendRedOxygen.SendSMS();
							JsfUtil.executeScript("carDialog.hide();");
							FacesContext.getCurrentInstance().addMessage("Successfull",
									new FacesMessage(FacesMessage.SEVERITY_INFO, "Succés d'envoi RedOxygen",
											"Succés d'envoi RedOxygen"));
						} catch (Exception e) {
							FacesContext.getCurrentInstance().addMessage("Echec", new FacesMessage(
									FacesMessage.SEVERITY_ERROR, "Echec d'envoi RedOxygen", "Echec d'envoi RedOxygen"));
							sms.setDestinataires(destinataires);
							sms.setMessage(texte);
							sms.setDateEnvoi(new Date());
							sms.setAccountId(compte);
							sms.setStatut("Echec");
							sms.setDescription(description);
							smsFacade.create(sms);
						}
					}
				}
			}
		} else {
			JsfUtil.executeScript("carDialog.hide();");
			FacesContext.getCurrentInstance().addMessage("Echec",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Echec d'envoi", "Echec d'envoi"));
			sms.setDestinataires(destinataires);
			sms.setMessage(texte);
			sms.setDateEnvoi(new Date());
			sms.setStatut("Echec");
			sms.setDescription(description);
			smsFacade.create(sms);

		}

	}

	public void findServiceByType() {
		try {
			setListService(serviceFacade.findServiceByTypeService("ServiceModem"));
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	public void findAccountService() {
		try {
			setListAccount(serviceFacade.getAllAccountByService(selectedService));

		} catch (Exception e) {
		}
	}

	public void findAllSms() {
		try {
			setListSms(smsFacade.findAllSms());
		} catch (Exception e) {
		}
	}

	public SendSMSManagedBean() {
	}

	public ServiceFacadeLocal getServiceFacade() {
		return serviceFacade;
	}

	public void setServiceFacade(ServiceFacadeLocal serviceFacade) {
		this.serviceFacade = serviceFacade;
	}

	public AccountFacadeLocal getAccountFacade() {
		return accountFacade;
	}

	public void setAccountFacade(AccountFacadeLocal accountFacade) {
		this.accountFacade = accountFacade;
	}

	public List<Service> getListService() {
		return listService;
	}

	public void setListService(List<Service> listService) {
		this.listService = listService;
	}

	public Service getSelectedService() {
		return selectedService;
	}

	public void setSelectedService(Service selectedService) {
		this.selectedService = selectedService;
	}

	public List<Account> getListAccount() {
		return listAccount;
	}

	public void setListAccount(List<Account> listAccount) {
		this.listAccount = listAccount;
	}

	public SendModem getSendModem() {
		return sendModem;
	}

	public void setSendModem(SendModem sendModem) {
		this.sendModem = sendModem;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

	public Account getSelectedAccount() {
		return selectedAccount;
	}

	public void setSelectedAccount(Account selectedAccount) {
		this.selectedAccount = selectedAccount;
	}

	public SendNexmo getSendNexmo() {
		return sendNexmo;
	}

	public void setSendNexmo(SendNexmo sendNexmo) {
		this.sendNexmo = sendNexmo;
	}

	public SendRedOxygen getSendRedOxygen() {
		return sendRedOxygen;
	}

	public void setSendRedOxygen(SendRedOxygen sendRedOxygen) {
		this.sendRedOxygen = sendRedOxygen;
	}

	public String getDestinataires() {
		return destinataires;
	}

	public void setDestinataires(String destinataires) {
		this.destinataires = destinataires;
	}

	public List<Service> getListSortService() {
		return listSortService;
	}

	public void setListSortService(List<Service> listSortService) {
		this.listSortService = listSortService;
	}

	public List<Sms> getListSms() {
		return listSms;
	}

	public void setListSms(List<Sms> listSms) {
		this.listSms = listSms;
	}

	public SmsFacadeLocal getSmsFacade() {
		return smsFacade;
	}

	public void setSmsFacade(SmsFacadeLocal smsFacade) {
		this.smsFacade = smsFacade;
	}

	public Sms getSms() {
		return sms;
	}

	public void setSms(Sms sms) {
		this.sms = sms;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
