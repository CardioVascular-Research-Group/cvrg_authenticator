package edu.jhu.cvrg.utilities.authentication;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class CILogonAuthenticator extends CVRGAuthenticator {

	private static org.apache.log4j.Logger logger = Logger.getLogger(GlobusRESTAuthenticator.class);
	
	private String userEmail, userFullname, userFirstname, userLastname, userOrganization, userInstitution;

	CILogonAuthenticator() {


	}

	@Override
	public boolean logMeIn(HttpServletRequest req) {

		
		userEmail = getEmailFromCookie(req.getCookies());
		String[] userName = getNameFromCookie(req.getCookies());
		return false;
	}
	
private String getEmailFromCookie(Cookie[] cookies){
		
		String email = "";
		
		if(cookies != null){
			for (Cookie ck : cookies) {
				if ("CILOGON-USER_EMAIL".equals(ck.getName()) && !ck.getValue().equals("")) {
					logger.info("User Login received:" + ck.getValue());
					email = ck.getValue();
				}
		  	}
		}
		
		return email;
	}
	
	private String[] getNameFromCookie(Cookie[] cookies){
		
		String name = "";
		
		if(cookies != null){
			for (Cookie ck : cookies) {
				if ("CILOGON-USER_NAME".equals(ck.getName()) && !ck.getValue().equals("")) {
					name = (String)ck.getValue();
				}
			}
			return name.split(" ");
		}

		return null;
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
		// TODO Auto-generated method stub
		return null;
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

	@Override
	public boolean logMeIn() {
		// TODO Auto-generated method stub
		return false;
	}
}