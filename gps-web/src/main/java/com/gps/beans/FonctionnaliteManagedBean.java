/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import com.gps.entities.Droit;
import com.gps.entities.DroitFonctionnalite;
import com.gps.entities.Fonctionnalite;
import com.gps.entities.Menu;
import com.gps.facades.local.DroitFacadeLocal;
import com.gps.facades.local.DroitFonctionnaliteFacadeLocal;
import com.gps.facades.local.FonctionnaliteFacadeLocal;
import com.gps.facades.local.MenuFacadeLocal;
import com.gps.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
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

@ManagedBean(name = "fonctionnaliteManagedBean")
@ViewScoped
public class FonctionnaliteManagedBean implements Serializable {

    private static final Logger LOGGER = Logger.getLogger(FonctionnaliteManagedBean.class);
     
    @EJB
    private FonctionnaliteFacadeLocal fonctionnaliteFacade;
    @EJB
    private MenuFacadeLocal menuFacade;
    @EJB
    private DroitFacadeLocal droitFacade;
    @EJB
    private DroitFonctionnaliteFacadeLocal droitFonctionnaliteFacade;
    private List<Fonctionnalite> listFonctionnalite, listFctsMenu;
    private List<Droit> listDroit;
    private List<Menu> listMenu;
    private List<DroitFonctionnalite> listDroitFonctionnalite;
    private Fonctionnalite selectedFonctionnalite;
    private Droit selectedDroit;
    private List<String> selectedDroits;
    private DroitFonctionnalite selectedDroitFonctionnalite;
    private Menu selectedMenu;

    public FonctionnaliteManagedBean() {
    }

    @PostConstruct
    public void init() {
    }

    public String prepareList() {
        findAllDroitFonctionnalite();
        findAllFonctionnalites();
        findAllDroits();
        findAllMenus();
        initMenus();
        selectedFonctionnalite = new Fonctionnalite();
        setSelectedDroit(getDroitFacade().findAllDroits().get(0));
        selectedMenu = menuFacade.findAllMenus().get(0);
        selectedMenu.setActive(Boolean.TRUE);
        menuFacade.edit(selectedMenu);
        findAllMenus();
        updateListDroitFonctionnalite();
        return "/pages/configuration/Fonctionnalites.xhtml";
    }
    
    public void prepareCreate() {
        setSelectedFonctionnalite(new Fonctionnalite());
        selectedDroits = new ArrayList<>();
    }

    public void create() {
        try {
            if (fonctionnaliteFacade.findFonctionnalitesByURL(selectedFonctionnalite.getUrl()).isEmpty()) {
                fonctionnaliteFacade.create(selectedFonctionnalite);
                for (Droit dro : getListDroit()) {
                    DroitFonctionnalite droitFonctionnalite = new DroitFonctionnalite();
                    droitFonctionnalite.setIdDroit(dro);
                    droitFonctionnalite.setIdFonctionnalite(selectedFonctionnalite);
                    for (String author : getSelectedDroits()) {
                        if (Integer.parseInt(author) == dro.getIdDroit()) {
                            droitFonctionnalite.setActive(Boolean.TRUE);
                        }
                    }
                    droitFonctionnaliteFacade.create(droitFonctionnalite);
                    new GpsFilterSecurityMetadataSource();
                }
                JsfUtil.addSuccessMessage("La fonctionnalité <" + selectedFonctionnalite.getLibelle() + "> a été ajoutée avec succès !");
                updateListDroitFonctionnalite();
            } else {
                JsfUtil.addErrorMessage("La fonctionnalité (URL) à ajouter existe déjà !!!");
            }
            
        } catch (Exception e) {
            JsfUtil.addErrorMessage("L'opération d'ajout de la fonctionnalité < " + selectedFonctionnalite.getLibelle() + " > a échoué !!!");
            LOGGER.error(e);
        }
    }

    public Boolean checkFonctionnalityUnicity(Fonctionnalite fct) throws Exception {

        if (fct.getUrl() != null && !fct.getUrl().isEmpty()) {

            List<Fonctionnalite> listFcts = fonctionnaliteFacade.findFonctionnalitesByURL(fct.getUrl());

            if (!listFcts.isEmpty() && listFcts.get(0).getIdFonctionnalite() != fct.getIdFonctionnalite()) {
                return true;
            }
        }
        return false;
    }

    public void prepareEdit(Fonctionnalite fct) {
        setSelectedFonctionnalite(fct);
    }

    public void update() {
        try {
            if (selectedFonctionnalite.getLibelle() != null && !selectedFonctionnalite.getLibelle().isEmpty()) {
                if (!checkFonctionnalityUnicity(selectedFonctionnalite)) {
                    fonctionnaliteFacade.edit(selectedFonctionnalite);
                    JsfUtil.addSuccessMessage("Mise à jour de la fonctionnalité effectuée avec succès !");
                    updateListDroitFonctionnalite();
                    JsfUtil.executeScript("dialogEdit.hide();");
                    JsfUtil.updateScript(":formRoles:listFonctionnalites");
                } else {
                    JsfUtil.executeScript("dialogEdit.hide();");
                    JsfUtil.updateScript(":formRoles:listFonctionnalites");
                    JsfUtil.addErrorMessage("La fonctionnalité (URL) à mettre à jour existe déjà !!!");
                }
            }
        } catch (Exception e) {
            LOGGER.error(e);
            JsfUtil.addErrorMessage("Opération de mise à jour de la fonctionnalité échouée !!!");
        }
    }

