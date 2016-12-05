package com.cube.lush.player.factory;

import android.app.Fragment;
import android.support.v17.leanback.app.BrowseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Maintains a mapping between model objects representing menu items, and the fragment that should be displayed when that menu item is selected.
 *
 */
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
