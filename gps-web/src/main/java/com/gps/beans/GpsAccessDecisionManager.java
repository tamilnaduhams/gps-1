/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import java.util.Collection;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "gpsAccessDecisionManager")
public class GpsAccessDecisionManager implements AccessDecisionManager {

	private static final Logger logger = Logger.getLogger(GpsAccessDecisionManager.class);

	private Boolean test = true;

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		if (configAttributes == null) {
			test = false;
			// logger.info("is allowed : " + test.toString());
			throw new AccessDeniedException("no right");
		}
		Iterator<ConfigAttribute> ite = configAttributes.iterator();
		while (ite.hasNext()) {
			ConfigAttribute ca = ite.next();
			String needRole = ((SecurityConfig) ca).getAttribute();
			for (GrantedAuthority ga : authentication.getAuthorities()) {
				if (needRole.equals(ga.getAuthority())) {
					test = true;
					// logger.info("is allowed : " + test.toString());
					return;
				} else {
					test = false;
				}
			}
		}
		// logger.info("is allowed : " + test.toString());
		throw new AccessDeniedException("no right");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	public Boolean getTest() {
		return test;
	}

	public void setTest(Boolean test) {
		this.test = test;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

}