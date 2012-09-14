package edu.jhu.cvrg.utilities.authentication;

public class MainAuthenticator {

	private CVRGAuthenticator authenticator = null;

	public boolean Authenticate(String[] args){
		return authenticate(args, AuthenticationMethod.DEFAULT);
	}
	
	public boolean authenticate(String[] args, AuthenticationMethod method){
		authenticator = AuthenticatorFactory.returnAuthenticator(method, args);
		
		return authenticator.logMeIn();
	}
	
	public boolean logOut(){
		return authenticator.logMeOut();
	}
	
	public boolean logOut(String logoutUrl){
		return authenticator.logMeOut(logoutUrl);
	}
	
	public AuthenticationMethod getAuthenticatorType(){
		return authenticator.getAuthenticatorType();
	}
	
	public String getUserEmail() {
		return authenticator.getUserEmail();
	}

	public String getUserFullname() {
		return authenticator.getUserFullname();
	}

	public String getUserFirstname() {
		return authenticator.getUserFirstname();
	}

	public String getUserLastname() {
		return authenticator.getUserLastname();
	}
	
	public String getUserInstitution(){
		return authenticator.getUserInstitution();
	}
	
	public String gertUserOrganization(){
		return authenticator.getUserOrganization();
	}

}
