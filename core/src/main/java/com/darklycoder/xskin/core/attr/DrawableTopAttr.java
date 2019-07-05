package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

/**
 * 支持 "drawableTop" 属性
 */
public class DrawableTopAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable drawableTop = SkinManager.getInstance().getDrawable(attrValueRefId);

                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawableTop, drawables[2], drawables[3]);
            }
        }
    }

}
