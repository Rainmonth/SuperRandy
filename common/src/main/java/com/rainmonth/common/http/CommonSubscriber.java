package com.rainmonth.common.http;

import android.content.Context;
import android.text.TextUtils;

import com.rainmonth.common.base.mvp.IBaseView;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subscribers.ResourceSubscriber;
import retrofit2.HttpException;


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> implements Observer<T> {

    private Context mContext;
    private IBaseView mView;
    private String mErrorMsg;
    private boolean isShowErrorState = true;

    protected CommonSubscriber(Context context) {
        this.mContext = context;
    }

    protected CommonSubscriber(IBaseView view) {
        this.mView = view;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
    }

    protected CommonSubscriber(IBaseView view, boolean isShowErrorState) {
        this.mView = view;
        this.isShowErrorState = isShowErrorState;
    }

    protected CommonSubscriber(IBaseView view, String errorMsg, boolean isShowErrorState) {
        this.mView = view;
        this.mErrorMsg = errorMsg;
        this.isShowErrorState = isShowErrorState;
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (mView == null && mContext == null) {
            return;
        }
        showError(e);
    }

    private void showError(Throwable e) {
        if (mView != null) {
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
//                mView.showError(mErrorMsg);
//            } else if (e instanceof ApiException) {
//                mView.showError(e.toString());
            } else if (e instanceof HttpException) {
//                mView.showError(R.string.msg_error_network);
            } else {
//                mView.showError(R.string.msg_error_unknown);
            }
        } else if (mContext != null) {
            if (mErrorMsg != null && !TextUtils.isEmpty(mErrorMsg)) {
//        Toasty.error(mContext, mErrorMsg, Toast.LENGTH_SHORT).show();
//            } else if (e instanceof ApiException) {
//                Toasty.error(mContext, e.toString(), Toast.LENGTH_SHORT).show();
            } else if (e instanceof HttpException) {
//                Toasty.error(
//                        mContext,
//                        mContext.getString(R.string.msg_error_network),
//                        Toast.LENGTH_SHORT
//                ).show();
            }
        }
    }

}
