/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.facades.local;


import com.gps.entities.Account;
import com.gps.entities.Service;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author 21590495
 */
@Local(value = AccountFacadeLocal.class)
public interface AccountFacadeLocal {
    void create(Object c);

    void edit(Object c);

    void remove(Object c);
    
    public Object find(Class entityClass, Object id);
    
    public List<Account> findAllAccounts();
    
    public List<Account> getAllAccountByService(Service service);
    
    
}
