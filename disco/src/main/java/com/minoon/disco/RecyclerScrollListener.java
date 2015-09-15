package com.minoon.disco;

import android.os.Parcel;
import android.os.Parcelable;
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

    /* package */ String tag;

    private int currentX = 0;

    private int currentY = 0;

    public RecyclerScrollListener(Disco disco, String tag) {
        scrollOrientationHelper = new ScrollOrientationChangeHelper(this);
        this.disco = disco;
        this.tag = tag;
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

    /* package */ int getCurrentX() {
        return currentX;
    }

    /* package */ int getCurrentY() {
        return currentY;
    }



    //// SaveState


    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.currentX = currentX;
        savedState.currentY = currentY;
        Logger.d(TAG, "currentX='%s', currentY='%s'", currentX, currentY);
        return savedState;
    }

    public void restoreState(Parcelable savedState) {
        SavedState state = (SavedState) savedState;
        currentX = state.currentX;
        currentY = state.currentY;
        Logger.d(TAG, "currentX='%s', currentY='%s'", currentX, currentY);
    }

    static class SavedState implements Parcelable {

        int currentX;

        int currentY;

        public SavedState() {
        }

        SavedState(Parcel in) {
            currentX = in.readInt();
            currentY = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(currentX);
            dest.writeInt(currentY);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            @Override
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            @Override
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }
}
