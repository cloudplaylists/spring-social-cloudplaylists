/*
 * Copyright 2011 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.social.test.client.RequestMatchers.header;
import static org.springframework.social.test.client.RequestMatchers.method;
import static org.springframework.social.test.client.RequestMatchers.requestTo;
import static org.springframework.social.test.client.ResponseCreators.withResponse;

import org.junit.Test;
import org.springframework.social.NotAuthorizedException;

import com.cloudplaylists.domain.CloudPlaylistsProfile;

/**
 * @author Michael Lavelle
 */
public class MeTemplateTest extends AbstractCloudPlaylistsApiTest {

	@Test
	public void getUserProfile_currentUser() {
		mockServer
				.expect(requestTo(API_BASE_URL + "/me"))
				.andExpect(method(GET))
				.andRespond(
						withResponse(jsonResource("testdata/full-profile"),
								responseHeaders));

		mockServer.expect(header("Authorization", "Bearer " + ACCESS_TOKEN));

		CloudPlaylistsProfile profile = cloudplaylists.meOperations()
				.getUserProfile();
		assertBasicProfileData(profile);
	}

	@Test(expected = NotAuthorizedException.class)
	public void getUserProfile_currentUser_unauthorized() {
		unauthorizedCloudPlaylists.meOperations().getUserProfile();
	}

	private void assertBasicProfileData(CloudPlaylistsProfile profile) {
		assertEquals("mattslip", profile.getUserName());
		assertEquals("Matt Slip", profile.getDisplayName());
		assertEquals("http://cloudplaylists.com/mattslip",
				profile.getProfileUrl());

	}

}
