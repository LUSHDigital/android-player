package com.cube.lush.player;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BackgroundManager;
import android.support.v17.leanback.app.BrowseFragment;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.view.View;
import android.widget.ImageView;

import com.cube.lush.player.model.Channel;
import com.cube.lush.player.model.MediaContent;

import java.io.Serializable;

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

		// The badge is very wide compared to its height, so reduce the height a bit or it looks too big
		ImageView badge = (ImageView) getTitleView().findViewById(R.id.title_badge);
		badge.getLayoutParams().height = 30;
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

		setOnItemViewClickedListener(new OnItemViewClickedListener()
		{
			@Override
			public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row)
			{
				if (item instanceof MediaContent)
				{
					Intent intent = new Intent(itemViewHolder.view.getContext(), MediaDetailsActivity.class);
					intent.putExtra(MediaDetailsActivity.EXTRA_MEDIA, (Serializable)item);
					startActivity(intent);
				}
				else if (item instanceof Channel)
				{
					Intent intent = new Intent(itemViewHolder.view.getContext(), ChannelActivity.class);
					intent.putExtra(ChannelActivity.EXTRA_CHANNEL, (Serializable)item);
					startActivity(intent);
				}
			}
		});
	}

}
