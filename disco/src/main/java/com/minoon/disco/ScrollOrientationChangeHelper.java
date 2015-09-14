package com.minoon.disco;

/**
 * Helper class for check scroll orientation changing.
 *
 * Created by hiroki-mino on 2015/07/01.
 */
/* package */ class ScrollOrientationChangeHelper {
    private static final String TAG = Logger.createTag(ScrollOrientationChangeHelper.class.getSimpleName());

    public interface ScrollOrientationChangeListener {
        void onOrientationChange(boolean up);
    }

    private int mSignal = 0;
    private int mSensitivity = 200;
    private final ScrollOrientationChangeListener mListener;
    private boolean pastOrientation = true;

    public ScrollOrientationChangeHelper(ScrollOrientationChangeListener listener) {
        mListener = listener;
    }

    public void onScroll(int dy) {
        if (Math.signum(dy) * Math.signum(mSignal) < 0) {
            // 今までと反対方向なのでシグナルの値をリセット
            mSignal = dy;
        } else {
            // シグナルの値を移動分だけ蓄積
            mSignal += dy;
        }

        if (-mSensitivity < mSignal && mSignal < mSensitivity) {
            // シグナルが[-規定値 ~ +規定値]以内であればなにもしない
            return;
        }

        // シグナルが規定値以下になったら（十分上方向にスクロールされたら）タブを表示（逆も同様）
        boolean up = (mSignal <= -mSensitivity);
        Logger.v(TAG, "onScrolled. dy='%s', up='%s', signal='%s'", dy, up, mSignal);
        if(pastOrientation == up) {
            return;
        }
        pastOrientation = up;
        mListener.onOrientationChange(up);
    }
}
