/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "vehicule_position")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "VehiculePosition.findAll", query = "SELECT v FROM VehiculePosition v") })
public class VehiculePosition implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Size(max = 100)
	@Column(name = "num_boitier")
	private String numBoitier;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "longitude")
	private Float longitude;
	@Column(name = "latitude")
	private Float latitude;
	@Column(name = "vitesse")
	private Float vitesse;
	@Column(name = "date_enregistrement")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateEnregistrement;

	public VehiculePosition() {
	}

	public VehiculePosition(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNumBoitier() {
		return numBoitier;
	}

	public void setNumBoitier(String numBoitier) {
		this.numBoitier = numBoitier;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getVitesse() {
		return vitesse;
	}

	public void setVitesse(Float vitesse) {
		this.vitesse = vitesse;
	}

	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof VehiculePosition)) {
			return false;
		}
		VehiculePosition other = (VehiculePosition) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.VehiculePosition[ id=" + id + " ]";
	}

}
