package com.lush.player.api.interceptor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lush.player.api.API;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Base Mock Interceptor which abstracts away most of the interceptor work
 *
 * @author Jamie Cruwys
 */
public abstract class BaseMockInterceptor implements Interceptor
{
	private final Context context;

	public BaseMockInterceptor(@NonNull Context context)
	{
		this.context = context;
	}

	/**
	 * Provide the endpoint name that you want to intercept
	 * @return string representing the endpoint you want to mock
	 */
	protected abstract String provideEndpointName();

	/**
	 * Whether or not the response for a particular request should be mocked
	 * @param originalRequest
	 * @return true if response should be mocked, false if it should not be mocked
	 */
	protected boolean shouldMockResponse(@NonNull Request originalRequest)
	{
		// Only proceed is the API mocked flag is set
		if (!API.INSTANCE.isMocked())
		{
			return false;
		}

		HttpUrl url = originalRequest.url();

		// 0 - lushtvapi
		// 1 - v2
		// 2 - endpoint e.g. categories
		List<String> strings = url.pathSegments();
		String originalEndpoint = strings.get(2);
		String endpointToIntercept = provideEndpointName();

		// If the request is for this interceptors endpoint, then replace the response with our mock one
		if (originalEndpoint.startsWith(endpointToIntercept))
		{
			return true;
		}

		return false;
	}

	/**
	 * Provide the location of the Json file in relation to /assets (assets folder)
	 * Examples:
	 * user_request.json
	 * request/user.json (request folder)
	 * @return The String that represents the Json files location in relation to the assets folder
	 */
	protected abstract String provideJsonFileName();

	@Override public Response intercept(Chain chain) throws IOException
	{
		Request originalRequest = chain.request();
		Response originalResponse = chain.proceed(originalRequest);

		if (shouldMockResponse(originalRequest))
		{
			return originalResponse.newBuilder()
				.code(200)
				.body(ResponseBody.create(MediaType.parse("application/json"), getResponse()))
				.build();
		}

		return originalResponse;
	}

	/**
	 * Gets the response.
	 * This is a local JSON file.
	 *
	 * @return {@link String} which is the local json
	 */
	protected String getResponse()
	{
		String response = "";

		try
		{
			InputStream is = context.getAssets().open(provideJsonFileName());
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();

			String json = new String(buffer, "UTF-8");

			if (!TextUtils.isEmpty(json))
			{
				response = json;
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}

		return response;
	}
}
