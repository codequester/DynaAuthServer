package org.dhravid.oauth2.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.dhravid.oauth2.service.AuthServerTokenService;
import org.dhravid.oauth2.service.DynaClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import org.dhravid.oauth2.service.AuthServerCustomGrantHandler;


@Configuration
@EnableAutoConfiguration
@EnableAuthorizationServer
@ComponentScan(basePackages={"org.dhravid.oauth2.*"})
public class DynaAuthServerAppConfig extends AuthorizationServerConfigurerAdapter
{
	@Autowired
	private DynaClientDetailsService dynaClientDetailsService;
	
	@Autowired
	private AuthServerTokenService defaultAuthServerTokenService;
	
    @Autowired
    @Qualifier("dynaRoutingAuthProvider")
    private AuthenticationProvider authenticationProvider;
    
    @Bean
    @ConfigurationProperties(prefix="ldap.contextSource")
    public LdapContextSource contextSource() 
    {
        LdapContextSource contextSource = new LdapContextSource();
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate(ContextSource contextSource) 
    {
        return new LdapTemplate(contextSource);
    }	
	
	@Bean
	public AuthenticationManager authenticationManager() throws Exception 
	{
		return new ProviderManager(Arrays.asList(authenticationProvider));
	}	
		
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception 
	{
		defaultAuthServerTokenService.setTokenStore(new InMemoryTokenStore());
		defaultAuthServerTokenService.setSupportRefreshToken(true);
		defaultAuthServerTokenService.setClientDetailsService(dynaClientDetailsService);
		defaultAuthServerTokenService.afterPropertiesSet();
		endpoints.authenticationManager(authenticationManager()).tokenServices(defaultAuthServerTokenService);
		endpoints.tokenGranter(constructHandlerForCustomGrants(endpoints));		
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception 
	{
		oauthServer.allowFormAuthenticationForClients();
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception 
	{
		clients.withClientDetails(dynaClientDetailsService);
	}
	
	public static void main(String[] args)
	{
		SpringApplication.run(DynaAuthServerAppConfig.class, args);
	}
	
	private TokenGranter constructHandlerForCustomGrants(final AuthorizationServerEndpointsConfigurer endpoints) 
	{
		List<TokenGranter> granters = new ArrayList<TokenGranter>(Arrays.asList(endpoints.getTokenGranter()));
		granters.add(new AuthServerCustomGrantHandler(defaultAuthServerTokenService, dynaClientDetailsService, endpoints.getOAuth2RequestFactory(), "uniqueid"));
		return new CompositeTokenGranter(granters);
	}
}
