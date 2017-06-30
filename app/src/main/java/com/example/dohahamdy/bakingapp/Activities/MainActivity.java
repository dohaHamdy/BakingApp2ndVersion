package com.example.dohahamdy.bakingapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dohahamdy.bakingapp.Fragments.RecipesListFragment;
import com.example.dohahamdy.bakingapp.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecipesListFragment recipesListFragment =new RecipesListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.item_container, recipesListFragment).commit();

    }

}
