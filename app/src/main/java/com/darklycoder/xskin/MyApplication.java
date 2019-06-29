package com.darklycoder.xskin;

import android.app.Application;

import com.darklycoder.xskin.core.config.SkinConfig;
import com.darklycoder.xskin.core.loader.SkinManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this, SkinConfig.DEFAULT_SKIN);
    }

}
