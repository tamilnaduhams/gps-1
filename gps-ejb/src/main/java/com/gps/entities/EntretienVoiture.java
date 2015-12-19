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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "entretien_voiture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EntretienVoiture.findAll", query = "SELECT e FROM EntretienVoiture e")})
public class EntretienVoiture implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date_entretien")
    @Temporal(TemporalType.DATE)
    private Date dateEntretien;
    @Size(max = 200)
    @Column(name = "description")
    private String description;
    @Column(name = "kilometrage")
    private Integer kilometrage;
    @Column(name = "effectue")
    private Boolean effectue;
    @JoinColumn(name = "id_vehicule", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Vehicule idVehicule;
    
    

    public EntretienVoiture() {
    }

    public EntretienVoiture(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateEntretien() {
        return dateEntretien;
    }

    public void setDateEntretien(Date dateEntretien) {
        this.dateEntretien = dateEntretien;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Vehicule getIdVehicule() {
        return idVehicule;
    }

    public void setIdVehicule(Vehicule idVehicule) {
        this.idVehicule = idVehicule;
    }

    public Integer getKilometrage() {
        return kilometrage;
    }

    public void setKilometrage(Integer kilometrage) {
        this.kilometrage = kilometrage;
    }

    public Boolean getEffectue() {
        return effectue;
    }

    public void setEffectue(Boolean effectue) {
        this.effectue = effectue;
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
        if (!(object instanceof EntretienVoiture)) {
            return false;
        }
        EntretienVoiture other = (EntretienVoiture) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.EntretienVoiture[ id=" + id + " ]";
    }
    
}
