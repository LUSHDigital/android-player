package com.cube.lush.player.tv.browse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.View;

import com.cube.lush.player.R;

/**
 * Base fragment displaying an interface with a side menu and content view, based on the Leanback {@link BrowseFragment}.
 *
 * Created by tim on 28/11/2016.
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
		setTitle("Lush Player");

		setBadgeDrawable(getResources().getDrawable(R.drawable.logo));

		setHeadersState(HEADERS_ENABLED);
		setHeadersTransitionOnBackEnabled(true);

		BackgroundManager backgroundManager = BackgroundManager.getInstance(getActivity());
		backgroundManager.attach(getActivity().getWindow());
		backgroundManager.setColor(getResources().getColor(R.color.primary));
		setBrandColor(getResources().getColor(R.color.primary));
		setSearchAffordanceColor(getResources().getColor(R.color.primary_dark));

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