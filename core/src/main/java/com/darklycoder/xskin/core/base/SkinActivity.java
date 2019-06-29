package com.darklycoder.xskin.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.listener.IDynamicNewView;
import com.darklycoder.xskin.core.listener.ISkinUpdate;
import com.darklycoder.xskin.core.loader.SkinInflaterFactory;
import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.util.SkinLog;

import java.util.List;

public class SkinActivity extends Activity implements ISkinUpdate, IDynamicNewView {
    private final static String TAG = "SkinActivity";
    /**
     * Whether response to skin changing after create
     */
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SkinLog.d(TAG, "onCreate() isExternalSkin() = " + SkinManager.getInstance().isExternalSkin());
        mSkinInflaterFactory = new SkinInflaterFactory();
        getLayoutInflater().setFactory(mSkinInflaterFactory);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinLog.d(TAG, "onResume()");
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinLog.d(TAG, "onDestroy()");
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

	protected void onThemeChanged(String theme) {
	    SkinLog.d(TAG, "onThemeChanged(" + theme + ")");
//	    SkinManager.getInstance().load(getCurrentTheme());
	}

    /**
     * dynamic add a skin view
     *
     * @param view
     * @param attrName
     * @param attrValueResId
     */
    protected void dynamicAddSkinEnableView(View view, String attrName, int attrValueResId) {
        SkinLog.d(TAG, "dynamicAddSkinEnableView(" + view + ", " + attrName + ")");
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrName, attrValueResId);
    }

    protected void dynamicAddSkinEnableView(View view, List<DynamicAttr> pDAttrs) {
        SkinLog.d(TAG, "dynamicAddSkinEnableView(" + view + ")");
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }

    final protected void enableResponseOnSkinChanging(boolean enable) {
        isResponseOnSkinChanging = enable;
    }

    @Override
    public void onThemeUpdate() {
        if (!isResponseOnSkinChanging) {
            return;
        }
        mSkinInflaterFactory.applySkin();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> pDAttrs) {
        SkinLog.d(TAG, "dynamicAddView(" + view + ")");
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, pDAttrs);
    }
}
