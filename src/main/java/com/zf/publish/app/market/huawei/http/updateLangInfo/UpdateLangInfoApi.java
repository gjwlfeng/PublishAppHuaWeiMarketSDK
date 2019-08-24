package com.zf.publish.app.market.huawei.http.updateLangInfo;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.Result;
import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.model.data.LangType;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;

import java.io.IOException;

public class UpdateLangInfoApi extends RequestApi<Result> {

    public final static String URL = "https://connect-api.cloud.huawei.com/api/publish/v1/languageInfo/%s/%s";


    public Result updateLangInfo(String appId, LangType lang, LangInfo request) throws IOException {
        HttpPut httpPut = new HttpPut(String.format(URL, appId, lang.value()));
        httpPut.setHeader("accept", "application/json");
        httpPut.setHeader("content-type", "application/json");
        StringEntity entity = new StringEntity(new Gson().toJson(request), "utf-8");
        httpPut.setEntity(entity);
        return execute(httpPut);
    }

}
