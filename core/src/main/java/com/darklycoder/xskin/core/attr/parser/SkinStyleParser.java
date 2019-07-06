package com.darklycoder.xskin.core.attr.parser;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.attr.base.SkinStyleAttr;
import com.darklycoder.xskin.core.util.SkinLog;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * style属性解析
 */
final class SkinStyleParser extends BaseSkinAttributeParser {

    private static LinkedList<SkinStyleAttr> styleAttrsList = new LinkedList<>();

    static {
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.BACKGROUND, android.R.attr.background));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.TEXT_COLOR, android.R.attr.textColor));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.TEXT_COLOR_HINT, android.R.attr.textColorHint));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.TEXT_COLOR_HINT_LIGHT, android.R.attr.textColorHighlight));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.TEXT_CURSOR_DRAWABLE, android.R.attr.textCursorDrawable));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.LIST_SELECTOR, android.R.attr.listSelector));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.SRC, android.R.attr.src));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_LEFT, android.R.attr.drawableLeft));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_TOP, android.R.attr.drawableTop));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_RIGHT, android.R.attr.drawableRight));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_BOTTOM, android.R.attr.drawableBottom));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_START, android.R.attr.drawableStart));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.DRAWABLE_END, android.R.attr.drawableEnd));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.PROGRESS_DRAWABLE, android.R.attr.progressDrawable));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.INDETERMINATE_DRAWABLE, android.R.attr.indeterminateDrawable));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.THUMB, android.R.attr.thumb));
        styleAttrsList.add(new SkinStyleAttr(AttrFactory.Attr.BUTTON, android.R.attr.button));
    }

    static void parseStyle(Context context, HashMap<String, SkinAttr> viewAttrs, AttributeSet attrs, String[] specifiedAttrs) {
        for (SkinStyleAttr styleAttr : styleAttrsList) {
            int[] attrsList = new int[]{styleAttr.id};

            TypedArray ta = context.obtainStyledAttributes(attrs, attrsList);
            if (null == ta) {
                continue;
            }

            if (ta.getIndexCount() <= 0) {
                continue;
            }

            try {
                int resId = ta.getResourceId(ta.getIndex(0), -1);
                if (resId == -1) {
                    continue;
                }

                String attrName = styleAttr.attr.getName();
                if (!isAttrSpecified(attrName, specifiedAttrs)) {
                    continue;
                }

                parseSkinAttr(context, viewAttrs, resId, attrName);

            } catch (Exception e) {
                SkinLog.e(e.getMessage());

            } finally {
                try {
                    ta.recycle();
                } catch (Exception ignored) {
                }
            }
        }
    }

    private static void parseSkinAttr(Context context, HashMap<String, SkinAttr> viewAttrs, int resId, String attrName) {
        try {
            String entryName = context.getResources().getResourceEntryName(resId);
            String typeName = context.getResources().getResourceTypeName(resId);

            SkinAttr skinAttr = AttrFactory.get(attrName, resId, entryName, typeName);
            if (null != skinAttr) {
                viewAttrs.put(attrName, skinAttr);
            }

        } catch (Exception e) {
            SkinLog.e(e.getMessage());
        }
    }

}
