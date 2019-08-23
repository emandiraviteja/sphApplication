package com.example.sphapplication;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.jar.Manifest;

import static org.junit.Assert.*;

public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<MainActivity>(MainActivity.class);

    private MainActivity mainActivity = null;

    @Before
    public void setUp() throws Exception {

        mainActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testLaunch(){

        View view = mainActivity.findViewById(R.id.recycler);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {

        mainActivity = null;
    }
}