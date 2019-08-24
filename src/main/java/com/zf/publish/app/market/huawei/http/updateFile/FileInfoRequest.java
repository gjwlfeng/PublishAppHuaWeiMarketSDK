package com.zf.publish.app.market.huawei.http.updateFile;


import com.zf.publish.app.market.huawei.model.data.ScreenOrientation;

import java.util.List;

/**
 * 上传，更新 icon ,RPK或者APK ,应用介绍视频,推荐视频,推荐图片,电子证书PDF文件,文化备案证明截图或者PDF,文化部版号截图 数据请求类
 */
public class FileInfoRequest<T> {

    /**
     * 0：icon
     * 需上传完整圆角图标。尺寸：216*216px，圆角弧度 32px，图片格式：PNG（不允许上传jpg）最大2MB
     */
    public final static int APP_ICON_TYPE = 0;
    /**
     * 1：应用介绍视频，最多3个视频以及对应海报
     * 介绍视频（1080*1920，宽高比为9:16，横屏为1920*1080，宽高比为16:9），支持mov、MP4格式，大小要求500M以内，视频长度15秒-2分钟
     * 视频海报支持上传JPG、JPEG或PNG格式，截图不能小于450*800像素（宽高比为9:16，横屏16:9），单张图片最大为2MB
     */
    public final static int APP_VIDEO_TYPE = 1;
    /**
     * 2：应用介绍截图，最多5张
     * 请上传最少3张截图，支持JPG、JPEG或PNG格式，截图不小于：450*800（宽高比为9:16，横屏16:9），单张图片最大为2M
     */
    public final static int APP_SCREENSHOT_TYPE = 2;

    /**
     * 3：推荐视频，1个推荐视频以及对应海报
     * 应用推荐视频，建议分辨率1600*1200或1200*900，宽高比为4:3，支持mov、MP4格式，大小要求500M以内，视频长度15秒-2分钟
     * 视频海报支持上传JPG、JPEG或PNG格式，截图不能小于960*720像素（宽高比为4:3），单张图片最大为2MB
     */
    public final static int APP_REC_VIDEO_TYPE = 3;
    /**
     * 推荐图片，最多4张
     * 分辨率必须按照以下顺序在data中更新，大小均不超过2MB
     * 1080w*684h,1776w*591h, 1280w*720h,1080w*1350h
     */
    public final static int APP_REC_PIC_TYPE = 4;
    /**
     * RPK或者APK
     */
    public final static int APP_RPK_TYPE = 5;

    /**
     * 代理证书或者代理版权图片
     * 尺寸不超过4MB，最多5张
     */
    public final static int APP_AGENCY_CER_TYPE = 6;

    /**
     * 7：电子证书PDF文件
     * 尺寸不超过5MB
     */
    public final static int APP_ELE_CER_PDF_TYPE = 7;

    /**
     * 8：文化部版号截图
     * 尺寸不超过4MB，最多3张
     */
    public final static int APP_MC_EDITION_NUMBER_TYPE = 8;

    /**
     * 9：文化备案证明截图或者PDF
     * 尺寸不超过4MB
     */
    public final static int APP_CER_FILING_SCREENSHOT_PDF_TYPE = 9;

    /**
     * 当type为2时该参数必选。
     * 截图展现方式。
     * ● 0：竖屏
     * ●1：横屏
     */
    private Integer showType;
    /**
     * 当type为1时该参数必选。
     * 视频展现方式。
     * ● 0：竖屏
     * ●1：横屏
     */
    private Integer videoShowType;
    /**
     * 上传内容内类型
     */
    private int type;

    private T data;
    /**
     * 敏感权限说明。
     */
    private String sensitivePermissionDesc;

    private FileInfoRequest(int type, Integer showType, Integer videoShowType, T data, String sensitivePermissionDesc) {
        this.showType = showType;
        this.videoShowType = videoShowType;
        this.type = type;
        this.data = data;
        this.sensitivePermissionDesc = sensitivePermissionDesc;
    }

