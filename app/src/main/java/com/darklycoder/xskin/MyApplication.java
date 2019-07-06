package com.darklycoder.xskin;

import android.app.Application;

import com.darklycoder.xskin.core.loader.SkinManager;
import com.squareup.leakcanary.LeakCanary;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        SkinManager.getInstance().init(this);
    }

}
