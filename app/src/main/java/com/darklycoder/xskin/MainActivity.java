package com.darklycoder.xskin;

import android.os.Bundle;

import com.darklycoder.xskin.core.loader.SkinManager;
import com.darklycoder.xskin.core.base.SkinActivity;
import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.fragments.IndexFragment;

public class MainActivity extends SkinActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_default).setOnClickListener(v -> {
            SkinManager.getInstance().load(SkinConfig.DEFAULT_SKIN);
        });

        findViewById(R.id.btn_skin_1).setOnClickListener(v -> {
            SkinManager.getInstance().load("sdcard/skin/skin01-debug.apk");
        });

        findViewById(R.id.btn_skin_2).setOnClickListener(v -> {
            SkinManager.getInstance().load("sdcard/skin/skin02-debug.apk");
        });

        getFragmentManager().beginTransaction().replace(R.id.fl_container, new IndexFragment()).commit();
    }

    @Override
    public int getKey() {
        return R.layout.activity_main;
    }
}
