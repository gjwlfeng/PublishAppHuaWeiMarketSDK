package com.zf.publish.app.market.huawei.http.queryAppInfo;

import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.model.data.LangType;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * 查询应用信息
 * <p>
 * 查询应用信息需要指定语言以及appid。
 */
public class QueryAppInfoApi extends RequestApi<QueryAppInfoResponse> {

    private final static String url = "https://connect-api.cloud.huawei.com/api/publish/v1/appInfo/%s";

    /**
     * 查询应用信息
     *
     * @param appId    appId为开发者联盟应用的ID。
     * @param langType 查询语言类型 参见{@link LangType}
     * @return
     * @throws IOException
     */
    public QueryAppInfoResponse query(String appId, LangType langType) throws IOException {
        String appIdUrl = String.format(url, appId);
        appIdUrl += ("?lang=" + langType.value());

        HttpGet httpGet = new HttpGet(appIdUrl);
        httpGet.setHeader("accept", "application/json");
        httpGet.setHeader("content-type", "application/json");
        return execute(httpGet);
    }

    /**
     * 查询应用信息,默认为语言为中文
     *
     * @param appId appId为开发者联盟应用的ID。
     * @return
     * @throws IOException
     */
    public QueryAppInfoResponse query(String appId) throws IOException {

        String appIdUrl = String.format(url, appId);
        HttpGet httpGet = new HttpGet(appIdUrl);
        httpGet.setHeader("accept", "application/json");
        httpGet.setHeader("content-type", "application/json");
        return execute(httpGet);
    }


}
