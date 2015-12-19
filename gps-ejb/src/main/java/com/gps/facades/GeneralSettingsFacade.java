package com.gps.facades;

import com.gps.entities.GeneralSettings;
import com.gps.facades.local.GeneralSettingsFacadeLocal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@Stateless 
public class GeneralSettingsFacade extends AbstractFacade implements GeneralSettingsFacadeLocal {

    @PersistenceContext(unitName = "gpsPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    
    @Override
    public GeneralSettings findGeneralSettingsBySettingsCode(String settingsCode) {

        Map condition = new HashMap();
        condition.put("settingCode", "='" + settingsCode + "'");
        condition.put("enabled", "=" + Boolean.TRUE);
        List<GeneralSettings> list = findByCriteria(condition, GeneralSettings.class, "", "");
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
    
    @Override
    public String findGeneralSettingsBySettingsCode(String settingsCode, String defaultValue) {
        GeneralSettings gs = findGeneralSettingsBySettingsCode(settingsCode);
        if (gs == null) {
            return defaultValue;
        }
        return gs.getSettingValue();
    }
    
    @Override
    public int findGeneralSettingsBySettingsCode(String settingsCode, int defaultValue) {
        GeneralSettings gs = findGeneralSettingsBySettingsCode(settingsCode);
        if (gs == null) {
            return defaultValue;
        }
        return Integer.parseInt(gs.getSettingValue());
    }

    @Override
    public List<GeneralSettings> findAllGeneralSettings() {
        return findAll(GeneralSettings.class, null, "");
    }
    
}
