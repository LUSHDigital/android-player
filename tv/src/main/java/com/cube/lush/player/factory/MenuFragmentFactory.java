package com.cube.lush.player.factory;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;

import java.util.HashMap;
import java.util.Map;

public class MenuFragmentFactory extends BrowseFragment.FragmentFactory<Fragment>
{
	private Map<Object, Fragment> fragmentRegistry = new HashMap<>();

	@Override public Fragment createFragment(Object row)
	{
		return fragmentRegistry.get(row);
	}

	public void registerFragment(Object item, Fragment fragment)
	{
		fragmentRegistry.put(item, fragment);
	}
}