/*
 * Copyright 2012 the original author or authors.
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
package org.springframework.social.cloudplaylists.connect;

import java.util.UUID;

import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Parameters;
import org.springframework.social.oauth2.OAuth2Template;

/**
 * CloudPlaylists-specific extension of OAuth2Template
 * 
 * @author Michael Lavelle
 */
public class CloudPlaylistsOAuth2Template extends OAuth2Template {

	private String clientSecret;

	public CloudPlaylistsOAuth2Template(String clientId, String clientSecret, String oauthAuthorizeUrl,
			String oauthTokenUrl) {
		super(clientId, clientSecret, oauthAuthorizeUrl, oauthTokenUrl);
		this.clientSecret = clientSecret;
	}

	@Override
	public String buildAuthorizeUrl(GrantType grantType, OAuth2Parameters parameters) {
		parameters.add("scope", "read");
		parameters.add("state", UUID.randomUUID().toString());

		String url = super.buildAuthorizeUrl(grantType, parameters);

		return url;
	}

}
