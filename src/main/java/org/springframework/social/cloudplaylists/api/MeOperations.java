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
package org.springframework.social.cloudplaylists.api;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;

import com.cloudplaylists.domain.Application;
import com.cloudplaylists.domain.Media;
import com.cloudplaylists.domain.Playlist;
import com.cloudplaylists.domain.PlaylistUpdate;

/**
 * @author Michael Lavelle
 */
public interface MeOperations extends UserOperations {

	public Playlist createPlaylist(PlaylistUpdate playlistUpdate);
	public Playlist publishCurrentPlaylist(String publishedPlaylistName);
	public Playlist updatePlaylist(String playlistName,List<String> urls);
	public Playlist addToPlaylist(String playlistName,List<String> urls);
	public Playlist deleteTrackFromPlaylist(String playlistName,int trackIndex);
	public void deletePlaylist(String playlistName);
	public Page<Application> getApplications();
	public Set<String> getConnections();
	public Media loveOnExFm(String url);
	public Playlist importExFmLovedSongs();
	public Playlist importSoundCloudFavorites();


}
