package org.dhravid.oauth2.service;

import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

public interface AuthServerTokenService extends AuthorizationServerTokenServices
{
	public void setTokenStore(TokenStore tokenStore);
	public void setTokenEnhancer(TokenEnhancer tokenEnhancer);
	
	public void setSupportRefreshToken(boolean isSupportRefreshToken);
	public void setClientDetailsService(ClientDetailsService clientDetailsService);
	public void afterPropertiesSet() throws Exception;
	
}
