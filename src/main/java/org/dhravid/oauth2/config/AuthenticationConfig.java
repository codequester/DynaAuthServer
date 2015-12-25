package org.dhravid.oauth2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.ldap.repository.config.EnableLdapRepositories;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;


@Configuration
@EnableMongoRepositories("org.dhravid.oauth2.mongo.repository")
@EnableLdapRepositories("org.dhravid.oauth2.ldap.repository")
public class AuthenticationConfig 
{

}
