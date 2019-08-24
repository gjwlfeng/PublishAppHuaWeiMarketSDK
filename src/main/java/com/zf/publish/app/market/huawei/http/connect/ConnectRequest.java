package com.zf.publish.app.market.huawei.http.connect;

public class ConnectRequest {

    /**
     * key_string :
     */

    private String key_string;

    public ConnectRequest(String key_string) {
        this.key_string = key_string;
    }

    public String getKey_string() {
        return key_string;
    }

    public void setKey_string(String key_string) {
        this.key_string = key_string;
    }
}
