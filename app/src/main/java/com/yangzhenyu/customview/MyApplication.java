package com.yangzhenyu.customview;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by yangzhenyu on 2018/4/28.
 */

public class MyApplication extends Application {

    public static Typeface sSongTi18030;
    public static Typeface sHuaWenZhongSong;
    public static Typeface sAparajita;
    public static Typeface sApplySymBols;
    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sSongTi18030 = Typeface.createFromAsset(getAssets(), "typeface/st18030.ttc");
        sHuaWenZhongSong = Typeface.createFromAsset(getAssets(), "typeface/hwzs.ttf");
        sAparajita = Typeface.createFromAsset(getAssets(), "typeface/Aparajita.ttf");
        sApplySymBols = Typeface.createFromAsset(getAssets(), "typeface/Apple Symbols.ttf");
    }
}
