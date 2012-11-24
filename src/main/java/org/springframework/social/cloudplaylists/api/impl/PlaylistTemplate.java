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
package org.springframework.social.cloudplaylists.api.impl;

import org.springframework.data.domain.Page;
import org.springframework.social.cloudplaylists.api.PlaylistOperations;
import org.springframework.social.cloudplaylists.api.UserOperations;
import org.springframework.social.cloudplaylists.api.impl.json.PlaylistDescriptorPage;
import org.springframework.social.cloudplaylists.api.impl.json.PlaylistPage;
import org.springframework.web.client.RestTemplate;

import com.cloudplaylists.domain.CloudPlaylistsProfile;
import com.cloudplaylists.domain.Playlist;
import com.cloudplaylists.domain.PlaylistDescriptor;
import com.cloudplaylists.domain.SimplePlaylistDescriptor;

/**
 * @author Michael Lavelle
 */
public  class PlaylistTemplate extends
		AbstractCloudPlaylistsResourceOperations implements PlaylistOperations {

	public PlaylistTemplate(String oauthApiBaseUrl,
			RestTemplate restTemplate, boolean isAuthorizedForUser) {
		super(oauthApiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	
	@Override
	public Page<? extends PlaylistDescriptor> getPlaylistDescriptors() {

		return restTemplate.getForObject(getApiResourceUrl("/descriptors"),
				PlaylistDescriptorPage.class);
	}


	@Override
	protected String getApiResourceBaseUrl() {
		return getApiBaseUrl() + "/playlists";
	}

}
