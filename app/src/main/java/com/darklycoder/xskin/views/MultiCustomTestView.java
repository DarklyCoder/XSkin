package com.darklycoder.xskin.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.darklycoder.xskin.R;

/**
 * 嵌套自定义view
 */
public class MultiCustomTestView extends LinearLayout {

    public MultiCustomTestView(Context context) {
        this(context, null);
    }

    public MultiCustomTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_test_multi_custom, this, true);
    }
}
