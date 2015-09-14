package com.minoon.disco;

import android.view.View;

/**
 * Created by a13587 on 15/09/11.
 */
public enum ViewParam {
    ALPHA {
        @Override
        float getValue(View view) {
            return view == null ? 0 : view.getAlpha();
        }
    },
    TRANSLATION_X {
        @Override
        float getValue(View view) {
            return view == null ? 0 : view.getTranslationX();
        }
    },
    TRANSLATION_Y {
        @Override
        float getValue(View view) {
            return view == null ? 0 : view.getTranslationY();
        }
    },
    SCALE_X {
        @Override
        float getValue(View view) {
            return view == null ? 0 : view.getScaleX();
        }
    },
    SCALE_Y {
        @Override
        float getValue(View view) {
            return view == null ? 0 : view.getScaleY();
        }
    };

    /* package */ abstract float getValue(View view);
}
