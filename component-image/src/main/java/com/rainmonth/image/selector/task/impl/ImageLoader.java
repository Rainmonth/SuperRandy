package com.rainmonth.image.selector.task.impl;

import android.content.ContentResolver;
import android.content.Context;

import com.rainmonth.image.selector.task.IMediaLoader;
import com.rainmonth.image.selector.task.callback.IMediaTaskCallback;

/**
 * @author RandyZhang
 * @date 2020/8/12 1:57 PM
 */
public class ImageLoader implements IMediaLoader {
    @Override
    public void load(ContentResolver cr, IMediaTaskCallback callback) {

    }

    @Override
    public void load(Context context, IMediaTaskCallback callback) {

    }

    @Override
    public String[] buildQueryArgs() {
        return null;
    }
}
