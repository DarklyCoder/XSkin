package com.darklycoder.xskin.core.loader;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

import com.darklycoder.xskin.core.base.SkinBaseInfo;
import com.darklycoder.xskin.core.listener.ILoaderListener;
import com.darklycoder.xskin.core.listener.ISkinLoader;
import com.darklycoder.xskin.core.listener.ISkinUpdate;
import com.darklycoder.xskin.core.util.SkinLog;
import com.darklycoder.xskin.core.util.SkinResourcesUtils;

import java.lang.ref.WeakReference;

/**
 * 皮肤管理
 */
public class SkinManager implements ISkinLoader {

    private WeakReference<Context> mContext;
    private SparseArray<WeakReference<ISkinUpdate>> skinObservers;
    private int baseInch = 0; // 设计稿尺寸(dp)
    private SkinBaseInfo mSkinInfo;

    private SkinManager() {
    }

    private static class SkinManagerInner {
        private static SkinManager mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SkinManagerInner.mInstance;
    }

    /**
     * 初始化
     */
    public void init(Context context) {
        init(context, 0);
    }

    /**
     * 初始化
     *
     * @param baseInch 设计稿尺寸(dp)
     */
    public void init(Context context, int baseInch) {
        this.mContext = new WeakReference<>(context.getApplicationContext());
        this.baseInch = baseInch;
        this.mSkinInfo = new SkinBaseInfo();
    }

    /**
     * 加载SD外置皮肤
     *
     * @param skinPath 皮肤包路径
     */
    public void load(String skinPath) {
        load(skinPath, null);
    }

    /**
     * 加载SD外置皮肤
     */
    public void load(String skinPath, ILoaderListener callback) {
        LoadSdSkinTask skinTask = new LoadSdSkinTask(callback, mContext);
        skinTask.execute(skinPath);
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (null == skinObservers) {
            skinObservers = new SparseArray<>();
        }

        if (null == observer) {
            return;
        }

        skinObservers.put(observer.getKey(), new WeakReference<>(observer));
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (null == skinObservers) return;
        if (null == observer) return;

        skinObservers.remove(observer.getKey());
    }

    @Override
    public void notifySkinUpdate() {
        if (null == skinObservers) return;

        try {
            ISkinUpdate mISkinUpdate;

            int size = skinObservers.size();
            for (int i = 0; i < size; i++) {
                mISkinUpdate = skinObservers.valueAt(i).get();

                if (null != mISkinUpdate) {
                    mISkinUpdate.onThemeUpdate();
                }
            }

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }
    }

    /**
     * 判断是否正在使用外部皮肤
     */
    public boolean isExternalSkin() {
        return null != mSkinInfo && mSkinInfo.isExternalSkin();
    }

    /**
     * 获取设计稿尺寸
     */
    public int getBaseInch() {
        return baseInch;
    }

    /**
     * 设置皮肤基础信息
     */
    public void setSkinBaseInfo(SkinBaseInfo info) {
        if (null == info) {
            return;
        }

        this.mSkinInfo = info;

        notifySkinUpdate();
    }

    /**
     * 获取Resources
     */
    public Resources getResoures() {
        return isExternalSkin() ? mSkinInfo.resources : mContext.get().getResources();
    }

    /***********动态获取资源************/

    public int getColor(int resId) {
        return SkinResourcesUtils.getColor(mContext.get(), resId, mSkinInfo);
    }

    public ColorStateList getColorStateList(int resId) {
        return SkinResourcesUtils.getColorStateList(mContext.get(), resId, mSkinInfo);
    }

    public Drawable getDrawable(int resId) {
        return SkinResourcesUtils.getDrawable(mContext.get(), resId, mSkinInfo);
    }

    public int getDrawableResId(int resId) {
        return getResId(resId, "drawable");
    }

    public int getResId(int resId, String defType) {
        return SkinResourcesUtils.getResId(mContext.get(), resId, defType, mSkinInfo);
    }

}
