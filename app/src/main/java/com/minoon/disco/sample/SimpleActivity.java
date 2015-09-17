package com.minoon.disco.sample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import com.minoon.disco.Disco;
import com.minoon.disco.Event;
import com.minoon.disco.Logger;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.builder.Position;
import com.minoon.disco.sample.adapter.SampleAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SimpleActivity extends AppCompatActivity {
    private static final String TAG = Logger.createTag(SimpleActivity.class.getSimpleName());
    private static final String SAVE_DISCO_STATE = "saveDiscoState";

    @Bind(R.id.a_simple_iv_header)
    ImageView mHeaderImage;
    @Bind(R.id.a_simple_btn_bottom)
    Button mButton;
    @Bind(R.id.a_simple_tb_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.a_sample_rv_list)
    RecyclerView mRecyclerView;

    private Disco mDisco;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SimpleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_simple);
        ButterKnife.bind(this);

        // set up views
        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SampleAdapter());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    DisplayMetrics metrics = view.getContext().getResources().getDisplayMetrics();
                    outRect.top = 240 * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
                }
            }
        });

        // set up disco
        mDisco = new Disco();
        mDisco.addScrollView(mRecyclerView);

        mDisco.addScrollObserver(mHeaderImage, mDisco.getScrollChoreographyBuilder()
                .onScroll()
                .scaleX(1f, 0.8f)
                .scaleY(1f, 0.8f)
                .multiplier(0.7f)
                .build());
        mDisco.addViewObserver(mHeaderImage, mHeaderImage, mDisco.getViewChaseChoreographyBuilder()
                        .atTag(ViewParam.TRANSLATION_Y, -200)
                        .alpha(0, 1)
                        .duration(600)
                        .build()
        );

        mDisco.addScrollObserver(mToolbar, mDisco.getScrollChoreographyBuilder()
                .at(Event.START_SCROLL_BACK)
                .translationY(0)
                .end()
                .at(Event.START_SCROLL_FORWARD)
                .translationY(-200)
                .build());

        mDisco.addViewObserver(mToolbar, mButton, mDisco.getViewChaseChoreographyBuilder()
                        .onChange(ViewParam.TRANSLATION_Y, 0, -200)
                        .translationX(Position.LEFT_OVER, Position.DEFAULT)
                        .interpolator(new DecelerateInterpolator())
                        .alpha(0f, 1f)
                        .build()
        );

        if (savedInstanceState != null) {
            mDisco.restoreInstanceState(savedInstanceState.getParcelable(SAVE_DISCO_STATE));
        }

        mDisco.setUp();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVE_DISCO_STATE, mDisco.onSaveInstanceState());
    }
}
