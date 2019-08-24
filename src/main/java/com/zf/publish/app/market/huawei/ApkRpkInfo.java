package com.zf.publish.app.market.huawei;

import com.zf.publish.app.market.huawei.http.updateLangInfo.LangInfo;
import com.zf.publish.app.market.huawei.model.data.LangType;

import java.io.File;
import java.util.Date;
import java.util.HashMap;

public class ApkRpkInfo {

    private File apkRpkFile;
    private Date releaseTime;

    /**
     * 提审发布备注。可以为空，如果设置，长度范围为10-300。
     */
    private String remark;

    /**
     * 渠道ID，可选。
     */
    private String channelId;

    public String sensitivePermissionDesc;

    private HashMap<LangType, LangInfo> apkRpkUpdateInfos = new HashMap<>();

    public ApkRpkInfo(File apkRpkFile, Date releaseTime) {
        this.apkRpkFile = apkRpkFile;
        this.releaseTime = releaseTime;
    }

    public ApkRpkInfo(File apkRpkFile) {
        this.apkRpkFile = apkRpkFile;
        this.releaseTime = new Date();
    }

    public ApkRpkInfo setRemark(String remark) {
        this.remark = remark;
        return this;
    }

    public ApkRpkInfo setChannelId(String channelId) {
        this.channelId = channelId;
        return this;
    }

    public File getApkRpkFile() {
        return apkRpkFile;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getChannelId() {
        return channelId;
    }

    public String getSensitivePermissionDesc() {
        return sensitivePermissionDesc;
    }

    public ApkRpkInfo setSensitivePermissionDesc(String sensitivePermissionDesc) {
        this.sensitivePermissionDesc = sensitivePermissionDesc;
        return this;
    }

    public HashMap<LangType, LangInfo> getApkRpkUpdateInfos() {
        return apkRpkUpdateInfos;
    }

    public ApkRpkInfo addApkRpkLangInfo(LangType langType, LangInfo langInfo) {
        apkRpkUpdateInfos.put(langType, langInfo);
        return this;
    }
}
