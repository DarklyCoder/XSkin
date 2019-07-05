package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.ProgressBar;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

public class IndeterminateDrawableAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                progressBar.setIndeterminateDrawable(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }

}
