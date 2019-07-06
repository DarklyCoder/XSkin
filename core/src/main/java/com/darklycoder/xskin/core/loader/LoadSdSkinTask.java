package com.darklycoder.xskin.core.loader;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.darklycoder.xskin.core.base.SkinBaseInfo;
import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.listener.ILoaderListener;
import com.darklycoder.xskin.core.util.SkinLog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * 加载SD外置皮肤
 */
public class LoadSdSkinTask extends AsyncTask<String, Void, SkinBaseInfo> {

    private WeakReference<ILoaderListener> callback;
    private WeakReference<Context> mContext;

    public LoadSdSkinTask(ILoaderListener callback, WeakReference<Context> weakReference) {
        this.callback = new WeakReference<>(callback);
        this.mContext = weakReference;
    }

    @Override
    protected void onPreExecute() {
        SkinLog.i("开始加载SD卡 Skin！");

        if (null != callback && null != callback.get()) {
            callback.get().onStart();
        }
    }

    @Override
    protected void onPostExecute(SkinBaseInfo result) {
        if (null == result) {
            SkinLog.i("Skin 加载失败！");

            if (null != callback && null != callback.get()) {
                callback.get().onFailed();
            }

            return;
        }

        SkinLog.i("Skin 加载成功！");

        SkinManager.getInstance().setSkinBaseInfo(result);
        if (null != callback && null != callback.get()) {
            callback.get().onSuccess();
        }
    }

    @Override
    protected SkinBaseInfo doInBackground(String... params) {
        try {
            if (null == params || params.length <= 0) {
                SkinLog.i("加载默认皮肤！");
                return new SkinBaseInfo();
            }

            String skinPkgPath = params[0];
            if (TextUtils.isEmpty(skinPkgPath) || TextUtils.equals(skinPkgPath, SkinConfig.DEFAULT_SKIN)) {
                // 加载默认皮肤
                SkinLog.i("加载默认皮肤！");
                return new SkinBaseInfo();
            }

            File file = new File(skinPkgPath);
            if (!file.exists()) {
                SkinLog.i(skinPkgPath + " 不存在！");
                return null;
            }

            SkinLog.i("加载：" + skinPkgPath);
            PackageManager pm = mContext.get().getPackageManager();
            PackageInfo pmInfo = pm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);

            Resources superRes = mContext.get().getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

            // 判断是否需要修改分辨率
            if (SkinManager.getInstance().getBaseInch() > 0) {
                setCustomDensity(skinResource.getDisplayMetrics(), skinResource);
            }

            SkinBaseInfo skinBaseInfo = new SkinBaseInfo();
            skinBaseInfo.skinPackageName = pmInfo.packageName;
            skinBaseInfo.resources = skinResource;
            skinBaseInfo.isDefaultSkin = false;

            return skinBaseInfo;

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        return null;
    }

    private void setCustomDensity(DisplayMetrics metrics, Resources resources) {
        try {
            float density = metrics.density;
            float scaledDensity = metrics.scaledDensity;
            float targetDensity = metrics.widthPixels * 1F / SkinManager.getInstance().getBaseInch();
            float targetScaledDensity = targetDensity * (scaledDensity / density);
            int targetDensityDpi = (int) (160F * targetDensity);

            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            displayMetrics.density = targetDensity;
            displayMetrics.scaledDensity = targetScaledDensity;
            displayMetrics.densityDpi = targetDensityDpi;

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }
    }

}
