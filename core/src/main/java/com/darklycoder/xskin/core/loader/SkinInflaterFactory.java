package com.darklycoder.xskin.core.loader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import android.view.ViewGroup;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.attr.base.SkinItem;
import com.darklycoder.xskin.core.attr.parser.SkinAttributeParser;
import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.util.SkinLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义InflaterFactory
 */
public class SkinInflaterFactory implements Factory2 {

    /**
     * 存储那些有皮肤更改需求View
     */
    private List<SkinItem> mSkinItems = new ArrayList<>();
    private AppCompatActivity mActivity;

    public SkinInflaterFactory(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return null;
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        // TODO 适配 Activity
        boolean isSkinEnable = attrs.getAttributeBooleanValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ENABLE, false);
        AppCompatDelegate delegate = mActivity.getDelegate();

        View view = delegate.createView(parent, name, context, attrs);

        if (isSkinEnable) {
            // 需要换肤
            if (null == view) {
                view = ViewProducer.createViewFromTag(context, name, attrs);
            }

            if (null == view) {
                return null;
            }

            parseSkinAttr(context, attrs, view);
        }

        return view;
    }

    /**
     * 收集换肤view
     */
    private void parseSkinAttr(Context context, AttributeSet attrs, View view) {
        // 解析指定属性
        String specifiedAttrs = attrs.getAttributeValue(SkinConfig.NAMESPACE, SkinConfig.ATTR_SKIN_ATTR);
        String[] specifiedAttrsList = null;
        if (!TextUtils.isEmpty(specifiedAttrs)) {
            specifiedAttrsList = specifiedAttrs.split("\\|");
        }

        List<SkinAttr> viewAttrs = SkinAttributeParser.parseSkinAttr(context, attrs, view, specifiedAttrsList);
        if (viewAttrs.size() <= 0) {
            return;
        }

        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        skinItem.attrs = viewAttrs;

        SkinLog.d("skinItem = " + skinItem);

        addSkinView(skinItem);

        if (SkinManager.getInstance().isExternalSkin()) {
            skinItem.apply();
        }
    }

    private void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    }

    /**
     * 应用换肤
     */
    public void applySkin() {
        SkinLog.d("applySkin start! ...");
        if (null == mSkinItems || mSkinItems.size() <= 0) {
            SkinLog.d("applySkin end! 换肤组件列表为空");
            return;
        }

        SkinLog.d("applySkin ing... 换肤组件列表:" + mSkinItems.size());

        for (SkinItem skinItem : mSkinItems) {
            if (null == skinItem.view) {
                continue;
            }

            skinItem.apply();
        }

        SkinLog.d("applySkin end!");
    }

    /**
     * 动态添加需要换肤的组件
     */
    public void dynamicAddSkinView(Context context, View view, List<DynamicAttr> attrs) {
        if (null == context || null == view || null == attrs || attrs.size() <= 0) {
            return;
        }

        List<SkinAttr> viewAttrs = new ArrayList<>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : attrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }
        skinItem.attrs = viewAttrs;

        addSkinView(skinItem);
    }

    /**
     * 清除换肤组件
     */
    public void clean() {
        if (null == mSkinItems || mSkinItems.size() <= 0) {
            return;
        }

        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            si.clean();
        }
    }

    /**
     * 清除界面内的换肤组件
     */
    public void removeAllSkinView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            int size = viewGroup.getChildCount();
            for (int i = 0; i < size; i++) {
                removeAllSkinView(viewGroup.getChildAt(i));
            }

            removeSkinView(v);
            return;
        }

        removeSkinView(v);
    }

    /**
     * 移除指定的需要换肤的组件
     */
    private void removeSkinView(View view) {
        if (null == view || null == mSkinItems || mSkinItems.size() <= 0) {
            return;
        }

        for (SkinItem si : mSkinItems) {
            if (si.view == view) {
                mSkinItems.remove(si);
                break;
            }
        }
    }

}
