package com.darklycoder.xskin.core.listener;

import android.view.View;

import com.darklycoder.xskin.core.attr.base.DynamicAttr;

import java.util.List;

/**
 * 动态添加需要更新的view
 */
public interface IDynamicNewView {

    void dynamicAddView(View view, List<DynamicAttr> attrs);
}
