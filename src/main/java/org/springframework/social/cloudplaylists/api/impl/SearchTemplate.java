package org.springframework.social.cloudplaylists.api.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.cloudplaylists.api.SearchOperations;
import org.springframework.social.cloudplaylists.api.impl.json.TrackPage;
import org.springframework.social.soundcloud.api.Track;
import org.springframework.web.client.RestTemplate;

public class SearchTemplate extends AbstractCloudPlaylistsResourceOperations
		implements SearchOperations {

	public SearchTemplate(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		super(apiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	@Override
	public Page<Track> searchSoundCloud(String q, Pageable pageable) {
		return restTemplate.getForObject(getApiResourceUrl("/soundcloud?q=" + q,pageable),
				TrackPage.class);
	}

	@Override
	public Page<Track> searchSoundCloud(String q) {
		return restTemplate.getForObject(getApiResourceUrl("/soundcloud?q=" + q),
				TrackPage.class);
	}

	@Override
	protected String getApiResourceBaseUrl() {
		
		return getApiBaseUrl() + "/search";
	}

	@Override
	public Track resolveTrack(String url) {
		return restTemplate.getForObject(getApiResourceUrl("/resolve?url=" + url),
				Track.class);
	}

}
