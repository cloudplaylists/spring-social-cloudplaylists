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
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.social.cloudplaylists.api.MeOperations;
import org.springframework.social.cloudplaylists.api.impl.json.ApplicationPage;
import org.springframework.social.cloudplaylists.api.impl.json.ProviderSet;
import org.springframework.web.client.RestTemplate;

import com.cloudplaylists.domain.Application;
import com.cloudplaylists.domain.CloudPlaylistsProfile;
import com.cloudplaylists.domain.Media;
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
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/apps"),
				ApplicationPage.class);

	}
	
	

	
	@Override
	public Set<String> getConnections() {
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/connections"),
				ProviderSet.class);

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
	public Media loveOnExFm(String url) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/loveOnExFm?url=" + url),
				null, Media.class);
	}

	@Override
	public void deletePlaylist(String playlistName) {
		requireAuthorization();
		restTemplate.delete(getApiResourceUrl("/playlists/" + playlistName));
	}

	@Override
	public Playlist publishCurrentPlaylist(String publishedPlaylistName) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/currentPlaylist/publish?title=" + publishedPlaylistName)
				,null, Playlist.class);
	}

	@Override
	public Playlist deleteTrackFromPlaylist(String playlistName, int trackIndex) {
		requireAuthorization();

		restTemplate.delete(getApiResourceUrl("/playlists/" + playlistName+ "/tracks/" + trackIndex));
		return getPlaylist(playlistName);
	}

	@Override
	public Playlist importExFmLovedSongs() {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/exfm_loved_songs/import")
				,null, Playlist.class);
	}

	@Override
	public Playlist importSoundCloudFavorites() {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/soundcloud_favorites/import")
				,null, Playlist.class);
	}



}
