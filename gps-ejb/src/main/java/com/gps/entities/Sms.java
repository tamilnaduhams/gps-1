/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Amine
 */
@Entity
@Table(name = "sms")
@NamedQueries({
    @NamedQuery(name = "Sms.findAll", query = "SELECT s FROM Sms s"),
    @NamedQuery(name = "Sms.findByIdSms", query = "SELECT s FROM Sms s WHERE s.idSms = :idSms"),
    @NamedQuery(name = "Sms.findByNbreMessages", query = "SELECT s FROM Sms s WHERE s.nbreMessages = :nbreMessages"),
    @NamedQuery(name = "Sms.findByDateEnvoi", query = "SELECT s FROM Sms s WHERE s.dateEnvoi = :dateEnvoi"),
    @NamedQuery(name = "Sms.findByCoutSms", query = "SELECT s FROM Sms s WHERE s.coutSms = :coutSms"),
    @NamedQuery(name = "Sms.findByStatut", query = "SELECT s FROM Sms s WHERE s.statut = :statut"),
    @NamedQuery(name = "Sms.findByDestinataires", query = "SELECT s FROM Sms s WHERE s.destinataires = :destinataires")})
public class Sms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_sms")
    private Integer idSms;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "message")
    private String message;
    @Column(name = "nbre_messages")
    private Integer nbreMessages;
    @Column(name = "date_envoi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnvoi;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "cout_sms")
    private String coutSms;
    @Size(max = 50)
    @Column(name = "statut")
    private String statut;
    @Size(max = 255)
    @Column(name = "destinataires")
    private String destinataires;
    @JoinColumn(name = "account_id", referencedColumnName = "account_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Account accountId;

    public Sms() {
    }

    public Sms(Integer idSms) {
        this.idSms = idSms;
    }

    public Integer getIdSms() {
        return idSms;
    }

    public void setIdSms(Integer idSms) {
        this.idSms = idSms;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNbreMessages() {
        return nbreMessages;
    }

    public void setNbreMessages(Integer nbreMessages) {
        this.nbreMessages = nbreMessages;
    }

    public Date getDateEnvoi() {
        return dateEnvoi;
    }

    public void setDateEnvoi(Date dateEnvoi) {
        this.dateEnvoi = dateEnvoi;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoutSms() {
        return coutSms;
    }

    public void setCoutSms(String coutSms) {
        this.coutSms = coutSms;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getDestinataires() {
        return destinataires;
    }

    public void setDestinataires(String destinataires) {
        this.destinataires = destinataires;
    }

    public Account getAccountId() {
        return accountId;
    }

    public void setAccountId(Account accountId) {
        this.accountId = accountId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSms != null ? idSms.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sms)) {
            return false;
        }
        Sms other = (Sms) object;
        if ((this.idSms == null && other.idSms != null) || (this.idSms != null && !this.idSms.equals(other.idSms))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.Sms[ idSms=" + idSms + " ]";
    }
    
}
