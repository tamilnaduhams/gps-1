/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import com.gps.entities.Vehicule;
import com.gps.dto.VehiculeMapPosition;
import com.gps.entities.VehiculePosition;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = VehiculesFacadeLocal.class)
public interface VehiculesFacadeLocal {
    
    void create(Object vehicule) ;

    void edit(Object vehicule) ;

    void remove(Object vehicule) ;

    Object find(Class entityClass, Object id);

    List<Vehicule> findAllVehicules();
    
    List<VehiculeMapPosition> findVehiculesPositions();

    List<Vehicule> findVehiculeByMatricule(String matricule);

    List<VehiculePosition> fetchVehiculeItinerary(Vehicule vehicule, Date dateDebut, Date dateFin);
}
