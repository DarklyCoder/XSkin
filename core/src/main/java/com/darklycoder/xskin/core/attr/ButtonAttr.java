package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.CompoundButton;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

/**
 * 按钮
 */
public class ButtonAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof CompoundButton) {
            CompoundButton compoundButton = (CompoundButton) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable button = SkinManager.getInstance().getDrawable(attrValueRefId);
                compoundButton.setButtonDrawable(button);
                SkinLog.i("attr", "apply: ButtonAttr - " + attrValueType);
            }
        }
    }

}
