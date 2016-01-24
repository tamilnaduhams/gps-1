package com.gps.helpers;

import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author amine.sagaama@gmail.com
 */

public class JSFHelper {

	public static HttpSession getHttpSession() {
		FacesContext facescontext = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) facescontext.getExternalContext().getSession(true);
		return session;
	}

	public static HttpServletResponse getHttpServletResponse() {
		FacesContext facescontext = FacesContext.getCurrentInstance();
		HttpServletResponse response = (HttpServletResponse) facescontext.getExternalContext().getResponse();
		return response;
	}

	public static HttpServletRequest getHttpServletRequest() {
		FacesContext facescontext = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) facescontext.getExternalContext().getRequest();
		return request;
	}

	public static ServletContext getServletContext() {
		ServletContext context = getHttpSession().getServletContext();
		return context;
	}

	public static ExternalContext getExternalContext() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getExternalContext();
	}

	public static Object getManagedBean(final String beanName) {
		FacesContext fc = FacesContext.getCurrentInstance();
		Object bean;

		try {
			ELContext elContext = fc.getELContext();
			bean = elContext.getELResolver().getValue(elContext, null, beanName);
		} catch (RuntimeException e) {
			throw new FacesException(e.getMessage(), e);
		}

		if (bean == null) {
			throw new FacesException("Managed bean with name '" + beanName
					+ "' was not found. Check your faces-config.xml or @ManagedBean annotation.");
		}

		return bean;
	}

	public static void addErrorMessage(String message) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMsg);
	}

	public static void addInfoMessage(String message) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMsg);
	}

	public static void addWarningMessage(String message) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, message, message);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, facesMsg);
	}

	public static String getRequestPath() {
		String path = "";
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		path += ec.getRequestScheme().toString() + "://";
		path += ec.getRequestServerName() + ":" + ec.getRequestServerPort();
		return path;
	}
}
