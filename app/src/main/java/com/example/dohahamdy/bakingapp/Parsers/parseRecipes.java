package com.example.dohahamdy.bakingapp.Parsers;

import android.util.Log;

import com.example.dohahamdy.bakingapp.Models.Ingredient;
import com.example.dohahamdy.bakingapp.Models.Recipe;
import com.example.dohahamdy.bakingapp.Models.Step;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by DOHA HAMDY on 6/22/2017.
 */

public class parseRecipes {

    public static List<Recipe> parseStringToJson(String data) {
        List<Recipe> myRecipes;

        try {


            JSONArray jsonArray = new JSONArray(data);

            myRecipes = new ArrayList<>();
            Log.d(TAG,"json");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recipeJsonObject = jsonArray.getJSONObject(i);
                String id = recipeJsonObject.optString("id");
                String name =recipeJsonObject.optString("name");
                String servings = recipeJsonObject.optString("servings");
                String image = recipeJsonObject.optString("image");

                List<Ingredient> recipeIngredients=new ArrayList<>();
                JSONArray ingredients = recipeJsonObject.optJSONArray("ingredients");
                JSONArray steps = recipeJsonObject.optJSONArray("steps");
                for (int j=0;j<ingredients.length();j++)
                {
                    JSONObject ingredientObject = ingredients.getJSONObject(j);
                    String quantity = ingredientObject.optString("quantity");
                    String measure =ingredientObject.optString("measure");
                    String ingredient = ingredientObject.optString("ingredient");

                    Ingredient ingredient1=new Ingredient();
                    ingredient1.setQuantity(quantity);
                    ingredient1.setMeasure(measure);
                    ingredient1.setIngredient(ingredient);

                    recipeIngredients.add(ingredient1);

                }
                List<Step> recipeSteps=new ArrayList<>();
                for (int j=0;j<steps.length();j++)
                {
                    JSONObject stepObject = steps.getJSONObject(j);
                    int stepId =Integer.parseInt( stepObject.optString("id"));
                    String shortDescription =stepObject.optString("shortDescription");
                    String description = stepObject.optString("description");
                    String videoURL = stepObject.optString("videoURL");
                    String thumbnailURL = stepObject.optString("thumbnailURL");

                    Step step=new Step();
                    step.setId(stepId);
                    step.setShortDescription(shortDescription);
                    step.setDescription(description);
                    step.setVideoURL(videoURL);
                    step.setThumbnailURL(thumbnailURL);

                    recipeSteps.add(step);
                }

                Recipe mRecipe=new Recipe();
                mRecipe.setId(id);
                mRecipe.setName(name);
                mRecipe.setImage(image);
                mRecipe.setServings(servings);
                mRecipe.setIngredients(recipeIngredients);
                mRecipe.setSteps(recipeSteps);

                myRecipes.add(mRecipe);
            }

            return myRecipes;

        } catch (Exception e) {
            e.printStackTrace();

        }


        return null;
    }

}
