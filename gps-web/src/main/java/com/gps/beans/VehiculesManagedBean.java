package com.gps.beans;

import static com.gps.beans.DroitManagedBean.getLogger;
import com.gps.entities.Vehicule;
import com.gps.facades.local.VehiculesFacadeLocal;
import com.gps.util.JsfUtil;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.log4j.Logger;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "vehiculesManagedBean")
@ViewScoped
public class VehiculesManagedBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(VehiculesManagedBean.class);

    @EJB
    private VehiculesFacadeLocal vehiculesFacade;
    private Vehicule selectedVehicule;
    private List<Vehicule> listVehicule;
    

    public VehiculesManagedBean() {
    }

    @PostConstruct
    public void init() {
        findAllVehicules();
        setSelectedVehicule(new Vehicule());
    }
    
    public String prepareList() {
        findAllVehicules();
        setSelectedVehicule(new Vehicule());
        return "/pages/vehicules/vehicules.xhtml";
    }

    public void prepareCreate() {
        setSelectedVehicule(new Vehicule());
    }
    
    public void create() {
        try {
            vehiculesFacade.create(getSelectedVehicule());
            JsfUtil.addSuccessMessage("Véhicule créé avec succès");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Echec de création du véhicule");
        }
    }

    public void save() {
        try {
            if (getSelectedVehicule().getMatricule()!= null && !selectedVehicule.getMatricule().isEmpty()) {
                if (getSelectedVehicule().getId()== null) {
                    List<Vehicule> vehicules = vehiculesFacade.findVehiculeByMatricule(getSelectedVehicule().getMatricule());
                    if ( vehicules == null || vehicules.isEmpty()) {
                        vehiculesFacade.create(getSelectedVehicule());
                        JsfUtil.addSuccessMessage("Création d'un nouveau véhicule effectuée avec succès !");
                        getListVehicule().add(getSelectedVehicule());
                        JsfUtil.executeScript("createVehiculeWidget.hide()");
                        JsfUtil.updateScript(":formVehicule:listVehicule");
                        
                    } else {
                        JsfUtil.addErrorMessage("Le matricule du véhicule à ajouter existe déjà !!!");
                    }
                } else {
                    if (!checkVehiculeUnicity(selectedVehicule)) {
                        vehiculesFacade.edit(getSelectedVehicule());
                        JsfUtil.addSuccessMessage("Mise à jour du véhicule effectuée avec succès !");
                        JsfUtil.executeScript("createVehiculeWidget.hide()");
                        JsfUtil.updateScript(":formVehicule:listVehicule");
                        
                    } else {
                        JsfUtil.addErrorMessage("Le matricule du vehicule à mettre à jour existe déjà !!!");
                    }
                }
            }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour du véhicule !!!");
            getLogger().error(e);
        }
    }
    
    public Boolean checkVehiculeUnicity(Vehicule veh) throws Exception {

        if (veh.getMatricule()!= null && !veh.getMatricule().isEmpty()) {

            List<Vehicule> listVeh = vehiculesFacade.findVehiculeByMatricule(veh.getMatricule());

            if (listVeh != null && !listVeh.isEmpty() && listVeh.get(0).getId() != veh.getId()) {
                return true;
            }
        }
        return false;
    }
    
    public String prepareEdit() {
        return "Edit";
    }

    public void update() {
        try {
            vehiculesFacade.edit(getSelectedVehicule());
            JsfUtil.addSuccessMessage("Mise à jour du véhicule effectuée avec succès !");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération de mise à jour du véhicule !!!");
        }
    }

    public void destroy() {
        try {
            vehiculesFacade.remove(getSelectedVehicule());
            getListVehicule().remove(getSelectedVehicule());
            JsfUtil.addSuccessMessage("Suppression du véhicule effectuée avec succès !");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération de suppression du véhicule !!!");
        }
    }

    public void findAllVehicules() {
        try {
            setListVehicule(vehiculesFacade.findAllVehicules());
        } catch (Exception ex) {
            getLogger().error(ex);
        }
    }

    public VehiculesFacadeLocal getVehiculesFacade() {
        return vehiculesFacade;
    }

    public void setVehiculesFacade(VehiculesFacadeLocal vehiculesFacade) {
        this.vehiculesFacade = vehiculesFacade;
    }

    public Vehicule getSelectedVehicule() {
        return selectedVehicule;
    }

    public void setSelectedVehicule(Vehicule selectedVehicule) {
        this.selectedVehicule = selectedVehicule;
    }

    public List<Vehicule> getListVehicule() {
        return listVehicule;
    }

    public void setListVehicule(List<Vehicule> listVehicule) {
        this.listVehicule = listVehicule;
    }
    
    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }
    
}
