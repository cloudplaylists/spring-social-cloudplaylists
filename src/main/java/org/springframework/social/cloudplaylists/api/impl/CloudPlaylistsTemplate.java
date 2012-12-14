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

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.cloudplaylists.api.CloudPlaylists;
import org.springframework.social.cloudplaylists.api.MeOperations;
import org.springframework.social.cloudplaylists.api.PlaylistOperations;
import org.springframework.social.cloudplaylists.api.SearchOperations;
import org.springframework.social.cloudplaylists.api.UsersOperations;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.soundcloud.api.impl.json.SoundCloudModule;
import org.springframework.social.support.ClientHttpRequestFactorySelector;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class CloudPlaylistsTemplate extends AbstractOAuth2ApiBinding implements
		CloudPlaylists {

	private MeOperations meOperations;
	private UsersOperations usersOperations;
	private PlaylistOperations playlistOperations;
	private SearchOperations searchOperations;
	
	private ObjectMapper objectMapper;

	/**
	 * Create a new instance of CloudPlaylistsTemplate. This constructor creates
	 * a new CloudPlaylistsTemplate able to perform unauthenticated operations
	 * against ExFm's API. Some operations do not require OAuth authentication.
	 * For example, retrieving a specified user's profile or loved tracks does
	 * not require authentication . An CloudPlaylistsTemplate created with this
	 * constructor will support those operations. Those operations requiring
	 * authentication will throw {@link NotAuthorizedException}.
	 */
	public CloudPlaylistsTemplate(String apiBaseUrl) {
		initialize(apiBaseUrl, null);
	}

	/**
	 * Create a new instance of CloudPlaylistsTemplate. This constructor creates
	 * the CloudPlaylistsTemplate using a given access token
	 * 
	 * @param oauthApiBaseUrl
	 *            An OAuth API Base url for Sparklr
	 * @param accessToken
	 *            An access token given by CloudPlaylistsTemplate after a
	 *            successful OAuth 2 authentication
	 */
	public CloudPlaylistsTemplate(String oauthApiBaseUrl, String accessToken) {
		super(accessToken);
		initialize(oauthApiBaseUrl, accessToken);
	}

	@Override
	protected List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> messageConverters = super
				.getMessageConverters();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		return messageConverters;
	}

	private void initSubApis(String oauthApiBaseUrl, String accessToken) {

		meOperations = new MeTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());
		usersOperations = new UsersTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());
		
		playlistOperations = new PlaylistTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());

		searchOperations = new SearchTemplate(oauthApiBaseUrl, getRestTemplate(),
				isAuthorized());

	}

	// private helpers
	private void initialize(String apiBaseUrl, String accessToken) {
		// Wrap the request factory with a BufferingClientHttpRequestFactory so
		// that the error handler can do repeat reads on the response.getBody()
		registerSoundCloudJsonModule(getRestTemplate());
		super.setRequestFactory(ClientHttpRequestFactorySelector
				.bufferRequests(getRestTemplate().getRequestFactory()));
		initSubApis(apiBaseUrl, accessToken);

	}

	private void registerSoundCloudJsonModule(RestTemplate restTemplate) {
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(new SoundCloudModule());
		List<HttpMessageConverter<?>> converters = restTemplate
				.getMessageConverters();
		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof MappingJacksonHttpMessageConverter) {
				MappingJacksonHttpMessageConverter jsonConverter = (MappingJacksonHttpMessageConverter) converter;
				jsonConverter.setObjectMapper(objectMapper);
			}
		}
	}

	@Override
	public MeOperations meOperations() {
		return meOperations;
	}

	@Override
	public UsersOperations usersOperations() {
		return usersOperations;
	}
	
	@Override
	public PlaylistOperations playlistOperations() {
		return playlistOperations;
	}
	
	@Override
	public SearchOperations searchOperations() {
		return searchOperations;
	}

}
