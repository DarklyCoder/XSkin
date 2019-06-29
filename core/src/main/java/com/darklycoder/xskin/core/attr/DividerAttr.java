package com.darklycoder.xskin.core.attr;

import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ListView;

import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

/**
 * ListView 分割线
 */
public class DividerAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof ListView) {
            ListView listView = (ListView) view;
            int dividerHeight = 1;

            switch (attrValueType) {
                case COLOR:
                    int color = SkinManager.getInstance().getColor(attrValueRefId);
                    ColorDrawable colorDrawable = new ColorDrawable(color);
                    listView.setDivider(colorDrawable);
                    listView.setDividerHeight(dividerHeight);
                    SkinLog.i("attr", "apply: DividerAttr - " + attrValueType);
                    break;

                case DRAWABLE:
                    listView.setDivider(SkinManager.getInstance().getDrawable(attrValueRefId));
                    SkinLog.i("attr", "apply: DividerAttr - " + attrValueType);
                    break;

                default:
                    break;
            }
        }
    }

}
