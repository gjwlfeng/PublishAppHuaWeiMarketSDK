package com.zf.publish.app.market.huawei.http.uploadFile;

import com.zf.publish.app.market.huawei.RequestApi;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.File;
import java.io.IOException;

public class UploadFileApi extends RequestApi<UploadFileResponse> {


    public final static String uploadFile = "https://%s/api/publish/v1/uploadFile";

    public UploadFileResponse uploadFile(String ip, String authCode, File file) throws IOException {
        HttpPost post = new HttpPost(String.format(uploadFile, ip));
        post.setHeader("accept", "application/json");
        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        entity.addPart("apk", new FileBody(file));
        entity.addPart("authCode", new StringBody(authCode, ContentType.APPLICATION_JSON));

        post.setEntity(entity.build());

        return execute(post);
    }

}
