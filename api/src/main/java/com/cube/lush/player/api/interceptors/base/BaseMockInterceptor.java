package com.cube.lush.player.api.interceptors.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 29/03/2017.
 */
public abstract class BaseMockInterceptor implements Interceptor
{
	private final Context context;

	public BaseMockInterceptor(@NonNull Context context)
	{
		this.context = context;
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
		Response originalResponse = chain.proceed(chain.request());

		return originalResponse.newBuilder()
			.code(200)
			.body(ResponseBody.create(MediaType.parse("application/json"), getResponse()))
			.build();
	}

	/**
	 * Gets the response.
	 * This is a local JSON file.
	 *
	 * @return {@link String} which is the local json
	 */
	private String getResponse()
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
