package com.minoon.disco;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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

    public ChoreographyBuilder getChoreographyBuilder() {
        return new ChoreographyBuilderImpl(this);
    }

    public void setUp() {
        // TODO use saveState
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playChase(v, v, true);
        }
    }

    public void addScrollView(RecyclerView recyclerView) {
        if (!recyclerMap.containsKey(recyclerView)) {
            RecyclerScrollListener listener = new RecyclerScrollListener(this);
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

    public void addViewObserver(View anchorView, View chaserView, Choreography choreography) {
        if (scrollObserver.containsKey(anchorView)) {
            scrollObserver.get(anchorView).addChildDependency(chaserView, choreography);
        }

        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).maybeAddDependency(anchorView, chaserView, choreography);
        }
    }

    public void addScrollObserver(View view, Choreography choreography) {
        ChoreographyChain chain = new ChoreographyChain(choreography);
        scrollObserver.put(view, chain);
    }


    /* package */ void event(Enum e) {
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playEvent(e, v);
        }
    }

    /* package */ void onScroll(int dx, int dy, int x, int y) {
        for (View v : scrollObserver.keySet()) {
            scrollObserver.get(v).playScroll(v, dx, dy, x, y);
        }
    }
}
