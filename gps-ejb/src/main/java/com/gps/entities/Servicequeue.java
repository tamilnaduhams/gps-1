/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author Amine
 */
@Entity
@Table(name = "servicequeue")
@NamedQueries({ @NamedQuery(name = "Servicequeue.findAll", query = "SELECT s FROM Servicequeue s"),
		@NamedQuery(name = "Servicequeue.findByIdServicequeue", query = "SELECT s FROM Servicequeue s WHERE s.idServicequeue = :idServicequeue"),
		@NamedQuery(name = "Servicequeue.findByOrdre", query = "SELECT s FROM Servicequeue s WHERE s.ordre = :ordre") })
public class Servicequeue implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "id_servicequeue")
	private Integer idServicequeue;
	@Column(name = "ordre")
	private Integer ordre;
	@JoinColumn(name = "id_servicequeue", referencedColumnName = "id_service", insertable = false, updatable = false)
	@OneToOne(optional = false)
	private Service service;

	public Servicequeue() {
	}

	public Servicequeue(Integer idServicequeue) {
		this.idServicequeue = idServicequeue;
	}

	public Integer getIdServicequeue() {
		return idServicequeue;
	}

	public void setIdServicequeue(Integer idServicequeue) {
		this.idServicequeue = idServicequeue;
	}

	public Integer getOrdre() {
		return ordre;
	}

	public void setOrdre(Integer ordre) {
		this.ordre = ordre;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idServicequeue != null ? idServicequeue.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof Servicequeue)) {
			return false;
		}
		Servicequeue other = (Servicequeue) object;
		if ((this.idServicequeue == null && other.idServicequeue != null)
				|| (this.idServicequeue != null && !this.idServicequeue.equals(other.idServicequeue))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.gps.entities.Servicequeue[ idServicequeue=" + idServicequeue + " ]";
	}

}
