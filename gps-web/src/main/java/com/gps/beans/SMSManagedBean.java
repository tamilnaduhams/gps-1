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

import org.apache.log4j.Logger;

import com.gps.entities.Sms;
import com.gps.facades.local.SmsFacadeLocal;
import com.gps.util.JsfUtil;

/**
 *
 * @author Amine Sagaama
 */
@ManagedBean
@ViewScoped
public class SMSManagedBean implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(SMSManagedBean.class);
	@EJB
	private SmsFacadeLocal smsFacade;
	private Sms[] selectedListSms;
	private List<Sms> listSms;

	@PostConstruct
	public void init() {
		findAllSms();

	}

	public void findAllSms() {
		try {
			setListSms(smsFacade.findAllSms());

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage());
		}
	}

	public void destroy() {
		try {
			int i;
			smsFacade.deleteItems(selectedListSms);
			JsfUtil.addSuccessMessage("Suppression effectuée avec succès.");
			for (i = 0; i < selectedListSms.length; i++) {
				listSms.remove(selectedListSms[i]);
			}

		} catch (Exception e) {
			JsfUtil.addErrorMessage("Erreur au niveau de destroy()");
			LOGGER.error("Erreur au niveau de destroy()");
		}
	}

	public SmsFacadeLocal getSmsFacade() {
		return smsFacade;
	}

	public void setSmsFacade(SmsFacadeLocal smsFacade) {
		this.smsFacade = smsFacade;
	}

	public Sms[] getSelectedListSms() {
		return selectedListSms;
	}

	public void setSelectedListSms(Sms[] selectedListSms) {
		this.selectedListSms = selectedListSms;
	}

	public List<Sms> getListSms() {
		return listSms;
	}

	public void setListSms(List<Sms> listSms) {
		this.listSms = listSms;
	}

}
