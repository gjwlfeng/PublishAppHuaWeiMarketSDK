package com.zf.publish.app.market.huawei.model.data;

public class UniversalErrorCode {
    public static String getErrorMessage(String errorCode) {
        if ("0".equals(errorCode)) {
            return "成功";
        } else if ("302".equals(errorCode)) {
            return "未获取session";
        } else if ("400100001".equals(errorCode)) {
            return "查询密钥失败";
        } else if ("400100002".equals(errorCode)) {
            return "删除密钥失败";
        } else if ("400100003".equals(errorCode)) {
            return "密钥名称已存在";
        } else if ("400100004".equals(errorCode)) {
            return "密钥超过最大限制";
        } else if ("400100005".equals(errorCode)) {
            return "密钥创建失败";
        } else if ("400100006".equals(errorCode)) {
            return "Auth验证不通过";
        } else if ("400100007".equals(errorCode)) {
            return "密钥验证不通过";
        } else if ("400100008".equals(errorCode)) {
            return "查询应用详情失败";
        } else if ("400100009".equals(errorCode)) {
            return "查询服务详情失败";
        } else if ("400100010".equals(errorCode)) {
            return "语言信息不存在";
        } else if ("400100011".equals(errorCode)) {
            return "调用网关失败";
        } else if ("400100012".equals(errorCode)) {
            return "参数不合法";
        } else if ("400100013".equals(errorCode)) {
            return "更新应用失败";
        } else if ("400100014".equals(errorCode)) {
            return "新增APK失败";
        } else if ("400100015".equals(errorCode)) {
            return "新增RPK失败";
        } else if ("400100016".equals(errorCode)) {
            return "获取上传地址失败";
        } else if ("400100017".equals(errorCode)) {
            return "上传失败";
        } else if ("400100018".equals(errorCode)) {
            return "上传失败";
        } else if ("400100019".equals(errorCode)) {
            return "用户未实名、未签署协议或已被禁用";
        } else if ("90200000".equals(errorCode)) {
            return "系统错误";
        } else if ("90202119".equals(errorCode)) {
            return "APP不存在";
        } else if ("90200001".equals(errorCode)) {
            return "参数错误";
        } else if ("90200002".equals(errorCode)) {
            return "无权限操作";
        } else if ("90208003".equals(errorCode)) {
            return "无权限操作服务，当前服务状态不正确";
        }
        return null;
    }
}
