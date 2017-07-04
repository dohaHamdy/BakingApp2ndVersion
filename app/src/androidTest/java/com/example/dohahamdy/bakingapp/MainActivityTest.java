package com.example.dohahamdy.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.dohahamdy.bakingapp.Activities.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.core.deps.guava.base.Predicates.instanceOf;
import static org.hamcrest.CoreMatchers.is;

/**
 * Created by DOHA HAMDY on 7/4/2017.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void GridViewTest()
    {
        onData(
                is(instanceOf(String.class))
        );
    }
}
