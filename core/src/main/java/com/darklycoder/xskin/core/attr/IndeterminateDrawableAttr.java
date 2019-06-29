package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ProgressBar;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;

public class IndeterminateDrawableAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ProgressBar) {
            ProgressBar progressBar = (ProgressBar) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable indeterminateDrawable = SkinManager.getInstance().getDrawable(attrValueRefId);
                progressBar.setIndeterminateDrawable(indeterminateDrawable);
            }
        }
    }

}