package com.zf.publish.app.market.huawei.http.updateFile;

/**
 * 需要上传的视频信息类
 */
public class VideoInfo {

    private String vedioUrl;
    private String frameUrl;

    public VideoInfo(String vedioUrl, String frameUrl) {
        this.vedioUrl = vedioUrl;
        this.frameUrl = frameUrl;
    }
}
