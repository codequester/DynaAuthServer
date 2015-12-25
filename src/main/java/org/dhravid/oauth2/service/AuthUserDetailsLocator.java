package org.dhravid.oauth2.service;

import java.util.Map;

import org.dhravid.oauth2.domain.AuthUserDetails;
import org.dhravid.oauth2.service.DynaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthUserDetailsLocator 
{
	@Autowired
	private DynaClientDetailsService dynaClientDetailsService;

	public AuthUserDetails fetchUserDetails(ApplicationContext appContext, String clientId, String userId)
	{
		AuthUserDetailsService userDetailService = getAuthUserDetailsServiceForClient(appContext,clientId);
		return userDetailService.fetchUserDetails(userId);
	}

	private AuthUserDetailsService getAuthUserDetailsServiceForClient(ApplicationContext appContext,String clientId)
	{
		ClientDetails clientDetails = dynaClientDetailsService.loadClientByClientId(clientId);
		Map<String, Object> additionalClientInformation = clientDetails.getAdditionalInformation();
		return (AuthUserDetailsService) appContext.getBean((String)additionalClientInformation.get("user_store"));
	}	
}
