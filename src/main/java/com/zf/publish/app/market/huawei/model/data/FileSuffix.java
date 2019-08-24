package com.zf.publish.app.market.huawei.model.data;

/**
 * apk/rpk/pdf/jpg/jpeg/png/bmp/mp4/mov
 */
public enum FileSuffix {

    APK("apk"),
    RPK("rpk"),
    PDF("pdf"),
    JPG("jpg"),
    JPEG("jpeg"),
    PNG("png"),
    BMP("bmp"),
    MP4("mp4"),
    MOV("mov");

    private String suffix;

    FileSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * 根据类型的名称，返回类型的枚举实例。
     *
     * @param suffix 类型名称
     */
    public static FileSuffix fromTypeName(String suffix) {
        for (FileSuffix type : FileSuffix.values()) {
            if (type.getSuffix().equals(suffix)) {
                return type;
            }
        }
        return null;
    }

    public String getSuffix() {
        return this.suffix;
    }
}