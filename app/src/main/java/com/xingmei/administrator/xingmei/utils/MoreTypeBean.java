package com.xingmei.administrator.xingmei.utils;

import java.util.ArrayList;
import java.util.List;

public class MoreTypeBean {
    /* 界面类型 */
    private int type;
    /* 标题 */
    private String titleString;
    /* 图片地址 */
    private List<String> iconURL;
    /* 副标题  如中华英才 */
    private String source;
    /* 内容地址 */
    private String contentURL;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitleString() {
        return titleString;
    }

    public void setTitleString(String titleString) {
        this.titleString = titleString;
    }

    public List<String> getIconURL() {
        return iconURL;
    }

    public void setIconURL(List<String> iconURL) {
        this.iconURL = iconURL;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

}
