/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gps.sms.ws;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 *
 * @author Amine
 */
public class SendRedOxygen {
    
    private String accountId;
    private String email;
    private String password;
    private String recipient;
    private String message;
    private String str = "a string";
    private StringBuffer Response = new StringBuffer(str);
    
    
   public  int SendSMS()
			{
			int Result = 0;
			try {
				String RequestURL = "http://www.redoxygen.net/sms.dll?Action=SendSMS";

				String Data = ("AccountId=" + URLEncoder.encode(accountId, "UTF-8"));
				Data += ("&Email=" + URLEncoder.encode(email, "UTF-8"));
				Data += ("&Password=" + URLEncoder.encode(password, "UTF-8"));
				Data += ("&Recipient=" + URLEncoder.encode(recipient, "UTF-8"));
				Data += ("&Message=" + URLEncoder.encode(message, "UTF-8"));

				Result = -1;
				URL Address = new URL(RequestURL);

				java.net.HttpURLConnection Connection = (java.net.HttpURLConnection) Address.openConnection();
				Connection.setRequestMethod("POST");
				Connection.setDoInput(true);
				Connection.setDoOutput(true);

				DataOutputStream Output;
				Output = new DataOutputStream(Connection.getOutputStream());
				Output.writeBytes(Data);
				Output.flush();
				Output.close();

				BufferedReader Input = new BufferedReader(new InputStreamReader(Connection.getInputStream()));
				StringBuffer ResponseBuffer = new StringBuffer();
				String InputLine;

				while ((InputLine = Input.readLine()) != null)
				{
				ResponseBuffer = ResponseBuffer.append(InputLine);
				ResponseBuffer = ResponseBuffer.append("\n\n\n");
				}

				Response.replace(0, 0, ResponseBuffer.toString());
				String ResultCode = Response.substring(0, 4);
				Result = Integer.parseInt(ResultCode);
				Input.close();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return Result;
			}

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StringBuffer getResponse() {
        return Response;
    }

    public void setResponse(StringBuffer Response) {
        this.Response = Response;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

  
   
   

    
}
