package org.springframework.social.cloudplaylists.api.impl;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.social.cloudplaylists.api.SearchOperations;
import org.springframework.social.cloudplaylists.api.impl.json.MediaMap;
import org.springframework.social.cloudplaylists.api.impl.json.MediaPage;
import org.springframework.web.client.RestTemplate;

import com.cloudplaylists.domain.Media;
import com.cloudplaylists.domain.MediaProvider;

public class SearchTemplate extends AbstractCloudPlaylistsResourceOperations
		implements SearchOperations {

	public SearchTemplate(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		super(apiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	@Override
	public Page<Media> searchSoundCloud(String q, Pageable pageable) {
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/soundcloud?q=" + q,pageable),
				MediaPage.class);
	}
	

	@Override
	public Page<Media> searchSoundCloud(String q) {
		requireAuthorization();
		return restTemplate.getForObject(getApiResourceUrl("/soundcloud?q=" + q),
				MediaPage.class);
	}

	@Override
	protected String getApiResourceBaseUrl() {
		
		return getApiBaseUrl() + "/search";
	}

	@Override
	public Media resolveMedia(String url, MediaProvider mediaProvider) {
		return restTemplate.getForObject(getApiResourceUrl("/media/" + mediaProvider.providerId().toLowerCase() + "?url=" + url),
				Media.class);
	}

	@Override
	public Map<MediaProvider, Media> resolveMedia(String url) {
		return restTemplate.getForObject(getApiResourceUrl("/media?url=" + url),
				MediaMap.class);
	}

}
