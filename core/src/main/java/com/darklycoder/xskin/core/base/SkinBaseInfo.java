package com.darklycoder.xskin.core.base;

import android.content.res.Resources;

/***
 * 皮肤基础信息
 */
public class SkinBaseInfo {

    /**
     * 皮肤插件包名
     */
    public String skinPackageName;
    /**
     * 皮肤插件对应的资源
     */
    public Resources resources = null;
    /**
     * 是否是默认皮肤
     */
    public boolean isDefaultSkin = true;

    /**
     * 判断是否正在使用外部皮肤
     */
    public boolean isExternalSkin() {
        return !isDefaultSkin && null != resources;
    }

}
