package com.darklycoder.xskin.core.attr.parser;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.util.SkinLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 组件属性解析
 */
public final class SkinAttributeParser extends BaseSkinAttributeParser {

    public static List<SkinAttr> parseSkinAttr(Context context, AttributeSet attrs, View view, String[] specifiedAttrs) {

        HashMap<String, SkinAttr> viewAttrs = new HashMap<>();

        // 先处理style类型, 避免布局中定义的属性被style中定义的属性覆盖
        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);

            // 处理控件中设置的style属性
            if ("style".equals(attrName)) {
                SkinStyleParser.parseStyle(context, viewAttrs, attrs, specifiedAttrs);
                break;
            }
        }

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);

            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            if (!isAttrSpecified(attrName, specifiedAttrs)) {
                continue;
            }

            if (!attrValue.startsWith("@")) {
                continue;
            }

            try {
                int id = Integer.parseInt(attrValue.substring(1));
                String entryName = context.getResources().getResourceEntryName(id);
                String typeName = context.getResources().getResourceTypeName(id);

                SkinAttr skinAttr = AttrFactory.get(attrName, id, entryName, typeName);
                if (null != skinAttr) {
                    viewAttrs.put(attrName, skinAttr);
                }

            } catch (Exception e) {
                SkinLog.e(e.getMessage());
            }
        }

        return new ArrayList<>(viewAttrs.values());
    }

}
