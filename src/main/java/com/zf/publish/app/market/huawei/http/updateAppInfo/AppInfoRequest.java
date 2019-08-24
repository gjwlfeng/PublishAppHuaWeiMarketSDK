package com.zf.publish.app.market.huawei.http.updateAppInfo;

import com.zf.publish.app.market.huawei.Result;
import com.zf.publish.app.market.huawei.model.data.CountryCode;
import com.zf.publish.app.market.huawei.model.data.LangType;

import java.util.HashMap;

public class AppInfoRequest extends Result {

    /**
     * 默认语言
     */
    public String defaultLang;

    /**
     * 二级分类，固定ID
     */
    public Integer childType;

    /**
     * 三级分类
     */
    public String grandChildType;

    /**
     * 隐私声明地址。
     */
    public String privacyPolicy;

    /**
     * ● 1 荣耀立方(路由器)
     * ● 2 荣耀创（TRON）
     * ● 3 秘盒
     * ● 4 手机
     * ● 5 平板
     * ● 6 VR
     * ● 7 手表
     * ● 8 TV
     * ● 9 路由器。
     * 4手机必选
     * 当前仅支持4/5/7
     * 复选格式如：1,2,3或1,3。
     */
    public String appAdapters;

    /**
     * 应用联网类型。
     * ● 1：单机(默认)
     * ● 2 网游
     */
    public Integer appNetType;

    /**
     * ● 1 免费
     * ● 0 付费
     */
    public Integer isFree;

    /**
     * 价格，json字符串。
     * {"CN":"12"}，参考联盟
     */
    public HashMap<String, Object> price;

    /**
     * 国家码逗号分隔例如：CN,DE,MC。
     */
    public String publishCountry;

    /**
     * isAppForcedUpdate
     */
    public Integer isAppForcedUpdate;


    /**
     * 是否允许华为应用市场抓包，配置了下载地址可以配置该项目。
     * ● 0 不允许
     * ● 1 允许
     */
    public Integer hispaceAutoDown;

    /**
     * 应用内付费道具类型。
     */
    public String appTariffType;

    /**
     * 版号信息
     */
    public String publicationNumber;

    /**
     * 文化部备案编号。
     */
    public String cultureRecordNumber;

    /**
     * developerAddr
     */
    public String developerAddr;

    /**
     * 开发者邮件。
     */
    public String developerEmail;

    /**
     * 开发者电话。
     */
    public String developerPhone;

    /**
     * 开发者官网。
     */
    public String developerWebsite;


    /**
     * 上传 app 语言描述信息类
     */
    public static class AppInfoRequestBuilder {
        /**
         * 默认语言
         */
        private LangType defaultLang;

        /**
         * 二级分类，固定ID
         */
        private Integer childType;

        /**
         * 三级分类
         */
        private String grandChildType;

        /**
         * 隐私声明地址。
         */
        private String privacyPolicy;

        /**
         * ● 1 荣耀立方(路由器)
         * ● 2 荣耀创（TRON）
         * ● 3 秘盒
         * ● 4 手机
         * ● 5 平板
         * ● 6 VR
         * ● 7 手表
         * ● 8 TV
         * ● 9 路由器。
         * 4手机必选
         * 当前仅支持4/5/7
         * 复选格式如：1,2,3或1,3。
         */
        private String appAdapters;

        /**
         * 应用联网类型。
         * ● 1：单机(默认)
         * ● 2 网游
         */
        private Integer appNetType;

        /**
         * ● 1 免费
         * ● 0 付费
         */
        private Boolean isFree;

        /**
         * 价格，json字符串。
         * {"CN":"12"}，参考联盟
         */
        private HashMap<String, Double> price = new HashMap<>();

        /**
         * 国家码逗号分隔例如：CN,DE,MC。
         */
        private CountryCode publishCountry;

        /**
         * 强制升级
         * ● 0 不强制
         * ● 1强制
         */
        private Boolean isAppForcedUpdate;


        /**
         * 是否允许华为应用市场抓包，配置了下载地址可以配置该项目。
         * ● 0 不允许
         * ● 1 允许
         */
        private Boolean hispaceAutoDown;

        /**
         * 应用内付费道具类型。
         */
        private String appTariffType;

        /**
         * 版号信息
         */
        private String publicationNumber;

        /**
         * 文化部备案编号。
         */
        private String cultureRecordNumber;

        /**
         * developerAddr
         */
        private String developerAddr;

        /**
         * 开发者邮件。
         */
        private String developerEmail;

