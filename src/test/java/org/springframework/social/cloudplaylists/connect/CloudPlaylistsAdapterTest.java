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
package org.springframework.social.cloudplaylists.connect;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.social.cloudplaylists.api.CloudPlaylists;
import org.springframework.social.cloudplaylists.api.MeOperations;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.cloudplaylists.domain.CloudPlaylistsProfile;

/**
 * @author Michael Lavelle
 */
public class CloudPlaylistsAdapterTest {

	private CloudPlaylistsAdapter apiAdapter = new CloudPlaylistsAdapter();

	private CloudPlaylists sparklr = Mockito.mock(CloudPlaylists.class);

	@Test
	public void fetchProfile() {
		MeOperations meOperations = Mockito.mock(MeOperations.class);
		Mockito.when(sparklr.meOperations()).thenReturn(meOperations);
		Mockito.when(meOperations.getUserProfile()).thenReturn(
				createCloudPlaylistsProfile());

		UserProfile profile = apiAdapter.fetchUserProfile(sparklr);
		assertEquals("Matt Slip", profile.getName());
		assertEquals("Matt", profile.getFirstName());
		assertEquals("Slip", profile.getLastName());
		assertNull(profile.getEmail());
		assertEquals("mattslip", profile.getUsername());
	}

	private CloudPlaylistsProfile createCloudPlaylistsProfile() {
		CloudPlaylistsProfile exFmProfile = new CloudPlaylistsProfile();
		exFmProfile.setUserName("mattslip");
		exFmProfile.setDisplayName("Matt Slip");

		// exFmProfile.setImage(image);

		return exFmProfile;
	}

	@Test
	public void setConnectionValues() {
		MeOperations meOperations = Mockito.mock(MeOperations.class);

		Mockito.when(sparklr.meOperations()).thenReturn(meOperations);
		Mockito.when(meOperations.getUserProfile()).thenReturn(
				createCloudPlaylistsProfile());

		TestConnectionValues connectionValues = new TestConnectionValues();
		apiAdapter.setConnectionValues(sparklr, connectionValues);
		assertEquals("Matt Slip", connectionValues.getDisplayName());

		assertNull(connectionValues.getProfileUrl());
		assertEquals("mattslip", connectionValues.getProviderUserId());
	}

	private static class TestConnectionValues implements ConnectionValues {

		private String displayName;
		private String imageUrl;
		private String profileUrl;
		private String providerUserId;

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getImageUrl() {
			return imageUrl;
		}

		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}

		public String getProfileUrl() {
			return profileUrl;
		}

		public void setProfileUrl(String profileUrl) {
			this.profileUrl = profileUrl;
		}

		public String getProviderUserId() {
			return providerUserId;
		}

		public void setProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
		}

	}
}
