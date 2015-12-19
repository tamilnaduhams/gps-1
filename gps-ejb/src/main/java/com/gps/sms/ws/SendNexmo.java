/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.sms.ws;


import com.nexmo.messaging.sdk.NexmoSmsClient;
import com.nexmo.messaging.sdk.SmsSubmissionResult;
import com.nexmo.messaging.sdk.messages.TextMessage;

/**
 *
 * @author 21590495
 */
public class SendNexmo {
    
    private String apiKey;
    private String apiSecret;

    private String smsFrom;
    private String smsTo;
    private String smsText;
    
    private String compte;
    
    private String coutSms;
    
    public void send(){
        NexmoSmsClient client = null;
        try {
            client = new NexmoSmsClient(apiKey, apiSecret);
        } catch (Exception e) {
            System.err.println("Failed to instanciate a Nexmo Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to instanciate a Nexmo Client");
        }
        
        // Create a Text SMS Message request object ...

        TextMessage message = new TextMessage(smsFrom, smsTo, smsText);

        // Use the Nexmo client to submit the Text Message ...

        SmsSubmissionResult[] results = null;
        try {
            results = client.submitMessage(message);
        } catch (Exception e) {
            System.err.println("Failed to communicate with the Nexmo Client");
            e.printStackTrace();
            throw new RuntimeException("Failed to communicate with the Nexmo Client");
        }

        // Evaluate the results of the submission attempt ...
        System.out.println("... Message submitted in [ " + results.length + " ] parts");
        for (int i=0;i<results.length;i++) {
            System.out.println("--------- part [ " + (i + 1) + " ] ------------");
            System.out.println("Status [ " + results[i].getStatus() + " ] ...");
            if (results[i].getStatus() == SmsSubmissionResult.STATUS_OK){
                System.out.println("SUCCESS");
             
            }
            else if (results[i].getTemporaryError())
                System.out.println("TEMPORARY FAILURE - PLEASE RETRY");
            else
                System.out.println("SUBMISSION FAILED!");
            System.out.println("Message-Id [ " + results[i].getMessageId() + " ] ...");
            System.out.println("Error-Text [ " + results[i].getErrorText() + " ] ...");

            if (results[i].getMessagePrice() != null){
                System.out.println("Message-Price [ " + results[i].getMessagePrice() + " ] ...");
            }
            if (results[i].getRemainingBalance() != null){
                System.out.println("Remaining-Balance [ " + results[i].getRemainingBalance() + " ] ...");
                compte = ""+ results[i].getRemainingBalance();
                coutSms = ""+ results[i].getMessagePrice();
            }
        }
        
    }
        
    

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public String getSmsFrom() {
        return smsFrom;
    }

    public void setSmsFrom(String smsFrom) {
        this.smsFrom = smsFrom;
    }

    public String getSmsTo() {
        return smsTo;
    }

    public void setSmsTo(String smsTo) {
        this.smsTo = smsTo;
    }

    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    public String getCompte() {
        return compte;
    }

    public void setCompte(String compte) {
        this.compte = compte;
    }

    public String getCoutSms() {
        return coutSms;
    }

    public void setCoutSms(String coutSms) {
        this.coutSms = coutSms;
    }

    

    
    
}
