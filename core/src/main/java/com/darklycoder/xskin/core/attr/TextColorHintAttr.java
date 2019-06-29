package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.TextView;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

public class TextColorHintAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;

            if (attrValueType == AttrFactory.ResType.COLOR) {
                textView.setHintTextColor(SkinManager.getInstance().convertToColorStateList(attrValueRefId));
                SkinLog.i("attr", "apply: TextColorHintAttr - " + attrValueType);
            }
        }
    }

}
