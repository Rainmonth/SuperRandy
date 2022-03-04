package com.rainmonth.common.decoration;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rainmonth.utils.log.LogUtils;

// todo 当屏幕空间不够时，会自动挤压中间的ItemView
public class CombineGridItemDecoration extends RecyclerView.ItemDecoration {
    public static final String TAG = "Combine";

    private final int spanCount;
    private final int totalCount;
    public boolean enableFirstLeft, enableLastRight, enableFirstTop, enableLastBottom;
    private final int firstLeft;
    private final int left;
    private final int right;
    private final int lastRight;
    private final int firstTop;
    private final int top;
    private final int bottom;
    private final int lastBottom;

    public CombineGridItemDecoration(Builder builder) {
        this.spanCount = builder.spanCount;
        this.totalCount = builder.totalCount;
        this.enableFirstLeft = builder.enableFirstLeft;
        this.enableLastRight = builder.enableLastRight;
        this.enableFirstTop = builder.enableFirstTop;
        this.enableLastBottom = builder.enableLastBottom;
        this.firstLeft = builder.firstLeft;
        this.left = builder.left;
        this.right = builder.right;
        this.lastRight = builder.lastRight;
        this.firstTop = builder.firstTop;
        this.top = builder.top;
        this.bottom = builder.bottom;
        this.lastBottom = builder.lastBottom;
    }

    public static class Builder {
        private int spanCount, totalCount;

        public boolean enableFirstLeft, enableLastRight, enableFirstTop, enableLastBottom;

        private int firstLeft, left, right, lastRight, firstTop, top, bottom, lastBottom;

        public Builder spanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public Builder enableFirstLeft(boolean enableFirstLeft, int firstLeft) {
            this.enableFirstLeft = enableFirstLeft;
            this.firstLeft = firstLeft;
            return this;
        }

        public Builder enableLastRight(boolean enableLastRight, int lastRight) {
            this.enableLastRight = enableLastRight;
            this.lastRight = lastRight;
            return this;
        }

        public Builder enableFirstTop(boolean enableFirstTop, int firstTop) {
            this.enableFirstTop = enableFirstTop;
            this.firstTop = firstTop;
            return this;
        }

        public Builder enableLastBottom(boolean enableLastBottom, int lastBottom) {
            this.enableLastBottom = enableLastBottom;
            this.lastBottom = lastBottom;
            return this;
        }

        public Builder totalCount(int totalCount) {
            this.totalCount = totalCount;
            return this;
        }

        public Builder left(int left) {
            this.left = left;
            return this;
        }

        public Builder right(int right) {
            this.right = right;
            return this;
        }

        public Builder top(int top) {
            this.top = top;
            return this;
        }

        public Builder bottom(int bottom) {
            this.bottom = bottom;
            return this;
        }

