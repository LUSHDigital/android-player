package com.cube.lush.player;

import android.support.v17.leanback.widget.ObjectAdapter;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.PresenterSelector;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Data;

/**
 * A Leanback adapter that uses {@link DiffUtil} to determine the minimal set of updates to perform in order to modify one list into another.
 * <p />
 * The adapter is already set up to notify observers with the minimal set of changes whenever {@link #setItems(List)} is called.
 *
 * Created by tim on 08/12/2016.
 */
@Data
public class DiffingAdapter<T> extends ObjectAdapter
{
	/**
	 * Predicate indicating whether two items are considered to be the same.
	 *
	 * @param <T>
	 */
	public interface EqualityTester<T>
	{
		boolean isEqual(T t1, T t2);
	}

	private List<T> items = Collections.emptyList();
	private EqualityTester<T> equalityTester = new EqualityTester<T>()
	{
		@Override
		public boolean isEqual(T t1, T t2)
		{
			return false;
		}
	};

	public DiffingAdapter(Presenter presenter)
	{
		super(presenter);
	}

	public DiffingAdapter(PresenterSelector presenterSelector)
	{
		super(presenterSelector);
	}

	public void clear()
	{
		setItems(new ArrayList<T>());
	}

	@Override
	public T get(int position)
	{
		return items.get(position);
	}

    @Override
    public boolean isImmediateNotifySupported()
    {
		return true;
    }

	public synchronized void setItems(final List<T> newItems)
	{
		DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback()
		{
			@Override
			public int getOldListSize()
			{
				return size();
			}

			@Override
			public int getNewListSize()
			{
				return newItems.size();
			}

			@Override
			public boolean areItemsTheSame(int oldItemPosition, int newItemPosition)
			{
				return equalityTester.isEqual(get(oldItemPosition), newItems.get(newItemPosition));
			}

			@Override
			public boolean areContentsTheSame(int oldItemPosition, int newItemPosition)
			{
				return areItemsTheSame(oldItemPosition, newItemPosition);
			}
		}, false);
		items = Collections.unmodifiableList(newItems);
		diffResult.dispatchUpdatesTo(new ListUpdateCallback()
		{
			@Override
			public void onInserted(int position, int count)
			{
				notifyItemRangeInserted(position, count);
			}

			@Override
			public void onRemoved(int position, int count)
			{
				notifyItemRangeRemoved(position, count);
			}

			@Override
			public void onMoved(int fromPosition, int toPosition)
			{
				// We've disabled moves
				throw new UnsupportedOperationException();
			}

			@Override
			public void onChanged(int position, int count, Object payload)
			{
				notifyItemRangeChanged(position, count);
			}
		});
	}

	@Override
	public int size()
	{
		return items.size();
	}
}
