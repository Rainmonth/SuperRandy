package com.rainmonth.common.widgets.loading;

import android.content.Context;
import android.view.View;

interface IVaryViewHelper {

    View getCurrentLayout();

    void restoreView();

    void showLayout(View view);

    View inflate(int layoutId);

    Context getContext();

    View getView();

}