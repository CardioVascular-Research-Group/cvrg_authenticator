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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import edu.jhu.cvrg.nexus.GlobusOnlineRestClient;

public class GlobusRESTAuthenticator extends CVRGAuthenticator{
	
	private static String GO_HOST = "";

	static org.apache.log4j.Logger logger = Logger.getLogger(GlobusRESTAuthenticator.class);
	private String username, password;
	
	private String userEmail, userFullname, userOrganization, userInstitution;

	private void loadDefaultURL(){
		
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
	
	GlobusRESTAuthenticator(String[] args){
		this.username = args[0];
		this.password = args[1];
		
		if(args.length == 2){
			loadDefaultURL();
		}
		else{
			GO_HOST=args[2];
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

	@Override
	public boolean logMeIn(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
	}
}
