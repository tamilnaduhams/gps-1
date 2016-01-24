/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.gps.helpers.RendererHelper;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "sessionExpiredHandlerMb")
@ViewScoped
public class SessionExpiredHandlerMb {

	private int timeout = 15 * 60 * 1_000;

	public SessionExpiredHandlerMb() {
	}

	public void idleListener() {
		if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getExternalContext() != null
				&& FacesContext.getCurrentInstance().getExternalContext().getSession(true) != null) {
			((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true)).invalidate();
		}
		RendererHelper.doRedirect(FacesContext.getCurrentInstance(), "/login.xhtml");
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
}
