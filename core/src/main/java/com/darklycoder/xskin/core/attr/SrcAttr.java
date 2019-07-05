package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageView;

import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

/**
 * 支持 ImageView 的 "src" 属性
 */
public class SrcAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ImageView) {
            ImageView imageView = (ImageView) view;

            switch (attrValueType) {
                case COLOR:
                    imageView.setImageDrawable(new ColorDrawable(SkinManager.getInstance().getColor(attrValueRefId)));
                    break;

                case DRAWABLE:
                    imageView.setImageDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
                    break;

                default:
                    break;
            }
        }
    }

}
