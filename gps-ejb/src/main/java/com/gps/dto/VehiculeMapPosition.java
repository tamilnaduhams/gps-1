/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.dto;

import com.gps.entities.Vehicule;
import com.gps.entities.VehiculePosition;
import java.util.Date;

/**
 *
 * @author amine.sagaama@gmail.com
 */
public class VehiculeMapPosition {
    
    private VehiculePosition vehiculePosition;
    
    private Vehicule vehicule;

    private Date dateEnregistrement;

    public VehiculeMapPosition(VehiculePosition vehiculePosition, Vehicule vehicule) {
        this.vehiculePosition = vehiculePosition;
        this.vehicule = vehicule;
    }
    
    

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }
        
    public VehiculePosition getVehiculePosition() {
        return vehiculePosition;
    }

    public void setVehiculePosition(VehiculePosition vehiculePosition) {
        this.vehiculePosition = vehiculePosition;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public void setVehicule(Vehicule vehicule) {
        this.vehicule = vehicule;
    }
    
    
    
    
}
