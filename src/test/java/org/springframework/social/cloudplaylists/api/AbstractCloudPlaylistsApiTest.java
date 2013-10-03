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

import org.junit.Before;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.social.cloudplaylists.api.impl.CloudPlaylistsTemplate;
import org.springframework.social.test.client.MockRestServiceServer;

/**
 * @author Michael Lavelle
 */
public abstract class AbstractCloudPlaylistsApiTest {

	protected static final String API_BASE_URL = "api.cloudplaylists.com";
	protected CloudPlaylistsTemplate cloudplaylists;
	protected CloudPlaylistsTemplate unauthorizedCloudPlaylists;
	protected MockRestServiceServer mockServer;
	protected MockRestServiceServer mockUnauthorizedServer;

	protected HttpHeaders responseHeaders;

	protected final static String ACCESS_TOKEN = "someAccessToken";

	@Before
	public void setup() {

		cloudplaylists = new CloudPlaylistsTemplate(API_BASE_URL, ACCESS_TOKEN);

		mockServer = MockRestServiceServer.createServer(cloudplaylists.getRestTemplate());

		responseHeaders = new HttpHeaders();
		responseHeaders.setContentType(MediaType.APPLICATION_JSON);

		unauthorizedCloudPlaylists = new CloudPlaylistsTemplate(API_BASE_URL);
		mockUnauthorizedServer = MockRestServiceServer.createServer(unauthorizedCloudPlaylists.getRestTemplate());
	}

	protected Resource jsonResource(String filename) {
		return new ClassPathResource(filename + ".json", getClass());
	}

}