        public Builder build() {
            return this;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        LogUtils.i(TAG, position + " viewWidth:" + view.getWidth());
        if (parent.getLayoutManager() instanceof GridLayoutManager) {
            // 列数
            int spanCount = 0;
            try {
                spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }

            if (spanCount == 0) {
                LogUtils.w(TAG, "无法直接读取spanCount!!");
                spanCount = this.spanCount;
            }
            if (spanCount == 0) {
                return;
            }
            int totalCount = 0;
            try {
                if (parent.getAdapter() != null) {
                    totalCount = parent.getAdapter().getItemCount();
                }
            } catch (Exception e) {
                LogUtils.printStackTrace(e);
            }
            if (totalCount == 0) {
                LogUtils.w(TAG, "无法直接读取totalCount!!");
                totalCount = this.totalCount;
            }

            if (totalCount == 0) {
                return;
            }

            if (spanCount == 1) {
                outRect.left = enableFirstLeft ? firstLeft : left;
                outRect.top = position == 0 ? (enableFirstTop ? firstTop : top) : top;
                outRect.right = enableLastRight ? lastRight : right;
                outRect.bottom = position == totalCount - 1 ? (enableLastBottom ? lastBottom : bottom) : bottom;
                return;
            }

            boolean isFirstColumn = isFirstColumn(position, spanCount);
            boolean isLastColumn = isLastColumn(position, spanCount);
            boolean isFirstRow = isFirstRow(position, spanCount);
            boolean isLastRow = isLastRow(position, totalCount, spanCount);

            boolean isLeftTop = isFirstColumn && isFirstRow;
            boolean isRightTop = isLastColumn && isFirstRow;
            boolean isLeftBottom = isFirstColumn && isLastRow;
            boolean isRightBottom = isLastColumn && isLastRow;

            boolean isMidLeft = isFirstColumn && !isFirstRow && !isLastRow;
            boolean isMidTop = !isFirstColumn && !isLastColumn && isFirstRow;
            boolean isMidRight = isLastColumn && !isFirstRow && !isLastRow;
            boolean isMidBottom = !isFirstColumn && !isLastColumn && isLastRow;

            LogUtils.i(TAG, "spanCount:" + spanCount + ", totalCount:" + totalCount);
            LogUtils.i(TAG, "position:" + position + ", isLastRow:" + isLastRow(position, 50, 4));
            if (isLeftTop) {
                outRect.left = enableFirstLeft ? firstLeft : left;
                outRect.top = enableFirstTop ? firstTop : top;
                outRect.right = right;
                outRect.bottom = bottom;
                return;
            }
            if (isRightTop) {
                outRect.left = left;
                outRect.top = enableFirstTop ? firstTop : top;
                outRect.right = enableLastRight ? lastRight : right;
                outRect.bottom = bottom;
                return;
            }
            if (isLeftBottom) {
                outRect.left = enableFirstLeft ? firstLeft : left;
                outRect.top = top;
                outRect.right = right;
                outRect.bottom = enableLastBottom ? lastBottom : bottom;
                return;
            }
            if (isRightBottom) {
                outRect.left = left;
                outRect.top = top;
                outRect.right = enableLastRight ? lastRight : right;
                outRect.bottom = enableLastBottom ? lastBottom : bottom;
                return;
            }
            if (isMidLeft) {
                outRect.left = enableFirstLeft ? firstLeft : left;
                outRect.top = top;
                outRect.right = right;
                outRect.bottom = bottom;
                return;
            }
            if (isMidTop) {
                outRect.left = left;
                outRect.top = enableFirstTop ? firstTop : top;
                outRect.right = right;
                outRect.bottom = bottom;
                return;
            }

            if (isMidRight) {
                outRect.left = left;
                outRect.top = top;
                outRect.right = enableLastRight ? lastRight : right;
                outRect.bottom = bottom;
                return;
            }
            if (isMidBottom) {
                outRect.left = left;
                outRect.top = top;
                outRect.right = right;
                outRect.bottom = enableLastBottom ? lastBottom : bottom;
                return;
            }
            outRect.left = left;
            outRect.top = top;
            outRect.right = right;
            outRect.bottom = bottom;
        } else {
            throw new RuntimeException("LayoutManager must extends GridLayoutManager!");
        }

    }

    /**
     * 是否第一行
     *
     * @param position  当前位置
     * @param spanCount 每行个数
     * @return true if is first row
     */
    private boolean isFirstRow(int position, int spanCount) {
        int row = position / spanCount;
        return row == 0;
    }


    /**
     * 是否最后一行
     *
     * @param position   当前位置 49
     * @param totalCount 总数 50
     * @param spanCount  每行个数 4
     * @return true if is last row
     */
    private boolean isLastRow(int position, int totalCount, int spanCount) {
        return position + 1 > totalCount - totalCount % spanCount;
    }

    /**
     * 是否第一列
     *
     * @param position  当前位置
     * @param spanCount 每行个数
     * @return true if is last column
     */
    private boolean isFirstColumn(int position, int spanCount) {
        // view位于的列
        int column = position % spanCount;
        return column == 0;
    }

    /**
     * 是否最后一列
     *
     * @param position  当前位置
     * @param spanCount 每行个数
     * @return true if is last column
     */
    private boolean isLastColumn(int position, int spanCount) {
        // view位于的列
        int column = position % spanCount;
        return column + 1 == spanCount;
    }
}