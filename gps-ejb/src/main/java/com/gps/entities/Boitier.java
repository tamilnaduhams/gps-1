/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "boitier")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Boitier.findAll", query = "SELECT b FROM Boitier b") })
public class Boitier implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 20)
	@Column(name = "num_boitier")
	private String numBoitier;
	@Basic(optional = false)
	@NotNull
	@Size(min = 1, max = 30)
	@Column(name = "type")
	private String type;
	@OneToMany(mappedBy = "boitierAffecte", fetch = FetchType.LAZY)
	private List<Vehicule> vehiculeList;

	public Boitier() {
	}

	public Boitier(Integer id) {
		this.id = id;
	}

	public Boitier(Integer id, String numBoitier, String type) {
		this.id = id;
		this.numBoitier = numBoitier;
		this.type = type;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@XmlTransient
	public List<Vehicule> getVehiculeList() {
		return vehiculeList;
	}

	public void setVehiculeList(List<Vehicule> vehiculeList) {
		this.vehiculeList = vehiculeList;
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
		if (!(object instanceof Boitier)) {
			return false;
		}
		Boitier other = (Boitier) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.Boitier[ id=" + id + " ]";
	}

}
