/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author Amine
 */
@Entity
@Table(name = "account")
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId"),
    @NamedQuery(name = "Account.findByLogin", query = "SELECT a FROM Account a WHERE a.login = :login"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByAuthToken", query = "SELECT a FROM Account a WHERE a.authToken = :authToken"),
    @NamedQuery(name = "Account.findByBalance", query = "SELECT a FROM Account a WHERE a.balance = :balance"),
    @NamedQuery(name = "Account.findByCompteWsSecret", query = "SELECT a FROM Account a WHERE a.compteWsSecret = :compteWsSecret"),
    @NamedQuery(name = "Account.findByModemPort", query = "SELECT a FROM Account a WHERE a.modemPort = :modemPort"),
    @NamedQuery(name = "Account.findByNumPort", query = "SELECT a FROM Account a WHERE a.numPort = :numPort"),
    @NamedQuery(name = "Account.findByVitesseMaxPort", query = "SELECT a FROM Account a WHERE a.vitesseMaxPort = :vitesseMaxPort"),
    @NamedQuery(name = "Account.findByNomConstructeur", query = "SELECT a FROM Account a WHERE a.nomConstructeur = :nomConstructeur"),
    @NamedQuery(name = "Account.findByVersionModem", query = "SELECT a FROM Account a WHERE a.versionModem = :versionModem"),
    @NamedQuery(name = "Account.findByNumTel", query = "SELECT a FROM Account a WHERE a.numTel = :numTel"),
    @NamedQuery(name = "Account.findByNumCentreService", query = "SELECT a FROM Account a WHERE a.numCentreService = :numCentreService"),
    @NamedQuery(name = "Account.findByCoutSms", query = "SELECT a FROM Account a WHERE a.coutSms = :coutSms")})
public class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_id")
    private Integer accountId;
    @Size(max = 255)
    @Column(name = "login")
    private String login;
    @Size(max = 255)
    @Column(name = "password")
    private String password;
    @Size(max = 255)
    @Column(name = "auth_token")
    private String authToken;
    @Size(max = 100)
    @Column(name = "balance")
    private String balance;
    @Size(max = 255)
    @Column(name = "compte_ws_secret")
    private String compteWsSecret;
    @Size(max = 255)
    @Column(name = "modem_port")
    private String modemPort;
    @Size(max = 255)
    @Column(name = "num_port")
    private String numPort;
    @Column(name = "vitesse_max_port")
    private Integer vitesseMaxPort;
    @Size(max = 255)
    @Column(name = "nom_constructeur")
    private String nomConstructeur;
    @Size(max = 255)
    @Column(name = "version_modem")
    private String versionModem;
    @Size(max = 255)
    @Column(name = "num_tel")
    private String numTel;
    @Size(max = 255)
    @Column(name = "num_centre_service")
    private String numCentreService;
    @Size(max = 255)
    @Column(name = "cout_sms")
    private String coutSms;
    @JoinColumn(name = "id_service", referencedColumnName = "id_service")
    @ManyToOne(fetch = FetchType.EAGER)
    private Service idService;
    @OneToMany(mappedBy = "accountId", fetch = FetchType.EAGER)
    private List<Sms> smsList;

    public Account() {
    }

    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getCompteWsSecret() {
        return compteWsSecret;
    }

    public void setCompteWsSecret(String compteWsSecret) {
        this.compteWsSecret = compteWsSecret;
    }

    public String getModemPort() {
        return modemPort;
    }

    public void setModemPort(String modemPort) {
        this.modemPort = modemPort;
    }

    public String getNumPort() {
        return numPort;
    }

    public void setNumPort(String numPort) {
        this.numPort = numPort;
    }

    public Integer getVitesseMaxPort() {
        return vitesseMaxPort;
    }

    public void setVitesseMaxPort(Integer vitesseMaxPort) {
        this.vitesseMaxPort = vitesseMaxPort;
    }

    public String getNomConstructeur() {
        return nomConstructeur;
    }

    public void setNomConstructeur(String nomConstructeur) {
        this.nomConstructeur = nomConstructeur;
    }

    public String getVersionModem() {
        return versionModem;
    }

    public void setVersionModem(String versionModem) {
        this.versionModem = versionModem;
    }

    public String getNumTel() {
        return numTel;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getNumCentreService() {
        return numCentreService;
    }

    public void setNumCentreService(String numCentreService) {
        this.numCentreService = numCentreService;
    }

    public String getCoutSms() {
        return coutSms;
    }

    public void setCoutSms(String coutSms) {
        this.coutSms = coutSms;
    }

    public Service getIdService() {
        return idService;
    }

    public void setIdService(Service idService) {
        this.idService = idService;
    }

    public List<Sms> getSmsList() {
        return smsList;
    }

    public void setSmsList(List<Sms> smsList) {
        this.smsList = smsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.gps.entities.Account[ accountId=" + accountId + " ]";
    }
    
}
