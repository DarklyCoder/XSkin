package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

import java.lang.reflect.Field;

/**
 * 支持 TextView 的 "textCursorDrawable"属性
 */
public class TextCursorDrawableAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView editText = (TextView) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable drawableCursor = SkinManager.getInstance().getDrawable(attrValueRefId);
                setCursorDrawableColor(editText, drawableCursor);
            }
        }
    }

    private void setCursorDrawableColor(TextView editText, Drawable drawableCursor) {
        try {
            Field fEditor = editText.getClass().getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Field fCursorDrawable = editor.getClass().getDeclaredField("mDrawableForCursor");
            fCursorDrawable.setAccessible(true);

            fCursorDrawable.set(editor, drawableCursor);

        } catch (Throwable ignored) {
        }
    }

}
