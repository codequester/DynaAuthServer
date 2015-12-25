package org.dhravid.oauth2.ldap.domain;

import java.io.UnsupportedEncodingException;

import javax.naming.Name;


import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Attribute.Type;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import lombok.Data;

@Entry(objectClasses={"inetOrgPerson","organizationalPerson","person","top"}, base="ou=users")
public @Data class LdapUserDetails 
{
	@Id
	private Name id;
	
	@Attribute(name="cn")
	@DnAttribute(value="cn")
	private String userId;
	
	@Attribute(name="userPassword", type=Type.BINARY)
	private byte[] userPassword;
	
	@Attribute(name="o")
	private String companyId;
	
	@Transient
	private String password;

	public String getPassword()
	{
		try 
		{
			password = new String(userPassword, "UTF-8");
		} 
		catch (UnsupportedEncodingException e) 
		{
			e.printStackTrace();
		}
		return password;
	}
}
