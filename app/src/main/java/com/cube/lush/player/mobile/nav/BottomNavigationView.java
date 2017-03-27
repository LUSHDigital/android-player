package com.cube.lush.player.mobile.nav;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.cube.lush.player.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 27/03/2017.
 */
public class BottomNavigationView extends LinearLayout
{
	private TabSelectedListener listener;

	public BottomNavigationView(Context context)
	{
		super(context);
		init(context);
	}

	public BottomNavigationView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		init(context);
	}

	public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);
		init(context);
	}

	@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
	public BottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
	{
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}

	private void init(Context context)
	{
		inflate(context, R.layout.tab_view, this);
		ButterKnife.bind(this);
	}

	public interface TabSelectedListener
	{
		void onHomeTabClicked();
		void onLiveTabClicked();
		void onChannelsTabClicked();
		void onEventsTabClicked();
		void onSearchTabClicked();
	}

	public void setTabSelectedListener(@NonNull TabSelectedListener listener)
	{
		this.listener = listener;
	}

	@OnClick(R.id.tab_home) public void onHomeTabClicked()
	{
		if (listener != null)
		{
			listener.onHomeTabClicked();
		}
	}

	@OnClick(R.id.tab_live) public void onLiveTabClicked()
	{
		if (listener != null)
		{
			listener.onLiveTabClicked();
		}
	}

	@OnClick(R.id.tab_channels) public void onChannelsTabClicked()
	{
		if (listener != null)
		{
			listener.onChannelsTabClicked();
		}
	}

	@OnClick(R.id.tab_events) public void onEventsTabClicked()
	{
		if (listener != null)
		{
			listener.onEventsTabClicked();
		}
	}

	@OnClick(R.id.tab_search) public void onSearchTabClicked()
	{
		if (listener != null)
		{
			listener.onSearchTabClicked();
		}
	}
}