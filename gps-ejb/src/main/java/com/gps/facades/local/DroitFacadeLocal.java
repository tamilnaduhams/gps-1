/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;

import com.gps.entities.Droit;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = DroitFacadeLocal.class)
public interface DroitFacadeLocal {
    
    void create(Object authorities) ;

    void edit(Object authorities) ;

    void remove(Object authorities) ;

    Object find(Class entityClass, Object id);

    List<Droit> findAllDroits() ;

    Droit findDroitByIdDroit(Integer dro) ;
    
    List<Droit> findDroitByLibDroit(String dro) ;
    
}
