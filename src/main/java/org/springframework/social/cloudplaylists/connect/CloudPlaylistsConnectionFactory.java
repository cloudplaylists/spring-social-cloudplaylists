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
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

/**
 * CloudPlaylists ConnectionFactory implementation.
 * 
 * @author Michael Lavelle
 */
public class CloudPlaylistsConnectionFactory extends
		OAuth2ConnectionFactory<CloudPlaylists> {

	public CloudPlaylistsConnectionFactory(String clientId,
			String clientSecret, String oauthAuthorizeUrl,
			String oauthTokenUrl, String oauthApiBaseUrl) {
		super("cloudplaylists",
				new CloudPlaylistsServiceProvider(clientId, clientSecret,
						oauthAuthorizeUrl, oauthTokenUrl, oauthApiBaseUrl),
				new CloudPlaylistsAdapter());
	}

	@Override
	public boolean supportsStateParameter() {
		return false;
	}
	
	

}