    public void update(DroitFonctionnalite authFct) {
        droitFonctionnaliteFacade.edit(authFct);
        new GpsFilterSecurityMetadataSource();
        if(authFct.getActive()) {
            JsfUtil.addSuccessMessage("La fonctionnalité <" + authFct.getIdFonctionnalite().getLibelle() + "> a été attribuée au rôle <" + authFct.getIdDroit().getLibelle()+ "> avec succès !");
        } else {
            JsfUtil.addSuccessMessage("La fonctionnalité <" + authFct.getIdFonctionnalite().getLibelle() + "> a été retirée du rôle <" + authFct.getIdDroit().getLibelle() + "> avec succès !");
        }
    }

    public void updateListDroitFonctionnalite() {
        if (getSelectedDroit() == null && selectedMenu != null) {
            findAllDroitFonctionnaliteByMenu();
        } else if (getSelectedDroit() != null && selectedMenu == null) {
            findAlDroitFonctionnaliteByDroit();
        } else if (getSelectedDroit() != null && selectedMenu != null) {
            findAllDroitFonctionnaliteByMenuAndDroit();
        }
    }

    public void updateMenu(Menu mnu) {
        for (Menu menu : listMenu) {
            if (menu == mnu) {
                menu.setActive(Boolean.TRUE);
            } else {
                menu.setActive(Boolean.FALSE);
            }
            menuFacade.edit(menu);
        }
        findAllMenus();
        selectedMenu = mnu;
        updateListDroitFonctionnalite();
    }

    public void initMenus() {
        for (Menu menu : listMenu) {
            menu.setActive(Boolean.FALSE);
            menuFacade.edit(menu);
        }
        findAllMenus();
    }

    public void destroy() {
        try {
            List<DroitFonctionnalite> lstDroitsFcts = droitFonctionnaliteFacade.findDroitFonctionnalitesByFct(selectedFonctionnalite.getIdFonctionnalite());
            for (DroitFonctionnalite droitsFonctionnalite : lstDroitsFcts) {
                droitFonctionnaliteFacade.remove(droitsFonctionnalite);
            }
            fonctionnaliteFacade.remove(selectedFonctionnalite);
            updateListDroitFonctionnalite();
            JsfUtil.addSuccessMessage("La fonctionnalité <" + selectedFonctionnalite.getLibelle() + "> a été supprimée avec succès !");
        } catch (Exception e) {
            LOGGER.error(e);
            JsfUtil.addErrorMessage("L'opération de suppression de la fonctionnalité < " + selectedFonctionnalite.getLibelle() + " > a échoué !!!");
        }
    }

    public void prepareDetails() {
        setListDroitFonctionnalite(droitFonctionnaliteFacade.findDroitFonctionnalitesByFct(selectedFonctionnalite.getIdFonctionnalite()));
    }

