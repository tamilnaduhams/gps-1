/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.sms.modem;
import com.sun.comm.Win32Driver;
import org.smslib.IOutboundMessageNotification;
import org.smslib.Library;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;

/**
 *
 * @author 21590495
 */
public class SendModem {
    
    private String modemPort;
    private String nPort;
    private int vitesseMaxPort;
    private String nomConstructeur;
    private String versionModem;
    private String nTelephone;
    private String message;
    
    public void doIt() throws Exception
    {
        Win32Driver w32Driver= new Win32Driver();
        w32Driver.initialize();
            //Début d'envoi
        Service srv;
        OutboundMessage msg;
        OutboundNotification outboundNotification = new OutboundNotification();
        System.out.println(Library.getLibraryDescription());
        System.out.println("Version: " + Library.getLibraryVersion());
        srv = new Service();
        SerialModemGateway gateway = new SerialModemGateway(modemPort, nPort, vitesseMaxPort,
                                                                nomConstructeur, versionModem);
        gateway.setInbound(true);
        gateway.setOutbound(true);
        gateway.setSimPin("");
        srv.setOutboundNotification(outboundNotification);
        srv.addGateway(gateway);
        srv.startService();
        System.out.println("  Manufacturer: " + gateway.getManufacturer());
        System.out.println("  Model: " + gateway.getModel());
        System.out.println("  Serial No: " + gateway.getSerialNo());
        System.out.println("  SIM IMSI: " + gateway.getImsi());
        System.out.println("  Signal Level: " + gateway.getSignalLevel() + "%");
        System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
        msg = new OutboundMessage(nTelephone, message);
        srv.sendMessage(msg);
        System.out.println("test :" + msg);
        //System.in.read();
        srv.stopService();
    }
    
     public class OutboundNotification implements IOutboundMessageNotification{
        public void process(String gatewayId, OutboundMessage msg){
            System.out.println("Outbound handler called from Gateway: " + gatewayId);
            System.out.println(msg);
        }
    }

    public String getModemPort() {
        return modemPort;
    }

    public void setModemPort(String modemPort) {
        this.modemPort = modemPort;
    }

    public String getnPort() {
        return nPort;
    }

    public void setnPort(String nPort) {
        this.nPort = nPort;
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

    public String getnTelephone() {
        return nTelephone;
    }

    public void setnTelephone(String nTelephone) {
        this.nTelephone = nTelephone;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getVitesseMaxPort() {
        return vitesseMaxPort;
    }

    public void setVitesseMaxPort(int vitesseMaxPort) {
        this.vitesseMaxPort = vitesseMaxPort;
    }
     
     

    
}
