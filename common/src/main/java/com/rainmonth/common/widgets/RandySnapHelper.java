package com.rainmonth.common.widgets;

import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.TextUtilsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

/**
 * @author 张豪成
 * @date 2019-11-26 13:54
 */
public class RandySnapHelper extends PagerSnapHelper {

    private RecyclerView mRecyclerView;
    /**
     * 是否从右到左
     */
    private boolean mIsRtl;
    private int mGravity;
    private OrientationHelper mVerticalHelper;
    private OrientationHelper mHorizontalHelper;
    private int mRetainStart;
    private int mCenterStart;

    RandySnapHelper(int retainStart, int centerStart) {
        mRetainStart = retainStart;
        mCenterStart = centerStart;
        mGravity = Gravity.START;
    }

    @Override
    public void attachToRecyclerView(@Nullable RecyclerView recyclerView)
            throws IllegalStateException {
        if (recyclerView != null) {
            recyclerView.setOnFlingListener(null);
            if (mGravity == Gravity.START || mGravity == Gravity.END) {
                mIsRtl = TextUtilsCompat.getLayoutDirectionFromLocale(Locale.getDefault())
                        == ViewCompat.LAYOUT_DIRECTION_RTL;
            }
            mRecyclerView = recyclerView;
        }
        super.attachToRecyclerView(recyclerView);
    }

    @Override
    public int[] calculateDistanceToFinalSnap(@NonNull RecyclerView.LayoutManager layoutManager,
                                              @NonNull View targetView) {

        if (!(layoutManager instanceof LinearLayoutManager)) {
            return super.calculateDistanceToFinalSnap(layoutManager, targetView);
        }

        int[] out = new int[2];

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

        if (linearLayoutManager.canScrollHorizontally()) {
            if ((mIsRtl && mGravity == Gravity.END) || (!mIsRtl && mGravity == Gravity.START)) {
                out[0] = distanceToStart(targetView, linearLayoutManager, getHorizontalHelper(linearLayoutManager));
            } else {
                out[0] = distanceToEnd(targetView, linearLayoutManager, getHorizontalHelper(linearLayoutManager));
            }
        } else {
            out[0] = 0;
        }

        if (linearLayoutManager.canScrollVertically()) {
            if (mGravity == Gravity.TOP) {
                out[1] = distanceToStart(targetView, linearLayoutManager, getVerticalHelper(linearLayoutManager));
            } else {
                out[1] = distanceToEnd(targetView, linearLayoutManager, getVerticalHelper(linearLayoutManager));
            }
        } else {
            out[1] = 0;
        }

        return out;
    }

    private int distanceToStart(View targetView, LinearLayoutManager lm,
                                @NonNull OrientationHelper helper) {
        int pos = mRecyclerView.getChildLayoutPosition(targetView);
        int distance;
        if ((pos == 0 && (!mIsRtl || lm.getReverseLayout())
                || pos == lm.getItemCount() - 1 && (mIsRtl || lm.getReverseLayout()))
                && !mRecyclerView.getClipToPadding()) {
            int childStart = helper.getDecoratedStart(targetView);
            if (childStart >= helper.getStartAfterPadding() / 2) {
                distance = childStart - helper.getStartAfterPadding();
            } else {
                distance = childStart;
            }
        } else {
            distance = helper.getDecoratedStart(targetView);
            if (pos != 0) {
                distance += mCenterStart;
            }
        }
        return distance;
    }

    private int distanceToEnd(View targetView, LinearLayoutManager lm,
                              @NonNull OrientationHelper helper) {
        int pos = mRecyclerView.getChildLayoutPosition(targetView);
        int distance;

        // 判断第一个和最后一个
        if ((pos == 0 && (mIsRtl || lm.getReverseLayout())
                || pos == lm.getItemCount() - 1 && (!mIsRtl || lm.getReverseLayout()))
                && !mRecyclerView.getClipToPadding()) {
            int childEnd = helper.getDecoratedEnd(targetView);
            if (childEnd >= helper.getEnd() - (helper.getEnd() - helper.getEndAfterPadding()) / 2) {
                distance = helper.getDecoratedEnd(targetView) - helper.getEnd();
            } else {
                distance = childEnd - helper.getEndAfterPadding();
            }
        } else {
            distance = helper.getDecoratedEnd(targetView) - helper.getEnd();
        }
        return distance;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mVerticalHelper == null) {
            mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return mVerticalHelper;
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        if (mHorizontalHelper == null) {
            mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return mHorizontalHelper;
    }

    @Override
    public View findSnapView(RecyclerView.LayoutManager layoutManager) {
        if (!(layoutManager instanceof LinearLayoutManager)) {
            return super.findSnapView(layoutManager);
        }
        View snapView;
        LinearLayoutManager lm = (LinearLayoutManager) layoutManager;
        switch (mGravity) {
            case Gravity.END:
                snapView = findEdgeView(lm, getHorizontalHelper(lm), false);
                break;
            case Gravity.TOP:
                snapView = findEdgeView(lm, getVerticalHelper(lm), true);
                break;
            case Gravity.BOTTOM:
                snapView = findEdgeView(lm, getVerticalHelper(lm), false);
                break;
            case Gravity.START:
            default:
                snapView = findEdgeView(lm, getHorizontalHelper(lm), true);
                break;
        }
        return snapView;
    }

    @Nullable
    private View findEdgeView(LinearLayoutManager lm, OrientationHelper helper, boolean start) {
        if (lm.getChildCount() == 0) {
            return null;
        }

        if (isAtEndOfList(lm)) {
            return null;
        }

        View edgeView = null;
        int distanceToEdge = Integer.MAX_VALUE;

        for (int i = 0; i < lm.getChildCount(); i++) {
            View currentView = lm.getChildAt(i);
            int currentViewDistance;
            if ((start && !mIsRtl) || (!start && mIsRtl)) {
                currentViewDistance = Math.abs(helper.getDecoratedStart(currentView));
            } else {
                currentViewDistance = Math.abs(helper.getDecoratedEnd(currentView)
                        - helper.getEnd());
            }
            if (currentViewDistance < distanceToEdge) {
                distanceToEdge = currentViewDistance;
                edgeView = currentView;
            }
        }
        return edgeView;
    }

    /**
     * 是否到最后一个
     */
    private boolean isAtEndOfList(LinearLayoutManager lm) {
        if ((!lm.getReverseLayout() && mGravity == Gravity.START)
                || (lm.getReverseLayout() && mGravity == Gravity.END)) {
            return lm.findLastCompletelyVisibleItemPosition() == lm.getItemCount() - 1;
        } else {
            return lm.findFirstCompletelyVisibleItemPosition() == 0;
        }
    }
}
