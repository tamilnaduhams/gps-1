/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.helpers;

import java.util.Date;
import java.util.TimeZone;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
/**
 *
 * @author amine.sagaama@gmail.com
 */

@ManagedBean(name = "defaultTimeZone")
@ApplicationScoped
public class DefaultTimeZone {

    private Date currentDate ;
    
    public DefaultTimeZone() {
    }

    public TimeZone getTimeZone() {
        return TimeZone.getDefault();
    }

    /**
     * @return the currentDate
     */
    public Date getCurrentDate() {
        currentDate = new Date();
        return currentDate;
    }

    /**
     * @param currentDate the currentDate to set
     */
    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }
}