    public int getType() {
        return type;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "UpdateAppInfo{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    public static class AppInfoRequestBuilder<T> {

        private ScreenOrientation showType;
        private ScreenOrientation videoShowType;
        private int type;
        private T data;
        private String sensitivePermissionDesc;

        public AppInfoRequestBuilder(int type, ScreenOrientation showType, ScreenOrientation videoShowType, T data) {
            this.showType = showType;
            this.videoShowType = videoShowType;
            this.type = type;
            this.data = data;
        }

        /**
         * 获取 更新 icon 参数对象
         *
         * @param url 地址
         * @return 返回更新 更新 icon 参数对象
         */
        public static AppInfoRequestBuilder<String> appIconType(String url) {
            return new AppInfoRequestBuilder<String>(APP_ICON_TYPE, null, null, url);
        }


        /**
         * 获取 更新 应用介绍视频 参数对象
         *
         * @param videoInfoList 地址
         * @param videoShowType 视频展现方式。 0：竖屏,1：横屏
         * @return 返回更新 更新 应用介绍截图 参数对象
         */
        public static AppInfoRequestBuilder<List<VideoInfo>> appVideoType(ScreenOrientation videoShowType, List<VideoInfo> videoInfoList) {
            return new AppInfoRequestBuilder<List<VideoInfo>>(APP_VIDEO_TYPE, null, videoShowType, videoInfoList);
        }


        /**
         * 获取 更新 应用介绍截图 参数对象
         *
         * @param urlList 地址
         * @return 返回更新 更新 应用介绍截图 参数对象
         */
        public static AppInfoRequestBuilder<String> appScreenshotType(ScreenOrientation showType, List<String> urlList) {
            String urls = String.join(",", urlList.toArray(new String[]{}));
            return new AppInfoRequestBuilder<String>(APP_SCREENSHOT_TYPE, showType, null, urls);
        }


        /**
         * 获取 更新 推荐视频 参数对象
         *
         * @param data 地址
         * @return 返回更新 更新 推荐视频 参数对象
         */
        public static AppInfoRequestBuilder<List<VideoInfo>> appRecVideoType(List<VideoInfo> data) {
            return new AppInfoRequestBuilder<List<VideoInfo>>(APP_REC_VIDEO_TYPE, null, null, data);
        }


        /**
         * 获取 更新 推荐图片 参数对象
         *
         * @param urlList 地址
         * @return 返回更新 更新 推荐图片 参数对象
         */
        public static AppInfoRequestBuilder<String> appRecPicType(List<String> urlList) {
            String urls = String.join(",", urlList.toArray(new String[]{}));
            return new AppInfoRequestBuilder<String>(APP_REC_PIC_TYPE, null, null, urls);
        }

        /**
         * 获取 更新 RPK或者APK 参数对象
         *
         * @param url 地址
         * @return 返回更新 更新 RPK或者APK 参数对象
         */
        public static AppInfoRequestBuilder apkRpkType(String url) {
            return new AppInfoRequestBuilder<String>(APP_RPK_TYPE, null, null, url);
        }

        /**
         * 获取 更新 代理证书或者代理版权图片 参数对象
         *
         * @param urlList 地址
         * @return 返回更新 更新 代理证书或者代理版权图片 参数对象
         */
        public static AppInfoRequestBuilder<String> appAgencyCerType(List<String> urlList) {
            String urls = String.join(",", urlList.toArray(new String[]{}));
            return new AppInfoRequestBuilder<String>(APP_AGENCY_CER_TYPE, null, null, urls);
        }


        /**
         * 获取 更新 电子证书PDF文件 参数对象
         *
         * @param url 地址
         * @return 返回更新 更新 电子证书PDF文件 参数对象
         */
        public static AppInfoRequestBuilder appEleCerPdfType(String url) {
            return new AppInfoRequestBuilder<String>(APP_ELE_CER_PDF_TYPE, null, null, url);
        }

        /**
         * 获取 更新 文化部版号截图 参数对象
         *
         * @param urlList 地址
         * @return 返回更新 更新 文化部版号截图 参数对象
         */

        public static AppInfoRequestBuilder<String> appMcEditionNumberType(List<String> urlList) {
            String urls = String.join(",", urlList.toArray(new String[]{}));
            return new AppInfoRequestBuilder<String>(APP_MC_EDITION_NUMBER_TYPE, null, null, urls);
        }

        /**
         * 获取 文化备案证明截图或者PDF 参数对象
         *
         * @param url 地址
         * @return 更新 文化备案证明截图或者PDF 参数对象
         */
        public static AppInfoRequestBuilder appCerFilingScreenshotPdfType(String url) {
            return new AppInfoRequestBuilder<String>(APP_CER_FILING_SCREENSHOT_PDF_TYPE, null, null, url);
        }

        /**
         * 敏感权限说明。
         *
         * @param sensitivePermissionDesc
         */
        public AppInfoRequestBuilder setSensitivePermissionDesc(String sensitivePermissionDesc) {
            this.sensitivePermissionDesc = sensitivePermissionDesc;
            return this;
        }

        public FileInfoRequest build() {
            return new FileInfoRequest<T>(type,
                    showType == null ? null : showType.orientation(),
                    videoShowType == null ? null : videoShowType.orientation(),
                    data, null);
        }


    }
}


