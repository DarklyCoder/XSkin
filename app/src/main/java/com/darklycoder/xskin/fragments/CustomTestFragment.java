package com.darklycoder.xskin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darklycoder.xskin.R;
import com.darklycoder.xskin.core.base.SkinFragment;

public class CustomTestFragment extends SkinFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_test_custom, container, false);
    }
}
