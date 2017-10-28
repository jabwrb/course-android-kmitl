package com.project.demorecord;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void clear() {
        Context context = InstrumentationRegistry.getTargetContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(CommonSharePreference.NAME, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();
    }

    @Test
    public void case01() {
        /**
         * โดยไม่กรอก Name และ Age กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void case02() {
        /**
         * โดยไม่กรอก Name และ Age=20 กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(clearText());
        onView(withId(R.id.editTextAge)).perform(replaceText("20"), closeSoftKeyboard());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void case03() {
        /**
         * ยังไม่มีการเพิ่ม UserInfo และกด GO TO LIST
         * จะเจอ Not Found
         */
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.textNotFound)).check(matches(isDisplayed()));
        pressBack();
    }

    @Test
    public void case04() {
        /**
         * โดยไม่กรอก Age และ Name=Ying กดปุ่ม ADDED
         * จะต้องเจอ Please Enter user info
         */
        onView(withId(R.id.editTExtName)).perform(replaceText("Ying"), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(clearText());
        onView(withId(R.id.buttonAdded)).perform(click());
        onView(withText("Please Enter user info")).check(matches(isDisplayed()));
        onView(withText("OK")).perform(click());
    }

    @Test
    public void case05() {
        /**
         * โดยกรอก Name=Ying และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Ying อายุ 20 เป็นตัวแรก
         */
        addUserInfo("Ying", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(0, hasDescendant(withText("Ying")))));
        onView(withId(R.id.list)).check(matches(atPosition(0, hasDescendant(withText("20")))));
        pressBack();
    }

    @Test
    public void case06() {
        /**
         * โดยกรอก Name=Ladarat และ Age=20 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Ladarat อายุ 20 ใน ListView ลำดับที่ 2
         */
        addUserInfo("Ying", "20");
        addUserInfo("Ladarat", "20");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(1, hasDescendant(withText("Ladarat")))));
        onView(withId(R.id.list)).check(matches(atPosition(1, hasDescendant(withText("20")))));
        pressBack();
    }

    @Test
    public void case07() {
        /**
         * โดยกรอก Name=Somkait และ Age=80 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Somkait อายุ 80 ใน ListView ลำดับที่ 3
         */
        addUserInfo("Ying", "20");
        addUserInfo("Ladarat", "20");
        addUserInfo("Somkait", "80");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(2, hasDescendant(withText("Somkait")))));
        onView(withId(R.id.list)).check(matches(atPosition(2, hasDescendant(withText("80")))));
        pressBack();
    }

    @Test
    public void case08() {
        /**
         * โดยกรอก Name=Prayoch และ Age=60 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Prayoch อายุ 60 ใน ListView ลำดับที่ 4
         */
        addUserInfo("Ying", "20");
        addUserInfo("Ladarat", "20");
        addUserInfo("Somkait", "80");
        addUserInfo("Prayoch", "60");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(3, hasDescendant(withText("Prayoch")))));
        onView(withId(R.id.list)).check(matches(atPosition(3, hasDescendant(withText("60")))));
        pressBack();
    }

    @Test
    public void case09() {
        /**
         * โดยกรอก Name=Prayoch และ Age=50 กดปุ่ม ADDED และกด GO TO LIST
         * จะต้องเจอ Prayoch อายุ 50 ใน ListView ลำดับที่ 5
         */
        addUserInfo("Ying", "20");
        addUserInfo("Ladarat", "20");
        addUserInfo("Somkait", "80");
        addUserInfo("Prayoch", "60");
        addUserInfo("Prayoch", "50");
        onView(withId(R.id.buttonGotoList)).perform(click());
        onView(withId(R.id.list)).check(matches(atPosition(4, hasDescendant(withText("Prayoch")))));
        onView(withId(R.id.list)).check(matches(atPosition(4, hasDescendant(withText("50")))));
        pressBack();
    }

    private void addUserInfo(String name, String age) {
        onView(withId(R.id.editTExtName)).perform(replaceText(name), closeSoftKeyboard());
        onView(withId(R.id.editTextAge)).perform(replaceText(age), closeSoftKeyboard());
        onView(withId(R.id.buttonAdded)).perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }
}
