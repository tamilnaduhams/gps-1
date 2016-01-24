/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gps.entities.Droit;
import com.gps.entities.Utilisateur;

/**
 *
 * @author amine.sagaama@gmail.com
 */

public class User implements UserDetails {

	private Utilisateur utilisateur;

	public User(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {

		final List<Droit> systemAuthorities = new ArrayList<>();
		systemAuthorities.add(utilisateur.getIdDroit());
		GrantedAuthority h = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return systemAuthorities.get(0).getLibelle();
			}
		};
		HashSet<GrantedAuthority> authoritys = new HashSet<>();
		authoritys.add(h);
		return authoritys;
	}

	@Override
	public String getPassword() {
		return utilisateur.getMotPasse();
	}

	@Override
	public String getUsername() {
		return utilisateur.getIdentifiant();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return utilisateur.getEnabled();
	}
}
