package com.zf.publish.app.market.huawei.http.publish;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.Result;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * 提交发布
 *
 * 开发者调用该接口提交app审核。
 */
public class PublishAppApi extends RequestApi<Result> {

    public final static String url = "https://connect-api.cloud.huawei.com/api/publish/v1/submit/%s";

    /**
     *
     * @param appId appId为开发者联盟应用的ID。
     * @param request 发布一个请求参数
     * @return
     * @throws IOException
     */
    public Result publish(String appId, PublishAppRequest request) throws IOException {
        HttpPost httpPut = new HttpPost(String.format(url, appId));
        httpPut.setHeader("accept", "application/json");
        httpPut.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPut.setEntity(entity);
        return execute(httpPut);
    }

}
