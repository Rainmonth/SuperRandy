package com.rainmonth.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author RandyZhang
 * @date 2020/11/17 3:48 PM
 */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = 23, manifest = Config.NONE)
public class PathUtilsTest {

    @Test
    public void getRootPath() {
        System.out.println(PathUtils.getRootPath());
    }

    @Test
    public void getDataPath() {
        System.out.println(PathUtils.getDataPath());
    }

    @Test
    public void getDownloadCachePath() {
        System.out.println(PathUtils.getDownloadCachePath());
    }

    @Test
    public void getInternalAppDataPath() {
        System.out.println(PathUtils.getInternalAppDataPath());
    }

    @Test
    public void getInternalAppCodeCacheDir() {
        System.out.println(PathUtils.getInternalAppCodeCacheDir());
    }

    @Test
    public void getInternalAppCachePath() {
        System.out.println(PathUtils.getInternalAppCachePath());
    }

    @Test
    public void getInternalAppDbsPath() {
        System.out.println(PathUtils.getInternalAppDbsPath());
    }

    @Test
    public void getInternalAppDbPath() {
        System.out.println(PathUtils.getInternalAppDbPath("test"));
    }

    @Test
    public void getInternalAppFilesPath() {
        System.out.println(PathUtils.getInternalAppFilesPath());
    }

    @Test
    public void getInternalAppSpPath() {
        System.out.println(PathUtils.getInternalAppSpPath());
    }

    @Test
    public void getInternalAppNoBackupFilesPath() {
        System.out.println(PathUtils.getInternalAppNoBackupFilesPath());
    }

    @Test
    public void getExternalStoragePath() {
        System.out.println(PathUtils.getExternalStoragePath());
        assertEquals("1", PathUtils.getExternalStoragePath());
    }

    @Test
    public void getExternalMusicPath() {
        System.out.println(PathUtils.getExternalMusicPath());
    }

    @Test
    public void getExternalPodcastsPath() {
        System.out.println(PathUtils.getExternalPodcastsPath());
    }

    @Test
    public void getExternalRingtonesPath() {
        System.out.println(PathUtils.getExternalRingtonesPath());
    }

    @Test
    public void getExternalAlarmsPath() {
        System.out.println(PathUtils.getExternalAlarmsPath());

    }

    @Test
    public void getExternalNotificationsPath() {
    }

    @Test
    public void getExternalPicturesPath() {
    }

    @Test
    public void getExternalMoviesPath() {
    }

    @Test
    public void getExternalDownloadsPath() {
    }

    @Test
    public void getExternalDcimPath() {
    }

    @Test
    public void getExternalDocumentsPath() {
    }

    @Test
    public void getExternalAppDataPath() {
    }

    @Test
    public void getExternalAppCachePath() {
    }

    @Test
    public void getExternalAppFilesPath() {
    }

    @Test
    public void getExternalAppMusicPath() {
    }

    @Test
    public void getExternalAppPodcastsPath() {
    }

    @Test
    public void getExternalAppRingtonesPath() {
    }

    @Test
    public void getExternalAppAlarmsPath() {
    }

    @Test
    public void getExternalAppNotificationsPath() {
    }

    @Test
    public void getExternalAppPicturesPath() {
    }

    @Test
    public void getExternalAppMoviesPath() {
    }

    @Test
    public void getExternalAppDownloadPath() {
    }

    @Test
    public void getExternalAppDcimPath() {
    }

    @Test
    public void getExternalAppDocumentsPath() {
    }

    @Test
    public void getExternalAppObbPath() {
    }

    @Test
    public void getRootPathExternalFirst() {
    }

    @Test
    public void getAppDataPathExternalFirst() {
    }

    @Test
    public void getFilesPathExternalFirst() {
    }

    @Test
    public void getCachePathExternalFirst() {
    }
}