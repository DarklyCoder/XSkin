package com.darklycoder.xskin.core.listener;

/**
 * 皮肤更新回调
 */
public interface ISkinLoader {

    /**
     * 加入监听
     */
    void attach(ISkinUpdate observer);

    /**
     * 取消监听
     */
    void detach(ISkinUpdate observer);

    /**
     * 通知回调
     */
    void notifySkinUpdate();
}
