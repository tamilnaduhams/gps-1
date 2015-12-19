    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.listeners;

import java.util.Locale;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.log4j.Logger;

/**
 *
 * @author amine.sagaama@gmail.com
 */

public class LifeCycleListener implements PhaseListener {
    
    private static final Logger logger = Logger.getLogger(SessionExpiredListener.class);
    
    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
    
    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context;
        if (event.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            context = event.getFacesContext();
            Application application = context.getApplication();
            ValueExpression ve = application.getExpressionFactory().createValueExpression(context.getELContext(), "fr", Locale.class);
            try {
            } catch (Exception e) {
                getLogger().error(e);
            }
        }
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
    }
        
    /**
     * @return the logger
     */
    public static Logger getLogger() {
        return logger;
    }
    
}