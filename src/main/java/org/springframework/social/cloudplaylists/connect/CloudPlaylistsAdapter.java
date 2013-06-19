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
package org.springframework.social.cloudplaylists.connect;

import org.springframework.social.ApiException;
import org.springframework.social.cloudplaylists.api.CloudPlaylists;
import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import com.cloudplaylists.domain.CloudPlaylistsProfile;

/**
 * CloudPlaylists ApiAdapter implementation.
 * 
 * @author Michael Lavelle
 */
public class CloudPlaylistsAdapter implements ApiAdapter<CloudPlaylists> {

	@Override
	public UserProfile fetchUserProfile(CloudPlaylists cloudPlaylists) {
		CloudPlaylistsProfile profile = cloudPlaylists.meOperations()
				.getUserProfile();
		return new UserProfileBuilder().setName(profile.getDisplayName())
				.setUsername(profile.getUserName()).build();

	}

	@Override
	public void setConnectionValues(CloudPlaylists cloudPlaylists,
			ConnectionValues values) {
		CloudPlaylistsProfile profile = cloudPlaylists.meOperations()
				.getUserProfile();
		values.setProviderUserId(profile.getUserName());
		values.setDisplayName(profile.getDisplayName());
		values.setProfileUrl(profile.getProfileUrl());
		values.setImageUrl(profile.getImageUrl());
	}

	@Override
	public boolean test(CloudPlaylists cloudPlaylists) {
		try {
			cloudPlaylists.meOperations().getUserProfile();
			return true;
		} catch (ApiException e) {
			return false;
		}
	}

	@Override
	public void updateStatus(CloudPlaylists exFm, String arg1) {

	}

}
