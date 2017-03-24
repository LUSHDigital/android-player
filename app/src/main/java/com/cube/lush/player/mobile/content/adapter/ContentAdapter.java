package com.cube.lush.player.mobile.content.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cube.lush.player.R;
import com.cube.lush.player.api.model.MediaContent;
import com.cube.lush.player.mobile.content.holder.ContentViewHolder;
import com.cube.lush.player.mobile.content.listener.ContentClickListener;
import com.cube.lush.player.tv.browse.MediaPresenter;
import com.cube.lush.player.tv.view.CardView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;

import static android.text.format.DateUtils.DAY_IN_MILLIS;

/**
 * Created by Jamie Cruwys of 3 SIDED CUBE on 23/03/2017.
 */
@AllArgsConstructor
public class ContentAdapter extends RecyclerView.Adapter<ContentViewHolder>
{
	private @NonNull ArrayList<MediaContent> contents = new ArrayList<MediaContent>();
	private @NonNull ContentClickListener contentClickListener = null;

	@Override public ContentViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content, parent, false);
		return new ContentViewHolder(itemView, contentClickListener);
	}

	@Override public void onBindViewHolder(ContentViewHolder holder, int position)
	{
		MediaContent mediaContent = contents.get(position);
		holder.setMediaContent(mediaContent);

		holder.type.setText(mediaContent.getType().getName());
		holder.title.setText(mediaContent.getTitle());
		holder.length.setText(mediaContent.getRelativeDate());

		Picasso.with(holder.image.getContext())
			.load(mediaContent.getThumbnail())
			.fit()
			.centerCrop()
			.into(holder.image);
	}

	@Override public int getItemCount()
	{
		return contents.size();
	}

	public void setItems(List<MediaContent> newContents)
	{
		contents.clear();

		if (newContents != null)
		{
			contents.addAll(newContents);
		}

		notifyDataSetChanged();
	}
}