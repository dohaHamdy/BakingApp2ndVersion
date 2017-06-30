package com.example.dohahamdy.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dohahamdy.bakingapp.Fragments.DetailsFragment;
import com.example.dohahamdy.bakingapp.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (savedInstanceState == null)
        {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putSerializable("stepDetailID",
                    getIntent().getSerializableExtra("stepDetailID"));
            DetailsFragment fragment = new DetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_container, fragment)
                    .commit();
        }


    }
}
