package com.darklycoder.xskin.fragments;

import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darklycoder.xskin.R;
import com.darklycoder.xskin.core.base.SkinFragment;

public class IndexFragment extends SkinFragment {

    TextView mBtnNormal;
    TextView mBtnList;
    TextView mBtnCustom;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_index, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBtnNormal = view.findViewById(R.id.btn_normal);
        mBtnList = view.findViewById(R.id.btn_list);
        mBtnCustom = view.findViewById(R.id.btn_custom);

        mBtnNormal.setOnClickListener(v -> toggle(new NormalTestFragment()));
        mBtnList.setOnClickListener(v -> toggle(new ListTestFragment()));
        mBtnCustom.setOnClickListener(v -> toggle(new CustomTestFragment()));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void toggle(Fragment fragment) {
        getChildFragmentManager().beginTransaction().replace(R.id.fl_container, fragment).commit();
    }

}
