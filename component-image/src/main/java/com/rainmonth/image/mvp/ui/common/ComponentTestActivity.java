package com.rainmonth.image.mvp.ui.common;

import android.view.MotionEvent;
import android.widget.Toast;

import com.rainmonth.common.base.BaseActivity;
import com.rainmonth.common.di.component.AppComponent;
import com.rainmonth.common.widgets.library.internal.PullAcrView;
import com.rainmonth.image.R;
import com.rainmonth.image.mvp.ui.test.WorkDemoActivity;

import butterknife.BindView;

/**
 * @author 张豪成
 * @date 2019-07-24 09:53
 */
public class ComponentTestActivity extends BaseActivity {
    @BindView(R.id.pull_arc_view1)
    PullAcrView recentPlayPullArcView;

    @BindView(R.id.pull_arc_view2)
    PullAcrView bookPullArcView;

    @BindView(R.id.pull_arc_view3)
    PullAcrView storyPullArcView;

    private PullAcrView.OnPullListener mOnPullListener = new PullAcrView.OnPullListener() {
        @Override
        public void onPull(float pullDistance) {

        }

        @Override
        public void onPullRelease() {
            Toast.makeText(mContext, "即将跳转", Toast.LENGTH_SHORT).show();
            getSafeHandler().postDelayed(() -> {
                readyGo(WorkDemoActivity.class);
            }, 1000);

        }

        @Override
        public void onPullCancel() {
            Toast.makeText(mContext, "拖动取消, 无需调整", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {

    }

    @Override
    public void initToolbar(int colorResId) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.image_activity_component_test2;
    }

    @Override
    protected void initViewsAndEvents() {
        recentPlayPullArcView.setOnPullListener(mOnPullListener);
        bookPullArcView.setOnPullListener(mOnPullListener);
        storyPullArcView.setOnPullListener(mOnPullListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
