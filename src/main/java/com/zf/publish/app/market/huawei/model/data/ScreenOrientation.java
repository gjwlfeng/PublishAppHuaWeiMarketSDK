package com.zf.publish.app.market.huawei.model.data;

public class ScreenOrientation {


    //0：竖屏,1：横屏
    private int orientation;

    public final static ScreenOrientation Horizontal = new ScreenOrientation(1);
    public final static ScreenOrientation vertical = new ScreenOrientation(0);

    private ScreenOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int orientation() {
        return orientation;
    }
}
