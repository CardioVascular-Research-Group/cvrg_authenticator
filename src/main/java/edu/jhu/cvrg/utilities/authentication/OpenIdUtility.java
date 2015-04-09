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
import java.util.List;

import org.apache.log4j.Logger;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.InMemoryConsumerAssociationStore;
import org.openid4java.consumer.InMemoryNonceVerifier;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.sreg.SRegRequest;

public class OpenIdUtility {
	
	private static ConsumerManager consumerManager;
	private static final Logger log = Logger.getLogger(OpenIdUtility.class);
	
	@SuppressWarnings("unchecked")
	public static DiscoveryInformation performDiscovery(String userSuppliedIdentifier) {
		DiscoveryInformation ret = null;
		//
		ConsumerManager consumerManager = getConsumerManager();
		try {
			// Perform discover on the User-Supplied Identifier
			List<DiscoveryInformation> discoveries = consumerManager
					.discover(userSuppliedIdentifier);
			// Pass the discoveries to the associate() method...
			ret = consumerManager.associate(discoveries);

		} catch (DiscoveryException e) {
			String message = "Error occurred during discovery!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return ret;
	}
	
	private static ConsumerManager getConsumerManager() {
		try {
			if (consumerManager == null) {
				consumerManager = new ConsumerManager();
				consumerManager.setAssociations(new InMemoryConsumerAssociationStore());
				consumerManager.setNonceVerifier(new InMemoryNonceVerifier(10000));
			}
		} catch (Exception e) {
			String message = "Exception creating ConsumerManager!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return consumerManager;
	}
	
	public static AuthRequest createOpenIdAuthRequest(DiscoveryInformation discoveryInformation, String returnToUrl) {
		AuthRequest ret = null;
		//
		try {
			// Create the AuthRequest object
			ret = getConsumerManager().authenticate(discoveryInformation, returnToUrl);
			// Create the Simple Registration Request
			SRegRequest sRegRequest = SRegRequest.createFetchRequest();
			sRegRequest.addAttribute("email", false);
			sRegRequest.addAttribute("fullname", false);
			sRegRequest.addAttribute("dob", false);
			sRegRequest.addAttribute("postcode", false);
			ret.addExtension(sRegRequest);
			
		} catch (Exception e) {
			String message = "Exception occurred while building AuthRequest object!";
			log.error(message, e);
			throw new RuntimeException(message, e);
		}
		return ret;
	}
}
