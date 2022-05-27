package com.example.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	AuthenticationManager amBean;
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
	{
		endpoints.authenticationManager(amBean);
	}
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
	{
		security.tokenKeyAccess("permitAll()")
		.checkTokenAccess("isAuthenticated()");
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception
	{
	clients
	.inMemory()
	.withClient("clientapp").secret(passwordEncoder.encode("123456"))
	.authorizedGrantTypes("authorization_code","refresh_token","password")
	.scopes("read")
	.redirectUris("http://localhost:5656/test")
	.accessTokenValiditySeconds(100)
	.refreshTokenValiditySeconds(300);
	}
}
