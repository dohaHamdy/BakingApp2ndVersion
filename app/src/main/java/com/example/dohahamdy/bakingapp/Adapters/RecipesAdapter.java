package com.example.dohahamdy.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dohahamdy.bakingapp.Models.Recipe;
import com.example.dohahamdy.bakingapp.R;
import com.example.dohahamdy.bakingapp.Widgets.CollectionWidget;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by DOHA HAMDY on 6/22/2017.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ViewHolder> {

    List<String> mCollection;
    private List<Recipe> DataSet;
    private static Context context;

    public RecipesAdapter(Context cont,List<Recipe> dataSet)
    {
        context=cont;
        DataSet = dataSet;
        mCollection=new ArrayList<>();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.image)ImageView poster;
        @BindView(R.id.title)TextView title;


        public ViewHolder(View v)
        {
            super(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData(getPosition());
                    Context mContext=v.getContext();
                    Intent intent=new Intent("recipesActivity");

                    intent.putExtra("recipeID",DataSet.get(getPosition()));
                    mContext.startActivity(intent);

                }
            });

            ButterKnife.bind(this,v);

        }

        public ImageView getPoster() {
            return poster;
        }

        public TextView getTitle() {
            return title;
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);

        return  new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        if (DataSet.get(position) != null)
        {
            Log.d("", "Element " + position + " set.");
            holder.getTitle().setText(DataSet.get(position).getName());

            // Feed image

            if (!DataSet.get(position).getImage().equals(""))
            {
                //download image.
                Picasso.with(context).load(DataSet.get(position).getImage()).into(holder.getPoster());
            }
        }
    }

    @Override
    public int getItemCount() {
        return DataSet.size();
    }

    private void initData(int index) {

        Intent i = new Intent(context, CollectionWidget.class);
        i.setAction("action");
        // Toast.makeText(context,"from the activity",
        //       Toast.LENGTH_SHORT).show();//This works just fine
        i.putExtra("title", "aa");
        i.putExtra("ingredients", (Serializable) DataSet.get(index).getIngredients());
        context.sendBroadcast(i);
    }
}
