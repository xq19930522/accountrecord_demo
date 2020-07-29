
package com.example.accountrecord;

import android.content.Context;

import java.lang.reflect.Field;

public class ResourceUtil {

    public static int getLayoutId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "layout", context.getPackageName());
    }

    public static int getStringId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "string", context.getPackageName());
    }

    public static int getDrawableId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "drawable", context.getPackageName());
    }

    public static int getStyleId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }

    public static int getId(Context context, String resName) {

        return context.getResources().getIdentifier(resName, "id", context.getPackageName());
    }

    public static int getColorId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "color", context.getPackageName());
    }

    public static int getValuesId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "style", context.getPackageName());
    }

    public static int getStyleableId(Context context, String resName) {
        return context.getResources().getIdentifier(resName, "styleable", context.getPackageName());
    }

    // 通过反射实现
    public static final int[] getStyleableIntArray(Context context, String name) {
        try {
            if (context == null) return null;
            Field field = Class.forName(context.getPackageName() + ".R$styleable").getDeclaredField(name);
            int[] ret = (int[]) field.get(null);
            return ret;
        } catch (Throwable t) {
        }
        return null;
    }

}
