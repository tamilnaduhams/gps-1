/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;


import com.gps.entities.Sms;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Amine Sagaama
 */
@Local(value = SmsFacadeLocal.class)
public interface SmsFacadeLocal {
    
    
    
    void create(Object c);

    void edit(Object c);

    void remove(Object c);
    
    public boolean deleteItems(Sms[] items);
    
    List<Sms> findAllSms();

    
    
}
