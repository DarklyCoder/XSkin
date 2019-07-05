package com.darklycoder.xskin.core.attr.base;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SkinItem {

    /**
     * 需要换肤的view
     */
    public View view;
    /**
     * 需要换肤的属性
     */
    public List<SkinAttr> attrs;

    public SkinItem() {
        attrs = new ArrayList<>();
    }

    public void apply() {
        if (null == attrs || attrs.size() <= 0) {
            return;
        }

        for (SkinAttr at : attrs) {
            at.apply(view);
        }
    }

    public void clean() {
        if (null == attrs || attrs.size() <= 0) {
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
