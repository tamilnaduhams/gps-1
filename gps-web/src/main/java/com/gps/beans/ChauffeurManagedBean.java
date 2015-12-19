package com.gps.beans;

import static com.gps.beans.ChauffeurManagedBean.getLogger;
import static com.gps.beans.DroitManagedBean.getLogger;
import com.gps.entities.Chauffeur;
import com.gps.facades.local.ChauffeurFacadeLocal;
import com.gps.facades.local.ChauffeurFacadeLocal;
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

@ManagedBean(name = "chauffeurManagedBean")
@ViewScoped
public class ChauffeurManagedBean implements Serializable {
    
    private static final Logger logger = Logger.getLogger(ChauffeurManagedBean.class);

    @EJB
    private ChauffeurFacadeLocal chauffeurFacade;
    private Chauffeur selectedChauffeur;
    private List<Chauffeur> listChauffeur;

    public ChauffeurManagedBean() {
    }

    @PostConstruct
    public void init() {
        findAllChauffeurs();
        setSelectedChauffeur(new Chauffeur());
    }
    
    public String prepareList() {
        findAllChauffeurs();
        setSelectedChauffeur(new Chauffeur());
        return "/pages/chauffeurs/chauffeurs.xhtml";
    }

    public void prepareCreate() {
        setSelectedChauffeur(new Chauffeur());
    }
    
    public void create() {
        try {
            chauffeurFacade.create(getSelectedChauffeur());
            JsfUtil.addSuccessMessage("Chauffeur créé avec succès");
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, "Echec de création du chauffeur");
        }
    }

    public void save() {
        try {
                if (getSelectedChauffeur().getId()== null) {
                        chauffeurFacade.create(getSelectedChauffeur());
                        JsfUtil.addSuccessMessage("Création d'un nouveau chauffeur effectuée avec succès !");
                        getListChauffeur().add(getSelectedChauffeur());
                        JsfUtil.executeScript("createChauffeurWidget.hide()");
                        JsfUtil.updateScript(":formChauffeur:listChauffeur");
                } else {
                        chauffeurFacade.edit(getSelectedChauffeur());
                        JsfUtil.addSuccessMessage("Mise à jour du chauffeur effectuée avec succès !");
                        JsfUtil.executeScript("createChauffeurWidget.hide()");
                        JsfUtil.updateScript(":formChauffeur:listChauffeur");
                }
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération d'ajout/mise à jour du chauffeur !!!");
            getLogger().error(e);
        }
    }
    
    public String prepareEdit() {
        return "Edit";
    }

    public void update() {
        try {
            chauffeurFacade.edit(getSelectedChauffeur());
            JsfUtil.addSuccessMessage("Mise à jour du chauffeur effectuée avec succès !");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération de mise à jour du chauffeur !!!");
        }
    }

    public void destroy() {
        try {
            chauffeurFacade.remove(getSelectedChauffeur());
            getListChauffeur().remove(getSelectedChauffeur());
            JsfUtil.addSuccessMessage("Suppression du chauffeur effectuée avec succès !");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("Echec de l'opération de suppression du chauffeur !!!");
        }
    }

    public void findAllChauffeurs() {
        try {
            setListChauffeur(chauffeurFacade.findAllChauffeurs());
        } catch (Exception ex) {
            getLogger().error(ex);
        }
    }



    public Chauffeur getSelectedChauffeur() {
        return selectedChauffeur;
    }

    public void setSelectedChauffeur(Chauffeur selectedChauffeur) {
        this.selectedChauffeur = selectedChauffeur;
    }

    public List<Chauffeur> getListChauffeur() {
        return listChauffeur;
    }

    public void setListChauffeur(List<Chauffeur> listChauffeur) {
        this.listChauffeur = listChauffeur;
    }
    
    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }

    
}
