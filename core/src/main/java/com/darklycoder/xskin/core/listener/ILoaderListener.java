package com.darklycoder.xskin.core.listener;

/**
 * 加载皮肤回调
 */
public interface ILoaderListener {

    /**
     * 开始加载
     */
    void onStart();

    /**
     * 加载成功
     */
    void onSuccess();

    /**
     * 加载失败
     */
    void onFailed();

}
