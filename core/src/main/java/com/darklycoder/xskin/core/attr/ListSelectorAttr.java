package com.darklycoder.xskin.core.attr;

import android.view.View;
import android.widget.AbsListView;

import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

public class ListSelectorAttr extends SkinAttr {

    @Override
    public void apply(View view) {
        if (view instanceof AbsListView) {
            AbsListView absListView = (AbsListView) view;

            switch (attrValueType) {
                case COLOR:
                    absListView.setSelector(SkinManager.getInstance().getColor(attrValueRefId));
                    SkinLog.i("attr", "apply: ListSelectorAttr - " + attrValueType);
                    break;
                case DRAWABLE:
                    absListView.setSelector(SkinManager.getInstance().getDrawable(attrValueRefId));
                    SkinLog.i("attr", "apply: ListSelectorAttr - " + attrValueType);
                    break;
                default:
                    break;
            }
        }
    }

}
