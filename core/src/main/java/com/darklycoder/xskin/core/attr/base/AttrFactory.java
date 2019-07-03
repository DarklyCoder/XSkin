package com.darklycoder.xskin.core.attr.base;

import android.text.TextUtils;

import com.darklycoder.xskin.core.attr.BackgroundAttr;
import com.darklycoder.xskin.core.attr.ButtonAttr;
import com.darklycoder.xskin.core.attr.DividerAttr;
import com.darklycoder.xskin.core.attr.DrawableBottomAttr;
import com.darklycoder.xskin.core.attr.DrawableEndAttr;
import com.darklycoder.xskin.core.attr.DrawableLeftAttr;
import com.darklycoder.xskin.core.attr.DrawableRightAttr;
import com.darklycoder.xskin.core.attr.DrawableStartAttr;
import com.darklycoder.xskin.core.attr.DrawableTopAttr;
import com.darklycoder.xskin.core.attr.IndeterminateDrawableAttr;
import com.darklycoder.xskin.core.attr.ListSelectorAttr;
import com.darklycoder.xskin.core.attr.ProgressDrawableAttr;
import com.darklycoder.xskin.core.attr.SrcAttr;
import com.darklycoder.xskin.core.attr.TextColorAttr;
import com.darklycoder.xskin.core.attr.TextColorHighlightAttr;
import com.darklycoder.xskin.core.attr.TextColorHintAttr;
import com.darklycoder.xskin.core.attr.TextCursorDrawableAttr;
import com.darklycoder.xskin.core.attr.ThumbAttr;

import java.util.HashMap;

public class AttrFactory {

    // 额外拓展支持的属性
    private static HashMap<String, SkinAttr> extAttr = new HashMap<>();

    /**
     * 内置支持的属性
     */
    public enum Attr {
        BACKGROUND("background"),
        TEXT_COLOR("textColor"),
        TEXT_COLOR_HINT("textColorHint"),
        TEXT_COLOR_HINT_LIGHT("textColorHighlight"),
        TEXT_CURSOR_DRAWABLE("textCursorDrawable"),
        LIST_SELECTOR("listSelector"),
        DIVIDER("divider"),
        SRC("src"),
        DRAWABLE_LEFT("drawableLeft"),
        DRAWABLE_TOP("drawableTop"),
        DRAWABLE_RIGHT("drawableRight"),
        DRAWABLE_BOTTOM("drawableBottom"),
        DRAWABLE_START("drawableStart"),
        DRAWABLE_END("drawableEnd"),
        PROGRESS_DRAWABLE("progressDrawable"),
        INDETERMINATE_DRAWABLE("indeterminateDrawable"),
        THUMB("thumb"),
        BUTTON("button"),
        NONE("not support"); // 不支持的Attr

        private final String name;

        Attr(String n) {
            name = n;
        }

        public static Attr byName(String name) {
            for (Attr attr : Attr.values()) {
                if (TextUtils.equals(attr.name, name)) {
                    return attr;
                }
            }
            return NONE;
        }
    }

    /**
     * 资源类型
     */
    public enum ResType {
        COLOR("color"),
        DRAWABLE("drawable"),
        DIMEN("dimen"),
        STRING("string"),
        ID("id"),
        NONE("not support"); // 不支持的ResType

        private final String type;

        ResType(String t) {
            type = t;
        }

        public static ResType byName(String type) {
            for (ResType resType : ResType.values()) {
                if (TextUtils.equals(resType.type, type)) {
                    return resType;
                }
            }
            return NONE;
        }
    }

    public static SkinAttr get(String attrName, int attrValueRefId, String attrValueRefName, String typeName) {
        SkinAttr mSkinAttr;

        if (extAttr.containsKey(attrName)) {
            // 拓展属性支持
            mSkinAttr = extAttr.get(attrName);

            if (null == mSkinAttr) {
                return null;
            }

        } else {
            switch (Attr.byName(attrName)) {
                case BACKGROUND:
                    mSkinAttr = new BackgroundAttr();
                    break;
                case TEXT_COLOR:
                    mSkinAttr = new TextColorAttr();
                    break;
                case TEXT_COLOR_HINT:
                    mSkinAttr = new TextColorHintAttr();
                    break;
                case TEXT_COLOR_HINT_LIGHT:
                    mSkinAttr = new TextColorHighlightAttr();
                    break;
                case TEXT_CURSOR_DRAWABLE:
                    mSkinAttr = new TextCursorDrawableAttr();
                    break;
                case LIST_SELECTOR:
                    mSkinAttr = new ListSelectorAttr();
                    break;
                case DIVIDER:
                    mSkinAttr = new DividerAttr();
                    break;
                case SRC:
                    mSkinAttr = new SrcAttr();
                    break;
                case DRAWABLE_LEFT:
                    mSkinAttr = new DrawableLeftAttr();
                    break;
                case DRAWABLE_TOP:
                    mSkinAttr = new DrawableTopAttr();
                    break;
                case DRAWABLE_RIGHT:
                    mSkinAttr = new DrawableRightAttr();
                    break;
                case DRAWABLE_BOTTOM:
                    mSkinAttr = new DrawableBottomAttr();
                    break;
                case DRAWABLE_START:
                    mSkinAttr = new DrawableStartAttr();
                    break;
                case DRAWABLE_END:
                    mSkinAttr = new DrawableEndAttr();
                    break;
                case PROGRESS_DRAWABLE:
                    mSkinAttr = new ProgressDrawableAttr();
                    break;
                case INDETERMINATE_DRAWABLE:
                    mSkinAttr = new IndeterminateDrawableAttr();
                    break;
                case THUMB:
                    mSkinAttr = new ThumbAttr();
                    break;
                case BUTTON:
                    mSkinAttr = new ButtonAttr();
                    break;
                case NONE:
                default:
                    return null;
            }
        }

        mSkinAttr.attrName = attrName;
        mSkinAttr.attrValueRefId = attrValueRefId;
        mSkinAttr.attrValueRefName = attrValueRefName;
        mSkinAttr.attrValueType = ResType.byName(typeName);
        return mSkinAttr;
    }

    /**
     * 是否是支持的属性
     */
    public static boolean isSupportedAttr(String attrName) {
        return extAttr.containsKey(attrName) || Attr.NONE != Attr.byName(attrName);
    }

    /**
     * 添加额外属性
     */
    public static void addExtAttr(String attrName, SkinAttr attr) {
        extAttr.put(attrName, attr);
    }

}
