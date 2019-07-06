package com.darklycoder.xskin.core.attr.parser;

public abstract class BaseSkinAttributeParser {

    /**
     * 当前属性是否在xml中使用 skin:attrs="textColor|background" 方式来指定，指定了，只替换指定属性，未指定替换全部
     *
     * @param attrName       当前属性
     * @param specifiedAttrs 指定的属性
     * @return true 表示需要解析
     */
    static boolean isAttrSpecified(String attrName, String[] specifiedAttrs) {
        if (specifiedAttrs == null || specifiedAttrs.length <= 0) {
            return true;
        }

        for (String a : specifiedAttrs) {
            if (a != null && (a.trim()).equals(attrName)) {
                return true;
            }
        }

        return false;
    }

}
