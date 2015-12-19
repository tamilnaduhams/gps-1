/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "page")
@ViewScoped
public class PageUri implements Serializable {

    private String thisPageUri;
    private int index;
    private Authentication auth;
    private Collection<GrantedAuthority> authority;

    public PageUri() {
    }

    @PostConstruct
    public void init() {
        Object request = FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request instanceof HttpServletRequest) {
//            this.thisPageUri = ((HttpServletRequest) request).getRequestURI().toString();
            auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.getAuthorities() != null) {
                authority = auth.getAuthorities();
                Iterator iterator = authority.iterator();
                while (iterator.hasNext()) {
                    thisPageUri = "" + iterator.next();
                }
                if (!this.authority.isEmpty() && this.authority != null) {

                    if (this.thisPageUri.contains("Administrateur")) {
                        this.index = 0;
                        return;
                    }
                    if (this.thisPageUri.contains("Utilisateur")) {
                        this.index = 1;
                        return;
                    }
                }
            }
        }
    }

    /**
     * Creates a new instance of PageUri
     */
    public String requestPath() {
        String path = "";
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        path += ec.getRequestScheme().toString() + "://";
        path += ec.getRequestServerName() + ":" + ec.getRequestServerPort();
        return path;
    }

    public String getThisPageUri() {
        return this.thisPageUri;
    }

    public void setThisPageUri(String thisPageUri) {
        this.thisPageUri = thisPageUri;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Authentication getAuth() {
        return auth;
    }

    public void setAuth(Authentication auth) {
        this.auth = auth;
    }

    public Collection<GrantedAuthority> getAuthority() {
        return authority;
    }

    public void setAuthority(Collection<GrantedAuthority> authority) {
        this.authority = authority;
    }
}
