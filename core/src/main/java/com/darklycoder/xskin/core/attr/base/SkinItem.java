package com.darklycoder.xskin.core.attr.base;

import android.support.annotation.NonNull;
import android.view.View;

import com.darklycoder.xskin.core.util.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class SkinItem {

    public View view;

    public List<SkinAttr> attrs;

    public SkinItem() {
        attrs = new ArrayList<>();
    }

    public void apply() {
        if (ListUtils.isEmpty(attrs)) {
            return;
        }

        for (SkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (ListUtils.isEmpty(attrs)) {
            return;
        }

        for (SkinAttr at : attrs) {
            at = null;
        }

        attrs.clear();
    }

    @NonNull
    @Override
    public String toString() {
        return "SkinItem [view=" + view.getClass().getSimpleName() + ", attrs=" + attrs + "]";
    }

}
