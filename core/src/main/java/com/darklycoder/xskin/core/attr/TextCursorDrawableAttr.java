package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

import java.lang.reflect.Field;

public class TextCursorDrawableAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof EditText) {
            EditText editText = (EditText) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable drawableCursor = SkinManager.getInstance().getDrawable(attrValueRefId);
                setCursorDrawableColor(editText, drawableCursor);
                SkinLog.i("attr", "apply: TextCursorDrawableAttr - " + attrValueType);
            }
        }
    }

    private void setCursorDrawableColor(EditText editText, Drawable drawableCursor) {
        try {
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            fCursorDrawable.set(editor, drawableCursor);

        } catch (Throwable ignored) {
        }
    }
}
