package com.example.dohahamdy.bakingapp.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.dohahamdy.bakingapp.Adapters.RecipesAdapter;
import com.example.dohahamdy.bakingapp.Apps.AppController;
import com.example.dohahamdy.bakingapp.Models.Recipe;
import com.example.dohahamdy.bakingapp.Parsers.parseRecipes;
import com.example.dohahamdy.bakingapp.R;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


@SuppressLint("ValidFragment")
public class RecipesListFragment extends Fragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecipesAdapter recipesAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;
    protected List<Recipe> dataSet;
    private String mJsonFileContent;


    public RecipesListFragment() {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dataSet=new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.list_of_recipes, container, false);
        ButterKnife.bind(this, view);
        // dataSet=parseRecipes.parseStringToJson(mJsonFileContent);
        // dataSet.size();


        mLayoutManager = new GridLayoutManager(getActivity(),2);
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
                fetchData();

            }
        });
    }
    private void clearDataSet()
    {
        if (dataSet != null){
            dataSet.clear();
            recipesAdapter.notifyDataSetChanged();
        }
    }
    public void fetchData()
    {
        connection();
        mRecyclerView.setHasFixedSize(true);
        recipesAdapter = new RecipesAdapter(getActivity(),dataSet);
        mRecyclerView.setAdapter(recipesAdapter);

        clearDataSet();


    }
    public  void connection() {
        String Url = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


        /**
         * Execute the background task, which uses {@link AsyncTask} to load the data.
         */
        // We first check for cached request
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(Url);
        if (entry != null) {
            // fetch the data from cache
            try {
                String data = new String(entry.data, "UTF-8");
                mJsonFileContent=data;


            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }

        /////////////connection//////////
        StringRequest strReq = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("response", response);
                mJsonFileContent=response;
                if (mJsonFileContent!=null) {
                    Iterator iterator = parseRecipes.parseStringToJson(mJsonFileContent).iterator();
                    while (iterator.hasNext()) {
                        Recipe recipe = (Recipe) iterator.next();
                        dataSet.add(recipe);
                        recipesAdapter.notifyItemInserted(dataSet.size() - 1);
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Stop the refreshing indicator
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("response", error.toString());
            }
        });

        // Adding request to volley request queue
        AppController.getInstance().addToRequestQueue(strReq);

    }


}
