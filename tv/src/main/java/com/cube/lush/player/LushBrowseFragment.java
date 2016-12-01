package com.cube.lush.player;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.View;

/**
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
