package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;

/**
 * 支持 TextView 的 "textColorHint" 属性
 */
public class TextColorHintAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            if (attrValueType == AttrFactory.ResType.COLOR) {
                textView.setHintTextColor(SkinManager.getInstance().getColorStateList(attrValueRefId));
            }
        }
    }

}
