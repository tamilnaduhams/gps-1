/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gps.facades.local;

import com.gps.entities.DroitFonctionnalite;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Local(value = DroitFonctionnaliteFacadeLocal.class)
public interface DroitFonctionnaliteFacadeLocal {
    
    void create(Object droitFonctionnalite) ;

    void edit(Object droitFonctionnalite) ;

    void remove(Object droitFonctionnalite) ;

    Object find(Class entityClass, Object id);

    List<DroitFonctionnalite> findAllDroitFonctionnalites() ;
    
    List<DroitFonctionnalite> findDroitFonctionnalitesByFonctionnalite(Integer dro, Integer mn) ;
    
    List<DroitFonctionnalite> findDroitFonctionnalitesByDroit(Integer dro) ;
    
    public List<DroitFonctionnalite> findDroitFonctionnalitesByFct(Integer fct) ;
    
    List<DroitFonctionnalite> findDroitFonctionnalitesByMenu(Integer menu) ;
    
    List<DroitFonctionnalite> findDroitFonctionnalitesByMenuAndDroit(Integer menu, Integer dro) ;
    
    boolean findEtatByFonctionnaliteAndDroit(Integer fct, Integer dro) ;
    
    DroitFonctionnalite findDroitFonctionnaliteByFonctionnaliteAndDroit(Integer fct, Integer dro) ;
    
}
