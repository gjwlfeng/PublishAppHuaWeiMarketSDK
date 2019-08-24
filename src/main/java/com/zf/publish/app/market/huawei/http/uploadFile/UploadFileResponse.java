package com.zf.publish.app.market.huawei.http.uploadFile;

import com.zf.publish.app.market.huawei.Result;

public class UploadFileResponse extends Result {

    public String uploadUrl;

    public String getUploadUrl() {
        return uploadUrl;
    }

    public void setUploadUrl(String uploadUrl) {
        this.uploadUrl = uploadUrl;
    }

    @Override
    public String toString() {
        return "UploadFileResponse{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                ", uploadUrl='" + uploadUrl + '\'' +
                '}';
    }
}
