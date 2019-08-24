package com.zf.publish.app.market.huawei.http.connect;

public class KeyString {


    /**
     * clientId : string
     * time : 距离1970-01-01的毫秒数
     * sign : 签名字符串
     */

    private String clientId;
    private String sign;
    private String time;

    public KeyString(String clientId, String sign, String time) {
        this.clientId = clientId;
        this.sign = sign;
        this.time = time;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
