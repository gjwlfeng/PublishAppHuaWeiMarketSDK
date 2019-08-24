package com.zf.publish.app.market.huawei.http.queryAppId;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.RequestApi;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

/**
 * 查询包名对应的appid
 * <p>
 * 根据传入的包名数组，返回对应的appid列表。
 */
public class QueryAppIdApi extends RequestApi<QueryAppIdResponse> {

    public final static String url = "https://connect-api.cloud.huawei.com/api/publish/v1/packageAppId";


    /**
     * @param packages 包名
     * @return 应用 appid列表
     * @throws Exception
     */
    public QueryAppIdResponse query(String... packages) throws Exception {
        QueryAppIdRequest request = new QueryAppIdRequest(packages);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("accept", "application/json");
        httpPost.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPost.setEntity(entity);
        return execute(httpPost);
    }

}
