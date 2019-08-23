package com.example.sphapplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.android.volley.NetworkError;

import org.json.JSONException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;
import static org.junit.Assert.assertEquals;

public class MainActivityInstrumentationTest {
    public MainActivityInstrumentationTest() {
        MainActivity.LOAD_DEFAULT_DATA = false;
    }

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    public int getCardCount(final String json) throws Throwable {

        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().showResponse(json);
            }
        });

        RecyclerView view = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recycler);
        int itemCount = view.getAdapter().getItemCount();
        return itemCount;
    }

    @Test
    public void testCards() throws Throwable {
        int cardCount = getCardCount("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q1\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"1\", \"quarter\": \"2005-q2\", \"id\": \"2\" }]}}");
        assertEquals(2, cardCount);
    }

    @Test
    public void testEmptyCards() throws Throwable {
        int cardCount = getCardCount(null);
        assertEquals(0, cardCount);
    }

    @Test
    public void testCachedResponse() throws Throwable {
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                activityTestRule.getActivity().saveToCache("{\"result\": {\"records\": [{\"volume_of_mobile_data\": \"1\", \"quarter\": \"2004-q1\", \"id\": \"2\" }, {\"volume_of_mobile_data\": \"1\", \"quarter\": \"2005-q2\", \"id\": \"2\" }]}}");
                activityTestRule.getActivity().showCachedResponse();
            }
        });
        RecyclerView view = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recycler);
        int itemCount = view.getAdapter().getItemCount();
        assertEquals(2, itemCount);
    }

   /* public void testLoadUrl() throws Throwable {
        activityTestRule.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    activityTestRule.getActivity().loadUrlData();

                    RecyclerView view = (RecyclerView) activityTestRule.getActivity().findViewById(R.id.recycler);
                    int itemCount = view.getAdapter().getItemCount();
                    assertEquals(true, itemCount > 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/

    @Test
    public void isConnectedOrConnectingReturnsTrueWhenInternetIsAvailable() throws Exception {
        Context context = activityTestRule.getActivity().getBaseContext();
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = cm.getActiveNetworkInfo().isConnectedOrConnecting();
        Assert.assertEquals(true, isConnected);
    }
}
