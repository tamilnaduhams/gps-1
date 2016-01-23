/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Amine
 */
@Entity
@Table(name = "service")
@NamedQueries({ @NamedQuery(name = "Service.findAll", query = "SELECT s FROM Service s"),
		@NamedQuery(name = "Service.findByIdService", query = "SELECT s FROM Service s WHERE s.idService = :idService"),
		@NamedQuery(name = "Service.findByNomService", query = "SELECT s FROM Service s WHERE s.nomService = :nomService"),
		@NamedQuery(name = "Service.findByClasseService", query = "SELECT s FROM Service s WHERE s.classeService = :classeService"),
		@NamedQuery(name = "Service.findByUrlService", query = "SELECT s FROM Service s WHERE s.urlService = :urlService"),
		@NamedQuery(name = "Service.findByTypeService", query = "SELECT s FROM Service s WHERE s.typeService = :typeService"),
		@NamedQuery(name = "Service.findByCoutSmsCalc", query = "SELECT s FROM Service s WHERE s.coutSmsCalc = :coutSmsCalc") })
public class Service implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id_service")
	private Integer idService;
	@Size(max = 255)
	@Column(name = "nom_service")
	private String nomService;
	@Size(max = 255)
	@Column(name = "classe_service")
	private String classeService;
	@Size(max = 255)
	@Column(name = "url_service")
	private String urlService;
	@Size(max = 50)
	@Column(name = "type_service")
	private String typeService;
	@Size(max = 255)
	@Column(name = "cout_sms_calc")
	private String coutSmsCalc;
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "service", fetch = FetchType.EAGER)
	private Servicequeue servicequeue;
	@OneToMany(mappedBy = "idService", fetch = FetchType.EAGER)
	private List<Account> accountList;

	public Service() {
	}

	public Service(Integer idService) {
		this.idService = idService;
	}

	public Integer getIdService() {
		return idService;
	}

	public void setIdService(Integer idService) {
		this.idService = idService;
	}

	public String getNomService() {
		return nomService;
	}

	public void setNomService(String nomService) {
		this.nomService = nomService;
	}

	public String getClasseService() {
		return classeService;
	}

	public void setClasseService(String classeService) {
		this.classeService = classeService;
	}

	public String getUrlService() {
		return urlService;
	}

	public void setUrlService(String urlService) {
		this.urlService = urlService;
	}

	public String getTypeService() {
		return typeService;
	}

	public void setTypeService(String typeService) {
		this.typeService = typeService;
	}

	public String getCoutSmsCalc() {
		return coutSmsCalc;
	}

	public void setCoutSmsCalc(String coutSmsCalc) {
		this.coutSmsCalc = coutSmsCalc;
	}

	public Servicequeue getServicequeue() {
		return servicequeue;
	}

	public void setServicequeue(Servicequeue servicequeue) {
		this.servicequeue = servicequeue;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idService != null ? idService.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Service)) {
			return false;
		}
		Service other = (Service) object;
		if ((this.idService == null && other.idService != null)
				|| (this.idService != null && !this.idService.equals(other.idService))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.Service[ idService=" + idService + " ]";
	}

}
