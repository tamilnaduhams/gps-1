/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.joda.time.DateTime;
import org.joda.time.Days;

import com.gps.constants.GeneralSettingsConstants;
import com.gps.entities.EntretienVoiture;
import com.gps.facades.local.EntretienFacadeLocal;
import com.gps.facades.local.GeneralSettingsFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */
@Stateless
public class EntretienFacade extends AbstractFacade implements EntretienFacadeLocal, Serializable {

	@EJB
	GeneralSettingsFacadeLocal generalSettingsFacade;

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<EntretienVoiture> findAllEntretiens() {
		return findAll(EntretienVoiture.class, null, "");
	}

	@Override
	public List<EntretienVoiture> findAllNearEntreiens() {
		List<EntretienVoiture> realEv = new ArrayList<>();
		List<EntretienVoiture> ev = em.createQuery("select ev from EntretienVoiture ev where ev.effectue = 0  ")
				.getResultList();

		for (EntretienVoiture entretienVoiture : ev) {
			if (entretienVoiture.getDateEntretien() != null) {
				int intervalle = generalSettingsFacade
						.findGeneralSettingsBySettingsCode(GeneralSettingsConstants.DAYS_INTERVALLE_GS_CODE, 10);
				int days = Days.daysBetween(new DateTime(new Date()), new DateTime(entretienVoiture.getDateEntretien()))
						.getDays();
				if (days <= intervalle) {
					realEv.add(entretienVoiture);
				}
			}
			if (entretienVoiture.getKilometrage() != null) {
				int kilos = generalSettingsFacade.findGeneralSettingsBySettingsCode(
						GeneralSettingsConstants.KILOMETERS_INTERVALLE_GS_CODE, 1500);
				int intervaleKilometres = entretienVoiture.getKilometrage()
						- entretienVoiture.getIdVehicule().getKilometrageReel();
				if (intervaleKilometres <= kilos) {
					realEv.add(entretienVoiture);
				}
			}

		}
		return realEv;

	}
}
