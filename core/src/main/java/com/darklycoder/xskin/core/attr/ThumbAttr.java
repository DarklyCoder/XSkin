package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.SeekBar;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.SkinManager;

/**
 * 支持 SeekBar 的 "thumb"属性
 */
public class ThumbAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof SeekBar) {
            SeekBar seekBar = (SeekBar) view;

            if (attrValueType == AttrFactory.ResType.DRAWABLE) {
                seekBar.setThumb(SkinManager.getInstance().getDrawable(attrValueRefId));
            }
        }
    }

}
