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
public class MainAuthenticator {

	private CVRGAuthenticator authenticator = null;

	public boolean authenticate(String[] args){
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
