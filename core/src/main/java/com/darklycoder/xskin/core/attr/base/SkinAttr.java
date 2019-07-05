package com.darklycoder.xskin.core.attr.base;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * 皮肤属性
 */
public abstract class SkinAttr {

    /**
     * 属性的名称，例如: "color" or "src"
     */
    public String attrName;
    /**
     * 属性值对应的资源索引id
     */
    public int attrValueRefId;
    /**
     * 属性对应的值，例如: "color_dark"
     */
    public String attrValueRefName;
    /**
     * 属性对应的类型
     */
    public AttrFactory.ResType attrValueType;

    /**
     * 应用皮肤更换
     */
    public abstract void apply(View view);

    @NonNull
    @Override
    public String toString() {
        return "SkinAttr [ attrName=" + attrName + ","
                + "  attrValueRefId=" + attrValueRefId + ","
                + "  attrValueRefName=" + attrValueRefName + ","
                + "  attrValueTypeName=" + attrValueType
                + " ]";
    }

}
