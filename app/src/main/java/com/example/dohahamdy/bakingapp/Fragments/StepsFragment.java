package com.example.dohahamdy.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dohahamdy.bakingapp.Adapters.StepsAdapter;
import com.example.dohahamdy.bakingapp.Models.Recipe;
import com.example.dohahamdy.bakingapp.Models.Step;
import com.example.dohahamdy.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class StepsFragment extends Fragment {
    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh1)
    SwipeRefreshLayout mSwipeRefreshLayout;
    List<Step> dataSet;
    List<Step>temp;
    Recipe recipe;
    StepsAdapter stepsAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataSet=new ArrayList<>();

        if (getArguments().containsKey("recipeID")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            recipe = (Recipe) getArguments().getSerializable("recipeID");
            temp=recipe.getSteps();
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_of_steps, container, false);
        ButterKnife.bind(this, view);
        // dataSet=parseRecipes.parseStringToJson(mJsonFileContent);
        // dataSet.size();
        mRecyclerView.setHasFixedSize(true);
        stepsAdapter = new StepsAdapter(getActivity(),dataSet);
        mRecyclerView.setAdapter(stepsAdapter);


        mLayoutManager = new GridLayoutManager(getActivity(),1);
        mRecyclerView.setLayoutManager(mLayoutManager);

        fetchData();

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        // handel swipe refresh listener
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }
    private void clearDataSet()
    {
        if (dataSet != null){
            dataSet.clear();
            stepsAdapter.notifyDataSetChanged();
        }
    }
    public void fetchData()
    {

        clearDataSet();
        for (int i=0;i<temp.size();i++){
            dataSet.add(temp.get(i));
            stepsAdapter.notifyItemInserted(dataSet.size() - 1);
        }

    }
}
