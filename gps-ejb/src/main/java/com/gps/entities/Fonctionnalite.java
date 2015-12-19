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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "fonctionnalite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fonctionnalite.findAll", query = "SELECT f FROM Fonctionnalite f")})
public class Fonctionnalite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_fonctionnalite")
    private Integer idFonctionnalite;
    @Size(max = 45)
    @Column(name = "libelle")
    private String libelle;
    @Size(max = 45)
    @Column(name = "url")
    private String url;
    @Size(max = 45)
    @Column(name = "description")
    private String description;
    @OneToMany(mappedBy = "idFonctionnalite", fetch = FetchType.LAZY)
    private List<DroitFonctionnalite> droitFonctionnaliteList;
    @JoinColumn(name = "id_menu", referencedColumnName = "id_menu")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu idMenu;

    public Fonctionnalite() {
    }

    public Fonctionnalite(Integer idFonctionnalite) {
        this.idFonctionnalite = idFonctionnalite;
    }

    public Integer getIdFonctionnalite() {
        return idFonctionnalite;
    }

    public void setIdFonctionnalite(Integer idFonctionnalite) {
        this.idFonctionnalite = idFonctionnalite;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @XmlTransient
    public List<DroitFonctionnalite> getDroitFonctionnaliteList() {
        return droitFonctionnaliteList;
    }

    public void setDroitFonctionnaliteList(List<DroitFonctionnalite> droitFonctionnaliteList) {
        this.droitFonctionnaliteList = droitFonctionnaliteList;
    }

    public Menu getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(Menu idMenu) {
        this.idMenu = idMenu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFonctionnalite != null ? idFonctionnalite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fonctionnalite)) {
            return false;
        }
        Fonctionnalite other = (Fonctionnalite) object;
        if ((this.idFonctionnalite == null && other.idFonctionnalite != null) || (this.idFonctionnalite != null && !this.idFonctionnalite.equals(other.idFonctionnalite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.Fonctionnalite[ idFonctionnalite=" + idFonctionnalite + " ]";
    }
    
}
