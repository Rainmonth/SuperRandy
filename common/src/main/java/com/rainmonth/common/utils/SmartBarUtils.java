package com.rainmonth.common.utils;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;

import androidx.annotation.ColorRes;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.appcompat.widget.Toolbar;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.rainmonth.common.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SmartBarUtils {

    private static final String TAG_FOR_FAKE_STATUS_BAR_VIEW = "fakeStatusBarView";

    private static final String TAG_FOR_ADD_CONTENT_MARGIN = "addContentMargin";

    /**
     * 调用 ActionBar.setTabsShowAtBottom(boolean) 方法。 如果
     * android:uiOptions="splitActionBarWhenNarrow"，则可设置ActionBar Tabs显示在底栏。
     * <p/>
     * 示例： public class MyActivity extends Activity implements
     * ActionBar.TabListener { protected void onCreate(Bundle
     * savedInstanceState) { super.onCreate(savedInstanceState); ...
     * <p/>
     * final ActionBar bar = getActionBar();
     * bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
     * SmartBarUtils.setActionBarTabsShowAtBottom(bar, true);
     * <p/>
     * bar.addTab(bar.newTab().setText(&quot;tab1&quot;).setTabListener(this));
     * ... } }
     */
    public static void setActionBarTabsShowAtBottom(ActionBar actionbar,
                                                    boolean showAtBottom) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setTabsShowAtBottom", boolean.class);
            try {
                method.invoke(actionbar, showAtBottom);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用 ActionBar.setActionBarViewCollapsable(boolean) 方法。
     * 设置ActionBar顶栏无显示内容时是否隐藏。
     * <p/>
     * 示例：
     * <p/>
     * public class MyActivity extends Activity {
     * <p/>
     * protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState); ...
     * <p/>
     * final ActionBar bar = getActionBar();
     * <p/>
     * // 调用setActionBarViewCollapsable，并设置ActionBar没有显示内容，则ActionBar顶栏不显示
     * SmartBarUtils.setActionBarViewCollapsable(bar, true);
     * bar.setDisplayOptions(0); } }
     */
    public static void setActionBarViewCollapsable(ActionBar actionbar,
                                                   boolean collapsable) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionBarViewCollapsable",
                    boolean.class);
            try {
                method.invoke(actionbar, collapsable);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用 ActionBar.setActionModeHeaderHidden(boolean) 方法。 设置ActionMode顶栏是否隐藏。
     * <p/>
     * public class MyActivity extends Activity {
     * <p/>
     * protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState); ...
     * <p/>
     * final ActionBar bar = getActionBar();
     * <p/>
     * // ActionBar转为ActionMode时，不显示ActionMode顶栏
     * SmartBarUtils.setActionModeHeaderHidden(bar, true); } }
     */
    public static void setActionModeHeaderHidden(ActionBar actionbar,
                                                 boolean hidden) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setActionModeHeaderHidden", boolean.class);
            try {
                method.invoke(actionbar, hidden);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 调用ActionBar.setBackButtonDrawable(Drawable)方法
     * <p/>
     * <p>设置返回键图标
     * <p/>
     * <p>示例：</p>
     * <pre class="prettyprint">
     * public class MyActivity extends Activity {
     * <p/>
     * protected void onCreate(Bundle savedInstanceState) {
     * super.onCreate(savedInstanceState);
     * ...
     * <p/>
     * final ActionBar bar = getActionBar();
     * // 自定义ActionBar的返回键图标
     * SmartBarUtils.setBackIcon(bar, getResources().getDrawable(R.drawable.ic_back));
     * ...
     * }
     * }
     * </pre>
     */
    public static void setBackIcon(ActionBar actionbar, Drawable backIcon) {
        try {
            Method method = Class.forName("android.app.ActionBar").getMethod(
                    "setBackButtonDrawable", Drawable.class);
            try {
                method.invoke(actionbar, backIcon);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 以下三个方法原作者为c跳跳(http://weibo.com/u/1698085875),
     * 由Shawn(http://weibo.com/linshen2011)在其基础上改进了一种判断SmartBar是否存在的方法,
     * 注意该方法反射的接口只存在于2013年6月之后魅族的flyme固件中
     */

    /**
     * 方法一:uc等在使用的方法(新旧版flyme均有效)，
     * 此方法需要配合requestWindowFeature(Window.FEATURE_NO_TITLE
     * )使用,缺点是程序无法使用系统actionbar
     *
     * @param decorView window.getDecorView
     */
    public static void hide(View decorView) {
        if (!hasSmartBar())
            return;

        try {
            @SuppressWarnings("rawtypes")
            Class[] arrayOfClass = new Class[1];
            arrayOfClass[0] = Integer.TYPE;
            Method localMethod = View.class.getMethod("setSystemUiVisibility",
                    arrayOfClass);
            Field localField = View.class
                    .getField("SYSTEM_UI_FLAG_HIDE_NAVIGATION");
            Object[] arrayOfObject = new Object[1];
            try {
                arrayOfObject[0] = localField.get(null);
            } catch (Exception e) {

            }
            localMethod.invoke(decorView, arrayOfObject);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法二：此方法需要配合requestWindowFeature(Window.FEATURE_NO_TITLE)使用
     * ，缺点是程序无法使用系统actionbar
     *
     * @param context
     * @param window
     */
    public static void hide(Context context, Window window) {
        hide(context, window, 0);
    }

    private static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen",
                "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 方法三：需要使用顶部actionbar的应用请使用此方法
     *
     * @param context
     * @param window
     * @param smartBarHeight set SmartBarUtils.SMART_BAR_HEIGHT_PIXEL
     */
    public static void hide(Context context, Window window, int smartBarHeight) {
        if (!hasSmartBar()) {
            return;
        }
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return;
        }

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        int statusBarHeight = getStatusBarHeight(context);

        window.getDecorView()
                .setPadding(0, statusBarHeight, 0, -smartBarHeight);
    }

    /**
     * 新型号可用反射调用Build.hasSmartBar()来判断有无SmartBar
     *
     * @return
     */
    public static boolean hasSmartBar() {
        try {
            Method method = Class.forName("android.os.Build").getMethod(
                    "hasSmartBar");
            return ((Boolean) method.invoke(null)).booleanValue();
        } catch (Exception e) {
        }

        if (Build.DEVICE.equals("mx2")) {
            return true;
        } else if (Build.DEVICE.equals("mx") || Build.DEVICE.equals("m9")) {
            return false;
        }
        return false;
    }

    /**
     * 设置状态栏颜色
     *
     * @param activity   Activity
     * @param colorResId 颜色资源
     */
    public static void setStatusBarColor(Activity activity, @ColorRes int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStatusBarColorForLollipop(activity, colorResId);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusBarColorForKitKat(activity, colorResId);
        }
    }

    public static void setToolbarBgColor(Toolbar toolbar, @ColorRes int colorResId) {
        if (null != toolbar) {
            toolbar.setBackgroundColor(toolbar.getContext().getResources().getColor(colorResId));
        }
    }

    /**
     * 4.4.2到5.0设置状态栏颜色
     *
     * @param activity
     * @param colorResId
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void setStatusBarColorForKitKat(Activity activity, @ColorRes int colorResId) {
        Window window = activity.getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        //获取父布局
        View mContentChild = mContentView.getChildAt(0);
        //获取状态栏高度
        int statusBarHeight = getStatusBarHeight(activity);
        //如果存在假的statusBarView则先移除，防止重复添加
        removeFakeStatusBarViewIfExist(activity);
        //设置颜色
        setFakeStatusBarViewWithColor(activity, statusBarHeight, colorResId);
        //添加ContentMargin，如果不添加这个，则要设置fitSystemWindow属性为true
        addMarginTopToContentChild(mContentChild, statusBarHeight);
        if (null != mContentChild) {
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }

        int action_bar_id = activity.getResources().getIdentifier("action_bar",
                "id", activity.getPackageName());
        View view = activity.findViewById(action_bar_id);
        if (view != null) {
            TypedValue typedValue = new TypedValue();
            if (activity.getTheme().resolveAttribute(R.attr.actionBarSize,
                    typedValue, true)) {
                int actionBarHeight = TypedValue.complexToDimensionPixelSize(typedValue.data,
                        activity.getResources().getDisplayMetrics());
                setContentTopPadding(activity, actionBarHeight);
            }
        }
    }

    /**
     * 5.0以上设置状态栏颜色处理
     *
     * @param activity
     * @param colorResId
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void setStatusBarColorForLollipop(Activity activity, @ColorRes int colorResId) {
        Window window = activity.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(activity.getResources().getColor(colorResId));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    /**
     * 移除假的状态View
     *
     * @param activity
     */
    private static void removeFakeStatusBarViewIfExist(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeView = decorView.findViewWithTag(TAG_FOR_FAKE_STATUS_BAR_VIEW);
        if (null != fakeView) {
            decorView.removeView(fakeView);
        }
    }

    private static void setFakeStatusBarViewWithColor(Activity activity,
                                                      int statusBarHeight,
                                                      int colorResId) {
        Window window = activity.getWindow();
        ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
        View statusBarView = new View(window.getContext());

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
        params.gravity = Gravity.TOP;
        statusBarView.setLayoutParams(params);
        statusBarView.setTag(TAG_FOR_FAKE_STATUS_BAR_VIEW);
        statusBarView.setBackgroundColor(activity.getResources().getColor(colorResId));
        decorViewGroup.addView(statusBarView);
    }

    /**
     * 为ContentView添加到状态栏的Margin（避免使用fitSystemWindow属性）
     */
    private static void addMarginTopToContentChild(View mContentChild, int statusBarHeight) {
        if (mContentChild == null) {
            return;
        }
        if (!TAG_FOR_ADD_CONTENT_MARGIN.equals(mContentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentChild.getLayoutParams();
            lp.topMargin += statusBarHeight;
            mContentChild.setLayoutParams(lp);
            mContentChild.setTag(TAG_FOR_ADD_CONTENT_MARGIN);
        }
    }

    private static void setContentTopPadding(Activity activity, int padding) {
        ViewGroup mContentView = (ViewGroup) activity.getWindow()
                .findViewById(Window.ID_ANDROID_CONTENT);
        mContentView.setPadding(0, padding, 0, 0);
    }

    public static void translucentStatusBar(Activity activity, boolean hideStatusBarBackground) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            translucentStatusBarForLollipop(activity, hideStatusBarBackground);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            translucentStatusBarForKitKat(activity);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static void translucentStatusBarForKitKat(Activity activity) {
        Window window = activity.getWindow();
        //设置Window为透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        ViewGroup mContentView = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        View mContentChild = mContentView.getChildAt(0);

        //移除已经存在假状态栏则,并且取消它的Margin间距
        removeFakeStatusBarViewIfExist(activity);
        removeMarginTopOfContentChild(mContentChild, getStatusBarHeight(activity));
        if (mContentChild != null) {
            //fitsSystemWindow 为 false, 不预留系统栏位置.
            ViewCompat.setFitsSystemWindows(mContentChild, false);
        }
    }

    private static void removeMarginTopOfContentChild(View contentChild, int statusBarHeight) {
        if (contentChild == null) {
            return;
        }
        if (TAG_FOR_ADD_CONTENT_MARGIN.equals(contentChild.getTag())) {
            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) contentChild.getLayoutParams();
            lp.topMargin -= statusBarHeight;
            contentChild.setLayoutParams(lp);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private static void translucentStatusBarForLollipop(Activity activity,
                                                        boolean hideStatusBarBackground) {
        Window window = activity.getWindow();
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (hideStatusBarBackground) {
            //如果为全透明模式，取消设置Window半透明的Flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置状态栏为透明
            window.setStatusBarColor(Color.TRANSPARENT);
            //设置window的状态栏不可见
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        } else {
            //如果为半透明模式，添加设置Window半透明的Flag
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //设置系统状态栏处于可见状态
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
        //view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }
    }

    private static View getFakeStatusBarView(Activity activity) {
        Window window = activity.getWindow();
        ViewGroup decorView = (ViewGroup) window.getDecorView();
        View fakeView = decorView.findViewWithTag(TAG_FOR_FAKE_STATUS_BAR_VIEW);
        if (fakeView != null) {
            return fakeView;
        } else {
            return null;
        }
    }

    /**
     * 隐藏虚导航栏
     *
     * @param context ctx上下文
     */
    public static void hideNavKey(Context context) {
        if (Build.VERSION.SDK_INT >= 29) {
            //       设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                            // bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //       设置屏幕始终在前面，不然点击鼠标，重新出现虚拟按键
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
                            // bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
        } else {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav
            );
        }
    }

    /**
     * 显示虚拟导航栏
     *
     * @param context            ctx上下文
     * @param systemUiVisibility 对应flag
     */
    public static void showNavKey(Context context, int systemUiVisibility) {
        ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(systemUiVisibility);
    }
}
