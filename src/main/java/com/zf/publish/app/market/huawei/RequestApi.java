package com.zf.publish.app.market.huawei;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;

public abstract class RequestApi<T extends Result> {

    protected Gson mGson;

    HttpClient httpClient;
    Class<T> mClz = null;

    public RequestApi() {
        this.httpClient = HuaWeiPublishingApi.getInstance().httpClient;
        this.mGson = HuaWeiPublishingApi.getInstance().sGson;
        mClz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    protected T execute(HttpUriRequest request) throws IOException {
        HttpResponse response = httpClient.execute(request);
        String content = getContent(response);

        T t = parseObj(content);

        return t;
    }

    private T parseObj(String content) {
        return new Gson().fromJson(content, mClz);
    }

    private String getContent(HttpResponse response) throws IOException {

        HttpEntity entity = response.getEntity();
        InputStream content = null;
        StringBuffer resultBuilder = new StringBuffer();
        try {
            content = entity.getContent();

            byte[] bt = new byte[1024];
            int length = 0;
            while ((length = content.read(bt)) != -1) {
                resultBuilder.append(new String(bt, 0, length));
            }
            return resultBuilder.toString();
        } finally {
            if (content != null) {
                content.close();
            }
        }
    }

}
