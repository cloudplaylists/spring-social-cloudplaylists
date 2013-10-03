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

import java.net.URLEncoder;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.social.cloudplaylists.api.MeOperations;
import org.springframework.social.cloudplaylists.api.impl.json.ApplicationPage;
import org.springframework.social.cloudplaylists.api.impl.json.MediaList;
import org.springframework.social.cloudplaylists.api.impl.json.PlaylistDescriptorList;
import org.springframework.social.cloudplaylists.api.impl.json.ProviderSet;
import org.springframework.web.client.RestTemplate;

import com.cloudplaylists.domain.Application;
import com.cloudplaylists.domain.CloudPlaylistsProfile;
import com.cloudplaylists.domain.Media;
import com.cloudplaylists.domain.MediaProvider;
import com.cloudplaylists.domain.Playlist;
import com.cloudplaylists.domain.PlaylistDescriptor;
import com.cloudplaylists.domain.PlaylistUpdate;
import com.cloudplaylists.domain.PlaylistVisibility;

/**
 * @author Michael Lavelle
 */
public class MeTemplate extends AbstractUserTemplate implements MeOperations {

	public MeTemplate(String oauthApiBaseUrl, RestTemplate restTemplate, boolean isAuthorizedForUser) {
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
		return restTemplate.postForObject(getApiResourceUrl("/playlists"), playlistUpdate, Playlist.class);
	}

	@Override
	public Page<Application> getApplications() {
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/apps"), ApplicationPage.class);

	}

	@Override
	public Set<String> getConnections() {
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/connections"), ProviderSet.class);

	}

	@Override
	public Playlist updatePlaylist(String playlistName, List<String> urls) {
		requireAuthorization();
		put(getApiResourceUrl("/playlists/" + playlistName), urls);
		return getPlaylist(playlistName);
	}

	@Override
	public Playlist addToPlaylist(String playlistName, List<String> urls) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/" + playlistName), urls, Playlist.class);
	}

	@Override
	public Media loveOnExFm(String url) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/loveOnExFm?url=" + url), null, Media.class);
	}

	@Override
	public Media loveOnExFm(String url, String fromPlaylistUserName, String fromPlaylistName) {
		requireAuthorization();
		String contextString = "";
		if (fromPlaylistUserName != null && fromPlaylistName != null) {
			contextString = "&fromPlaylistUserName=" + URLEncoder.encode(fromPlaylistUserName) + "&fromPlaylistName="
					+ URLEncoder.encode(fromPlaylistName);
		}
		return restTemplate.postForObject(getApiResourceUrl("/loveOnExFm?url=" + url + contextString), null,
				Media.class);
	}

	@Override
	public void deletePlaylist(String playlistName) {
		requireAuthorization();
		restTemplate.delete(getApiResourceUrl("/playlists/" + playlistName));
	}

	@Override
	public Playlist publishCurrentPlaylist(String publishedPlaylistName) {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/currentPlaylist/publish?title="
				+ publishedPlaylistName), null, Playlist.class);
	}

	@Override
	public Playlist deleteTrackFromPlaylist(String playlistName, int trackIndex) {
		requireAuthorization();

		restTemplate.delete(getApiResourceUrl("/playlists/" + playlistName + "/tracks/" + trackIndex));
		return getPlaylist(playlistName);
	}

	@Override
	public Playlist importExFmLovedSongs() {
		requireAuthorization();
		return restTemplate
				.postForObject(getApiResourceUrl("/playlists/exfm_loved_songs/import"), null, Playlist.class);
	}

	@Override
	public Playlist importSoundCloudFavorites() {
		requireAuthorization();
		return restTemplate.postForObject(getApiResourceUrl("/playlists/soundcloud_favorites/import"), null,
				Playlist.class);
	}

	@Override
	public List<Media> searchLibrary(String q, MediaProvider[] providers) {
		requireAuthorization();
		String providersString = null;
		;
		for (MediaProvider provider : providers) {
			if (providersString != null) {
				providersString = providersString + ",";
			} else {
				providersString = "";
			}
			providersString = providersString + provider.name();
		}

		String queryString = "?q=" + q + (providersString == null ? "" : ("&providers=" + providersString));
		return restTemplate.getForObject(getApiResourceUrl("/library" + queryString), MediaList.class);
	}

	@Override
	public List<Media> searchTracks(String q, MediaProvider[] providers) {
		requireAuthorization();
		String providersAsString = getProvidersAsString(providers);
		String queryString = "?q=" + q + (providersAsString == null ? "" : ("&providers=" + providersAsString));
		return restTemplate.getForObject(getApiResourceUrl("/searchTracks" + queryString), MediaList.class);
	}

	@Override
	public List<PlaylistDescriptor> searchPlaylists(String q, MediaProvider[] providers) {
		requireAuthorization();
		String providersAsString = getProvidersAsString(providers);
		;
		String queryString = "?q=" + q + (providersAsString == null ? "" : ("&providers=" + providersAsString));
		return restTemplate.getForObject(getApiResourceUrl("/searchPlaylists" + queryString),
				PlaylistDescriptorList.class);
	}

	private String getProvidersAsString(MediaProvider[] providers) {
		String providersAsString = null;
		;
		for (MediaProvider provider : providers) {
			if (providersAsString != null) {
				providersAsString = providersAsString + ",";
			} else {
				providersAsString = "";
			}
			providersAsString = providersAsString + provider.name();
		}
		return providersAsString;
	}

	@Override
	public Playlist updatePlaylistVisibility(String playlistName, PlaylistVisibility playlistVisibility) {

		requireAuthorization();

		return restTemplate.postForObject(getApiResourceUrl("/playlists/" + playlistName + "/playlistVisibility"),
				playlistVisibility, Playlist.class);
	}

}
