/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@WebListener
public class SessionExpiredListener implements HttpSessionListener {

	private static final Logger logger = Logger.getLogger(SessionExpiredListener.class);

	public SessionExpiredListener() {
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		getLogger().info("Session opened");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		getLogger().info("Session closed");
		se.getSession().invalidate();
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return logger;
	}

}
