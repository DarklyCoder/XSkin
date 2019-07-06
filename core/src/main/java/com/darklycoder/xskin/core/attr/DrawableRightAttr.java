package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;

/**
 * 支持 "drawableRight" 属性
 */
public class DrawableRightAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable drawableRight = SkinManager.getInstance().getDrawable(attrValueRefId);

                Drawable[] drawables = textView.getCompoundDrawables();
                textView.setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableRight, drawables[3]);
            }
        }
    }

}
