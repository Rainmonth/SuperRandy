package com.rainmonth.player.gsy.cache_proxy.sourcestorage;


import com.rainmonth.player.gsy.cache_proxy.SourceInfo;

/**
 * Storage for {@link SourceInfo}.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
public interface SourceInfoStorage {

    SourceInfo get(String url);

    void put(String url, SourceInfo sourceInfo);

    void release();
}
