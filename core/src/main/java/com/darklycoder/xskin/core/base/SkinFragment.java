package com.darklycoder.xskin.core.base;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darklycoder.xskin.core.attr.base.DynamicAttr;
import com.darklycoder.xskin.core.listener.IDynamicNewView;
import com.darklycoder.xskin.core.loader.SkinInflaterFactory;
import com.darklycoder.xskin.core.util.SkinLog;

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

    @Override
    public void onDestroyView() {
        removeAllView(getView());
        SkinLog.d("移除" + getClass().getSimpleName() + "界面需要换肤的组件！");
        super.onDestroyView();
    }

    private void removeAllView(View v) {
        if (v instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v;
            int size = viewGroup.getChildCount();
            for (int i = 0; i < size; i++) {
                removeAllView(viewGroup.getChildAt(i));
            }

            removeViewInSkinInflaterFactory(v);
            return;
        }

        removeViewInSkinInflaterFactory(v);
    }

    /**
     * 此方法用于Activity中Fragment销毁的时候，移除Fragment中的View
     */
    private void removeViewInSkinInflaterFactory(View v) {
        LayoutInflater.Factory2 factory = getActivity().getLayoutInflater().getFactory2();
        if (factory instanceof SkinInflaterFactory) {
            ((SkinInflaterFactory) factory).removeSkinView(v);
        }
    }

}
