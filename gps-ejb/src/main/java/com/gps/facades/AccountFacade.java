
package com.gps.facades;


import com.gps.entities.Account;
import com.gps.entities.Service;
import com.gps.facades.local.AccountFacadeLocal;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author 21590495
 */
@Stateless
public class AccountFacade extends AbstractFacade implements AccountFacadeLocal, Serializable {
    
    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;
    Map conditions = new HashMap();

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<Account> findAllAccounts() {
         return findAll(Account.class, null, "");
    }

    @Override
    public List<Account> getAllAccountByService(Service service) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
