package com.yangon.baidumap.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Yangon on 2018/8/6
 */

public class ToastUtil {

    /**
     * 在屏幕中央显示一个Toast
     * @param text
     */
    public static void showToast(Context context, CharSequence text) {
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
