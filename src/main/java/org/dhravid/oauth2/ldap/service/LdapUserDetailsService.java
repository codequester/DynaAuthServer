package org.dhravid.oauth2.ldap.service;

import org.dhravid.oauth2.domain.AuthUserDetails;
import org.dhravid.oauth2.ldap.domain.LdapUserDetails;
import org.dhravid.oauth2.ldap.repository.LdapUserDetailsRepository;
import org.dhravid.oauth2.service.AuthUserDetailsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * @author Shankar Govindarajan
*/

@Service("ldapUserStore")
public class LdapUserDetailsService implements AuthUserDetailsService 
{

	@Autowired
	public LdapUserDetailsRepository userDetailsRepository;
	
	@Override
	public AuthUserDetails fetchUserDetails(String userId) 
	{
		AuthUserDetails userDetails = new AuthUserDetails();
		LdapUserDetails ldapUserDetails = userDetailsRepository.findByUserId(userId);
		BeanUtils.copyProperties(ldapUserDetails, userDetails);
		return userDetails;
	}
}
