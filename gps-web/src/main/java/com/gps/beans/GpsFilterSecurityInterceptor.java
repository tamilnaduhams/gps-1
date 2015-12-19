/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.io.IOException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean
@SessionScoped
public class GpsFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

    private FilterInvocationSecurityMetadataSource gpsFilterSecurityMetadataSource;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(request, response, chain);
        invoke(fi);
    }

    public void setMessages(MessageSourceAccessor messages) {
        this.messages = messages;
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(fi);
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    public MessageSourceAccessor getMessages() {
        return messages;
    }

    @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
    }

    @Override
    public Class<? extends Object> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.getGpsFilterSecurityMetadataSource();
    }

    /**
     * @return the gpsFilterSecurityMetadataSource
     */
    public FilterInvocationSecurityMetadataSource getGpsFilterSecurityMetadataSource() {
        return gpsFilterSecurityMetadataSource;
    }

    /**
     * @param gpsFilterSecurityMetadataSource the gpsFilterSecurityMetadataSource to set
     */
    public void setGpsFilterSecurityMetadataSource(FilterInvocationSecurityMetadataSource gpsFilterSecurityMetadataSource) {
        this.gpsFilterSecurityMetadataSource = gpsFilterSecurityMetadataSource;
    }

}