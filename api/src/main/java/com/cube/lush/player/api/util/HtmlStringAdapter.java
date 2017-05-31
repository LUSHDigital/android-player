package com.cube.lush.player.api.util;

import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.unbescape.html.HtmlEscape;

import java.io.IOException;

/**
 * Wraps the default Gson string adapter to unescape strings it gets back from the API, which could be HTML escaped.
 *
 * @author Jamie Cruwys
 */
public class HtmlStringAdapter extends TypeAdapter<String>
{
	private TypeAdapter<String> wrappedAdapter = TypeAdapters.STRING;

	@Override
	public String read(JsonReader in) throws IOException
	{
		return HtmlEscape.unescapeHtml(wrappedAdapter.read(in));
	}

	@Override
	public void write(JsonWriter out, String value) throws IOException
	{
		// We don't need to care about HTML-escaping strings for the API so just pass through
		wrappedAdapter.write(out, value);
	}
}