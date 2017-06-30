package com.example.dohahamdy.bakingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.dohahamdy.bakingapp.R;

public class StarterActivity extends AppCompatActivity {
    private ImageView image0;
    private Animation fade0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starter);




        image0 = (ImageView) findViewById(R.id.imageView);
        fade0 = AnimationUtils.loadAnimation(this, R.anim.fade_in_enter);

        image0.startAnimation(fade0);
        fade0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(StarterActivity.this, MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                overridePendingTransition(R.anim.push_up_enter, R.anim.push_up_exit);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
