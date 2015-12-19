/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import com.gps.entities.Droit;
import com.gps.entities.DroitFonctionnalite;
import com.gps.entities.Fonctionnalite;
import com.gps.facades.local.DroitFacadeLocal;
import com.gps.facades.local.FonctionnaliteFacadeLocal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntUrlPathMatcher;
import org.springframework.security.web.util.UrlMatcher;

/**
 *
 * @author amine.sagaama@gmail.com
 */

public class GpsFilterSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    private static final Logger logger = Logger.getLogger(GpsFilterSecurityMetadataSource.class);
    private static final String FONCTIONNALITE_EJB_LOOKUP_PATH = "java:comp/env/FonctionnaliteFacadeLocal";
    private static final String DROIT_EJB_LOOKUP_PATH = "java:comp/env/DroitFacadeLocal";
    private static final String PUBLIC_FOLDER_URL = "/pages/public/**";
    private static Map<String, Collection<ConfigAttribute>> resourceMap = null;
    private UrlMatcher urlMatcher = new AntUrlPathMatcher();
    @EJB(name = "FonctionnaliteFacadeLocal", beanInterface = FonctionnaliteFacadeLocal.class)
    private FonctionnaliteFacadeLocal fonctionnaliteFacade;
    @EJB(name = "DroitFacadeLocal", beanInterface = DroitFacadeLocal.class)
    private DroitFacadeLocal droitFacadeLocal;
    private List<DroitFonctionnalite> listDroitFonctionnaliteByFonctionnalite;

    public GpsFilterSecurityMetadataSource() {
        loadResourceDefine();
    }

    private void loadResourceDefine() {
        try {
            resourceMap = new HashMap<>();
            ConfigAttribute ca;
            String role, url;
            Collection<ConfigAttribute> atts = null;
            InitialContext initialContext = new InitialContext();
            fonctionnaliteFacade = (FonctionnaliteFacadeLocal) initialContext.lookup(FONCTIONNALITE_EJB_LOOKUP_PATH);
            droitFacadeLocal = (DroitFacadeLocal) initialContext.lookup(DROIT_EJB_LOOKUP_PATH);
            List<Fonctionnalite> listFonctionnalites = (List<Fonctionnalite>) fonctionnaliteFacade.findAllFonctionnalites();
            for (Fonctionnalite fonctionnalite : listFonctionnalites) {
                url = fonctionnalite.getUrl();
                atts = new ArrayList<>();
                listDroitFonctionnaliteByFonctionnalite = fonctionnalite.getDroitFonctionnaliteList();
                if (!CollectionUtils.isEmpty(listDroitFonctionnaliteByFonctionnalite)) {
                    for (DroitFonctionnalite droitFonctionnalite : listDroitFonctionnaliteByFonctionnalite) {
                        if (droitFonctionnalite.getIdDroit() != null && droitFonctionnalite.getActive()) {
                            role = droitFonctionnalite.getIdDroit().getLibelle();
                            ca = new SecurityConfig(role);
                            atts.add(ca);
                            resourceMap.put(url, atts);
                        }
                    }
                } else {
                    resourceMap.put(url, atts);
                }
            }
            atts = new ArrayList<>();
            List<Droit> listDroits = droitFacadeLocal.findAllDroits();
            for (Droit droit : listDroits) {
                role = droit.getLibelle();
                ca = new SecurityConfig(role);
                atts.add(ca);
                resourceMap.put(PUBLIC_FOLDER_URL, atts);
            }
        } catch (NamingException ex) {
            logger.error("Could not lookup for EJB CollaborateurFacadeLocal with lookup path " + FONCTIONNALITE_EJB_LOOKUP_PATH);
            ex.printStackTrace();
        }
    }

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object)
            throws IllegalArgumentException {
        String url = ((FilterInvocation) object).getRequestUrl();
        Iterator<String> ite = resourceMap.keySet().iterator();
        while (ite.hasNext()) {
            String resURL = ite.next();
            if (urlMatcher.pathMatchesUrl(resURL, url)) {
                return resourceMap.get(resURL);
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return new ArrayList<>();
    }

    /**
     * @return the listDroitFonctionnaliteByFonctionnalite
     */
    public List<DroitFonctionnalite> getListDroitFonctionnaliteByFonctionnalite() {
        return listDroitFonctionnaliteByFonctionnalite;
    }

    /**
     * @param listDroitFonctionnaliteByFonctionnalite the
     * listDroitFonctionnaliteByFonctionnalite to set
     */
    public void setListDroitFonctionnaliteByFonctionnalite(List<DroitFonctionnalite> listDroitFonctionnaliteByFonctionnalite) {
        this.listDroitFonctionnaliteByFonctionnalite = listDroitFonctionnaliteByFonctionnalite;
    }
}