    public void findAllFonctionnalites() {
        try {
            setListFonctionnalite(fonctionnaliteFacade.findAllFonctionnalites());
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAllDroits() {
        try {
            setListDroit(getDroitFacade().findAllDroits());
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAllMenus() {
        try {
            setListMenu(menuFacade.findAllMenus());
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAllDroitFonctionnalite() {
        try {
            setListDroitFonctionnalite(droitFonctionnaliteFacade.findAllDroitFonctionnalites());
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAllFonctionnalitesByMenu(Menu menu) {
        try {
            setListFonctionnalite(fonctionnaliteFacade.findFonctionnalitesByMenu(menu.getIdMenu()));
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public List<DroitFonctionnalite> ListDroitFonctionnaliteByDroitAndMenu(Droit dro, Menu men) {
        return droitFonctionnaliteFacade.findDroitFonctionnalitesByFonctionnalite(dro.getIdDroit(), men.getIdMenu());
    }

    public void findAllDroitFonctionnaliteByMenu() {
        try {
            setListDroitFonctionnalite(droitFonctionnaliteFacade.findDroitFonctionnalitesByMenu(selectedMenu.getIdMenu()));
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAlDroitFonctionnaliteByDroit() {
        try {
            setListDroitFonctionnalite(droitFonctionnaliteFacade.findDroitFonctionnalitesByDroit(getSelectedDroit().getIdDroit()));
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public void findAllDroitFonctionnaliteByMenuAndDroit() {
        try {
            setListDroitFonctionnalite(droitFonctionnaliteFacade.findDroitFonctionnalitesByMenuAndDroit(selectedMenu.getIdMenu(), getSelectedDroit().getIdDroit()));
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }

    public List<Fonctionnalite> listOfFonctionnalitesByMenu(Menu menu) {
        try {
            return fonctionnaliteFacade.findFonctionnalitesByMenu(menu.getIdMenu());
        } catch (Exception ex) {
            LOGGER.error(ex);
            return null;
        }
    }

    public List<Droit> complete(String query) {

        List<Droit> brandList = new ArrayList<>();

        for (Droit brand : listDroit) {
            if (brand.getLibelle().toUpperCase().contains(query.toUpperCase())) {
                brandList.add(brand);
            }
        }

        return brandList;
    }

    /**
     * @return the fonctionnaliteFacade
     */
    public FonctionnaliteFacadeLocal getFonctionnaliteFacade() {
        return fonctionnaliteFacade;
    }

    /**
     * @param fonctionnaliteFacade the fonctionnaliteFacade to set
     */
    public void setFonctionnaliteFacade(FonctionnaliteFacadeLocal fonctionnaliteFacade) {
        this.fonctionnaliteFacade = fonctionnaliteFacade;
    }

    /**
     * @return the menuFacade
     */
    public MenuFacadeLocal getMenuFacade() {
        return menuFacade;
    }

    /**
     * @param menuFacade the menuFacade to set
     */
    public void setMenuFacade(MenuFacadeLocal menuFacade) {
        this.menuFacade = menuFacade;
    }

    /**
     * @return the listFonctionnalite
     */
    public List<Fonctionnalite> getListFonctionnalite() {
        return listFonctionnalite;
    }

    /**
     * @param listFonctionnalite the listFonctionnalite to set
     */
    public void setListFonctionnalite(List<Fonctionnalite> listFonctionnalite) {
        this.listFonctionnalite = listFonctionnalite;
    }

    /**
     * @return the selectedFonctionnalite
     */
    public Fonctionnalite getSelectedFonctionnalite() {
        return selectedFonctionnalite;
    }

    /**
     * @param selectedFonctionnalite the selectedFonctionnalite to set
     */
    public void setSelectedFonctionnalite(Fonctionnalite selectedFonctionnalite) {
        this.selectedFonctionnalite = selectedFonctionnalite;
    }

    /**
     * @return the listMenu
     */
    public List<Menu> getListMenu() {
        return listMenu;
    }

    /**
     * @param listMenu the listMenu to set
     */
    public void setListMenu(List<Menu> listMenu) {
        this.listMenu = listMenu;
    }

    /**
     * @return the listFctsMenu
     */
    public List<Fonctionnalite> getListFctsMenu() {
        return listFctsMenu;
    }

    /**
     * @param listFctsMenu the listFctsMenu to set
     */
    public void setListFctsMenu(List<Fonctionnalite> listFctsMenu) {
        this.listFctsMenu = listFctsMenu;
    }

    /**
     * @return the selectedMenu
     */
    public Menu getSelectedMenu() {
        return selectedMenu;
    }

    /**
     * @param selectedMenu the selectedMenu to set
     */
    public void setSelectedMenu(Menu selectedMenu) {
        this.selectedMenu = selectedMenu;
    }

    /**
     * @return the droitFacade
     */
    public DroitFacadeLocal getDroitFacade() {
        return droitFacade;
    }

    /**
     * @param droitFacade the droitFacade to set
     */
    public void setDroitFacade(DroitFacadeLocal droitFacade) {
        this.droitFacade = droitFacade;
    }

    /**
     * @return the listDroit
     */
    public List<Droit> getListDroit() {
        return listDroit;
    }

    /**
     * @param listDroit the listDroit to set
     */
    public void setListDroit(List<Droit> listDroit) {
        this.listDroit = listDroit;
    }

    /**
     * @return the listDroitFonctionnalite
     */
    public List<DroitFonctionnalite> getListDroitFonctionnalite() {
        return listDroitFonctionnalite;
    }

    /**
     * @param listDroitFonctionnalite the listDroitFonctionnalite to set
     */
    public void setListDroitFonctionnalite(List<DroitFonctionnalite> listDroitFonctionnalite) {
        this.listDroitFonctionnalite = listDroitFonctionnalite;
    }

    /**
     * @return the selectedDroit
     */
    public Droit getSelectedDroit() {
        return selectedDroit;
    }

    /**
     * @param selectedDroit the selectedDroit to set
     */
    public void setSelectedDroit(Droit selectedDroit) {
        this.selectedDroit = selectedDroit;
    }

    /**
     * @return the selectedDroits
     */
    public List<String> getSelectedDroits() {
        return selectedDroits;
    }

    /**
     * @param selectedDroits the selectedDroits to set
     */
    public void setSelectedDroits(List<String> selectedDroits) {
        this.selectedDroits = selectedDroits;
    }

    /**
     * @return the selectedDroitFonctionnalite
     */
    public DroitFonctionnalite getSelectedDroitFonctionnalite() {
        return selectedDroitFonctionnalite;
    }

    /**
     * @param selectedDroitFonctionnalite the selectedDroitFonctionnalite to set
     */
    public void setSelectedDroitFonctionnalite(DroitFonctionnalite selectedDroitFonctionnalite) {
        this.selectedDroitFonctionnalite = selectedDroitFonctionnalite;
    }

}
