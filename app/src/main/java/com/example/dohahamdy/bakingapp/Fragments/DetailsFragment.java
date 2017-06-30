package com.example.dohahamdy.bakingapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.dohahamdy.bakingapp.Adapters.StepsAdapter;
import com.example.dohahamdy.bakingapp.Models.Step;
import com.example.dohahamdy.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsFragment extends Fragment {

    SimpleExoPlayer player;
    String mURI;
    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady;
    @BindView(R.id.floatingActionButton)FloatingActionButton mFloatingActionButton;
    @BindView(R.id.stepDescription)
    TextView stepDES;
    @BindView(R.id.button)Button nextStep;
    Step step;
    @BindView(R.id.stepImage)ImageView imageView;
    @BindView(R.id.player)SimpleExoPlayerView playerView;
    List<Step> lStep= StepsAdapter.DataSet;
    int count;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);



        if (getArguments().containsKey("stepDetailID")) {
            step = (Step) getArguments().getSerializable("stepDetailID");
            count=step.getId()-1;
        }
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step_details, container, false);
        ButterKnife.bind(this, view);




        mURI=step.getVideoURL();
        stepDES.setText(step.getDescription());
        if (!step.getThumbnailURL().equals(""))
        {
            //download image.
            Picasso.with(view.getContext()).load(step.getThumbnailURL()).into(imageView);
        }
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("PlayerActivity");
                intent.putExtra("movieURI",mURI);
                Context context=v.getContext();
                context.startActivity(intent);
            }
        });
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(count<lStep.size()-1) {
                    step = lStep.get(++count);
                    stepDES.setText(step.getDescription());
                    if (!step.getThumbnailURL().equals(""))
                    {
                        //download image.
                        Picasso.with(v.getContext()).load(step.getThumbnailURL()).into(imageView);
                    }
                    mURI=step.getVideoURL();
                    initializePlayer(mURI);
                }else{
                    count=0;
                }
            }
        });
        return view;
    }
    private void initializePlayer(String movieUri) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(movieUri);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);

    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mURI);
        }
    }
    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}
