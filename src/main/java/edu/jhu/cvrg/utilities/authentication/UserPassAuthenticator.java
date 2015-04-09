package edu.jhu.cvrg.utilities.authentication;

import javax.servlet.http.HttpServletRequest;
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

	@Override
	public boolean logMeIn(HttpServletRequest req) {
		// TODO Auto-generated method stub
		return false;
	}

}
