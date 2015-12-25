package org.dhravid.oauth2.provider;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.dhravid.oauth2.service.AuthServerJwtEnhancer;
import org.dhravid.oauth2.service.AuthServerTokenService;
import org.dhravid.oauth2.service.DynaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.stereotype.Component;

@Component("dynaRoutingAuthProvider")
public class DynaRoutingAuthProvider implements AuthenticationProvider 
{
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private DynaClientDetailsService dynaClientDetailsService;
		
	private DaoAuthenticationProvider daoAuthProvider;
	
	@Autowired
	public DynaRoutingAuthProvider(UserDetailsService userDetailsService) throws Exception
	{
		daoAuthProvider = new DaoAuthenticationProvider();
		daoAuthProvider.setUserDetailsService(userDetailsService);
	}
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException
	{
		String clientId = request.getParameter(OAuth2Utils.CLIENT_ID);
		ClientDetails clientDetails = dynaClientDetailsService.loadClientByClientId(clientId);
		return getAuthenticationProviderForClient(clientDetails).authenticate(authentication);
	}
	
	private AuthenticationProvider getAuthenticationProviderForClient(ClientDetails clientDetails)
	{
		AuthenticationProvider authProviderForClient = daoAuthProvider;
		Map<String, Object> additionalClientInformation = clientDetails.getAdditionalInformation();
		String authProviderNames = (String)additionalClientInformation.get("auth_providers");
		if(StringUtils.isNotBlank(authProviderNames))
		{
			for(String authProviderName : authProviderNames.split(","))
			{
				authProviderForClient = ((AuthenticationProvider)appContext.getBean(authProviderName));
				break;
			}
		}
		return authProviderForClient;
	}
	
	@Override
	public boolean supports(Class<?> authentication) 
	{
		return true;
	}	
}
