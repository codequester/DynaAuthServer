package org.dhravid.oauth2.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * @author Shankar Govindarajan
*/

@Service
public class DynaAuthServerTokenService extends DefaultTokenServices implements AuthServerTokenService 
{
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private DynaClientDetailsService dynaClientDetailsService;
	
	@Autowired
	private DataSource dataSource;

	public DynaAuthServerTokenService()
	{
		super();
		this.setTokenStore(new InMemoryTokenStore());
	}
	
	@Transactional
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException 
	{
		String clientId = request.getParameter(OAuth2Utils.CLIENT_ID);
		ClientDetails clientDetails = dynaClientDetailsService.loadClientByClientId(clientId);
		setTokenStoreForClient(clientDetails);
		return super.createAccessToken(authentication);
	}
	
	private void setTokenStoreForClient(ClientDetails clientDetails)
	{
		Map<String, Object> additionalClientInformation = clientDetails.getAdditionalInformation();
		String tokenStoreName = (String)additionalClientInformation.get("token_store");
		if("jdbcTokenStore".equals(tokenStoreName))
		{
			this.setTokenStore(new JdbcTokenStore(dataSource));
			this.setTokenEnhancer(null);
		}
		else if("jwtTokenStore".equals(tokenStoreName))
		{
			JwtAccessTokenConverter tokenEnhancer = null;
			try {
				tokenEnhancer = new AuthServerJwtEnhancer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			TokenStore defaultTokenStore = new JwtTokenStore(tokenEnhancer);
			this.setTokenStore(defaultTokenStore);
			this.setTokenEnhancer(tokenEnhancer);
		}
	}
}
