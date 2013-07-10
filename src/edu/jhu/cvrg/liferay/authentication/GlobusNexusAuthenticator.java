package edu.jhu.cvrg.liferay.authentication;

import java.util.Map;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.AuthException;
import com.liferay.portal.security.auth.Authenticator;
import com.liferay.portal.service.UserLocalServiceUtil;

import edu.jhu.cvrg.utilities.authentication.AuthenticationMethod;
import edu.jhu.cvrg.utilities.authentication.MainAuthenticator;

public class GlobusNexusAuthenticator implements Authenticator{

	@Override
	public int authenticateByEmailAddress(long companyId, String emailAddress,
			String password, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
		
		System.out.println("Authenticating by Email");
			
			try {
				User user = UserLocalServiceUtil.getUserByEmailAddress(companyId, emailAddress);
				return authenticateByScreenName(companyId, user.getScreenName(), password, headerMap, null);
			} catch (PortalException e) {
				e.printStackTrace();
				return DNE;
			} catch (SystemException e) {
				e.printStackTrace();
				return FAILURE;
			}
	}

	@Override
	public int authenticateByScreenName(long companyId, String screenName,
			String password, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
		
		System.out.println("Authenticating by Screenname");
			
			MainAuthenticator authenticator = new MainAuthenticator();

		String[] args = { screenName, password };
		if (authenticator.authenticate(args, AuthenticationMethod.GLOBUS_REST)) {

			System.out.println("Globus Worked.");
			return SUCCESS;
		}
		else{
			return FAILURE;
		}
		
		
	}

	@Override
	public int authenticateByUserId(long companyId, long userId,
			String password, Map<String, String[]> headerMap,
			Map<String, String[]> parameterMap) throws AuthException {
		
		System.out.println("Authenticating by UID");

		try {
			User user = UserLocalServiceUtil.getUserById(companyId, userId);
			return authenticateByScreenName(companyId, user.getScreenName(), password, headerMap, null);
		} catch (PortalException e) {
			e.printStackTrace();
			return DNE;
		} catch (SystemException e) {
			e.printStackTrace();
			return FAILURE;
		}
	}

}
