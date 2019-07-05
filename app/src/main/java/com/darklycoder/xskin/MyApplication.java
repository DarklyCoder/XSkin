package com.darklycoder.xskin;

import android.app.Application;

import com.darklycoder.xskin.core.SkinManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SkinManager.getInstance().init(this);
    }

}
