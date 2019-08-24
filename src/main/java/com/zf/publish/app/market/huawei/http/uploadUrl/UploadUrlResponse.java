package com.zf.publish.app.market.huawei.http.uploadUrl;

import com.zf.publish.app.market.huawei.Result;

public class UploadUrlResponse extends Result {
    public String uploadUrl;
    public String authCode;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "UploadUrlResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", uploadUrl='" + uploadUrl + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
