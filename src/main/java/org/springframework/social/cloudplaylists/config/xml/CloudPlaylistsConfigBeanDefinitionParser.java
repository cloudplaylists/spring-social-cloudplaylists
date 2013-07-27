package org.springframework.social.cloudplaylists.config.xml;

/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Map;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.social.cloudplaylists.config.support.CloudPlaylistsApiHelper;
import org.springframework.social.cloudplaylists.connect.CloudPlaylistsConnectionFactory;
import org.springframework.social.cloudplaylists.security.CloudPlaylistsAuthenticationService;
import org.springframework.social.config.xml.AbstractProviderConfigBeanDefinitionParser;
import org.springframework.social.security.provider.SocialAuthenticationService;

/**
 * Implementation of {@link AbstractConnectionFactoryBeanDefinitionParser} that creates a {@link CloudPlaylistsConnectionFactory}.
 * @author Michael Lavelle
 */
class CloudPlaylistsConfigBeanDefinitionParser extends AbstractProviderConfigBeanDefinitionParser {

	public CloudPlaylistsConfigBeanDefinitionParser() {
		super(CloudPlaylistsConnectionFactory.class, CloudPlaylistsApiHelper.class);
	}
	
	@Override
	protected Class<? extends SocialAuthenticationService<?>> getAuthenticationServiceClass() {
		return CloudPlaylistsAuthenticationService.class;
	}

	@Override
	protected BeanDefinition getAuthenticationServiceBeanDefinition(
			String appId, String appSecret, Map<String, Object> allAttributes) {
		return BeanDefinitionBuilder.genericBeanDefinition(authenticationServiceClass).addConstructorArgValue(appId).addConstructorArgValue(appSecret).
		addConstructorArgValue(getOauthAuthorizeUrl(allAttributes)).
		addConstructorArgValue(getOauthTokenUrl(allAttributes)).
		addConstructorArgValue(getOauthApiBaseUrl(allAttributes))

		.getBeanDefinition();
	}

	@Override
	protected BeanDefinition getConnectionFactoryBeanDefinition(String appId, String appSecret, Map<String, Object> allAttributes) {
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(CloudPlaylistsConnectionFactory.class).addConstructorArgValue(appSecret).
		addConstructorArgValue(getOauthAuthorizeUrl(allAttributes)).
		addConstructorArgValue(getOauthTokenUrl(allAttributes)).
		addConstructorArgValue(getOauthApiBaseUrl(allAttributes));
					
		return builder.getBeanDefinition();
	}
	
	protected String getOauthAuthorizeUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauth-authorize-url");
	}
	
	protected String getOauthTokenUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauth-token-url");
	}
	
	protected String getOauthApiBaseUrl(Map<String, Object> allAttributes) {
		return (String)allAttributes.get("oauth-api-base-url");
	}

}
