package com.darklycoder.xskin.core.base;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.listener.IDynamicNewView;

import java.util.List;

public class SkinFragment extends Fragment implements IDynamicNewView {

    private IDynamicNewView mIDynamicNewView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof IDynamicNewView) {
            mIDynamicNewView = (IDynamicNewView) activity;
        }
    }

    @Override
    public void dynamicAddView(View view, List<DynamicAttr> attrs) {
        if (null == mIDynamicNewView) {
            throw new RuntimeException("IDynamicNewView should be implements !");
        }

        mIDynamicNewView.dynamicAddView(view, attrs);
    }

}
