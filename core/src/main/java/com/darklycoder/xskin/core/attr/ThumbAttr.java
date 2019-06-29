package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.SeekBar;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;

public class ThumbAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof SeekBar) {
            SeekBar seekBar = (SeekBar) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                Drawable thumb = SkinManager.getInstance().getDrawable(attrValueRefId);
                seekBar.setThumb(thumb);
            }
        }
    }

}
