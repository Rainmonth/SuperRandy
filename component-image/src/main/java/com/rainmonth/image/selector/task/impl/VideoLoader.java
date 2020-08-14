package com.rainmonth.image.selector.task.impl;

import android.content.ContentResolver;
import android.content.Context;

import com.rainmonth.image.selector.task.IMediaLoader;

/**
 * @author RandyZhang
 * @date 2020/8/12 3:38 PM
 */
public class VideoLoader implements IMediaLoader {
    @Override
    public void load(ContentResolver cr) {

    }

    @Override
    public void load(Context context) {

    }

    @Override
    public String[] buildQueryArgs() {
        return null;
    }
}
