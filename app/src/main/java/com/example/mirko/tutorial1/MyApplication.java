package com.example.mirko.tutorial1;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseObject;

import java.lang.reflect.Field;

public class MyApplication extends Application implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e("UNCAUGHT", ex.getMessage(), ex);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setDefaultFont(this, "SERIF", "fonts/roof-runners-active.bold.ttf");

        Parse.enableLocalDatastore(this);

        Parse.initialize(
                this,
                "0sJuJGyaAx8wwJ35WALeLAEwnlZeb1xG4WKvYbZO",
                "Q95dPyOjyBcF985641WXCWrONSePjohLONtmCNkn");
    }

    private static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFont(staticTypefaceFieldName, regular);
    }

    private static void replaceFont(String staticTypefaceFieldName,
                                      final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch(NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }
}