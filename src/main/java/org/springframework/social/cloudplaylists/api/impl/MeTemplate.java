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

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.social.cloudplaylists.api.MeOperations;
import org.springframework.social.cloudplaylists.api.impl.json.ApplicationPage;
import org.springframework.social.cloudplaylists.api.impl.json.PlaylistPage;
import org.springframework.web.client.RestTemplate;

import com.cloudplaylists.domain.Application;
import com.cloudplaylists.domain.CloudPlaylistsProfile;
import com.cloudplaylists.domain.Playlist;
import com.cloudplaylists.domain.PlaylistUpdate;

/**
 * @author Michael Lavelle
 */
public class MeTemplate extends AbstractUserTemplate implements MeOperations {

	public MeTemplate(String oauthApiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		super(oauthApiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	@Override
	protected String getApiResourceBaseUrl() {
		return getApiBaseUrl() + "/me";
	}

	@Override
	public CloudPlaylistsProfile getUserProfile() {
		requireAuthorization();
		return super.getUserProfile();
	}

	@Override
	public Playlist createPlaylist(PlaylistUpdate playlistUpdate) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists"),
				playlistUpdate, Playlist.class);
	}
	
	@Override
	public Page<Application> getApplications() {

		return restTemplate.getForObject(getApiResourceUrl("/apps"),
				ApplicationPage.class);

	}

	@Override
	public Playlist updatePlaylist(String playlistName, List<String> urls) {
		requireAuthorization();
		restTemplate.put(getApiResourceUrl("/playlists/" + playlistName), urls);
		return getPlaylist(playlistName);
	}

	@Override
	public Playlist addToPlaylist(String playlistName, List<String> urls) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/" + playlistName),
				urls, Playlist.class);
	}

	@Override
	public void deletePlaylist(String playlistName) {
		requireAuthorization();
		restTemplate.delete(getApiResourceUrl("/playlists/" + playlistName));
	}

}
