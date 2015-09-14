package com.minoon.disco;

import android.support.v7.widget.RecyclerView;

/**
 *
 * Created by hiroki-mino on 2015/07/02.
 */
/* package */ class RecyclerScrollListener extends RecyclerView.OnScrollListener implements
        ScrollOrientationChangeHelper.ScrollOrientationChangeListener {
    private static final String TAG = Logger.createTag(RecyclerScrollListener.class.getSimpleName());

    Disco disco;

    ScrollOrientationChangeHelper scrollOrientationHelper;

    private int currentX = 0;

    private int currentY = 0;

    public RecyclerScrollListener(Disco disco) {
        scrollOrientationHelper = new ScrollOrientationChangeHelper(this);
        this.disco = disco;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        scrollOrientationHelper.onScroll(dy);
        currentX += dx;
        currentY += dy;
        disco.onScroll(dx, dy, currentX, currentY);
    }

    @Override
    public void onOrientationChange(boolean up) {
        Event ev = up ? Event.START_SCROLL_BACK : Event.START_SCROLL_FORWARD;
        disco.event(ev);
    }
}
