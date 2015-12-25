package org.dhravid.oauth2.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("defaultLdapAuthentication")
public class DefaultLdapAuthenticationProvider implements AuthenticationProvider
{
	private LdapAuthenticationProvider ldapAuthProvider;
	
	@Autowired
	public DefaultLdapAuthenticationProvider(LdapContextSource contextSource)
	{
		BindAuthenticator ldapAuthenticator = new BindAuthenticator(contextSource);
		String[] userDnPattern = {"cn={0},ou=users"};
		ldapAuthenticator.setUserDnPatterns(userDnPattern);
		ldapAuthProvider = new LdapAuthenticationProvider(ldapAuthenticator);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
		return ldapAuthProvider.authenticate(authentication);
	}

	@Override
	public boolean supports(Class<?> authentication) 
	{
		return false;
	}
}
