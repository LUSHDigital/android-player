package com.cube.lush.player;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v17.leanback.app.BrowseFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.adapter.BasicMainFragmentAdapter;
import com.cube.lush.player.handler.ResponseHandler;
import com.cube.lush.player.manager.MediaManager;
import com.cube.lush.player.model.MediaContent;

import java.util.List;

/**
 * Created by tim on 01/12/2016.
 */
public class LiveDetailsFragment extends Fragment implements BrowseFragment.MainFragmentAdapterProvider
{
	private BrowseFragment.MainFragmentAdapter<LiveDetailsFragment> mMainFragmentAdapter;

	private PlaybackFragment playbackFragment;
	private LiveMediaDetailsFragment detailsFragment;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_live, container, false);

		// Show live playback in the background
        if (getChildFragmentManager().findFragmentById(R.id.background_container) == null)
        {
	        playbackFragment = new PlaybackFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.background_container, playbackFragment).commit();
        }
        else
        {
            playbackFragment = (PlaybackFragment) getChildFragmentManager().findFragmentById(R.id.background_container);
        }

		// Show media details in the foreground
        if (getChildFragmentManager().findFragmentById(R.id.foreground_container) == null)
        {
	        detailsFragment = new LiveMediaDetailsFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.foreground_container, detailsFragment).commit();
        }
        else
        {
            detailsFragment = (LiveMediaDetailsFragment) getChildFragmentManager().findFragmentById(R.id.foreground_container);
        }

        MediaManager.getInstance().getLiveContent(new ResponseHandler<MediaContent>()
        {
	        @Override
	        public void onSuccess(@NonNull List<MediaContent> items)
	        {
		        if (!items.isEmpty() && items.get(0) != null)
		        {
					playbackFragment.queuePlaylist(items.get(0).getId());
		        }
	        }

	        @Override
	        public void onFailure(@Nullable Throwable t)
	        {

	        }
        });

		return view;
	}

	@Override
	public BrowseFragment.MainFragmentAdapter<LiveDetailsFragment> getMainFragmentAdapter()
	{
		if (mMainFragmentAdapter == null)
		{
			mMainFragmentAdapter = new BasicMainFragmentAdapter<>(this);
		}
		return mMainFragmentAdapter;
	}
}
