package com.cube.lush.player.mobile;

import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.cube.lush.player.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;

/**
 * <Class Description>
 *
 * @author Jamie Cruwys
 */
public class TabSelector
{
	public static void selectTab(@NonNull LushTab tab)
	{
		ViewInteraction viewInteraction = onView(
			allOf(
				withId(R.id.bottom_navigation_container),
				byChildPosition(LinearLayout.class, tab.getPosition())
			)
		);

		assertDisplayed(viewInteraction);
		click(viewInteraction);
	}

	private static Matcher<View> byChildPosition(Class<? extends View> view, int position)
	{
		return childAtPosition(IsInstanceOf.<View>instanceOf(view), position);
	}

	private static void assertDisplayed(@NonNull ViewInteraction viewInteraction)
	{
		viewInteraction.check(matches(isDisplayed()));
	}

	private static void click(@NonNull ViewInteraction viewInteraction)
	{
		viewInteraction.perform(ViewActions.click());
	}

	private static Matcher<View> childAtPosition(final Matcher<View> parentMatcher, final int position)
	{
		return new TypeSafeMatcher<View>()
		{
			@Override
			public void describeTo(Description description)
			{
				description.appendText("Child at position " + position + " in parent ");
				parentMatcher.describeTo(description);
			}

			@Override
			public boolean matchesSafely(View view)
			{
				ViewParent parent = view.getParent();
				return parent instanceof ViewGroup && parentMatcher.matches(parent)
						&& view.equals(((ViewGroup) parent).getChildAt(position));
			}
		};
	}
}