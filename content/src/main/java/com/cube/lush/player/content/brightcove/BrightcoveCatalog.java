package com.cube.lush.player.content.brightcove;

import com.brightcove.player.edge.Catalog;
import com.brightcove.player.event.EventEmitterImpl;
import com.cube.lush.player.content.BuildConfig;

import lombok.Getter;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 31/05/2017.
 */
public class BrightcoveCatalog
{
	public static final BrightcoveCatalog INSTANCE = new BrightcoveCatalog();

	@Getter private Catalog catalog;

	private BrightcoveCatalog()
	{
		catalog = new Catalog(new EventEmitterImpl(), BuildConfig.BRIGHTCOVE_ACCOUNT_ID, BuildConfig.BRIGHTCOVE_POLICY_KEY);
	}
}