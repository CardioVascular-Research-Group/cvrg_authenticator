package edu.jhu.cvrg.utilities.authentication;

/*
 Copyright 2012 Johns Hopkins University Institute for Computational Medicine

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
 * @author Chris Jurado
 * 
 */
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;

public class OpenIdAuthenticator extends CVRGAuthenticator {

	private String returnToUrl = "";
	private String identifier = "";
	private String logoutUrl = "";

	public OpenIdAuthenticator(String identifier, String returnToUrl) {
		initializeAuthenticator(identifier, returnToUrl, null);
	}
	
	public OpenIdAuthenticator(String identifier, String returnToUrl, String logoutUrl){
		initializeAuthenticator(identifier, returnToUrl, logoutUrl);
	}
	
	private void initializeAuthenticator(String identifier, String returnToUrl, String logoutUrl){
		this.logoutUrl = logoutUrl;
		this.returnToUrl = returnToUrl;
		this.identifier = identifier;
	}

	@Override
	public boolean logMeIn() {

		System.out.println("Authenticating");
		boolean success = false;

		if (!identifier.isEmpty() && identifier != null) {
			
			System.out.println("Identifier has something");

			DiscoveryInformation discoveryInformation = OpenIdUtility.performDiscovery(identifier);
			AuthRequest authRequest = OpenIdUtility.createOpenIdAuthRequest(discoveryInformation, returnToUrl);

//			try {
//				HttpServletResponse response = (HttpServletResponse) FacesContext
//						.getCurrentInstance().getExternalContext()
//						.getResponse();
//				response.sendRedirect(authRequest.getDestinationUrl(true));
//			} catch (IOException e) {
//				System.out.println("oops");
//				e.printStackTrace();
//				return success;
//			}
//			
			System.out.println("SUCCESS");
			
			success = true;
		}
		
		System.out.println(success);
		return success;
	}

	public boolean logMeIn(String identifier, String returnToUrl) {
		
		this.identifier = identifier;
		this.returnToUrl = returnToUrl;

		return logMeIn();
	}

	@Override
	public boolean logMeOut() {
		
		boolean success = false;

		if (!logoutUrl.isEmpty() && logoutUrl != null) {

//			try {
//				HttpServletResponse response = (HttpServletResponse) FacesContext
//						.getCurrentInstance().getExternalContext()
//						.getResponse();
//				response.sendRedirect(this.logoutUrl);
//			} catch (IOException e) {
//				e.printStackTrace();
//				return success;
//			}
			
			success = true;
		}
		
		return success;
	}
	
	public boolean logMeOut(String logoutUrl){
		
		this.logoutUrl = logoutUrl;

		return logMeOut();
	}

	@Override
	public AuthenticationMethod getAuthenticatorType() {
		return AuthenticationMethod.OPENID;
	}

	@Override
	public String getUserEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserFullname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserFirstname() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserLastname() {
		// TODO Auto-generated method stub
		return null;
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
