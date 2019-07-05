package com.darklycoder.xskin.core.listener;

/**
 * 皮肤更新回调，一般 Activity 或者 Fragment 实现此回调
 */
public interface ISkinUpdate {

    int getKey();

    void onThemeUpdate();
}
