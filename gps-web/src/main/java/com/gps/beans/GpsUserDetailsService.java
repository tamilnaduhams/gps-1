/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.beans;

import com.gps.entities.Utilisateur;
import com.gps.facades.local.UtilisateurFacadeLocal;
import com.gps.util.User;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 *
 * @author amine.sagaama@gmail.com
 */

@EJB(name = "utilisateurFacadeLocal", beanInterface = UtilisateurFacadeLocal.class)
public class GpsUserDetailsService implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(GpsUserDetailsService.class);
    private static final String COLLABORATEUR_EJB_LOOKUP_PATH = "java:comp/env/utilisateurFacadeLocal";
    private UtilisateurFacadeLocal utilisateurFacade;

    @Override
    public UserDetails loadUserByUsername(String userName) {
        User user;
        Utilisateur collab = getUser(userName);
        if (collab == null) {
            throw new UsernameNotFoundException(userName + " not found");
        }
        user = new User(collab);
        if (user == null) {
            throw new UsernameNotFoundException(userName + " not found");
        }
        if (!user.isEnabled()){
            throw new DisabledException("Le compte de '"+ userName + "' est désactivé"); 
        }
        return user;
    }

    private Utilisateur getUser(String userName) {
        try {
            InitialContext initialContext = new InitialContext();
            utilisateurFacade = (UtilisateurFacadeLocal) initialContext.lookup(COLLABORATEUR_EJB_LOOKUP_PATH);
            return utilisateurFacade.findUtilisateurByIdentifiant(userName);
        } catch (NamingException ex) {
            logger.error("Could not lookup for EJB CollaborateurFacadeLocal with lookup path " + COLLABORATEUR_EJB_LOOKUP_PATH);
        }
        return null;
    }
}
