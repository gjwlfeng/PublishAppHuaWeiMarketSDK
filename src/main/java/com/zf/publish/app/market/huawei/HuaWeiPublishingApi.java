package com.zf.publish.app.market.huawei;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zf.publish.app.market.huawei.http.publish.PublishAppRequest;
import com.zf.publish.app.market.huawei.http.queryAppInfo.QueryAppInfoApi;
import com.zf.publish.app.market.huawei.http.updateFile.VideoInfo;
import com.zf.publish.app.market.huawei.http.updateLangInfo.UpdateLangInfoApi;
import com.zf.publish.app.market.huawei.http.uploadFile.UploadFileApi;
import com.zf.publish.app.market.huawei.exception.HuaWeiException;
import com.zf.publish.app.market.huawei.http.connect.ConnectApi;
import com.zf.publish.app.market.huawei.http.publish.PublishAppApi;
import com.zf.publish.app.market.huawei.http.queryAppInfo.QueryAppInfoResponse;
import com.zf.publish.app.market.huawei.http.queryAppId.QueryAppIdApi;
import com.zf.publish.app.market.huawei.http.queryAppId.QueryAppIdResponse;
import com.zf.publish.app.market.huawei.http.updateFile.FileInfoRequest;
import com.zf.publish.app.market.huawei.http.updateFile.UpdateFileApi;
import com.zf.publish.app.market.huawei.http.updateAppInfo.AppInfoRequest;
import com.zf.publish.app.market.huawei.http.updateAppInfo.UpdateAppInfoApi;
import com.zf.publish.app.market.huawei.http.updateLangInfo.LangInfo;
import com.zf.publish.app.market.huawei.http.uploadUrl.UploadUrlApi;
import com.zf.publish.app.market.huawei.http.uploadUrl.UploadUrlResponse;
import com.zf.publish.app.market.huawei.http.uploadFile.UploadFileResponse;
import com.zf.publish.app.market.huawei.model.data.FileSuffix;
import com.zf.publish.app.market.huawei.model.data.LangType;
import com.zf.publish.app.market.huawei.model.data.ScreenOrientation;
import org.apache.commons.logging.Log;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class HuaWeiPublishingApi {

    private static HuaWeiPublishingApi publishingApi;

    HttpClient httpClient;

    Gson sGson = new GsonBuilder()
            .setPrettyPrinting().create();

    public HuaWeiPublishingApi() {
        this.httpClient = getHttpClient();
    }

    public static HuaWeiPublishingApi getInstance() {
        if (publishingApi == null) {
            synchronized (HuaWeiPublishingApi.class) {
                if (publishingApi == null) {
                    publishingApi = new HuaWeiPublishingApi();
                }
            }
        }
        return publishingApi;
    }


    /**
     * 查询应用appid
     *
     * @param clientId appId为开发者联盟应用的ID。
     * @param priKey   私钥
     * @param packages 应用包名
     * @return 包含appId的对象
     * @throws Exception
     */
    public static QueryAppIdResponse queryAppid(String clientId, String priKey, String... packages) throws Exception {

        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }
        QueryAppIdApi queryAppidApi = new QueryAppIdApi();
        return queryAppidApi.query(packages);
    }

    /**
     * 查看app信息
     *
     * @param clientId 华为clientId
     * @param priKey   私钥
     * @param appId    应用id
     * @param langType 查询语言类型
     * @return 返回 app信息
     * @throws Exception
     */
    public static QueryAppInfoResponse queryAppInfo(String clientId, String priKey, String appId, LangType langType) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }
        QueryAppInfoApi queryAppInfoApi = new QueryAppInfoApi();
        return queryAppInfoApi.query(appId, langType);
    }

    /**
     * 更新app,rpk
     *
     * @param clientId   华为clientId
     * @param priKey     私钥
     * @param appId      应用id
     * @param apkRpkInfo 语言类型
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateApkRpk(String clientId, String priKey, String appId, ApkRpkInfo apkRpkInfo) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }

        System.out.println("连接服务器");

        if (apkRpkInfo == null) {
            throw new IllegalArgumentException("apkRpkInfo参数不能为空 ");
        }

        File apkRpkFile = apkRpkInfo.getApkRpkFile();
        if (apkRpkFile == null) {
            throw new IllegalArgumentException("RPK或者APK文件不能为空");
        }

        if ((!apkRpkFile.exists()) || (!apkRpkFile.isFile())) {
            throw new IllegalArgumentException("RPK或者APK文件。path=" + apkRpkFile.getAbsolutePath());
        }

        String suffix = FileUtils.getFileSuffix(apkRpkFile);
        if (suffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + apkRpkFile.getAbsolutePath());
        }
        FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
        if (fileSuffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + apkRpkFile.getAbsolutePath());
        }

        UploadUrlApi uploadUrlApi = new UploadUrlApi();
        UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
        if (!uploadUrl.isSuc()) {
            throw new HuaWeiException(uploadUrl);
        }

        System.out.println("获取上传地址");

        UploadFileApi uploadFileApi = new UploadFileApi();
        UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, apkRpkFile);
        if (!uploadFileResponse.isSuc()) {
            throw new HuaWeiException(uploadFileResponse);
        }

        System.out.println("上传文件成功");

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.apkRpkType(uploadFileResponse.uploadUrl)
                .setSensitivePermissionDesc(apkRpkInfo.sensitivePermissionDesc).build();
        Result updateAppInfoResult = updateFileApi.updateApkRpkFile(appId, fileInfoRequest);
        if (!updateAppInfoResult.isSuc()) {
            throw new HuaWeiException(updateAppInfoResult);
        }

        System.out.println("更新应用文件地址成功");

        HashMap<LangType, LangInfo> apkRpkUpdateInfos = apkRpkInfo.getApkRpkUpdateInfos();
        for (Map.Entry<LangType, LangInfo> langTypeLangInfoEntry : apkRpkUpdateInfos.entrySet()) {
            UpdateLangInfoApi updateLangInfoApi = new UpdateLangInfoApi();

            LangType langType = langTypeLangInfoEntry.getKey();
            LangInfo langInfo = langTypeLangInfoEntry.getValue();

            Result langInfoResult = updateLangInfoApi.updateLangInfo(appId, langType, langInfo);
            if (!langInfoResult.isSuc()) {
                throw new HuaWeiException(langInfoResult);
            }

            System.out.println("更新应用语言类型（"+langType.value()+"）信息成功");
        }

        PublishAppApi publishAppApi = new PublishAppApi();
        PublishAppRequest publishAppRequest = PublishAppRequest.PublishAppRequestBuilder.getModifyPublishAppRequest(apkRpkInfo.getReleaseTime())
                .setRemark(apkRpkInfo.getRemark()).setChannelId(apkRpkInfo.getChannelId()).build();
        return publishAppApi.publish(appId, publishAppRequest);
    }


    /**
     * 更新app 图标
     *
     * @param clientId 华为clientId
     * @param priKey   私钥
     * @param appId    应用id
     * @param lang     语言类型
     * @param iconFile 图标文件
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateIcon(String clientId, String priKey, String appId, LangType lang, File iconFile) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }

        if (iconFile == null) {
            throw new IllegalArgumentException("icon文件不能为空");
        }

        if ((!iconFile.exists()) || (!iconFile.isFile())) {
            throw new IllegalArgumentException("icon文件。path=" + iconFile.getAbsolutePath());
        }


        String suffix = FileUtils.getFileSuffix(iconFile);
        if (suffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + iconFile.getAbsolutePath());
        }
        FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
        if (fileSuffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + iconFile.getAbsolutePath());
        }

        UploadUrlApi uploadUrlApi = new UploadUrlApi();
        UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
        if (!uploadUrl.isSuc()) {
            throw new HuaWeiException(uploadUrl);
        }

        UploadFileApi uploadFileApi = new UploadFileApi();
        UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, iconFile);
        if (!uploadFileResponse.isSuc()) {
            throw new HuaWeiException(uploadFileResponse);
        }

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appIconType(uploadFileResponse.uploadUrl).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 更新应用介绍视频
     *
     * @param clientId                华为clientId
     * @param priKey                  私钥
     * @param appId                   应用id
     * @param lang                    语言类型
     * @param videoFileList           介绍视频
     * @param videoScreenshotFileList 介绍视频截图
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppIntrVideo(String clientId, String priKey, String appId, LangType lang, ScreenOrientation videoShowType, List<File> videoFileList, List<File> videoScreenshotFileList) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }


        if (videoFileList == null) {
            throw new IllegalArgumentException("视频文件列表不能为空");
        }

        if (videoScreenshotFileList == null) {
            throw new IllegalArgumentException("视频截图文件列表不能为空");
        }

        if (videoFileList.size() > 3) {
            throw new IllegalArgumentException("应用介绍视频至多3个");
        }

        if (videoFileList.size() != videoScreenshotFileList.size()) {
            throw new IllegalArgumentException("视频截图文件个数，与视频文件个数不一致");
        }

        for (File file : videoFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("找不到视频文件。path=" + file.getAbsolutePath());
            }
        }

        for (File file : videoScreenshotFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("找不到视频截图文件。path=" + file.getAbsolutePath());
            }
        }


        List<UploadFileResponse> videoFileUploadResponseList = new ArrayList<>();

        for (File file : videoFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }


            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            videoFileUploadResponseList.add(uploadFileResponse);
        }


        List<UploadFileResponse> videoScreenshotFileUploadResponseList = new ArrayList<>();

        for (File file : videoScreenshotFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            videoScreenshotFileUploadResponseList.add(uploadFileResponse);
        }


        List<VideoInfo> videoInfos = new ArrayList<>();

        for (int i = 0; i < videoFileUploadResponseList.size(); i++) {

            UploadFileResponse videoFileuploadResponse = videoFileUploadResponseList.get(i);
            UploadFileResponse videoScreenshotFileUploadResponse = videoScreenshotFileUploadResponseList.get(i);

            videoInfos.add(new VideoInfo(videoFileuploadResponse.uploadUrl, videoScreenshotFileUploadResponse.uploadUrl));

        }


        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appVideoType(videoShowType, videoInfos).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 更新应用介绍视频
     *
     * @param clientId           华为clientId
     * @param priKey             私钥
     * @param appId              应用id
     * @param lang               语言类型
     * @param screenshotFileList 应用介绍截图
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppScreenshot(String clientId, String priKey, String appId, LangType lang, ScreenOrientation videoShowType, List<File> screenshotFileList) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }


        if (screenshotFileList == null) {
            throw new IllegalArgumentException("视频文件列表不能为空");
        }

        if (screenshotFileList.size() < 3 || screenshotFileList.size() > 5) {
            throw new IllegalArgumentException("应用介绍截图，最多5张请上传最少3张截图");
        }

        for (File file : screenshotFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("应用介绍截图文件。path=" + file.getAbsolutePath());
            }
        }


        List<UploadFileResponse> screenshotFileUploadResponseList = new ArrayList<>();

        for (File file : screenshotFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            screenshotFileUploadResponseList.add(uploadFileResponse);
        }

        List<String> videoInfos = new ArrayList<>();

        for (int i = 0; i < screenshotFileUploadResponseList.size(); i++) {
            UploadFileResponse videoFileuploadResponse = screenshotFileUploadResponseList.get(i);
            videoInfos.add(videoFileuploadResponse.uploadUrl);
        }

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appScreenshotType(videoShowType, videoInfos).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 更新应用介绍视频
     *
     * @param clientId            华为clientId
     * @param priKey              私钥
     * @param appId               应用id
     * @param lang                语言类型
     * @param appRecVideoFileList 推荐视频
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppRecVideoType(String clientId, String priKey, String appId, LangType lang, List<File> appRecVideoFileList, List<File> appRecVideoScreenshotFileList) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }

        if (appRecVideoFileList == null) {
            throw new IllegalArgumentException("推荐视频文件列表不能为空");
        }

        if (appRecVideoScreenshotFileList == null) {
            throw new IllegalArgumentException("推荐视频截图文件列表不能为空");
        }

        if (appRecVideoFileList.size() != 1) {
            throw new IllegalArgumentException("推荐视频最多1个");
        }

        if (appRecVideoScreenshotFileList.size() != 1) {
            throw new IllegalArgumentException("推荐视频截图最多1个");
        }
        for (File file : appRecVideoFileList) {
            if (!file.exists()) {
                throw new IllegalArgumentException("推荐视频文件找不到。path=" + file.getAbsolutePath());
            }
        }

        for (File file : appRecVideoScreenshotFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("推荐视频截图文件找不到。path=" + file.getAbsolutePath());
            }
        }

        List<UploadFileResponse> appRecVideoFileUploadResponses = new ArrayList<>();
        for (File file : appRecVideoFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }


            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            appRecVideoFileUploadResponses.add(uploadFileResponse);
        }


        List<UploadFileResponse> appRecVideoScreenshotFileUploadResponses = new ArrayList<>();
        for (File file : appRecVideoScreenshotFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            appRecVideoScreenshotFileUploadResponses.add(uploadFileResponse);
        }


        List<VideoInfo> videoInfos = new ArrayList<>();

        for (int i = 0; i < appRecVideoFileUploadResponses.size(); i++) {
            UploadFileResponse videoFileUploadResponse = appRecVideoFileUploadResponses.get(i);
            UploadFileResponse appRecVideoScreenshotFileUploadResponse = appRecVideoScreenshotFileUploadResponses.get(i);
            videoInfos.add(new VideoInfo(videoFileUploadResponse.uploadUrl, appRecVideoScreenshotFileUploadResponse.uploadUrl));
        }

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appRecVideoType(videoInfos).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 更新推荐图片
     *
     * @param clientId          华为clientId
     * @param priKey            私钥
     * @param appId             应用id
     * @param lang              语言类型
     * @param appRecPicFileList 推荐图片
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppRecPic(String clientId, String priKey, String appId, LangType lang, List<File> appRecPicFileList) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }


        if (appRecPicFileList == null) {
            throw new IllegalArgumentException("推荐图片文件列表不能为空");
        }

        if (appRecPicFileList.size() > 4) {
            throw new IllegalArgumentException("推荐图片最多4个");
        }


        for (File file : appRecPicFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("推荐图片文件找不到。path=" + file.getAbsolutePath());
            }
        }


        List<UploadFileResponse> appRecPicFileUploadResponses = new ArrayList<>();
        for (File file : appRecPicFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            appRecPicFileUploadResponses.add(uploadFileResponse);
        }


        List<String> videoInfos = new ArrayList<>();
        for (UploadFileResponse appRecPicFileUploadRespons : appRecPicFileUploadResponses) {
            videoInfos.add(appRecPicFileUploadRespons.uploadUrl);
        }

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appRecPicType(videoInfos).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 代理证书或者代理版权图片
     * 尺寸不超过4MB，最多5张
     *
     * @param clientId             华为clientId
     * @param priKey               私钥
     * @param appId                应用id
     * @param lang                 语言类型
     * @param appAgencyCerFileList 推荐图片
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppAgencyCer(String clientId, String priKey, String appId, LangType lang, List<File> appAgencyCerFileList) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }


        if (appAgencyCerFileList == null) {
            throw new IllegalArgumentException("代理证书或者代理版权图片文件列表不能为空");
        }

        if (appAgencyCerFileList.size() > 4) {
            throw new IllegalArgumentException("代理证书或者代理版权图片最多5个");
        }


        for (File file : appAgencyCerFileList) {
            if ((!file.exists()) || (!file.isFile())) {
                throw new IllegalArgumentException("推荐图片文件找不到。path=" + file.getAbsolutePath());
            }
        }


        List<UploadFileResponse> appAgencyCerFileUploadResponses = new ArrayList<>();
        for (File file : appAgencyCerFileList) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }
            appAgencyCerFileUploadResponses.add(uploadFileResponse);
        }


        List<String> videoInfos = new ArrayList<>();
        for (UploadFileResponse appRecPicFileUploadRespons : appAgencyCerFileUploadResponses) {
            videoInfos.add(appRecPicFileUploadRespons.uploadUrl);
        }

        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appAgencyCerType(videoInfos).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 代理证书或者代理版权图片
     * 尺寸不超过4MB，最多5张
     *
     * @param clientId         华为clientId
     * @param priKey           私钥
     * @param appId            应用id
     * @param lang             语言类型
     * @param appEleCerPdfFile 代理证书或者代理版权图片
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppEleCerPdf(String clientId, String priKey, String appId, LangType lang, File appEleCerPdfFile) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }

        if (appEleCerPdfFile == null) {
            throw new IllegalArgumentException("代理证书或者代理版权图片不能为空");
        }

        if ((!appEleCerPdfFile.exists()) || (!appEleCerPdfFile.isFile())) {
            throw new IllegalArgumentException("代理证书或者代理版权图片文件找不到。path=" + appEleCerPdfFile.getAbsolutePath());
        }

        String suffix = FileUtils.getFileSuffix(appEleCerPdfFile);
        if (suffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + appEleCerPdfFile.getAbsolutePath());
        }
        FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
        if (fileSuffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + appEleCerPdfFile.getAbsolutePath());
        }

        UploadUrlApi uploadUrlApi = new UploadUrlApi();
        UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
        if (!uploadUrl.isSuc()) {
            throw new HuaWeiException(connect);
        }

        UploadFileApi uploadFileApi = new UploadFileApi();
        UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, appEleCerPdfFile);
        if (!uploadFileResponse.isSuc()) {
            throw new HuaWeiException(uploadFileResponse);
        }


        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appEleCerPdfType(uploadFileResponse.uploadUrl).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 文化部版号截图
     *
     * @param clientId                      华为clientId
     * @param priKey                        私钥
     * @param appId                         应用id
     * @param lang                          语言类型
     * @param appMcEditionNumberScreenshots 文化部版号截图
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppMcEditionNumberScreenshot(String clientId, String priKey, String appId, LangType lang, List<File> appMcEditionNumberScreenshots) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }


        if (appMcEditionNumberScreenshots == null) {
            throw new IllegalArgumentException("文化部版号截图不能为空");
        }

        for (File appMcEditionNumberScreenshot : appMcEditionNumberScreenshots) {


            if ((!appMcEditionNumberScreenshot.exists()) || (!appMcEditionNumberScreenshot.isFile())) {
                throw new IllegalArgumentException("文化部版号截图文件找不到。path=" + appMcEditionNumberScreenshot.getAbsolutePath());
            }
        }

        List<String> screenshots = new ArrayList<>();
        for (File file : appMcEditionNumberScreenshots) {

            String suffix = FileUtils.getFileSuffix(file);
            if (suffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }
            FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
            if (fileSuffix == null) {
                throw new IllegalArgumentException("不支持上传的文件格式。path=" + file.getAbsolutePath());
            }

            UploadUrlApi uploadUrlApi = new UploadUrlApi();
            UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
            if (!uploadUrl.isSuc()) {
                throw new HuaWeiException(uploadUrl);
            }

            UploadFileApi uploadFileApi = new UploadFileApi();
            UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, file);
            if (!uploadFileResponse.isSuc()) {
                throw new HuaWeiException(uploadFileResponse);
            }

            screenshots.add(uploadFileResponse.uploadUrl);
        }


        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appMcEditionNumberType(screenshots).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }


    /**
     * 文化备案证明截图或者PDF
     * 尺寸不超过4MB
     *
     * @param clientId               华为clientId
     * @param priKey                 私钥
     * @param appId                  应用id
     * @param lang                   语言类型
     * @param cerFilingScreenshotPdf 文化部版号截图
     * @return 返回更新结果
     * @throws InvalidKeySpecException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws SignatureException
     */
    public static Result updateAppMcEditionNumberScreenshot(String clientId, String priKey, String appId, LangType lang, File cerFilingScreenshotPdf) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }

        if (cerFilingScreenshotPdf == null) {
            throw new IllegalArgumentException("文化备案证明截图或者PDF不能为空");
        }

        if ((!cerFilingScreenshotPdf.exists()) || (!cerFilingScreenshotPdf.isFile())) {
            throw new IllegalArgumentException("文化备案证明截图或者PDF截图文件找不到。path=" + cerFilingScreenshotPdf.getAbsolutePath());
        }


        String suffix = FileUtils.getFileSuffix(cerFilingScreenshotPdf);
        if (suffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + cerFilingScreenshotPdf.getAbsolutePath());
        }
        FileSuffix fileSuffix = FileSuffix.fromTypeName(suffix);
        if (fileSuffix == null) {
            throw new IllegalArgumentException("不支持上传的文件格式。path=" + cerFilingScreenshotPdf.getAbsolutePath());
        }

        UploadUrlApi uploadUrlApi = new UploadUrlApi();
        UploadUrlResponse uploadUrl = uploadUrlApi.getUploadUrl(fileSuffix);
        if (!uploadUrl.isSuc()) {
            throw new HuaWeiException(uploadUrl);
        }

        UploadFileApi uploadFileApi = new UploadFileApi();
        UploadFileResponse uploadFileResponse = uploadFileApi.uploadFile(uploadUrl.uploadUrl, uploadUrl.authCode, cerFilingScreenshotPdf);
        if (!uploadFileResponse.isSuc()) {
            throw new HuaWeiException(uploadFileResponse);
        }


        UpdateFileApi updateFileApi = new UpdateFileApi();
        FileInfoRequest fileInfoRequest = FileInfoRequest.AppInfoRequestBuilder.appCerFilingScreenshotPdfType(uploadFileResponse.uploadUrl).build();
        return updateFileApi.updateFile(appId, lang, fileInfoRequest);
    }

    /**
     * 更新应用基本信息
     *
     * @return 返回更新结果
     */
    public static Result updateAppInfo(String clientId, String priKey, String appId, AppInfoRequest appInfoRequest) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }
        UpdateAppInfoApi updateAppInfoApi = new UpdateAppInfoApi();
        return updateAppInfoApi.update(appId, appInfoRequest);
    }


    /**
     * 更新应用基本信息
     *
     * @return 返回更新结果
     */
    public static Result updateLangInfo(String clientId, String priKey, String appId, LangType langType, LangInfo langInfo) throws InvalidKeySpecException, IOException, NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        ConnectApi connectApi = new ConnectApi();
        Result connect = connectApi.connect(clientId, priKey);
        if (!connect.isSuc()) {
            throw new HuaWeiException(connect);
        }
        UpdateLangInfoApi updateLangInfoApi = new UpdateLangInfoApi();
        return updateLangInfoApi.updateLangInfo(appId, langType, langInfo);
    }


    private HttpClient getHttpClient() {

        // 设置CookieStore
        BasicCookieStore cookieStore = new BasicCookieStore();
        return HttpClients.custom()
                .setDefaultCookieStore(cookieStore)
                .build();
    }


}
