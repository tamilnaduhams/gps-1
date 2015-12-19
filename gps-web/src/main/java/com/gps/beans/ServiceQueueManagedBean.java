/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;


import com.gps.entities.Service;
import com.gps.entities.Servicequeue;
import com.gps.facades.local.ServiceFacadeLocal;
import com.gps.facades.local.ServiceQueueFacadeLocal;
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
@ManagedBean
@ViewScoped
public class ServiceQueueManagedBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(ServiceQueueManagedBean.class);
    @EJB
    private ServiceQueueFacadeLocal serviceQFacade;
    @EJB
    private ServiceFacadeLocal serviceFacade;
    private List<Servicequeue> listServicesQ;
    private List<Service> listService, listAdd, listAdd2;
    private Servicequeue selectedServiceQ;
    private ServiceManagedBean serviceM;
    private Integer id;
    private Integer selectedOrder;
    private List<Integer> ordres = new ArrayList<Integer>();
    private boolean test1 = false;
    
    
     @PostConstruct
    public void init() {
        setSelectedServiceQ(new Servicequeue());
        findAllServicesQ();
        findAllServices();
        findIfNotExist();
    }

    public void findAllServices() {
        try {
            setListService(serviceFacade.findAllServices());

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
    }

    public void findIfNotExist() {
        boolean test = true;
        listAdd = new ArrayList<>();
        for (Service service : listService) {
            test = true;
            for (Servicequeue serviceq : listServicesQ) {
                if (service.getIdService() == serviceq.getIdServicequeue()) {
                    test = false;
                }} if (test) {
                listAdd.add(service);
            }}}

    public void prepareCreate() {
        setSelectedServiceQ(new Servicequeue());
    }

    public Boolean isOrderUnique(Integer order) {
        Servicequeue s = serviceQFacade.findServicequeueByOrder(selectedServiceQ.getOrdre());
        if (s != null && !s.getIdServicequeue().equals(selectedServiceQ.getIdServicequeue())) {
            return false;
        }
        return true;
    }

    public void update() {
        String msg = "Cet ordre est dèjà existant";
        try {
            if (isOrderUnique(selectedServiceQ.getOrdre())) {
                serviceQFacade.edit(selectedServiceQ);
                JsfUtil.executeScript("dialogEdit.hide();");
                JsfUtil.addSuccessMessage("Mise à jour du service  avec succès.");
            } else {
                JsfUtil.addErrorMessage(msg);
            }
            findAllServicesQ();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error lors de la mise à jour du service  !");
            LOGGER.error("Error lors de la mise à jour du service  !");
        }
    }

    public void destroy() {
        try {
            serviceQFacade.remove(selectedServiceQ);
            JsfUtil.addSuccessMessage("Suppression du service effectuée avec succès.");
            listServicesQ.remove(selectedServiceQ);
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Erreur au niveau de destroy()");
            LOGGER.error("Erreur au niveau de destroy()");
        }
    }

    public void create() {

        try {
            String msg = "Cet ordre est dèjà existant";
            if (isOrderUnique(selectedServiceQ.getOrdre())) {
                serviceQFacade.create(selectedServiceQ);
                JsfUtil.addSuccessMessage("Création du service queue  avec succès.");
                JsfUtil.executeScript("dialogwsCreate.hide();");
                selectedServiceQ.setService((Service) serviceFacade.find(Service.class, selectedServiceQ.getIdServicequeue()));
                listServicesQ.add(selectedServiceQ);
                serviceQFacade.edit(selectedServiceQ);
                findAllServices();
                findIfNotExist();
            } else {
                JsfUtil.addErrorMessage(msg);
                }
              findAllServicesQ();
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Error lors de création du service queue  !");
            LOGGER.error("Error lors de création du service queue  !");
            System.out.println("ID Q : " + selectedServiceQ.getIdServicequeue());
        }
    }
    
    public void refresh(){
        findAllServices();
        findAllServicesQ();
        findIfNotExist();
    }
    

    public void showErreur() {
        String msg = "La liste des services est vide";
        findAllServices();
        findAllServicesQ();
        findIfNotExist();
        if (listAdd.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, null));
        } else {
            
            JsfUtil.executeScript("dialogwsCreate.show();");
        }

    }

    public void findAllServicesQ() {
        try {
            setListServicesQ(serviceQFacade.findAllServicesQ());
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }

    }

    public Servicequeue getSelectedServiceQ() {
        return selectedServiceQ;
    }

    public void setSelectedServiceQ(Servicequeue selectedServiceQ) {
        this.selectedServiceQ = selectedServiceQ;
    }

    public ServiceQueueFacadeLocal getServiceQFacade() {
        return serviceQFacade;
    }

    public void setServiceQFacade(ServiceQueueFacadeLocal serviceQFacade) {
        this.serviceQFacade = serviceQFacade;
    }

    public List<Servicequeue> getListServicesQ() {
        return listServicesQ;
    }

    public void setListServicesQ(List<Servicequeue> listServicesQ) {
        this.listServicesQ = listServicesQ;
    }

    public ServiceQueueManagedBean() {
    }

    public List<Service> getListAdd() {
        return listAdd;
    }

    public void setListAdd(List<Service> listAdd) {
        this.listAdd = listAdd;
    }

    public Integer getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(Integer selectedOrder) {
        this.selectedOrder = selectedOrder;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ServiceManagedBean getServiceM() {
        return serviceM;
    }

    public void setServiceM(ServiceManagedBean serviceM) {
        this.serviceM = serviceM;
    }

    public List<Integer> getOrdres() {
        return ordres;
    }

    public void setOrdres(List<Integer> ordres) {
        this.ordres = ordres;
    }

    public boolean isTest1() {
        return test1;
    }

    public void setTest1(boolean test1) {
        this.test1 = test1;
    }
    
    public ServiceFacadeLocal getServiceFacade() {
        return serviceFacade;
    }

    public void setServiceFacade(ServiceFacadeLocal serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    public List<Service> getListService() {
        return listService;
    }

    public void setListService(List<Service> listService) {
        this.listService = listService;
    }

    public List<Service> getListAdd2() {
        return listAdd2;
    }

    public void setListAdd2(List<Service> listAdd2) {
        this.listAdd2 = listAdd2;
    }
    
    

}
