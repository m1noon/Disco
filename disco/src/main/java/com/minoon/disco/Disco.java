package com.minoon.disco;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.minoon.disco.choreography.ScrollChoreography;
import com.minoon.disco.choreography.ViewChaseChoreography;
import com.minoon.disco.choreography.builder.ScrollChoreographyBuilder;
import com.minoon.disco.choreography.builder.ViewChaseChoreographyBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/11.
 */
public class Disco {
    private static final String TAG = Logger.createTag(Disco.class.getSimpleName());

    private final Map<View, ChoreographyChain> scrollObserver;

    private final Map<RecyclerView, RecyclerScrollListener> recyclerMap;


    public Disco() {
        scrollObserver = new HashMap<>();
        recyclerMap = new HashMap<>();
    }

    public ScrollChoreographyBuilder getScrollChoreographyBuilder() {
        return new ScrollChoreographyBuilder(this);
    }

    public ViewChaseChoreographyBuilder getViewChaseChoreographyBuilder() {
        return new ViewChaseChoreographyBuilder(this);
    }

    public void setUp() {
        // force scroll transform
        for (RecyclerScrollListener listener : recyclerMap.values()) {
            onScroll(0, 0, listener.getCurrentX(), listener.getCurrentY());
        }

        // force view chase
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playChase(v, v, true);
        }
    }

    public void addScrollView(RecyclerView recyclerView) {
        addScrollView(recyclerView, "");
    }

    public void addScrollView(RecyclerView recyclerView, String tag) {
        if (tag == null) {
            tag = "";
        }

        if (!recyclerMap.containsKey(recyclerView)) {
            RecyclerScrollListener listener = new RecyclerScrollListener(this, tag);
            recyclerMap.put(recyclerView, listener);
            recyclerView.addOnScrollListener(listener);
        }
    }

    public void removeScrollView(RecyclerView recyclerView) {
        if (recyclerMap.containsKey(recyclerView)) {
            RecyclerScrollListener listener = recyclerMap.get(recyclerView);
            recyclerView.removeOnScrollListener(listener);
            recyclerMap.remove(recyclerView);
        }
    }

    public void addViewObserver(View anchorView, View chaserView, ViewChaseChoreography choreography) {
        if (scrollObserver.containsKey(anchorView)) {
            scrollObserver.get(anchorView).addChildDependency(chaserView, choreography);
        }

        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).maybeAddDependency(anchorView, chaserView, choreography);
        }
    }

    public void addScrollObserver(View view, ScrollChoreography choreography) {
        ChoreographyChain chain = new ChoreographyChain(choreography);
        scrollObserver.put(view, chain);
    }


    public void event(Enum e) {
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playEvent(e, v);
        }
    }

    /* package */ void onScroll(int dx, int dy, int x, int y) {
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playScroll(v, dx, dy, x, y);
        }
    }




    //// SaveState


    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        Bundle bundle = new Bundle(RecyclerScrollListener.SavedState.class.getClassLoader());
        for (RecyclerScrollListener l : recyclerMap.values()) {
            bundle.putParcelable(l.tag, l.onSaveInstanceState());
        }
        savedState.listenerSaveStateMap = bundle;
        return savedState;
    }

    public void restoreInstanceState(Parcelable savedState) {
        SavedState s = (SavedState) savedState;
        s.listenerSaveStateMap.setClassLoader(RecyclerScrollListener.SavedState.class.getClassLoader());
        for (RecyclerScrollListener listener : recyclerMap.values()) {
            if (s.listenerSaveStateMap.containsKey(listener.tag)) {
                listener.restoreState(s.listenerSaveStateMap.getParcelable(listener.tag));
            }
        }
    }


    /* package */ static class SavedState implements Parcelable {
        Bundle listenerSaveStateMap;

        public SavedState() {
        }

        SavedState(Parcel in) {
            listenerSaveStateMap = in.readBundle();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeBundle(listenerSaveStateMap);
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
