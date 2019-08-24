package com.zf.publish.app.market.huawei.http.connect;

import com.google.gson.Gson;
import com.zf.publish.app.market.huawei.RequestApi;
import com.zf.publish.app.market.huawei.Result;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * 连接
 *
 * 连接Publish API获得session。
 */
public class ConnectApi extends RequestApi<Result> {

    private static final String connect_url = "https://connect-api.cloud.huawei.com/api/common/v1/connect";


    /**
     * @param clientId appId为开发者联盟应用的ID。
     * @param priKey   私钥
     * @return 返回签名
     */
    private String getSign(String clientId, String priKey) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, InvalidKeySpecException {

        String content = clientId + System.currentTimeMillis();
        //执行签名
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(priKey));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initSign(privateKey);
        signature.update(content.getBytes());
        return Base64.encodeBase64String(signature.sign());//生成的数
    }

    private String getKeyString(String clientId, String priKey) throws NoSuchAlgorithmException, SignatureException, InvalidKeySpecException, InvalidKeyException {
        long now = System.currentTimeMillis();
        String sign = getSign(clientId, priKey);
        return new Gson().toJson(new KeyString(clientId, sign, String.valueOf(now)));
    }


    private HttpPost getRequest(String clientId, String priKey) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        HttpPost post = new HttpPost(connect_url);
        post.setHeader("accept", "application/json");
        post.setHeader("content-type", "application/json");
        // authInfo组装请求body对象
        String keyString = getKeyString(clientId, priKey);
        ConnectRequest request = new ConnectRequest(keyString);

        // 构造post请求
        StringEntity entity = new StringEntity(new Gson().toJson(request), "UTF-8");
        /*entity.setContentEncoding("UTF-8");
           entity.setContentType("application/json");*/
        post.setEntity(entity);


        return post;
    }

    /**
     *
     * @param clientId appId为开发者联盟应用的ID。
     * @param priKey 私钥
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     * @throws InvalidKeyException
     * @throws SignatureException
     * @throws IOException
     */
    public Result connect(String clientId, String priKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, SignatureException, IOException {
        HttpPost request = getRequest(clientId, priKey);
        return execute(request);

    }

}
