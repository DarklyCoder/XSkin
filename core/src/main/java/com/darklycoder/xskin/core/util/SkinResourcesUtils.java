package com.darklycoder.xskin.core.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.darklycoder.xskin.core.base.SkinBaseInfo;

/**
 * 皮肤资源工具
 */
public class SkinResourcesUtils {

    /**
     * 获取颜色
     */
    public static int getColor(Context context, int resId, SkinBaseInfo skinInfo) {
        int originColor = -1;
        try {
            originColor = context.getResources().getColor(resId);
            if (!skinInfo.isExternalSkin()) {
                return originColor;
            }

            String resName = context.getResources().getResourceEntryName(resId);
            int trueResId = skinInfo.resources.getIdentifier(resName, "color", skinInfo.skinPackageName);
            return skinInfo.resources.getColor(trueResId);

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        if (-1 != originColor) {
            return originColor;
        }

        return Color.TRANSPARENT;
    }

    /**
     * 获取Drawable
     */
    public static Drawable getDrawable(Context context, int resId, SkinBaseInfo skinInfo) {
        Drawable originDrawable = null;
        try {
            originDrawable = context.getResources().getDrawable(resId);
            if (!skinInfo.isExternalSkin()) {
                return originDrawable;
            }

            String resName = context.getResources().getResourceEntryName(resId);
            int trueResId = skinInfo.resources.getIdentifier(resName, "drawable", skinInfo.skinPackageName);
            if (trueResId == 0) {
                trueResId = skinInfo.resources.getIdentifier(resName, "mipmap", skinInfo.skinPackageName);
            }

            return skinInfo.resources.getDrawable(trueResId);

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        if (null != originDrawable) {
            return originDrawable;
        }

        return new ColorDrawable(Color.TRANSPARENT);
    }

    public static int getResId(Context context, int resId, String defType, SkinBaseInfo skinInfo) {
        try {
            String resName = context.getResources().getResourceEntryName(resId);
            return skinInfo.resources.getIdentifier(resName, defType, skinInfo.skinPackageName);

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }

        return resId;
    }

    /**
     * 加载指定资源颜色drawable,转化为ColorStateList，保证selector类型的Color也能被转换。</br>
     * 无皮肤包资源返回默认主题颜色
     */
    public static ColorStateList getColorStateList(Context context, int resId, SkinBaseInfo skinInfo) {
        boolean isExtendSkin = skinInfo.isExternalSkin();
        String resName = context.getResources().getResourceEntryName(resId);

        if (isExtendSkin) {
            int trueResId = skinInfo.resources.getIdentifier(resName, "color", skinInfo.skinPackageName);
            ColorStateList trueColorList;
            if (trueResId == 0) {
                // 如果皮肤包没有复写该资源，但是需要判断是否是ColorStateList
                try {
                    return context.getResources().getColorStateList(resId);

                } catch (Resources.NotFoundException e) {
                    SkinLog.e("resName = " + resName + " NotFoundException");
                }

            } else {
                try {
                    trueColorList = skinInfo.resources.getColorStateList(trueResId);
                    return trueColorList;

                } catch (Resources.NotFoundException e) {
                    SkinLog.e("resName = " + resName + " NotFoundException");
                }
            }

        } else {
            try {
                return context.getResources().getColorStateList(resId);

            } catch (Resources.NotFoundException e) {
                SkinLog.e("resName = " + resName + " NotFoundException");
            }
        }

        int[][] states = new int[1][1];
        return new ColorStateList(states, new int[]{context.getResources().getColor(resId)});
    }

}
