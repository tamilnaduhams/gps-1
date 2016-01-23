/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Amine Sagaama
 */
@Entity
@Table(name = "general_settings")
@XmlRootElement
@NamedQueries({ @NamedQuery(name = "GeneralSettings.findAll", query = "SELECT g FROM GeneralSettings g") })
public class GeneralSettings implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_general_settings")
	private Integer idGeneralSettings;
	@Size(max = 256)
	@Column(name = "setting_code")
	private String settingCode;
	@Size(max = 256)
	@Column(name = "setting_value")
	private String settingValue;
	@Size(max = 1024)
	@Column(name = "description")
	private String description;
	@Column(name = "enabled")
	private Boolean enabled;

	public GeneralSettings() {
	}

	public GeneralSettings(Integer idGeneralSettings) {
		this.idGeneralSettings = idGeneralSettings;
	}

	public Integer getIdGeneralSettings() {
		return idGeneralSettings;
	}

	public void setIdGeneralSettings(Integer idGeneralSettings) {
		this.idGeneralSettings = idGeneralSettings;
	}

	public String getSettingCode() {
		return settingCode;
	}

	public void setSettingCode(String settingCode) {
		this.settingCode = settingCode;
	}

	public String getSettingValue() {
		return settingValue;
	}

	public void setSettingValue(String settingValue) {
		this.settingValue = settingValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idGeneralSettings != null ? idGeneralSettings.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof GeneralSettings)) {
			return false;
		}
		GeneralSettings other = (GeneralSettings) object;
		if ((this.idGeneralSettings == null && other.idGeneralSettings != null)
				|| (this.idGeneralSettings != null && !this.idGeneralSettings.equals(other.idGeneralSettings))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.GeneralSettings[ idGeneralSettings=" + idGeneralSettings + " ]";
	}

}
