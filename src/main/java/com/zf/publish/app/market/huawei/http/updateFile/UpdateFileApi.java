package com.zf.publish.app.market.huawei.http.updateFile;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.Result;
import com.zf.publish.app.market.huawei.model.data.LangType;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

/**
 * 更新应用文件信息
 * <p>
 * 图片视频APK以及RPK上传完成后，通过该接口刷新应用文件信息。
 */
public class UpdateFileApi extends RequestApi<Result> {

    public final static String UPDATE_APP_URL_LANG_TYPE = "https://connect-api.cloud.huawei.com/api/publish/v1/mediaInfo/%s/%s";

    /**
     * @param appId   appId为开发者联盟应用的ID。
     *                //@param lang    语言类型
     * @param request 请求参数详细信息
     * @return
     * @throws IOException
     */
    public Result updateApkRpkFile(String appId, FileInfoRequest request) throws IOException {
        HttpPut httpPut = new HttpPut(String.format(UPDATE_APP_URL_LANG_TYPE, appId, LangType.Chinese_PRC));
        httpPut.setHeader("accept", "application/json");
        httpPut.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPut.setEntity(entity);
        return execute(httpPut);
    }

    /**
     * @param appId    appId为开发者联盟应用的ID。
     * @param langType 语言类型
     * @param request  请求参数详细信息
     * @return
     * @throws IOException
     */
    public Result updateFile(String appId, LangType langType, FileInfoRequest request) throws IOException {
        HttpPut httpPut = new HttpPut(String.format(UPDATE_APP_URL_LANG_TYPE, appId, langType.value()));
        httpPut.setHeader("accept", "application/json");
        httpPut.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPut.setEntity(entity);
        return execute(httpPut);
    }

}
