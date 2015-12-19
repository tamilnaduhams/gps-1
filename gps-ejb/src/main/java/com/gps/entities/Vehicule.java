/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "vehicule")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vehicule.findAll", query = "SELECT v FROM Vehicule v")})
public class Vehicule implements Serializable {
    @JoinColumn(name = "chauffeur", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Chauffeur chauffeur;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 45)
    @Column(name = "matricule")
    private String matricule;
    @Column(name = "kilometrage_reel")
    private Integer kilometrageReel;
    @Column(name = "date_circulation")
    @Temporal(TemporalType.DATE)
    private Date dateCirculation;
    @JoinColumn(name = "boitier_affecte", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Boitier boitierAffecte;
    @Size(max = 60)
    @Column(name = "marque")
    private String marque;
    @OneToMany(mappedBy = "idVehicule", fetch = FetchType.LAZY)
    private List<SuiviKilométrique> suiviKilométriqueList;
    @OneToMany(mappedBy = "idVehicule", fetch = FetchType.LAZY)
    private List<EntretienVoiture> entretienVoitureList;

    public Vehicule() {
    }

    public Vehicule(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Integer getKilometrageReel() {
        return kilometrageReel;
    }

    public void setKilometrageReel(Integer kilometrageReel) {
        this.kilometrageReel = kilometrageReel;
    }

    public Date getDateCirculation() {
        return dateCirculation;
    }

    public void setDateCirculation(Date dateCirculation) {
        this.dateCirculation = dateCirculation;
    }

    public Boitier getBoitierAffecte() {
        return boitierAffecte;
    }

    public void setBoitierAffecte(Boitier boitierAffecte) {
        this.boitierAffecte = boitierAffecte;
    }

    @XmlTransient
    public List<SuiviKilométrique> getSuiviKilométriqueList() {
        return suiviKilométriqueList;
    }

    public void setSuiviKilométriqueList(List<SuiviKilométrique> suiviKilométriqueList) {
        this.suiviKilométriqueList = suiviKilométriqueList;
    }

    @XmlTransient
    public List<EntretienVoiture> getEntretienVoitureList() {
        return entretienVoitureList;
    }

    public void setEntretienVoitureList(List<EntretienVoiture> entretienVoitureList) {
        this.entretienVoitureList = entretienVoitureList;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehicule)) {
            return false;
        }
        Vehicule other = (Vehicule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.Vehicule[ id=" + id + " ]";
    }

    public Chauffeur getChauffeur() {
        return chauffeur;
    }

    public void setChauffeur(Chauffeur chauffeur) {
        this.chauffeur = chauffeur;
    }
    
}
