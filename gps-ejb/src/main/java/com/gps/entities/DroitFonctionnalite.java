/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "droit_fonctionnalite")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "DroitFonctionnalite.findAll", query = "SELECT d FROM DroitFonctionnalite d") })
public class DroitFonctionnalite implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_droit_fonctionnalite")
	private Integer idDroitFonctionnalite;
	@Column(name = "active")
	private Boolean active;
	@JoinColumn(name = "id_droit", referencedColumnName = "id_droit")
	@ManyToOne(fetch = FetchType.LAZY)
	private Droit idDroit;
	@JoinColumn(name = "id_fonctionnalite", referencedColumnName = "id_fonctionnalite")
	@ManyToOne(fetch = FetchType.LAZY)
	private Fonctionnalite idFonctionnalite;

	public DroitFonctionnalite() {
	}

	public DroitFonctionnalite(Integer idDroitFonctionnalite) {
		this.idDroitFonctionnalite = idDroitFonctionnalite;
	}

	public Integer getIdDroitFonctionnalite() {
		return idDroitFonctionnalite;
	}

	public void setIdDroitFonctionnalite(Integer idDroitFonctionnalite) {
		this.idDroitFonctionnalite = idDroitFonctionnalite;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Droit getIdDroit() {
		return idDroit;
	}

	public void setIdDroit(Droit idDroit) {
		this.idDroit = idDroit;
	}

	public Fonctionnalite getIdFonctionnalite() {
		return idFonctionnalite;
	}

	public void setIdFonctionnalite(Fonctionnalite idFonctionnalite) {
		this.idFonctionnalite = idFonctionnalite;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idDroitFonctionnalite != null ? idDroitFonctionnalite.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof DroitFonctionnalite)) {
			return false;
		}
		DroitFonctionnalite other = (DroitFonctionnalite) object;
		if ((this.idDroitFonctionnalite == null && other.idDroitFonctionnalite != null)
				|| (this.idDroitFonctionnalite != null
						&& !this.idDroitFonctionnalite.equals(other.idDroitFonctionnalite))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.DroitFonctionnalite[ idDroitFonctionnalite=" + idDroitFonctionnalite + " ]";
	}

}
