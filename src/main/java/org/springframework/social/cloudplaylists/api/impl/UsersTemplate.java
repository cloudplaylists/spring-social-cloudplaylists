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

import org.springframework.social.cloudplaylists.api.UserOperations;
import org.springframework.social.cloudplaylists.api.UsersOperations;
import org.springframework.web.client.RestTemplate;

/**
 * @author Michael Lavelle
 */
public class UsersTemplate extends AbstractCloudPlaylistsOperations implements
		UsersOperations {

	public UsersTemplate(String apiBaseUrl, RestTemplate restTemplate,
			boolean isAuthorizedForUser) {
		super(apiBaseUrl, restTemplate, isAuthorizedForUser);
	}

	@Override
	public UserOperations userOperations(String userId) {
		return new UserTemplate(getApiBaseUrl(), restTemplate, userId,
				isAuthorizedForUser);
	}

}