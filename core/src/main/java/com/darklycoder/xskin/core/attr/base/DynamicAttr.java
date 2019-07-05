package com.darklycoder.xskin.core.attr.base;

/**
 * 动态添加需要解析的属性
 */
public class DynamicAttr {

    /**
     * 属性名， {@link AttrFactory} 里支持的属性
     */
    public String attrName;
    /**
     * 对应的索引值，例如：R.color.color_dark
     */
    public int refResId;

    public DynamicAttr(String attrName, int refResId) {
        this.attrName = attrName;
        this.refResId = refResId;
    }

}
