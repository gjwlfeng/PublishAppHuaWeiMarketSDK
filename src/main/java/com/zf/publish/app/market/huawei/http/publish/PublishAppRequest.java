package com.zf.publish.app.market.huawei.http.publish;

import java.util.Date;

/**
 * 发布应用请求参数
 */
public class PublishAppRequest {

    private final static int REMARK_MIN_LENGTH = 10;
    private final static int REMARK_MAX_LENGTH = 300;


    private PublishAppRequest(String releaseTime, String remark, String channelId) {
        this.releaseTime = releaseTime;
        this.remark = remark;
        this.channelId = channelId;
    }

    /**
     * 指定发布时间，UTC时间。
     */
    private String releaseTime;

    /**
     * 提审发布备注。可以为空，如果设置，长度范围为10-300。
     */
    public String remark;

    /**
     * 渠道ID，可选。
     */
    public String channelId;


    public String getReleaseTime() {
        return releaseTime;
    }

    public String getRemark() {
        return remark;
    }

    public String getChannelId() {
        return channelId;
    }


    @Override
    public String toString() {
        return "PublishAppRequest{" +
                "releaseTime='" + releaseTime + '\'' +
                ", remark='" + remark + '\'' +
                ", channelId='" + channelId + '\'' +
                '}';
    }

    /**
     * 上传发布信息类
     */
    public static class PublishAppRequestBuilder {
        /**
         * 指定发布时间，UTC时间。
         */
        private Date releaseTime;

        /**
         * 提审发布备注。可以为空，如果设置，长度范围为10-300。
         */
        public String remark;

        /**
         * 渠道ID，可选。
         */
        public String channelId;

        public static PublishAppRequestBuilder getModifyPublishAppRequest(Date releaseTime) {
            PublishAppRequestBuilder request = new PublishAppRequestBuilder();
            request.releaseTime = releaseTime;
            return request;
        }

        public static PublishAppRequestBuilder getModifyPublishAppRequest() {
            PublishAppRequestBuilder request = new PublishAppRequestBuilder();
            return request;
        }

        public PublishAppRequestBuilder setRemark(String remark) {
            if (remark != null && (remark.length() < REMARK_MIN_LENGTH || remark.length() > REMARK_MAX_LENGTH)) {
                throw new IllegalArgumentException("提审发布备注。可以为空，如果设置，长度范围为" + REMARK_MIN_LENGTH + "-" + REMARK_MAX_LENGTH + "。");
            }
            this.remark = remark;
            return this;
        }

        public PublishAppRequestBuilder setChannelId(String channelId) {
            this.channelId = channelId;
            return this;
        }

        public PublishAppRequestBuilder setReleaseTime(Date releaseTime) {
            this.releaseTime = releaseTime;
            return this;
        }

        public PublishAppRequest build() {
            return new PublishAppRequest(this.releaseTime.toString(), this.remark, this.channelId);
        }
    }
}
