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
@Table(name = "suivi_kilom\u00e9trique")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SuiviKilom\u00e9trique.findAll", query = "SELECT s FROM SuiviKilom\u00e9trique s")})
public class SuiviKilométrique implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "kilometres")
    private Integer kilometres;
    @JoinColumn(name = "id_chauffeur", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Chauffeur idChauffeur;
    @JoinColumn(name = "id_vehicule", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicule idVehicule;

    public SuiviKilométrique() {
    }

    public SuiviKilométrique(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKilometres() {
        return kilometres;
    }

    public void setKilometres(Integer kilometres) {
        this.kilometres = kilometres;
    }

    public Chauffeur getIdChauffeur() {
        return idChauffeur;
    }

    public void setIdChauffeur(Chauffeur idChauffeur) {
        this.idChauffeur = idChauffeur;
    }

    public Vehicule getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Vehicule idVehicule) {
        this.idVehicule = idVehicule;
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
        if (!(object instanceof SuiviKilométrique)) {
            return false;
        }
        SuiviKilométrique other = (SuiviKilométrique) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.SuiviKilom\u00e9trique[ id=" + id + " ]";
    }
    
}
