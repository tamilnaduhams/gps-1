/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "vehicule_consommation")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "VehiculeConsommation.findAll", query = "SELECT v FROM VehiculeConsommation v") })
public class VehiculeConsommation implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name = "id_vehicule")
	private Integer idVehicule;
	@Size(max = 45)
	@Column(name = "type_fuel")
	private String typeFuel;
	@Column(name = "nbr_litre")
	private Integer nbrLitre;
	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "prix_litre")
	private Float prixLitre;

	public VehiculeConsommation() {
	}

	public VehiculeConsommation(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdVehicule() {
		return idVehicule;
	}

	public void setIdVehicule(Integer idVehicule) {
		this.idVehicule = idVehicule;
	}

	public String getTypeFuel() {
		return typeFuel;
	}

	public void setTypeFuel(String typeFuel) {
		this.typeFuel = typeFuel;
	}

	public Integer getNbrLitre() {
		return nbrLitre;
	}

	public void setNbrLitre(Integer nbrLitre) {
		this.nbrLitre = nbrLitre;
	}

	public Float getPrixLitre() {
		return prixLitre;
	}

	public void setPrixLitre(Float prixLitre) {
		this.prixLitre = prixLitre;
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
		if (!(object instanceof VehiculeConsommation)) {
			return false;
		}
		VehiculeConsommation other = (VehiculeConsommation) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.VehiculeConsommation[ id=" + id + " ]";
	}

}
