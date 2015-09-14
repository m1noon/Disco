package com.minoon.disco;

import android.animation.ValueAnimator;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by a13587 on 15/09/11.
 */
/* package */ class ChoreographyChain {
    private static final String TAG = ChoreographyChain.class.getSimpleName();

    protected final List<Choreography> myChoreography;

    protected final Map<View, ChoreographyChain> childChoreography;

    public ChoreographyChain(Choreography choreography) {
        childChoreography = new HashMap<>();
        myChoreography = new ArrayList<>();
        myChoreography.add(choreography);
    }

    public void onScroll(View chaserView, int dx, int dy, int x, int y) {
        boolean changed = false;
        for (Choreography c : myChoreography) {
            changed = changed || c.playScroll(chaserView, dx, dy, x, y);
        }

        if (changed) {
            for (View v : childChoreography.keySet()) {
                childChoreography.get(v).play(chaserView, v);
            }
        }
    }

    public void play(View anchorView, View chaserView) {
        play(anchorView, chaserView, false);
    }

    public void play(View anchorView, View chaserView, boolean force) {
        boolean changed = false;
        for (Choreography c : myChoreography) {
            changed = changed || c.playChase(anchorView, chaserView);
        }

        if (changed || force) {
            for (View v : childChoreography.keySet()) {
                childChoreography.get(v).play(chaserView, v, force);
            }
        }
    }

    public void play(Enum event, final View chaserView) {
        long duration = 0;
        for (Choreography c : myChoreography) {
            duration = Math.max(duration, c.playEvent(event, chaserView));
        }
        for (View v : childChoreography.keySet()) {
            childChoreography.get(v).play(event, v);
        }

        // animate child views while chaserView is animating.
        if (duration > 0) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
            animator.setDuration(duration);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    for (View v : childChoreography.keySet()) {
                        childChoreography.get(v).play(chaserView, v);
                    }
                }
            });
            animator.start();
        }
    }

    public void addChildDependency(View childView, Choreography choreography) {
        if (childChoreography.containsKey(childView)) {
            childChoreography.get(childView).addMyChoreography(choreography);
        } else {
            ChoreographyChain childChain = new ChoreographyChain(choreography);
            childChoreography.put(childView, childChain);
        }
    }

    private void addMyChoreography(Choreography choreography) {
        if (choreography != null && !myChoreography.contains(choreography)) {
            myChoreography.add(choreography);
        }
    }

    public void maybeAddDependency(View anchorView, View childView, Choreography choreography) {
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

    /**
     * remove anchor view.
     * return true if this dependency meaning is gone and should be removed.
     *
     * @param chaserView
     * @return
     */
    public void removeChaserView(View chaserView) {
        if (childChoreography.containsKey(chaserView)) {
            childChoreography.remove(chaserView);
        }

        for (View v : childChoreography.keySet()) {
            childChoreography.get(v).removeChaserView(chaserView);
        }
    }
}
