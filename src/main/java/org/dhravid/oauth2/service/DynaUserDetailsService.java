package org.dhravid.oauth2.service;

import javax.servlet.http.HttpServletRequest;

import org.dhravid.oauth2.domain.AuthUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.stereotype.Service;

@Service
public class DynaUserDetailsService implements UserDetailsService 
{

	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	private AuthUserDetailsLocator authUserDetailsLocator;
	
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException 
	{
		String clientId = request.getParameter(OAuth2Utils.CLIENT_ID);
		AuthUserDetails userDetails = authUserDetailsLocator.fetchUserDetails(appContext, clientId, userId);
		if(userDetails.getUserId()!=null)
		{
			return new User(userDetails.getUserId(), userDetails.getPassword(), AuthorityUtils.createAuthorityList("USER"));
		}
		else
		{
			throw new UsernameNotFoundException("User ["+userId+"] Not Found");
		}
	}
}
