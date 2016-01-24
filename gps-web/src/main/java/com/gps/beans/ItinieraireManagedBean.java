/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

import com.gps.entities.Vehicule;
import com.gps.entities.VehiculePosition;
import com.gps.facades.local.VehiculesFacadeLocal;
import com.gps.helpers.CoordinatesHelper;

/**
 *
 * @author amine.sagaama@gmail.com
 */
@ManagedBean(name = "itineraireManagedBean")
@ViewScoped
public class ItinieraireManagedBean {

	private Vehicule selectedVehicule;

	private Date dateDebut;

	private Date dateFin;

	private MapModel vehiculePolylineModel;

	@EJB
	VehiculesFacadeLocal vehiculeFacade;

	private float distanceParcourue;

	/**
	 * Creates a new instance of ItinieraireManagedBean
	 */
	public ItinieraireManagedBean() {
	}

	public void buildItinerary() {
		if (selectedVehicule != null) {
			vehiculePolylineModel = new DefaultMapModel();
			List<VehiculePosition> vehiculePositions = vehiculeFacade.fetchVehiculeItinerary(selectedVehicule,
					dateDebut, dateFin);
			Polyline polyline = new Polyline();
			boolean firstIndex = true;
			distanceParcourue = 0;
			float oldLat = 0;
			float oldLong = 0;
			if (vehiculePositions != null) {
				oldLat = vehiculePositions.get(0).getLatitude();
				oldLong = vehiculePositions.get(0).getLongitude();
			}
			for (VehiculePosition vehiculePosition : vehiculePositions) {
				distanceParcourue += CoordinatesHelper.calculateDistance(oldLat, oldLong,
						vehiculePosition.getLatitude(), vehiculePosition.getLongitude());
				polyline.getPaths().add(new LatLng(vehiculePosition.getLongitude(), vehiculePosition.getLatitude()));
				polyline.setStrokeWeight(8);
				polyline.setStrokeColor("#FF9900");
				polyline.setStrokeOpacity(0.7);
				oldLat = vehiculePosition.getLatitude();
				oldLong = vehiculePosition.getLongitude();
			}
			vehiculePolylineModel.addOverlay(polyline);

		}
	}

	public Vehicule getSelectedVehicule() {
		return selectedVehicule;
	}

	public void setSelectedVehicule(Vehicule selectedVehicule) {
		this.selectedVehicule = selectedVehicule;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public MapModel getVehiculePolylineModel() {
		return vehiculePolylineModel;
	}

	public void setVehiculePolylineModel(MapModel vehiculePolylineModel) {
		this.vehiculePolylineModel = vehiculePolylineModel;
	}

	public float getDistanceParcourue() {
		return distanceParcourue;
	}

	public void setDistanceParcourue(float distanceParcourue) {
		this.distanceParcourue = distanceParcourue;
	}

}
