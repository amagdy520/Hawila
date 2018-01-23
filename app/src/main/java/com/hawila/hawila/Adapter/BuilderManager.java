package com.hawila.hawila.Adapter;

import com.hawila.hawila.R;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomButtons.TextInsideCircleButton;
import com.nightonke.boommenu.BoomButtons.TextOutsideCircleButton;
import com.nightonke.boommenu.Util;

/**
 * Created by Ahmed Magdy
 *
 */
public class BuilderManager {
    //**************************************************************************************************
    //User Builder Button
    private static int[] imageResources = new int[]{
            R.drawable.posts,
            R.drawable.price,
            R.drawable.logout,
            R.drawable.facebook,
            R.drawable.instagram,
            R.drawable.whatsapp,
            R.drawable.phone,
            R.drawable.account,
            R.drawable.setting
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length)
            imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }

    private static int[] titleResources = new int[]{
            R.string.gallery,
            R.string.price,
            R.string.logout,
            R.string.facebook,
            R.string.instagram,
            R.string.whats,
            R.string.phone,
            R.string.acc,
            R.string.setting
    };
    private static int titleResourceIndex = 0;

    static int getTitleResource() {
        if (titleResourceIndex >= titleResources.length)
            titleResourceIndex = 0;
        return titleResources[titleResourceIndex++];
    }
    public static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilder() {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(getTitleResource())
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15));
    }
    //**********************************************************************************************
    //Admin Builder Button
    private static int[] imageResourcesAdmin = new int[]{
            R.drawable.posts,
            R.drawable.price,
            R.drawable.logout,
            R.drawable.facebook,
            R.drawable.instagram,
            R.drawable.whatsapp,
            R.drawable.phone,
            R.drawable.account,
            R.drawable.setting
    };

    private static int imageResourceIndexAdmin = 0;

    static int getImageResourceAdmin() {
        if (imageResourceIndexAdmin >= imageResourcesAdmin.length)
            imageResourceIndexAdmin = 0;
        return imageResourcesAdmin[imageResourceIndexAdmin++];
    }

    private static int[] titleResourcesAdmin = new int[]{
            R.string.gallery,
            R.string.price,
            R.string.logout,
            R.string.facebook,
            R.string.instagram,
            R.string.whats,
            R.string.phone,
            R.string.acc,
            R.string.setting
    };
    private static int titleResourceIndexAdmin = 0;

    static int getTitleResourceAdmin() {
        if (titleResourceIndexAdmin >= titleResourcesAdmin.length)
            titleResourceIndexAdmin = 0;
        return titleResourcesAdmin[titleResourceIndexAdmin++];
    }
    public static TextOutsideCircleButton.Builder getTextOutsideCircleButtonBuilderAdmin() {
        return new TextOutsideCircleButton.Builder()
                .normalImageRes(getImageResourceAdmin())
                .normalTextRes(getTitleResourceAdmin())
                .shadowCornerRadius(Util.dp2px(15))
                .buttonCornerRadius(Util.dp2px(15));
    }
    //**********************************************************************************************
    private static BuilderManager ourInstance = new BuilderManager();
    public static BuilderManager getInstance() {
        return ourInstance;
    }
    private BuilderManager() {
    }
}
