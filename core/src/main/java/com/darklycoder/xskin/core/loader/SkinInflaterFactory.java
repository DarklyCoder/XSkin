package com.darklycoder.xskin.core.loader;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.AttributeSet;
import android.view.LayoutInflater.Factory2;
import android.view.View;

import com.darklycoder.xskin.core.attr.base.AttrFactory;
import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.attr.base.SkinAttr;
import com.darklycoder.xskin.core.attr.base.SkinItem;
import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.util.ListUtils;
import com.darklycoder.xskin.core.util.SkinLog;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义InflaterFactory
 */
public class SkinInflaterFactory implements Factory2 {

    private final static String TAG = "SkinInflaterFactory";

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
        SkinLog.i(TAG, "onCreateView: " + name);

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
        SkinLog.d(TAG, "parseSkinAttr()");
        List<SkinAttr> viewAttrs = new ArrayList<>();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            String attrName = attrs.getAttributeName(i);
            String attrValue = attrs.getAttributeValue(i);
            SkinLog.d(TAG, "attrName = " + attrName + ", attrValue = " + attrValue);

            if (!AttrFactory.isSupportedAttr(attrName)) {
                continue;
            }

            if (attrValue.startsWith("@")) {
                try {
                    int id = Integer.parseInt(attrValue.substring(1));
                    String entryName = context.getResources().getResourceEntryName(id);
                    String typeName = context.getResources().getResourceTypeName(id);
                    SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);

                    SkinLog.d(TAG, "mSkinAttr = " + mSkinAttr);

                    if (null != mSkinAttr) {
                        viewAttrs.add(mSkinAttr);
                    }

                } catch (Exception e) {
                    SkinLog.e(e.getMessage());
                }
            }
        }

        SkinLog.d(TAG, "viewAttrs = " + viewAttrs.size());

        if (!ListUtils.isEmpty(viewAttrs)) {
            SkinItem skinItem = new SkinItem();
            skinItem.view = view;
            skinItem.attrs = viewAttrs;

            SkinLog.d(TAG, "skinItem = " + skinItem);

            addSkinView(skinItem);

            if (SkinManager.getInstance().isExternalSkin()) {
                skinItem.apply();
            }
        }
    }

    public void applySkin() {
        if (ListUtils.isEmpty(mSkinItems)) {
            return;
        }
        for (SkinItem skinItem : mSkinItems) {
            if (skinItem.view == null) {
                continue;
            }
            skinItem.apply();
        }
    }

    public void dynamicAddSkinEnableView(Context context, View view, List<DynamicAttr> pDAttrs) {
        SkinLog.d(TAG, "dynamicAddSkinEnableView(" + view + ")");
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;

        for (DynamicAttr dAttr : pDAttrs) {
            int id = dAttr.refResId;
            String entryName = context.getResources().getResourceEntryName(id);
            String typeName = context.getResources().getResourceTypeName(id);
            SkinAttr mSkinAttr = AttrFactory.get(dAttr.attrName, id, entryName, typeName);
            viewAttrs.add(mSkinAttr);
        }

        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    public void dynamicAddSkinEnableView(Context context, View view, String attrName, int attrValueResId) {
        SkinLog.d(TAG, "dynamicAddSkinEnableView(" + view + ", " + attrName + ")");
        int id = attrValueResId;
        String entryName = context.getResources().getResourceEntryName(id);
        String typeName = context.getResources().getResourceTypeName(id);
        SkinAttr mSkinAttr = AttrFactory.get(attrName, id, entryName, typeName);
        SkinItem skinItem = new SkinItem();
        skinItem.view = view;
        List<SkinAttr> viewAttrs = new ArrayList<SkinAttr>();
        viewAttrs.add(mSkinAttr);
        skinItem.attrs = viewAttrs;
        addSkinView(skinItem);
    }

    private void addSkinView(SkinItem item) {
        mSkinItems.add(item);
    }

    public void clean() {
        if (ListUtils.isEmpty(mSkinItems)) {
            return;
        }

        for (SkinItem si : mSkinItems) {
            if (si.view == null) {
                continue;
            }
            si.clean();
        }
    }

}
