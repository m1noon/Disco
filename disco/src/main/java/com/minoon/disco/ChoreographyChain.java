package com.minoon.disco;

import android.animation.ValueAnimator;
import android.view.View;

import com.minoon.disco.choreography.Choreography;
import com.minoon.disco.choreography.ScrollChoreography;
import com.minoon.disco.choreography.ViewChaseChoreography;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A Choreography set linked with a view.
 * This class has dependent child views and choreography set.
 *
 * Created by a13587 on 15/09/11.
 */
/* package */ class ChoreographyChain {
    private static final String TAG = Logger.createTag(ChoreographyChain.class.getSimpleName());

    private final List<ScrollChoreography> myScrollChoreography;

    private final List<ViewChaseChoreography> myChoreography;

    private final Map<View, ChoreographyChain> childChoreography;

    /**
     * Constructor
     *
     */
    /* package */ ChoreographyChain() {
        childChoreography = new HashMap<>();
        myChoreography = new ArrayList<>();
        myScrollChoreography = new ArrayList<>();
    }

    /* package */ ChoreographyChain(ScrollChoreography scrollChoreography) {
        this();
        myScrollChoreography.add(scrollChoreography);
    }

    /**
     * transform the chaser view based on the scroll information.
     *
     * @param chaserView
     * @param dx
     * @param dy
     * @param x
     * @param y
     */
    /* package */ void playScroll(View chaserView, int dx, int dy, int x, int y) {
        boolean changed = false;
        for (ScrollChoreography c : myScrollChoreography) {
            changed = changed || c.playScroll(chaserView, dx, dy, x, y);
        }

        if (changed) {
            for (View v : childChoreography.keySet()) {
                childChoreography.get(v).playChase(chaserView, v);
            }
        }
    }

    /**
     * {@link #playChase(View, View, boolean)}
     *
     * @param anchorView
     * @param chaserView
     */
    /* package */ void playChase(View anchorView, View chaserView) {
        playChase(anchorView, chaserView, false);
    }

    /**
     * transform the chaser view based on the condition of the anchor view.
     *
     * @param anchorView
     * @param chaserView
     * @param force
     */
    /* package */ void playChase(View anchorView, View chaserView, boolean force) {
        boolean changed = false;
        for (ViewChaseChoreography c : myChoreography) {
            changed = changed || c.playChase(anchorView, chaserView);
        }

        if (changed || force) {
            for (View v : childChoreography.keySet()) {
                childChoreography.get(v).playChase(chaserView, v, force);
            }
        }
    }

    /**
     * animate view and notify event to child views.
     *
     * @param event
     * @param chaserView
     */
    /* package */ void playEvent(Enum event, final View chaserView) {
        long duration = 0;
        for (Choreography c : myChoreography) {
            duration = Math.max(duration, c.playEvent(event, chaserView));
        }
        for (Choreography c : myScrollChoreography) {
            duration = Math.max(duration, c.playEvent(event, chaserView));
        }
        for (View v : childChoreography.keySet()) {
            childChoreography.get(v).playEvent(event, v);
        }

        // animate child views while chaserView is animating.
        if (duration > 0) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    for (View v : childChoreography.keySet()) {
                        childChoreography.get(v).playChase(chaserView, v);
                    }
                }
            });
            animator.start();
        }
    }

    /**
     * add as child which depends on their own.
     *
     * @param childView
     * @param choreography
     */
    /* package */ void addChildDependency(View childView, ViewChaseChoreography choreography) {
        if (childChoreography.containsKey(childView)) {
            childChoreography.get(childView).addMyChoreography(choreography);
        } else {
            ChoreographyChain childChain = new ChoreographyChain();
            childChain.addMyChoreography(choreography);
            childChoreography.put(childView, childChain);
        }
    }

    /**
     * add as child which depends on their own.
     *
     * @param childView
     * @param choreography
     */
    /* package */ void addChildDependency(View childView, ScrollChoreography choreography) {
        if (childChoreography.containsKey(childView)) {
            childChoreography.get(childView).addMyChoreography(choreography);
        } else {
            ChoreographyChain childChain = new ChoreographyChain();

            childChoreography.put(childView, childChain);
        }
    }

    /**
     * add view dependency if there is an anchor view in the offspring.
     *
     * @param anchorView
     * @param childView
     * @param choreography
     */
    /* package */ void maybeAddDependency(View anchorView, View childView, ViewChaseChoreography choreography) {
        // if the child map has a anchor view, add grandchild view with choreography.
        if (childChoreography.containsKey(anchorView)) {
            ChoreographyChain child = childChoreography.get(anchorView);
            child.addChildDependency(childView, choreography);
            return;
        }

        // loop for child instance
        for (ChoreographyChain d : childChoreography.values()) {
            d.maybeAddDependency(anchorView, childView, choreography);
        }
    }

    private void addMyChoreography(ViewChaseChoreography choreography) {
        if (choreography != null && !myChoreography.contains(choreography)) {
            myChoreography.add(choreography);
        }
    }

    private void addMyChoreography(ScrollChoreography choreography) {
        if (choreography != null && !myChoreography.contains(choreography)) {
            myScrollChoreography.add(choreography);
        }
    }

    /**
     * remove anchor view.
     * return true if this dependency meaning is gone and should be removed.
     *
     * @param chaserView
     * @return
     */
    /* package */  void removeChaserView(View chaserView) {
        if (childChoreography.containsKey(chaserView)) {
            childChoreography.remove(chaserView);
        }

        for (View v : childChoreography.keySet()) {
            childChoreography.get(v).removeChaserView(chaserView);
        }
    }
}
