package com.rainmonth.library.smartlayout;

import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

abstract class SmartTabIndicationInterpolator {

    private static final SmartTabIndicationInterpolator SMART = new SmartIndicationInterpolator();
    private static final SmartTabIndicationInterpolator LINEAR = new LinearIndicationInterpolator();

    static final int ID_SMART = 0;
    static final int ID_LINEAR = 1;

    public static SmartTabIndicationInterpolator of(int id) {
        switch (id) {
            case ID_SMART:
                return SMART;
            case ID_LINEAR:
                return LINEAR;
            default:
                throw new IllegalArgumentException("Unknown id: " + id);
        }
    }

    public abstract float getLeftEdge(float offset);

    public abstract float getRightEdge(float offset);

    public float getThickness(float offset) {
        return 1f; //Always the same thickness by default
    }

    private static class SmartIndicationInterpolator extends SmartTabIndicationInterpolator {

        private static final float DEFAULT_INDICATOR_INTERPOLATION_FACTOR = 3.0f;

        private final Interpolator mLeftEdgeInterpolator;
        private final Interpolator mRightEdgeInterpolator;

        SmartIndicationInterpolator() {
            this(DEFAULT_INDICATOR_INTERPOLATION_FACTOR);
        }

        SmartIndicationInterpolator(float factor) {
            mLeftEdgeInterpolator = new AccelerateInterpolator(factor);
            mRightEdgeInterpolator = new DecelerateInterpolator(factor);
        }

        @Override
        public float getLeftEdge(float offset) {
            return mLeftEdgeInterpolator.getInterpolation(offset);
        }

        @Override
        public float getRightEdge(float offset) {
            return mRightEdgeInterpolator.getInterpolation(offset);
        }

        @Override
        public float getThickness(float offset) {
            return 1f / (1.0f - getLeftEdge(offset) + getRightEdge(offset));
        }

    }

    private static class LinearIndicationInterpolator extends SmartTabIndicationInterpolator {

        @Override
        public float getLeftEdge(float offset) {
            return offset;
        }

        @Override
        public float getRightEdge(float offset) {
            return offset;
        }

    }
}
