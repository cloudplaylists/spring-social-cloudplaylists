package org.springframework.social.cloudplaylists.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.soundcloud.api.Track;

public interface SearchOperations {

	public Page<Track> searchSoundCloud(String q,Pageable pageable);
	public Page<Track> searchSoundCloud(String q);
	public Track resolveTrack(String url);


	
}
