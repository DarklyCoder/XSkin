package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.CompoundButton;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;

/**
 * 支持 "button" 属性
 */
public class ButtonAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof CompoundButton) {
            CompoundButton compoundButton = (CompoundButton) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                compoundButton.setButtonDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }

}
