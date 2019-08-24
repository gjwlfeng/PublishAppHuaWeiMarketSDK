package com.zf.publish.app.market.huawei.http.queryAppId;

public class AppId {

    String packageName;
    String appId;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    @Override
    public String toString() {
        return "{" +
                "packageName='" + packageName + '\'' +
                ", appId='" + appId + '\'' +
                '}';
    }
}
