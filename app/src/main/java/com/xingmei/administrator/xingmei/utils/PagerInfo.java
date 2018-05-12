package com.xingmei.administrator.xingmei.utils;

/**
 * Created by Administrator on 2018/3/12.
 */

public class PagerInfo {
    private int iconResId;
//    private String intro;//定义文本
    private String iconUrl;

    public PagerInfo(String iconUrl) {
        super();
//        this.iconResId = iconResId;
        this.iconUrl = iconUrl;
//        this.intro = intro;
    }

    public int getIconResId() {
        return iconResId;
    }
    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
    public void setIconUrl(String url){

    }
    public String getIconUrl(){
        return iconUrl;
    }
//    public String getIntro() {
//        return intro;
//    }
//    public void setIntro(String intro) {
//        this.intro = intro;
//    }
}
