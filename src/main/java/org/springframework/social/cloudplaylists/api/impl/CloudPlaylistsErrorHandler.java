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
package org.springframework.social.cloudplaylists.api.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.social.ExpiredAuthorizationException;
import org.springframework.social.InternalServerErrorException;
import org.springframework.social.NotAuthorizedException;
import org.springframework.social.OperationNotPermittedException;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.social.ServerDownException;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.cloudplaylists.exceptions.PlaylistCreationException;
import com.cloudplaylists.exceptions.PlaylistUpdateException;

/**
 * Subclass of {@link DefaultResponseErrorHandler} that handles errors from
 * CloudPlaylists API, interpreting them into appropriate exceptions.
 * 
 * @author Michael Lavelle
 */
class CloudPlaylistsErrorHandler extends DefaultResponseErrorHandler {

	private void handleUncategorizedError(ClientHttpResponse response,
			Map errorDetails) {
		try {
			super.handleError(response);
		} catch (Exception e) {
			if (errorDetails != null && errorDetails.get("error_description") != null) {
				String m = (String)errorDetails.get("error_description");
				throw new UncategorizedApiException("cloudplaylists",m, e);
			} else {
				throw new UncategorizedApiException("cloudplaylists",
						"No error details from CloudPlaylists", e);
			}
		}
	}

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		
		
		Map errorDetails = extractErrorDetailsFromResponse(response);
		handleCloudPlaylistsError(response.getStatusCode(), errorDetails);
		handleUncategorizedError(response, errorDetails);
	}
	


	@Override
	public boolean hasError(ClientHttpResponse response) throws IOException {

		if (super.hasError(response)) {
			return true;
		}
		return false;
	}

	void handleCloudPlaylistsError(HttpStatus statusCode, Map errorDetails) {

		String message = (String)errorDetails.get("error_description");
		HttpStatus httpStatus = statusCode ;

		if (httpStatus == HttpStatus.OK) {
			// Should never happen
		} else if (httpStatus == HttpStatus.BAD_REQUEST) {
			
			String error = (String)errorDetails.get("error");
			String error_description = (String)errorDetails.get("error_description");

			if (error != null &&  PlaylistUpdateException.class.getName().equals(error))
			{
				throw new PlaylistUpdateException(error_description);
			}
			if (error != null &&  PlaylistCreationException.class.getName().equals(error))
			{
				throw new PlaylistCreationException(error_description);
			}
			
			
			throw new ResourceNotFoundException("cloudplaylists",message);

		} else if (httpStatus == HttpStatus.NOT_FOUND) {
			throw new ResourceNotFoundException("cloudplaylists",message);

		} else if (httpStatus == HttpStatus.UNAUTHORIZED) {

			throw new NotAuthorizedException("cloudplaylists",message);
		} else if (httpStatus == HttpStatus.FORBIDDEN) {
			String provider = (String)errorDetails.get("provider");
			String error = (String)errorDetails.get("error");
			if (error != null && provider != null && NotConnectedException.class.getName().equals(error))
			{
				throw new NotConnectedException(provider);
			}
			if (error != null && provider != null && ExpiredAuthorizationException.class.getName().equals(error))
			{
				throw new ExpiredAuthorizationException(provider);
			}
			throw new OperationNotPermittedException("cloudplaylists",message);
		} else if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
			throw new InternalServerErrorException("cloudplaylists",message);
		} else if (httpStatus == HttpStatus.SERVICE_UNAVAILABLE) {
			throw new ServerDownException("cloudplaylists",message);
		}
	}
	

	

	/*
	 * Attempts to extract ExFm error details from the response. Returns null if
	 * the response doesn't match the expected JSON error response.
	 */
	@SuppressWarnings("rawtypes")
	private Map extractErrorDetailsFromResponse(ClientHttpResponse response)
			throws IOException {

		ObjectMapper mapper = new ObjectMapper(new JsonFactory());
		try {
			String json = readFully(response.getBody());
			HashMap errorDetails  = mapper.readValue(json, HashMap.class);
			return errorDetails;
		} catch (JsonParseException e) {
			return null;
		}
	}

	private String readFully(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		StringBuilder sb = new StringBuilder();
		while (reader.ready()) {
			sb.append(reader.readLine());
		}
		return sb.toString();
	}

}
