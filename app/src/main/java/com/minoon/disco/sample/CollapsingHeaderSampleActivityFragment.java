package com.minoon.disco.sample;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.minoon.disco.Disco;
import com.minoon.disco.ViewParam;
import com.minoon.disco.choreography.ScrollChoreography;
import com.minoon.disco.choreography.builder.Position;
import com.minoon.disco.sample.adapter.SampleAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class CollapsingHeaderSampleActivityFragment extends Fragment {

    private static final String ARG_DISCO_STATE = "argDiscoState";

    @Bind(R.id.a_collapsing_header_iv_header)
    ImageView mHeaderImage;
    @Bind(R.id.a_collapsing_header_tv_title)
    TextView mTitleText;
    @Bind(R.id.a_collapsing_header_tb_toolbar)
    Toolbar mToolbar;
    @Bind(R.id.a_collapsing_header_rv_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.a_collapsing_header_fab_button)
    FloatingActionButton mFab;

    Disco mDisco;

    public CollapsingHeaderSampleActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.f_collapsing_header_sample, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        // set up views
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(new SampleAdapter());
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                if (parent.getChildAdapterPosition(view) == 0) {
                    outRect.top = dpToPixcel(view.getContext(), 240);
                }
            }
        });

        mDisco = new Disco();
        mDisco.addScrollView(mRecyclerView);

        // set up header image behavior
        mDisco.addScrollObserver(mHeaderImage, mDisco.getScrollChoreographyBuilder()
                        .onScroll()
                        .offset(100)
                        .multiplier(0.7f)
                        .build()
        );

        // set up header text behavior
        mDisco.addScrollObserver(mTitleText, mDisco.getScrollChoreographyBuilder()
                        .onScroll()
                        .stopAtBorder()
                        .topOffset(dpToPixcel(getActivity(), 4))
                        .scaleX(1f, 0.6f)
                        .scaleY(1f, 0.6f)
                        .translationX(Position.DEFAULT, 0, Position.LEFT, dpToPixcel(getActivity(), 8))
                        .build()
        );

        // set up fab behavior
        mDisco.addScrollObserver(mFab, mDisco.getScrollChoreographyBuilder()
                        .onScroll()
                        .topOffset(dpToPixcel(getActivity(), 90))
                        .build()
        );
        mDisco.addViewObserver(mFab, mFab, mDisco.getViewChaseChoreographyBuilder()
                        .atTag(ViewParam.TRANSLATION_Y, dpToPixcel(getActivity(), -150))
                        .scaleX(0, 1)
                        .scaleY(0, 1)
                        .duration(200)
                        .interpolator(new DecelerateInterpolator())
                        .notifyEvent(SampleEvent.BACK, SampleEvent.FORWARD)
                        .build()
        );

        // set up toolbar behavior
        mDisco.addScrollObserver(mToolbar, new ScrollChoreography() {

            @Override
            public long playEvent(Enum e, View chaserView) {
                int duration = 300;
                if (e instanceof SampleEvent) {
                    TransitionDrawable drawable = (TransitionDrawable) chaserView.getBackground();
                    switch ((SampleEvent) e) {
                        case BACK:
                            drawable.startTransition(duration);
                            return duration;
                        case FORWARD:
                            drawable.reverseTransition(duration);
                            return duration;
                    }
                }
                return 0;
            }

            @Override
            public boolean playScroll(View chaserView, int dx, int dy, int x, int y) {
                return false;
            }
        });

        if (savedInstanceState != null) {
            mDisco.restoreInstanceState(savedInstanceState.getParcelable(ARG_DISCO_STATE));
        }
        mDisco.setUp();
    }

    private int dpToPixcel(Context context, int dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private enum SampleEvent {
        BACK,
        FORWARD
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mDisco != null) {
            outState.putParcelable(ARG_DISCO_STATE, mDisco.onSaveInstanceState());
        }
    }
}
