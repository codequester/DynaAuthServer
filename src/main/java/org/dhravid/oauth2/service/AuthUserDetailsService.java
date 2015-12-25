package org.dhravid.oauth2.service;

import org.dhravid.oauth2.domain.AuthUserDetails;

/*
 * @author Shankar Govindarajan
*/

public interface AuthUserDetailsService 
{
	public AuthUserDetails fetchUserDetails(String userId);
}
