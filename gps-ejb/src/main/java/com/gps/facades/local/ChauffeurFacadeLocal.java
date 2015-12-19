/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import com.gps.entities.Boitier;
import com.gps.entities.Chauffeur;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = ChauffeurFacadeLocal.class)
public interface ChauffeurFacadeLocal {
    
    void create(Object chauffeur) ;

    void edit(Object chauffeur) ;

    void remove(Object chauffeur) ;

    Object find(Class entityClass, Object id);

    List<Chauffeur> findAllChauffeurs();
}
