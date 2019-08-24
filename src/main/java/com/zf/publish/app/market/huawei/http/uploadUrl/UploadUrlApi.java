package com.zf.publish.app.market.huawei.http.uploadUrl;

import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.model.data.FileSuffix;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

public class UploadUrlApi extends RequestApi<UploadUrlResponse> {

    private static final String uploadUrl = "https://connect-api.cloud.huawei.com/api/publish/v1/uploadUrl?suffix=%s";


    public UploadUrlResponse getUploadUrl(FileSuffix suffix) throws IOException {

        HttpGet httpGet = new HttpGet(String.format(uploadUrl, suffix.getSuffix()));
        httpGet.setHeader("accept", "application/json");
        httpGet.setHeader("content-type", "application/json");
        return execute(httpGet);
    }

}
