package com.example.dohahamdy.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dohahamdy.bakingapp.Fragments.StepsFragment;
import com.example.dohahamdy.bakingapp.R;

public class RecipesActivity extends AppCompatActivity {

    public static boolean mTwoPane;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        if (findViewById(R.id.detail_container) != null)
        {
            mTwoPane = true;

        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable("recipeID",
                    getIntent().getSerializableExtra("recipeID"));

            StepsFragment stepsfragment = new StepsFragment();
            stepsfragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().add(R.id.steps_container, stepsfragment).commit();
        }
    }
}
