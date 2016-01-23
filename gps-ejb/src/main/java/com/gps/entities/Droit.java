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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "droit")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "Droit.findAll", query = "SELECT d FROM Droit d") })
public class Droit implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_droit")
	private Integer idDroit;
	@Size(max = 45)
	@Column(name = "libelle")
	private String libelle;
	@Column(name = "niveau")
	private Integer niveau;
	@OneToMany(mappedBy = "idDroit", fetch = FetchType.LAZY)
	private List<DroitFonctionnalite> droitFonctionnaliteList;
	@OneToMany(mappedBy = "idDroit", fetch = FetchType.LAZY)
	private List<Utilisateur> utilisateurList;

	public Droit() {
	}

	public Droit(Integer idDroit) {
		this.idDroit = idDroit;
	}

	public Integer getIdDroit() {
		return idDroit;
	}

	public void setIdDroit(Integer idDroit) {
		this.idDroit = idDroit;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Integer getNiveau() {
		return niveau;
	}

	public void setNiveau(Integer niveau) {
		this.niveau = niveau;
	}

	@XmlTransient
	public List<DroitFonctionnalite> getDroitFonctionnaliteList() {
		return droitFonctionnaliteList;
	}

	public void setDroitFonctionnaliteList(List<DroitFonctionnalite> droitFonctionnaliteList) {
		this.droitFonctionnaliteList = droitFonctionnaliteList;
	}

	@XmlTransient
	public List<Utilisateur> getUtilisateurList() {
		return utilisateurList;
	}

	public void setUtilisateurList(List<Utilisateur> utilisateurList) {
		this.utilisateurList = utilisateurList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idDroit != null ? idDroit.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Droit)) {
			return false;
		}
		Droit other = (Droit) object;
		if ((this.idDroit == null && other.idDroit != null)
				|| (this.idDroit != null && !this.idDroit.equals(other.idDroit))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.Droit[ idDroit=" + idDroit + " ]";
	}

}
