package com.darklycoder.xskin.core;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.SparseArray;

import com.darklycoder.xskin.core.listener.ILoaderListener;
import com.darklycoder.xskin.core.listener.ISkinLoader;
import com.darklycoder.xskin.core.listener.ISkinUpdate;
import com.darklycoder.xskin.core.loader.LoadSdSkinTask;
import com.darklycoder.xskin.core.util.SkinLog;

import java.lang.ref.WeakReference;

/**
 * 皮肤管理
 */
public class SkinManager implements ISkinLoader {

    public WeakReference<Context> mContext;
    private SparseArray<WeakReference<ISkinUpdate>> skinObservers;

    public String skinPackageName;
    public Resources mResources;
    public String skinPath;
    public boolean isDefaultSkin = false;
    public int baseInch = 0;

    private SkinManager() {
    }

    private static class SkinManagerInner {
        private static SkinManager mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SkinManagerInner.mInstance;
    }

    /**
     * 判断是否正在使用外部皮肤
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    public String getSkinPath() {
        return skinPath;
    }

    public String getSkinPackageName() {
        return skinPackageName;
    }

    public Resources getResources() {
        return null == mResources ? mContext.get().getResources() : mResources;
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
        LoadSdSkinTask skinTask = new LoadSdSkinTask(callback);
        skinTask.execute(skinPath);
    }

    public void setResources(boolean isDefault, Resources resources) {
        this.isDefaultSkin = isDefault;
        this.mResources = resources;
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

        ISkinUpdate mISkinUpdate;

        int size = skinObservers.size();
        for (int i = 0; i < size; i++) {
            mISkinUpdate = skinObservers.valueAt(i).get();

            if (null != mISkinUpdate) {
                mISkinUpdate.onThemeUpdate();
            }
        }
    }

    /***********动态获取资源************/

    public int getColor(int resId) {
        int originColor = -1;
        try {
            originColor = mContext.get().getResources().getColor(resId);
            if (null == mResources || isDefaultSkin) {
                return originColor;
            }

            String resName = mContext.get().getResources().getResourceEntryName(resId);

            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            return mResources.getColor(trueResId);

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        if (-1 != originColor) {
            return originColor;
        }

        return Color.TRANSPARENT;
    }

    public Drawable getDrawable(int resId) {
        Drawable originDrawable = null;
        try {
            originDrawable = mContext.get().getResources().getDrawable(resId);
            if (null == mResources || isDefaultSkin) {
                return originDrawable;
            }

            String resName = mContext.get().getResources().getResourceEntryName(resId);
            int trueResId = mResources.getIdentifier(resName, "drawable", skinPackageName);

            return mResources.getDrawable(trueResId);

        } catch (NotFoundException e) {
            SkinLog.e(e.getMessage());
        }

        if (null != originDrawable) {
            return originDrawable;
        }

        return new ColorDrawable(Color.TRANSPARENT);
    }

    public int getDrawableResId(int resId) {
        return getResId(resId, "drawable");
    }

    public int getResId(int resId, String defType) {
        try {
            String resName = mContext.get().getResources().getResourceEntryName(resId);
            return mResources.getIdentifier(resName, defType, skinPackageName);

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        return resId;
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。</br>
     * 无皮肤包资源返回默认主题颜色
     */
    public ColorStateList convertToColorStateList(int resId) {
        SkinLog.e("attr", "convertToColorStateList");
        boolean isExtendSkin = true;

        if (mResources == null || isDefaultSkin) {
            isExtendSkin = false;
        }

        String resName = mContext.get().getResources().getResourceEntryName(resId);
        SkinLog.e("attr", "resName = " + resName);
        if (isExtendSkin) {
            int trueResId = mResources.getIdentifier(resName, "color", skinPackageName);
            SkinLog.e("attr", "convertToColorStateList(color." + resName + "[0x" + Integer.toHexString(resId) + "]) = 0x" + Integer.toHexString(trueResId));
            ColorStateList trueColorList = null;
            if (trueResId == 0) { // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    ColorStateList originColorList = mContext.get().getResources().getColorStateList(resId);
                    return originColorList;
                } catch (NotFoundException e) {
                    SkinLog.e("resName = " + resName + " NotFoundException");
                    e.printStackTrace();
                }
            } else {
                try {
                    trueColorList = mResources.getColorStateList(trueResId);
                    SkinLog.e("attr", "trueColorList = " + trueColorList);
                    return trueColorList;
                } catch (NotFoundException e) {
                    SkinLog.e("resName = " + resName + " NotFoundException");
                    e.printStackTrace();
                }
            }
        } else {
            try {
                ColorStateList originColorList = mContext.get().getResources().getColorStateList(resId);
                return originColorList;
            } catch (NotFoundException e) {
                SkinLog.e("resName = " + resName + " NotFoundException");
                e.printStackTrace();
            }

        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{mContext.get().getResources().getColor(resId)});
    }

}