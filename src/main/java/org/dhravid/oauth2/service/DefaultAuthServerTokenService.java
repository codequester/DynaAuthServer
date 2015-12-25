package org.dhravid.oauth2.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DefaultAuthServerTokenService extends DefaultTokenServices implements AuthServerTokenService 
{
/*	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ApplicationContext appContext;	
*/
	public DefaultAuthServerTokenService()
	{
		super();
		this.setTokenStore(new InMemoryTokenStore());
	}
	
/*	@Transactional
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException 
	{
		super.createAccessToken();
	}
*/	
}
