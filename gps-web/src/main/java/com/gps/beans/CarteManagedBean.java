/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.event.map.StateChangeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.gps.dto.VehiculeMapPosition;
import com.gps.facades.local.VehiculesFacadeLocal;

/**
 *
 * @author amine.sagaama@gmail.com
 */
@ManagedBean(name = "carteManagedBean")
@ViewScoped
public class CarteManagedBean implements Serializable {

	private static final Logger logger = Logger.getLogger(CarteManagedBean.class);

	@EJB
	private VehiculesFacadeLocal vehiculesFacade;

	private List<VehiculeMapPosition> vehiculeMapPositions;

	private MapModel allVehiculesMapModel;

	private VehiculeMapPosition selectedVehiculeMapPosition;

	private int refreshPeriod = 30;

	private int zoom = 7;

	private double centerLongitude;

	private double centerLatitude;

	@ManagedProperty(value = "#{login}")
	private Login login;

	/**
	 * Creates a new instance of CarteManagedBean
	 */
	public CarteManagedBean() {
	}

	@PostConstruct
	public void init() {
		buildAllVehiculesMapModel();
		centerLongitude = Double.parseDouble(login.getLongitude());
		centerLatitude = Double.parseDouble(login.getLatitude());
	}

	public void onStateChange(StateChangeEvent event) {
		if (event != null) {
			zoom = event.getZoomLevel();
			centerLongitude = event.getCenter().getLat();
			centerLatitude = event.getCenter().getLng();
		}

	}

	public void buildAllVehiculesMapModel() {
		System.out.println("Refreshing poll on " + new SimpleDateFormat("mm:ss").format(System.currentTimeMillis()));
		allVehiculesMapModel = new DefaultMapModel();
		vehiculeMapPositions = vehiculesFacade.findVehiculesPositions();
		for (VehiculeMapPosition vehiculeMapPosition : vehiculeMapPositions) {
			Marker marker = new Marker(
					new LatLng(vehiculeMapPosition.getVehiculePosition().getLongitude(),
							vehiculeMapPosition.getVehiculePosition().getLatitude()),
					"Matricule: " + vehiculeMapPosition.getVehicule().getMatricule() + " Boitier : "
							+ vehiculeMapPosition.getVehicule().getBoitierAffecte().getNumBoitier());
			marker.setData(vehiculeMapPosition);
			marker.setIcon(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath()
					+ "/resources/images/lorrygreen.png");
			allVehiculesMapModel.addOverlay(marker);
		}
	}

	public void onMarkerSelect(OverlaySelectEvent event) {
		Marker marker = (Marker) event.getOverlay();
		if (marker != null) {
			selectedVehiculeMapPosition = (VehiculeMapPosition) marker.getData();
		}
	}

	public List<VehiculeMapPosition> getVehiculeMapPositions() {
		return vehiculeMapPositions;
	}

	public void setVehiculeMapPositions(List<VehiculeMapPosition> vehiculeMapPositions) {
		this.vehiculeMapPositions = vehiculeMapPositions;
	}

	public MapModel getAllVehiculesMapModel() {
		return allVehiculesMapModel;
	}

	public void setAllVehiculesMapModel(MapModel allVehiculesMapModel) {
		this.allVehiculesMapModel = allVehiculesMapModel;
	}

	public VehiculeMapPosition getSelectedVehiculeMapPosition() {
		return selectedVehiculeMapPosition;
	}

	public void setSelectedVehiculeMapPosition(VehiculeMapPosition selectedVehiculeMapPosition) {
		this.selectedVehiculeMapPosition = selectedVehiculeMapPosition;
	}

	public int getRefreshPeriod() {
		return refreshPeriod;
	}

	public void setRefreshPeriod(int refreshPeriod) {
		this.refreshPeriod = refreshPeriod;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public void setCenterLongitude(int centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public void setCenterLatitude(int centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public Login getLogin() {
		return login;
	}

	public void setLogin(Login login) {
		this.login = login;
	}

	public double getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(double centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public double getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(double centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

}
