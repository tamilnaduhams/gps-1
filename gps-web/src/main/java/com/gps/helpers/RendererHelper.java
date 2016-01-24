/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.helpers;

import java.io.IOException;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;

/**
 *
 * @author amine.sagaama@gmail.com
 */
public class RendererHelper {

	private static Logger logger = Logger.getLogger(RendererHelper.class);

	/**
	 *
	 * @param fc
	 * @param redirectPage
	 * @throws FacesException
	 */
	public static void doRedirect(FacesContext fc, String redirectPage) throws FacesException {
		ExternalContext ec = fc.getExternalContext();

		try {
			if (ec.isResponseCommitted()) {
				return;
			}
			if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc.getPartialViewContext().isPartialRequest())
					&& fc.getResponseWriter() == null && fc.getRenderKit() == null) {
				ServletResponse response = (ServletResponse) ec.getResponse();
				ServletRequest request = (ServletRequest) ec.getRequest();
				response.setCharacterEncoding(request.getCharacterEncoding());

				RenderKitFactory factory = (RenderKitFactory) FactoryFinder
						.getFactory(FactoryFinder.RENDER_KIT_FACTORY);

				RenderKit renderKit = factory.getRenderKit(fc,
						fc.getApplication().getViewHandler().calculateRenderKitId(fc));

				ResponseWriter responseWriter = renderKit.createResponseWriter(response.getWriter(), null,
						request.getCharacterEncoding());
				fc.setResponseWriter(responseWriter);
			}

			ec.redirect(ec.getRequestContextPath() + (redirectPage != null ? redirectPage : ""));
		} catch (IOException e) {
			logger.error("Redirect to the specified page '" + redirectPage + "' failed");
			throw new FacesException(e);
		}
	}
}