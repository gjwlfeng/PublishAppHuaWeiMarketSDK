package com.zf.publish.app.market.huawei.http.updateLangInfo;

public class LangInfo {

    /**
     * 应用名称。
     */
    private String appName;

    /**
     * 应用描述
     */
    private String appDesc;

    /**
     * 一句话简介。
     */
    private String briefInfo;

    /**
     * 新版本简介。
     */
    private String newFeatures;

    public LangInfo() {
    }

    private LangInfo(String appName, String appDesc, String briefInfo, String newFeatures) {
        this.appName = appName;
        this.appDesc = appDesc;
        this.briefInfo = briefInfo;
        this.newFeatures = newFeatures;
    }

    public String getAppName() {
        return appName;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public String getBriefInfo() {
        return briefInfo;
    }

    public String getNewFeatures() {
        return newFeatures;
    }

    public LangInfo setAppName(String appName) {
        this.appName = appName;
        return this;
    }

    public LangInfo setAppDesc(String appDesc) {
        this.appDesc = appDesc;
        return this;
    }

    public LangInfo setBriefInfo(String briefInfo) {
        this.briefInfo = briefInfo;
        return this;
    }

    public LangInfo setNewFeatures(String newFeatures) {
        this.newFeatures = newFeatures;
        return this;
    }
}
