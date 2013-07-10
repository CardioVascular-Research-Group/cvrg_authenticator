package edu.jhu.cvrg.utilities.authentication;
/*
Copyright 2012 Johns Hopkins University Institute for Computational Medicine
Portions of the authenticate method and the entire trustAllCerts method written originally
by Kyle Chard, Washington University School of Medicine

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
/**
* @author Chris Jurado, Kyle Chard
* 
*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import edu.jhu.cvrg.nexus.GlobusOnlineRestClient;

public class GlobusRESTAuthenticator extends CVRGAuthenticator{
	
	private static String GO_HOST = "";

	static org.apache.log4j.Logger logger = Logger.getLogger(GlobusRESTAuthenticator.class);
	private String username, password;
	
	private String userEmail, userFullname, userOrganization, userInstitution;

	GlobusRESTAuthenticator(String username, String password){
		this.username = username;
		this.password = password;
		
		Properties props = new Properties();
		
	     try {
	            String fileName = "/resources/authenticator.config";            
	            InputStream stream = GlobusRESTAuthenticator.class.getResourceAsStream(fileName);

	            props.load(stream);

	            GO_HOST = (props.getProperty("globus.url", "missing"));
	        } catch (FileNotFoundException e) {
	        	logger.error("authenticator.config not found.");
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}

	@Override
	public boolean logMeIn() {
		return authenticate(username, password);
	}

	@Override
	public boolean logMeOut() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	public boolean authenticate(String username, String password){
		
		boolean success = false;
		
		GlobusOnlineRestClient client = new GlobusOnlineRestClient();
		success = client.usernamePasswordLogin(username, password);
		userFullname = client.getCurrentUserFullName();
		userEmail = client.getCurrentUserEmail();
		
		if(success){
			System.out.println("Authentication succeeded for user " + userFullname);
		}
		else{
			System.out.println("Authentication failed, because you suck.");
		}
		
		return success;
	}
	
//	public boolean authenticateOld(String username, String password){
//		
//		boolean success = false;
//		
//		if(GO_HOST.equals("missing")){
//			logger.error("Host URL Configuration missing.");
//			return success;
//		}
//		
//		 try {
//		        SSLContext sc = SSLContext.getInstance("SSL");
//		        sc.init(null, trustAllCerts, new SecureRandom());
//		        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
//		        
//		        URL url = new URL("https://" + GO_HOST + "/authenticate"); 
//		        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection(); 
//		        connection.setDoOutput(true); 
//		        connection.setInstanceFollowRedirects(false); 
//		        connection.setRequestMethod("POST"); 
//		        connection.setRequestProperty("Content-Type", "application/json"); 
//		        connection.setRequestProperty("Accept", "application/json"); 
//		        
//		        OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
//		        out.write("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}");
//		        out.close();
//
//		        if(connection.getResponseCode() == 203){
//		        	logger.error("Access is denied.  Invalid credentials.");
//		        }
//		        if(connection.getResponseCode() == 204){
//		        	logger.error("Authentciation URL invalid.");
//		        }      
//		        
//		        if (connection.getResponseCode() != 200){
//		        	logger.error("Unable to Authenticate.  Error code " + connection.getResponseCode());
//		        }else {        	
//		        	logger.info("Valid GO login details");
//		        	
//		        	success = true;
//			        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));		
//			        String decodedString = in.readLine();
//			        
//			        System.out.println("Response data is " + decodedString);
//			        
//			        JSONObject json = new JSONObject(decodedString);
//			        userFullname = (String)json.get("fullname");
//			        userEmail = (String)json.get("email");
//			        int index = userFullname.indexOf(" ");
//			        if (index > 0){
////			        	userFirstname = userFullname.substring(0,index);
////			        	userLastname = userFullname.substring(index+1);
//			        	userInstitution = (String)json.get("institution");
//			        	userOrganization = (String)json.get("organization");
//			        }
////			        json.put("firstname", userFirstname);
////			        json.put("lastname", userLastname);
////			        return json;
//		        }
//		        connection.disconnect(); 
//		    } catch (Exception e) {
//		    	System.out.println(e);
//		        return success;
//		    }
//		    return success;   
//	}
	
	// Create a trust manager that does not validate certificate chains
	static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
    	public X509Certificate[] getAcceptedIssuers() { return null;}
    	public void checkClientTrusted(X509Certificate[] certs, String authType) { return; }
    	public void checkServerTrusted(X509Certificate[] certs, String authType) { return; }
	}};

	@Override
	public boolean logMeOut(String logOutUrl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AuthenticationMethod getAuthenticatorType() {
		return AuthenticationMethod.GLOBUS_REST;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserFullname() {
		return userFullname;
	}

	public String getUserFirstname() {
        int index = userFullname.indexOf(" ");
        if (index > 0){
        	return userFullname.substring(0,index);
        }
        return "";
	}

	public String getUserLastname() {
        int index = userFullname.indexOf(" ");
        if (index > 0){
        	return userFullname.substring(index+1);
        }
        return "";
	}

	@Override
	public String getUserInstitution() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserOrganization() {
		// TODO Auto-generated method stub
		return null;
	}
}
