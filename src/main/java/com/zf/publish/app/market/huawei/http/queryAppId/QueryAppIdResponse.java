package com.zf.publish.app.market.huawei.http.queryAppId;

import com.zf.publish.app.market.huawei.Result;

import java.util.Arrays;

public class QueryAppIdResponse extends Result {
    public AppId[] appIds;

    @Override
    public String toString() {
        return "QueryAppIdResponse{" +
                "appIds=" + Arrays.toString(appIds) +
                ", errorCode='" + errorCode + '\'' +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
