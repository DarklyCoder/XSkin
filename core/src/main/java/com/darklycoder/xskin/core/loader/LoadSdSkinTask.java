package com.darklycoder.xskin.core.loader;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.DisplayMetrics;

import com.darklycoder.xskin.core.SkinManager;
import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.listener.ILoaderListener;
import com.darklycoder.xskin.core.util.SkinLog;

import java.io.File;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

/**
 * 加载SD外置皮肤
 */
public class LoadSdSkinTask extends AsyncTask<String, Void, Resources> {

    private WeakReference<ILoaderListener> callback;

    public LoadSdSkinTask(ILoaderListener callback) {
        this.callback = new WeakReference<>(callback);
    }

    @Override
    protected void onPreExecute() {
        SkinLog.i("开始加载SD卡 Skin！");

        if (null != callback && null != callback.get()) {
            callback.get().onStart();
        }
    }

    @Override
    protected void onPostExecute(Resources result) {
        SkinLog.i("Skin 加载完毕：" + (null == result));

        if (null != result) {
            // 加载成功
            SkinManager.getInstance().setResources(false, result);
            SkinManager.getInstance().notifySkinUpdate();

            if (null != callback && null != callback.get()) {
                callback.get().onSuccess();
            }

            return;
        }

        // 加载失败
        if (null != callback && null != callback.get()) {
            callback.get().onFailed();
        }
    }

    @Override
    protected Resources doInBackground(String... params) {
        if (null == params || params.length <= 0) {
            return null;
        }

        String skinPkgPath = params[0];
        try {
            if (TextUtils.isEmpty(skinPkgPath) || TextUtils.equals(skinPkgPath, SkinConfig.DEFAULT_SKIN)) {
                SkinManager.getInstance().isDefaultSkin = true;
                return SkinManager.getInstance().mContext.get().getResources();
            }

            File file = new File(skinPkgPath);
            if (!file.exists()) {
                return null;
            }

            PackageManager pm = SkinManager.getInstance().mContext.get().getPackageManager();
            PackageInfo pmInfo = pm.getPackageArchiveInfo(skinPkgPath, PackageManager.GET_ACTIVITIES);

            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = assetManager.getClass().getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, skinPkgPath);

            Resources superRes = SkinManager.getInstance().mContext.get().getResources();
            Resources skinResource = new Resources(assetManager, superRes.getDisplayMetrics(), superRes.getConfiguration());

            // 判断是否需要修改分辨率
            if (SkinManager.getInstance().baseInch > 0) {
                setCustomDensity(skinResource.getDisplayMetrics(), skinResource);
            }

            SkinManager.getInstance().skinPackageName = pmInfo.packageName;
            SkinManager.getInstance().skinPath = skinPkgPath;
            SkinManager.getInstance().isDefaultSkin = false;

            return skinResource;

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        return null;
    }

    private void setCustomDensity(DisplayMetrics metrics, Resources resources) {
        try {
            float density = metrics.density;
            float scaledDensity = metrics.scaledDensity;
            float targetDensity = metrics.widthPixels / SkinManager.getInstance().baseInch;
            float targetScaledDensity = targetDensity * (scaledDensity / density);
            int targetDensityDpi = (int) (160f * targetDensity);

            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            displayMetrics.density = targetDensity;
            displayMetrics.scaledDensity = targetScaledDensity;
            displayMetrics.densityDpi = targetDensityDpi;

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }
    }
}
