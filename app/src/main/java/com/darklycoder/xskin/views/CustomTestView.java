package com.darklycoder.xskin.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.darklycoder.xskin.R;

/**
 * 自定义view
 */
public class CustomTestView extends LinearLayout {

    public CustomTestView(Context context) {
        this(context, null);
    }

    public CustomTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_test_custom, this, true);
    }

}
