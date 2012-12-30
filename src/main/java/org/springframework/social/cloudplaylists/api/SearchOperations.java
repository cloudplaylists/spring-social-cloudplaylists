package org.springframework.social.cloudplaylists.api;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.cloudplaylists.domain.Media;
import com.cloudplaylists.domain.MediaProvider;

public interface SearchOperations {

	public Page<Media> searchSoundCloud(String q,Pageable pageable);
	public Page<Media> searchSoundCloud(String q);
	public Media resolveMedia(String url,MediaProvider mediaProvider);
	public Map<MediaProvider,Media> resolveMedia(String url);



	
}
