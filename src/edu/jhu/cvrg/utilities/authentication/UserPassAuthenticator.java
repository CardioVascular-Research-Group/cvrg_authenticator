package edu.jhu.cvrg.utilities.authentication;

public class UserPassAuthenticator extends CVRGAuthenticator{
	
	private String username, password;

	UserPassAuthenticator(String username, String password){
		this.username = username;
		this.password = password;
	}
	
	@Override
	public boolean logMeIn() {

		if(username.equals("test") && password.equals("test"))
			return true;
		else
			return false;
	}

	@Override
	public boolean logMeOut() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean logMeOut(String logOutUrl) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AuthenticationMethod getAuthenticatorType() {
		return AuthenticationMethod.USERPASS;
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
