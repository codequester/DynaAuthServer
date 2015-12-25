package org.dhravid.oauth2.ldap.repository;

import org.dhravid.oauth2.ldap.domain.LdapUserDetails;
import org.springframework.ldap.repository.LdapRepository;

public interface LdapUserDetailsRepository extends LdapRepository<LdapUserDetails> 
{
	public LdapUserDetails findByUserId(String userId);
}
