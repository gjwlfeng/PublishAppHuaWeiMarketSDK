package com.zf.publish.app.market.huawei.http.updateAppInfo;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.Result;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * 更新应用基本信息
 *
 * 开发者调用该接口更新指定的App信息。
 */
public class UpdateAppInfoApi extends RequestApi<Result> {

    public final static String url = "https://connect-api.cloud.huawei.com/api/publish/v1/appInfo/%s";


    public Result update(String appId, AppInfoRequest request) throws IOException {
        HttpPut httpPut = new HttpPut(String.format(url, appId));
        httpPut.setHeader("accept", "application/json");
        httpPut.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPut.setEntity(entity);
        return execute(httpPut);
    }


}
