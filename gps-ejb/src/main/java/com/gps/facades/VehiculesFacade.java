/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.gps.dto.VehiculeMapPosition;
import com.gps.entities.Vehicule;
import com.gps.entities.VehiculePosition;
import com.gps.facades.local.VehiculesFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless
public class VehiculesFacade extends AbstractFacade implements VehiculesFacadeLocal, Serializable {

	@PersistenceContext(unitName = "gpsPU")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	@Override
	public List<Vehicule> findAllVehicules() {
		return findAll(Vehicule.class, null, "");
	}

	@Override
	public List<VehiculeMapPosition> findVehiculesPositions() {
		// String query = "select new VehiculeMapPosition(vp, veh,
		// max(vp.dateEnregistrement)) vmp from VehiculePosition vp, Vehicule
		// veh where vp.numBoitier = veh.boitierAffecte.numBoitier group by
		// vmp.vehiculePosition.numBoitier " ;
		// String query = "select vp.numBoitier, max(vp.dateEnregistrement) from
		// VehiculePosition vp group by vp.numBoitier" ;
		// String query = "select new com.gps.dto.VehiculeMapPosition(vp,
		// min(vp.id)) from VehiculePosition vp group by vp.numBoitier" ;
		String query = "select max(vp.id) from VehiculePosition vp group by vp.numBoitier";
		List<Integer> ids = getEntityManager().createQuery(query).getResultList();
		if (ids != null && !ids.isEmpty()) {
			List<VehiculeMapPosition> vehiculeMapPositions = new ArrayList<>();
			for (Integer id : ids) {
				String queryString = "select new com.gps.dto.VehiculeMapPosition(vp, veh) from VehiculePosition vp, Vehicule veh where vp.id = "
						+ id.intValue() + " and veh.boitierAffecte.numBoitier = vp.numBoitier";
				VehiculeMapPosition vmp = getEntityManager().createQuery(queryString, VehiculeMapPosition.class)
						.getSingleResult();
				vehiculeMapPositions.add(vmp);
			}
			return vehiculeMapPositions;
		}
		return null;

	}

	@Override
	public List<Vehicule> findVehiculeByMatricule(String matricule) {
		Map condition = new HashMap();
		condition.put("matricule", " = '" + matricule + "'");
		return findByCriteria(condition, Vehicule.class, "", "");
	}

	@Override
	public List<VehiculePosition> fetchVehiculeItinerary(Vehicule vehicule, Date dateDebut, Date dateFin) {

		StringBuilder queryString = new StringBuilder("select vp from VehiculePosition vp where ");
		queryString.append("vp.numBoitier = ?1 ");
		if (dateDebut != null) {
			queryString.append(" AND vp.dateEnregistrement >= ?2 ");
		}
		if (dateDebut != null) {
			queryString.append(" AND vp.dateEnregistrement <= ?3 ");
		}
		queryString.append(" order by  vp.dateEnregistrement ASC");
		Query query = getEntityManager().createQuery(queryString.toString()).setParameter(1,
				vehicule.getBoitierAffecte().getNumBoitier());
		if (dateDebut != null) {
			query.setParameter(2, dateDebut);
		}
		if (dateFin != null) {
			query.setParameter(3, dateFin);
		}

		return query.getResultList();
	}
}