        /**
         * 开发者电话。
         */
        private String developerPhone;

        /**
         * 开发者官网。
         */
        private String developerWebsite;

        /**
         * 得到发布应用信息的构造者
         *
         * @return
         */
        public static AppInfoRequestBuilder publishAppInfoRequestBuilder(LangType langType, CountryCode countryCode, String privacyPolicy, String appAdapters, boolean isFree, String developerPhone, String developerAddr, String developerEmail, String developerWebsite) {
            return new AppInfoRequestBuilder(langType, countryCode, privacyPolicy, appAdapters, isFree, developerPhone, developerAddr, developerEmail, developerWebsite);
        }

        /**
         * 得到更新应用信息的构造者
         *
         * @return
         */
        public static AppInfoRequestBuilder updateAppInfoRequestBuilder() {
            return new AppInfoRequestBuilder();
        }


        private AppInfoRequestBuilder() {
        }

        private AppInfoRequestBuilder(LangType langType, CountryCode countryCode, String privacyPolicy, String appAdapters, boolean isFree, String developerPhone, String developerAddr, String developerEmail, String developerWebsite) {
            this.defaultLang = langType;
            this.privacyPolicy = privacyPolicy;
            this.appAdapters = appAdapters;
            this.isFree = isFree;
            this.publishCountry = countryCode;
            this.developerEmail = developerEmail;
            this.developerPhone = developerPhone;
            this.developerWebsite = developerWebsite;
            this.developerAddr = developerAddr;
        }

        public AppInfoRequestBuilder setChildType(Integer childType) {
            this.childType = childType;
            return this;
        }

        public AppInfoRequestBuilder setGrandChildType(String grandChildType) {
            this.grandChildType = grandChildType;
            return this;
        }

        public AppInfoRequestBuilder setAppAdapters(String appAdapters) {
            this.appAdapters = appAdapters;
            return this;
        }

        public AppInfoRequestBuilder setAppNetType(Integer appNetType) {
            this.appNetType = appNetType;
            return this;
        }

        public AppInfoRequestBuilder addPrice(CountryCode countryCode, Double price) {
            this.price.put(countryCode.code, price);
            return this;
        }

        public AppInfoRequestBuilder setPublishCountry(CountryCode countryCode) {
            this.publishCountry = countryCode;
            return this;
        }

        public AppInfoRequestBuilder setIsAppForcedUpdate(Boolean isAppForcedUpdate) {
            this.isAppForcedUpdate = isAppForcedUpdate;
            return this;
        }

        public AppInfoRequestBuilder setHispaceAutoDown(Boolean hispaceAutoDown) {
            this.hispaceAutoDown = hispaceAutoDown;
            return this;
        }

        public AppInfoRequestBuilder setAppTariffType(String appTariffType) {
            this.appTariffType = appTariffType;
            return this;
        }

        public AppInfoRequestBuilder setPublicationNumber(String publicationNumber) {
            this.publicationNumber = publicationNumber;
            return this;
        }

        public AppInfoRequestBuilder setCultureRecordNumber(String cultureRecordNumber) {
            this.cultureRecordNumber = cultureRecordNumber;
            return this;
        }

        public AppInfoRequest build() {

            AppInfoRequest appInfoRequest = new AppInfoRequest();

            appInfoRequest.defaultLang = this.defaultLang.value();
            appInfoRequest.childType = this.childType;
            appInfoRequest.grandChildType = this.grandChildType;
            appInfoRequest.privacyPolicy = this.privacyPolicy;
            appInfoRequest.appAdapters = this.appAdapters;
            appInfoRequest.appNetType = this.appNetType;
            appInfoRequest.isFree = this.isFree ? 1 : 0;

            if (this.price != null && this.price.size() > 0) {
                appInfoRequest.price.putAll(this.price);
            }

            appInfoRequest.publishCountry = this.publishCountry.code;
            appInfoRequest.isAppForcedUpdate = this.isAppForcedUpdate ? 1 : 0;
            appInfoRequest.hispaceAutoDown = this.hispaceAutoDown ? 1 : 0;
            appInfoRequest.appTariffType = this.appTariffType;
            appInfoRequest.publicationNumber = this.publicationNumber;
            appInfoRequest.cultureRecordNumber = this.cultureRecordNumber;

            appInfoRequest.developerAddr = this.developerAddr;
            appInfoRequest.developerEmail = this.developerEmail;
            appInfoRequest.developerPhone = this.developerPhone;
            appInfoRequest.developerWebsite = this.developerWebsite;

            return appInfoRequest;
        }
    }


}
