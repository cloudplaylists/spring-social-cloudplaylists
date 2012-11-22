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

import org.springframework.social.cloudplaylists.api.CloudPlaylists;
import org.springframework.social.cloudplaylists.api.impl.CloudPlaylistsTemplate;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

/**
 * CloudPlaylists ServiceProvider implementation.
 * 
 * @author Michael Lavelle
 */
public class CloudPlaylistsServiceProvider extends
		AbstractOAuth2ServiceProvider<CloudPlaylists> {

	private String oauthApiBaseUrl;

	public CloudPlaylistsServiceProvider(String clientId, String clientSecret,
			String oauthAuthorizeUrl, String oauthTokenUrl,
			String oauthApiBaseUrl) {
		super(new CloudPlaylistsOAuth2Template(clientId, clientSecret,
				oauthAuthorizeUrl, oauthTokenUrl));
		this.oauthApiBaseUrl = oauthApiBaseUrl;
	}

	public CloudPlaylistsServiceProvider(String clientId, String clientSecret) {
		super(new CloudPlaylistsOAuth2Template(clientId, clientSecret,
				"http://api.cloudplaylists.com/oauth/authorize",
				"http://api.cloudplaylists.com/oauth/token"));
		this.oauthApiBaseUrl = "http://api.cloudplaylists.com";
	}

	@Override
	public CloudPlaylists getApi(String accessToken) {
		return new CloudPlaylistsTemplate(oauthApiBaseUrl, accessToken);
	}

}
