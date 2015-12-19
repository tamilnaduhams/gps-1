/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;


import com.gps.entities.Account;
import com.gps.entities.Service;
import com.gps.facades.local.AccountFacadeLocal;
import com.gps.facades.local.ServiceFacadeLocal;
import com.gps.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.log4j.Logger;

/**
 *
 * @author Amine Sagaama
 */
@ManagedBean(name = "serviceManagedBean")
@ViewScoped
public class ServiceManagedBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ServiceManagedBean.class);
    @EJB
    private ServiceFacadeLocal serviceFacade;
    @EJB
    private AccountFacadeLocal accountFacade;
    private List<Service> listServices;
    private Service selectedService;
    private List<Account> account, listeAccounts;
    private Account selectedAccount;

    public ServiceManagedBean() {
    }

    @PostConstruct
    public void init() {
        setSelectedService(new Service());
        findAllServices();
        findAllAccounts();
        account = new ArrayList<>();
        setSelectedAccount(new Account());
    }
    
    public void create() {
        try {
            serviceFacade.create(selectedService);
            JsfUtil.executeScript("dialogwsCreate.hide();");
            listServices.add(selectedService);
            JsfUtil.addSuccessMessage("Création service  avec succès.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error lors de création du service  !");
            LOGGER.error("Error lors de création du service  !");
        }
    }
    
       public void createAccount() {
        try {
            accountFacade.create(selectedAccount);
            JsfUtil.executeScript("dialogCompteCreate.hide();");
            if (!selectedService.getAccountList().contains(selectedAccount)) {
                selectedService.getAccountList().add(selectedAccount);
            }
            serviceFacade.edit(selectedService);
            JsfUtil.addSuccessMessage("Création compte  avec succès.");
            JsfUtil.executeScript("dialogCreate.hide();");

        } catch (Exception e) {
            System.out.println("Erreur: " + e);
        }
    }
       
       public void update() {
        try {
            serviceFacade.edit(selectedService);
            JsfUtil.executeScript("dialogEdit.hide();");
            JsfUtil.addSuccessMessage("Mise à jour du service  avec succès.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error lors de la mise à jour du service  !");
            LOGGER.error("Error lors de la mise à jour du service  !");
        }
    }

    public void updateAccount() {
        try {
            accountFacade.edit(selectedAccount);
            JsfUtil.executeScript("dialogEdit.hide();");
            JsfUtil.addSuccessMessage("Mise à jour du compte  avec succès.");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error lors de la mise à jour du compte  !");
            LOGGER.error("Error lors de la mise à jour du compte  !");
        }
    }

    public void destroy() {
        try {
            serviceFacade.remove(selectedService);
            JsfUtil.addSuccessMessage("Suppression du service effectuée avec succès.");
            listServices.remove(selectedService);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erreur au niveau de destroy()");
            LOGGER.error("Erreur au niveau de destroy()");
        }
    }

    public void destroyAccount() {
        try {
            accountFacade.remove(selectedAccount);
            if (selectedService.getAccountList().contains(selectedAccount)) {
                selectedService.getAccountList().remove(selectedAccount);
            }
            serviceFacade.edit(selectedService);
            JsfUtil.updateScript(":listUtilisateurForm");
            String msg = "Suppression du compte effectuée avec succès.";
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));

        } catch (Exception e) {
            System.out.println("Erreur: " + e);
        }
    }

    public boolean hasServiceAccount(Service service) {
        if (service != null) {
            return !serviceFacade.getAllAccountByService(service).isEmpty();
        }
        return false;
    }

    public void findAllServices() {
        try {
            setListServices(serviceFacade.findAllServices());

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void findAllAccounts() {
        try {
            setAccount(accountFacade.findAllAccounts());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }


    public void prepareCreate() {
        setSelectedService(new Service());
    }

    public void prepareCreateAccount() {
        setSelectedAccount(new Account());
    }

    public void prepareEdit(Service selectedService) {
        setSelectedService(selectedService);

    }

    public void prepareEditAccount(Account selectedAccount) {
        setSelectedAccount(selectedAccount);
    }
    
    public ServiceFacadeLocal getServiceFacade() {
        return serviceFacade;
    }

    public void setServiceFacade(ServiceFacadeLocal serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    public List<Service> getListServices() {
        return listServices;
    }

    public void setListServices(List<Service> listServices) {
        this.listServices = listServices;
    }

    public Service getSelectedService() {
        return selectedService;
    }

    public void setSelectedService(Service selectedService) {
        this.selectedService = selectedService;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public List<Account> getListeAccounts() {
        return listeAccounts;
    }

    public void setListeAccounts(List<Account> listeAccounts) {
        this.listeAccounts = listeAccounts;
    }

    public Account getSelectedAccount() {
        return selectedAccount;
    }

    public void setSelectedAccount(Account selectedAccount) {
        this.selectedAccount = selectedAccount;
    }

    public AccountFacadeLocal getAccountFacade() {
        return accountFacade;
    }

    public void setAccountFacade(AccountFacadeLocal accountFacade) {
        this.accountFacade = accountFacade;
    }
}
