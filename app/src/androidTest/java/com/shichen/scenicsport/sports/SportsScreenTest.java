package com.shichen.scenicsport.sports;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.shichen.scenicsport.Injection;
import com.shichen.scenicsport.R;
import com.shichen.scenicsport.data.Sport;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.google.common.base.Preconditions.checkArgument;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SportsScreenTest {
    @Rule
    public ActivityTestRule<SportActivity> mSportsActivityTestRule=
            new ActivityTestRule<SportActivity>(SportActivity.class){
                @Override
                protected void beforeActivityLaunched() {
                    super.beforeActivityLaunched();
                    Injection.provideTasksRepository(InstrumentationRegistry.getTargetContext()).deleteAllSports();
                }
            };

    private Matcher<View> withItemText(final String itemText) {
        checkArgument(!TextUtils.isEmpty(itemText), "itemText cannot be null or empty");
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(
                        isDescendantOfA(isAssignableFrom(RecyclerView.class)),
                        withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA LV with text " + itemText);
            }
        };
    }

    @Test
    public void loadMoreSports(){
        addTestData();
        onView(withId(R.id.cl_load_more)).perform(click());
        onView(withItemText("Content Value")).check(matches(isDisplayed()));
        
    }

    private void addTestData(){
        Injection.provideTasksRepository(InstrumentationRegistry.getTargetContext())
                .saveSport(new Sport("1234","Content Value"));
    }
}
