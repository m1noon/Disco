package com.minoon.disco;

import android.view.View;
import android.view.ViewPropertyAnimator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by a13587 on 15/09/11.
 */
/* package */ class BasicChoreography extends Choreography {
    private static final String TAG = Logger.createTag(BasicChoreography.class.getSimpleName());
    /* package */ static final float NO_VALUE = Float.MIN_VALUE;

    Map<Enum, BasicAnimator> eventAnimator;

    BasicViewTransformer viewTransformer;

    BasicViewTagAnimator viewTagAnimator;

    BasicScrollTransformer scrollTransformer;

    public BasicChoreography(Disco disco) {
        super(disco);
        eventAnimator = new HashMap<>();
    }

    @Override
    public boolean playChase(View anchorView, View chaserView) {
        boolean changed = false;
        if (viewTransformer != null) {
            changed = viewTransformer.transform(anchorView, chaserView);
        }

        if (viewTagAnimator != null) {
            changed = changed || viewTagAnimator.animateIfNeed(anchorView, chaserView);
        }

        return changed;
    }

    @Override
    public long playEvent(Enum e, View chaserView) {
        BasicAnimator animator = eventAnimator.get(e);
        if (animator != null) {
            animator.animate(chaserView);
            return animator.duration;
        }
        return 0;
    }

    @Override
    public boolean playScroll(View chaserView, int dx, int dy, int x, int y) {
        if (scrollTransformer != null) {
            return scrollTransformer.transform(chaserView, x, y);
        }
        return false;
    }


    /* package */ void setScrollTransformer(BasicScrollTransformer transformer) {
        this.scrollTransformer = transformer;
    }

    /* package */ void addEventAnimators(Map<Enum, BasicAnimator> animators) {
        if (animators != null && animators.size() > 0) {
            eventAnimator.putAll(animators);
        }
    }

    /* package */ void setViewTransformer(BasicViewTransformer viewTransformer) {
        this.viewTransformer = viewTransformer;
    }

    /* package */ void setViewTagAnimator(BasicViewTagAnimator viewTagAnimator) {
        this.viewTagAnimator = viewTagAnimator;
    }

    private static void startAnimation(View view, float alpha, float translationX, float translationY, float scaleX, float scaleY, long duration) {
        view.animate().cancel();
        ViewPropertyAnimator animator = view.animate();
        if (alpha != NO_VALUE) {
            animator.alpha(alpha);
        }
        if (translationX != NO_VALUE) {
            animator.translationX(translationX);
        }
        if (translationY != NO_VALUE) {
            animator.translationY(translationY);
        }
        if (scaleX != NO_VALUE) {
            animator.scaleX(scaleX);
        }
        if (scaleY != NO_VALUE) {
            animator.scaleY(scaleY);
        }
        animator.setDuration(duration);
        animator.start();
    }

    /* package */ static class BasicAnimator {
        private final float alpha;
        private final float translationX;
        private final float translationY;
        private final float scaleX;
        private final float scaleY;
        private final long duration;

        public BasicAnimator(float alpha, float translationX, float translationY, float scaleX, float scaleY, long duration) {
            this.alpha = alpha;
            this.translationX = translationX;
            this.translationY = translationY;
            this.scaleX = scaleX;
            this.scaleY = scaleY;
            this.duration = duration;
        }

        public void animate(View view) {
            startAnimation(view, alpha, translationX, translationY, scaleX, scaleY, duration);
        }
    }


    /* package */ static class BasicViewTransformer {
        private final ViewParam param;

        private final float fromBounds;
        private final float toBounds;

        private final float fromAlpha;
        private final float fromTranslationX;
        private final float fromTranslationY;
        private final float fromScaleX;
        private final float fromScaleY;

        private final float toAlpha;
        private final float toTranslationX;
        private final float toTranslationY;
        private final float toScaleX;
        private final float toScaleY;

        private float prevProgress;

        public BasicViewTransformer(ViewParam param, float fromBounds, float toBounds, float fromAlpha, float fromTranslationX, float fromTranslationY, float fromScaleX, float fromScaleY, float toAlpha, float toTranslationX, float toTranslationY, float toScaleX, float toScaleY) {
            this.param = param;
            this.fromBounds = fromBounds;
            this.toBounds = toBounds;
            this.fromAlpha = fromAlpha;
            this.fromTranslationX = fromTranslationX;
            this.fromTranslationY = fromTranslationY;
            this.fromScaleX = fromScaleX;
            this.fromScaleY = fromScaleY;
            this.toAlpha = toAlpha;
            this.toTranslationX = toTranslationX;
            this.toTranslationY = toTranslationY;
            this.toScaleX = toScaleX;
            this.toScaleY = toScaleY;
        }

        boolean transform(View anchorView, View chaserView) {
            float progress = getProgress(anchorView);
            // set params
            if (fromAlpha != NO_VALUE && toAlpha != NO_VALUE) {
                chaserView.setAlpha(fromAlpha + (toAlpha - fromAlpha) * progress);
            }
            if (fromTranslationX != NO_VALUE && toTranslationX != NO_VALUE) {
                chaserView.setTranslationX(fromTranslationX + (toTranslationX - fromTranslationX) * progress);
            }
            if (fromTranslationY != NO_VALUE && toTranslationY != NO_VALUE) {
                chaserView.setTranslationY(fromTranslationY + (toTranslationY - fromTranslationY) * progress);
            }
            if (fromScaleX != NO_VALUE && toScaleX != NO_VALUE) {
                chaserView.setScaleX(fromScaleX + (toScaleX - fromScaleX) * progress);
            }
            if (fromScaleY != NO_VALUE && toScaleY != NO_VALUE) {
                chaserView.setScaleY(fromScaleY + (toScaleY - fromScaleY) * progress);
            }

            final boolean changed = prevProgress != progress;
            prevProgress = progress;
            return changed;
        }

        private float getProgress(View view) {
            float progress = param.getValue(view) / (toBounds - fromBounds);
            return Math.min(1, Math.max(0, progress));
        }
    }

    /* package */ static class BasicViewTagAnimator {
        private final ViewParam param;
        private final float bounds;

        private final float fromAlpha;
        private final float fromTranslationX;
        private final float fromTranslationY;
        private final float fromScaleX;
        private final float fromScaleY;

        private final float toAlpha;
        private final float toTranslationX;
        private final float toTranslationY;
        private final float toScaleX;
        private final float toScaleY;
        private long duration;

        private float prevValue;

        public BasicViewTagAnimator(ViewParam param, float bounds, float fromAlpha, float fromTranslationX, float fromTranslationY, float fromScaleX, float fromScaleY, float toAlpha, float toTranslationX, float toTranslationY, float toScaleX, float toScaleY, long duration) {
            this.param = param;
            this.bounds = bounds;
            this.fromAlpha = fromAlpha;
            this.fromTranslationX = fromTranslationX;
            this.fromTranslationY = fromTranslationY;
            this.fromScaleX = fromScaleX;
            this.fromScaleY = fromScaleY;
            this.toAlpha = toAlpha;
            this.toTranslationX = toTranslationX;
            this.toTranslationY = toTranslationY;
            this.toScaleX = toScaleX;
            this.toScaleY = toScaleY;
            this.duration = duration;
        }

        boolean animateIfNeed(View anchorView, View chaserView) {
            float value = param.getValue(anchorView);
            boolean changed = false;
            Logger.d(TAG, "animateIfNeed. prevValue='%s', value='%s', bounds='%s'", prevValue, value, bounds);

            if (prevValue < bounds && value > bounds) {
                changed = true;
                startAnimation(chaserView, toAlpha, toTranslationX, toTranslationY, toScaleX, toScaleY, duration);
            }

            if (prevValue > bounds && value < bounds) {
                changed = true;
                startAnimation(chaserView, fromAlpha, fromTranslationX, fromTranslationY, fromScaleX, fromScaleY, duration);
            }

            prevValue = value;
            return changed;
        }
    }


    /* package */ static class BasicScrollTransformer {
        /* package */ static final int HORIZONTAL = 0;
        /* package */ static final int VERTICAL = 1;

        private int orientation;
        private float multiplier;
        private int offset;

        private float fromAlpha;
        private float fromScaleX;
        private float fromScaleY;

        private float toAlpha;
        private float toScaleX;
        private float toScaleY;

        private float prevProgress;


        public BasicScrollTransformer(int orientation, float multiplier, int offset, float fromAlpha, float fromScaleX, float fromScaleY, float toAlpha, float toScaleX, float toScaleY) {
            this.orientation = orientation;
            this.multiplier = multiplier;
            this.offset = offset;
            this.fromAlpha = fromAlpha;
            this.fromScaleX = fromScaleX;
            this.fromScaleY = fromScaleY;
            this.toAlpha = toAlpha;
            this.toScaleX = toScaleX;
            this.toScaleY = toScaleY;
        }

        /* package */ boolean transform(View view, int scrollPositionX, int scrollPositionY) {
            int targetScrollPosition = orientation == HORIZONTAL ? scrollPositionX : scrollPositionY;
            int offsetScrollPosition = Math.max(0, targetScrollPosition - offset);
            final float viewScrollPosition = -offsetScrollPosition * multiplier;
            switch (orientation) {
                case HORIZONTAL:
                    view.setTranslationX(viewScrollPosition);
                    break;
                case VERTICAL:
                    view.setTranslationY(viewScrollPosition);
                    break;
            }
            final int range = view.getBottom();
            final float progress = Math.max(0, Math.min(1, Math.abs(viewScrollPosition / (float) range)));
            Logger.d(TAG, "scrollTransform. x='%s', y='%s', range='%s', progress='%s'", scrollPositionX, scrollPositionY, range, progress);
            if (fromAlpha != NO_VALUE && toAlpha != NO_VALUE) {
                view.setAlpha(fromAlpha + (toAlpha - fromAlpha) * progress);
            }
            if (fromScaleX != NO_VALUE && toScaleX != NO_VALUE) {
                view.setScaleX(fromScaleX + (toScaleX - fromScaleX) * progress);
            }
            if (fromScaleY != NO_VALUE && toScaleY != NO_VALUE) {
                view.setScaleY(fromScaleY + (toScaleY - fromScaleY) * progress);
            }

            boolean changed = prevProgress != progress;
            prevProgress = progress;
            return changed;
        }
    }
}
