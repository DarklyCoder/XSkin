package com.darklycoder.xskin.core.attr;

import android.view.View;

import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

/**
 * 支持 "background" 属性
 */
public class BackgroundAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        switch (attrValueType) {
            case COLOR:
                view.setBackgroundColor(SkinManager.getInstance().getColor(attrValueRefId));
                break;

            case DRAWABLE:
                view.setBackground(SkinManager.getInstance().getDrawable(attrValueRefId));
                break;

            default:
                break;
        }
    }

}
