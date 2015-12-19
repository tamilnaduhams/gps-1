/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.gps.beans;

import java.io.Serializable;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import org.apache.log4j.Logger;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "versionManagedBean")
@ApplicationScoped
public class VersionManagedBean implements Serializable {

    private static final Logger logger = Logger.getLogger(VersionManagedBean.class);
    
    private static String version;
    private static String versionDate;

    public VersionManagedBean() {
    }

    @PostConstruct
    public void init() {
        try {
            version = ResourceBundle.getBundle("version").getString("version");
            versionDate = ResourceBundle.getBundle("version").getString("version_date");
        } catch (Exception e) {
            logger.error(e);
        }        
    }

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the versionDate
     */
    public String getVersionDate() {
        return versionDate;
    }

}
