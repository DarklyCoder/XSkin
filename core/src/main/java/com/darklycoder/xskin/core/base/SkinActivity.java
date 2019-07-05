package com.darklycoder.xskin.core.base;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.darklycoder.xskin.core.SkinManager;
import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.listener.IDynamicNewView;
import com.darklycoder.xskin.core.listener.ISkinUpdate;
import com.darklycoder.xskin.core.loader.SkinInflaterFactory;

import java.util.List;

public abstract class SkinActivity extends AppCompatActivity implements ISkinUpdate, IDynamicNewView {

    /**
     * 是否在皮肤包更新后立即换肤
     */
    private boolean isResponseOnSkinChanging = true;

    private SkinInflaterFactory mSkinInflaterFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mSkinInflaterFactory = new SkinInflaterFactory(this);
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), mSkinInflaterFactory);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SkinManager.getInstance().attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SkinManager.getInstance().detach(this);
        mSkinInflaterFactory.clean();
    }

    @Override
    public void onThemeUpdate() {
        if (!isResponseOnSkinChanging) {
            return;
        }

        mSkinInflaterFactory.applySkin();
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> attrs) {
        mSkinInflaterFactory.dynamicAddSkinEnableView(this, view, attrs);
    }

}
