/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;


import com.gps.entities.Servicequeue;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Amine
 */
@Local(value = ServiceQueueFacadeLocal.class)
public interface ServiceQueueFacadeLocal {
    
    void create(Object c);

    void edit(Object c);

    void remove(Object c);
    
    List<Servicequeue> findAllServicesQ();
    
    Servicequeue findServicequeueByOrder(Integer order);
    
}
