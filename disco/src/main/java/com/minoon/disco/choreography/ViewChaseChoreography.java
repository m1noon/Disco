package com.minoon.disco.choreography;

import android.view.View;

/**
 * Created by a13587 on 15/09/15.
 */
public interface ViewChaseChoreography extends Choreography {

    boolean playChase(View anchorView, View chaserView);
}
