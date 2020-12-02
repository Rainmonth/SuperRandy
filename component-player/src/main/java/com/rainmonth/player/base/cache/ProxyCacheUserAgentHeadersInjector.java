package com.rainmonth.player.base.cache;

import com.rainmonth.player.cache_proxy.headers.HeaderInjector;
import com.rainmonth.player.utils.Debugger;

import java.util.HashMap;
import java.util.Map;

/**
 for android video cache header
 */
public class ProxyCacheUserAgentHeadersInjector implements HeaderInjector {

    public final static Map<String, String> mMapHeadData = new HashMap<>();

    @Override
    public Map<String, String> addHeaders(String url) {
        Debugger.printfLog("****** proxy addHeaders ****** " + mMapHeadData.size());
        return mMapHeadData;
    }
}