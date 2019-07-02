package com.darklycoder.xskin.core.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.listener.ILoaderListener;
import com.darklycoder.xskin.core.listener.ISkinLoader;
import com.darklycoder.xskin.core.listener.ISkinUpdate;
import com.darklycoder.xskin.core.util.SkinLog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class SkinManager implements ISkinLoader {

    private List<ISkinUpdate> skinObservers;
    private WeakReference<Context> mContext;
    private String skinPackageName;
    private Resources mResources;
    private String skinPath;
    private boolean isDefaultSkin = false;

    private SkinManager() {
    }

    private static class SkinManagerInner {
        private static SkinManager mInstance = new SkinManager();
    }

    public static SkinManager getInstance() {
        return SkinManagerInner.mInstance;
    }

    /**
     * whether the skin being used is from external .skin file
     *
     * @return is external skin = true
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && mResources != null;
    }

    /**
     * get current skin path
     *
     * @return current skin path
     */
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
    public void init(Context context, String skinPackagePath) {
        SkinLog.i("init (" + skinPackagePath + ")");
        mContext = new WeakReference<>(context.getApplicationContext());

        if (TextUtils.isEmpty(skinPackagePath) || TextUtils.equals(skinPackagePath, SkinConfig.DEFAULT_SKIN)) {
            // 使用内置皮肤
            isDefaultSkin = true;
            mResources = mContext.get().getResources();
            return;
        }

        load(skinPackagePath);
    }

    /**
     * 加载皮肤
     */
    public void load(String skinPackagePath) {
        load(skinPackagePath, null);
    }

    /**
     * 加载皮肤
     */
    public void load(String skinPackagePath, final ILoaderListener callback) {
        SkinLog.i("load (" + skinPackagePath + ")");

        MyAsyncTask asyncTask = new MyAsyncTask(callback);
        asyncTask.execute(skinPackagePath);
    }

    private static class MyAsyncTask extends AsyncTask<String, Void, Resources> {

        private WeakReference<ILoaderListener> callback;

        MyAsyncTask(ILoaderListener callback) {
            this.callback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            SkinLog.i("onPreExecute()");
            if (null != callback && null != callback.get()) {
                callback.get().onStart();
            }
        }

        @Override
        protected void onPostExecute(Resources result) {
            SkinLog.i("onPostExecute isDefaultSkin = " + (null == result));

            getInstance().mResources = result;
            if (null != result) {
                getInstance().notifySkinUpdate();

                if (null != callback && null != callback.get()) {
                    callback.get().onSuccess();
                }
                return;
            }

            getInstance().isDefaultSkin = true;
            if (null != callback && null != callback.get()) {
                callback.get().onFailed();
            }
        }

        @Override
        protected Resources doInBackground(String... params) {
            SkinLog.i("doInBackground()");

            if (null == params || params.length <= 0) {
                return null;
            }

            String skinPkgPath = params[0];

            try {
                if (TextUtils.isEmpty(skinPkgPath) || TextUtils.equals(skinPkgPath, SkinConfig.DEFAULT_SKIN)) {
                    getInstance().isDefaultSkin = true;
                    return getInstance().mContext.get().getResources();
                }

                File file = new File(skinPkgPath);
                if (!file.exists()) {
                    return null;
                }

                PackageManager pm = getInstance().mContext.get().getPackageManager();
                PackageInfo pmInfo = pm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);

                AssetManager assetManager = AssetManager.class.newInstance();
                Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
                addAssetPath.invoke(assetManager, skinPkgPath);

                Resources superRes = getInstance().mContext.get().getResources();
                Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

                getInstance().skinPackageName = pmInfo.packageName;
                getInstance().skinPath = skinPkgPath;
                getInstance().isDefaultSkin = false;

                return skinResource;

            } catch (Exception e) {
                SkinLog.e(e.getMessage());
            }

            return null;
        }
    }

    @Override
    public void attach(ISkinUpdate observer) {
        if (skinObservers == null) {
            skinObservers = new ArrayList<>();
        }

        if (!skinObservers.contains(observer)) {
            skinObservers.add(observer);
        }
    }

    @Override
    public void detach(ISkinUpdate observer) {
        if (skinObservers == null) return;

        if (skinObservers.contains(observer)) {
            skinObservers.remove(observer);
        }
    }

    @Override
    public void notifySkinUpdate() {
        if (skinObservers == null) return;

        for (ISkinUpdate observer : skinObservers) {
            observer.onThemeUpdate();
        }
    }

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