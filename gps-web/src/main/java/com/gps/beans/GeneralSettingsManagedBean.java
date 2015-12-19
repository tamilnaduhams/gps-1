package com.gps.beans;

import com.gps.entities.GeneralSettings;
import com.gps.facades.local.GeneralSettingsFacadeLocal;
import com.gps.helpers.ClassEnumHelper;
import com.gps.helpers.ClassEnumHelper.SettingKeys;
import com.gps.util.JsfUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.apache.log4j.Logger;


/**
 *
 * @author amine.sagaama@gmail.com
 */
@ManagedBean(name = "generalSettingsManagedBean")
@SessionScoped
public class GeneralSettingsManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(GeneralSettingsManagedBean.class);
    @EJB
    private GeneralSettingsFacadeLocal generalSettingsFacade;
    
    private GeneralSettings selectedGeneralSettings;
    private List<GeneralSettings> listGeneralSettings;
    private List<String> listCode;

    public GeneralSettingsManagedBean() {
    }

    @PostConstruct
    public void init() {
        selectedGeneralSettings = new GeneralSettings();
        findAllCode();
    }

    public void prepareCreate() {
        selectedGeneralSettings = new GeneralSettings();
    }

    public void create() {
        try {
            selectedGeneralSettings.setEnabled(Boolean.TRUE);
            generalSettingsFacade.create(selectedGeneralSettings);
            JsfUtil.addSuccessMessage("Paramètre ajouté avec succès");
            JsfUtil.executeScript("createGeneralSettingsWidget.hide()");
            findAllCode();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void update() {
        try {
            generalSettingsFacade.edit(selectedGeneralSettings);
            JsfUtil.addSuccessMessage("Paramètre mis à jour avec succès");
            JsfUtil.executeScript("editGeneralSettingsWidget.hide()");
            findAllCode();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void destroy() {
        try {
            generalSettingsFacade.remove(selectedGeneralSettings);
            JsfUtil.addSuccessMessage("Paramètre supprimer avec succès");
            findAllCode();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    public void findAllGeneralSettingss() {
        try {
            listGeneralSettings = generalSettingsFacade.findAllGeneralSettings();
        } catch (Exception ex) {
            logger.error(ex);
        }
    }

    public void findAllCode() {
        listCode = new ArrayList<>();
        SettingKeys[] sk = ClassEnumHelper.SettingKeys.values();
        findAllGeneralSettingss();
        for (SettingKeys settingKeys : sk) {
            Boolean b = true;
            for (GeneralSettings gs : listGeneralSettings) {
                if (gs.getSettingCode().contentEquals(settingKeys.toString())) {
                    b = false;
                }
            }
            if (b) {
                listCode.add(settingKeys.toString());
            }
        }
    }

    public void activationParametre(GeneralSettings generalSettings) {
        try {
            generalSettings.setEnabled(generalSettings.getEnabled());
            generalSettingsFacade.edit(generalSettings);
            findAllCode();
            JsfUtil.addSuccessMessage("" + (generalSettings.getEnabled() ? "Activation" : "Désactivation") + " du paramètre");
        } catch (Exception ex) {
            JsfUtil.addErrorMessage(ex, "Erreur lors de l'activation du paramètre");
        }
    }

    public List<String> getListCode() {
        return listCode;
    }

    public void setListCode(List<String> listCode) {
        this.listCode = listCode;
    }

    public GeneralSettings getSelectedGeneralSettings() {
        return selectedGeneralSettings;
    }

    public void setSelectedGeneralSettings(GeneralSettings selectedGeneralSettings) {
        this.selectedGeneralSettings = selectedGeneralSettings;
    }

    public List<GeneralSettings> getListGeneralSettings() {
        return listGeneralSettings;
    }

    public void setListGeneralSettings(List<GeneralSettings> listGeneralSettings) {
        this.listGeneralSettings = listGeneralSettings;
    }

    @FacesConverter(forClass = GeneralSettings.class)
    public static class GeneralSettingsManagedBeanConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            GeneralSettingsManagedBean ManagedBean = (GeneralSettingsManagedBean) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "generalSettingsManagedBean");
            return ManagedBean.generalSettingsFacade.find(GeneralSettings.class, getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof GeneralSettings) {
                GeneralSettings o = (GeneralSettings) object;
                return getStringKey(o.getIdGeneralSettings());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + GeneralSettings.class.getName());
            }
        }
    }
}
