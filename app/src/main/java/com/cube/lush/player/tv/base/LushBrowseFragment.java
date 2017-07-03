package com.cube.lush.player.tv.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.cube.lush.player.R;

/**
 * Base fragment displaying an interface with a side menu and content view, based on the Leanback {@link BrowseFragment}.
 *
 * @author Jamie Cruwys
 */
public abstract class LushBrowseFragment extends BrowseFragment
{
	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		initialiseUI();
	}

	/**
	 * Apply default Lush branding
	 */
	protected void initialiseUI()
	{
		setTitle(getString(R.string.app_name));

		setBadgeDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.logo));

		setHeadersState(HEADERS_ENABLED);
		setHeadersTransitionOnBackEnabled(true);

		BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
		backgroundManager.attach(getActivity().getWindow());

		backgroundManager.setColor(ContextCompat.getColor(getActivity(), R.color.primary));
		setBrandColor(ContextCompat.getColor(getActivity(), R.color.primary));
		setSearchAffordanceColor(ContextCompat.getColor(getActivity(), R.color.primary_dark));

		setOnSearchClickedListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				getActivity().onSearchRequested();
			}
		});
	}
}