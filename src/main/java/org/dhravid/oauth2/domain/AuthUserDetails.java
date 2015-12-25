package org.dhravid.oauth2.domain;

import lombok.Data;

@Data
public class AuthUserDetails 
{
	private String id;
	private String userId;
	private String password;
	private String companyId;
}
