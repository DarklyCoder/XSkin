package com.darklycoder.xskin.core.listener;

import android.view.View;

import com.darklycoder.xskin.core.attr.base.DynamicAttr;

import java.util.List;

public interface IDynamicNewView {

    void dynamicAddView(View view, List<DynamicAttr> pDAttrs);
}
