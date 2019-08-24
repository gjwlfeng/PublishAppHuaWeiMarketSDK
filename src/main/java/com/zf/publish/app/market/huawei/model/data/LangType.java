package com.zf.publish.app.market.huawei.model.data;

public enum LangType {

    Arabic("ar"),
    Azerbaijani("az-AZ"),
    Basque("eu-ES"),
    Belarusian("be"),
    Bengali("bn-BD"),
    Bosnian("bs"),
    Bulgarian("bg"),
    Burmese("my-MM"),
    Catalan("ca"),
    Chinese_PRC("zh-CN"),
    Chinese_Taiwan("zh-TW"),
    Chinese_Hong_Kong("zh-HK"),
    Croatian("hr"),
    Czech("cs-CZ"),
    Danish("da-DK"),
    Dutch("nl-NL"),
    English_US("en-US"),
    English_UK("en-GB"),
    Estonian("et"),
    Filipino("fil"),
    Finnish("fi-FI"),
    French_France("fr-FR"),
    Galician("gl-ES"),
    Georgian("ka-GE"),
    German("de-DE"),
    Greek("el-GR"),
    Hebrew("iw-IL"),
    Hindi("hi-IN"),
    Hungarian("hu-HU"),
    Indonesian("id"),
    Italian("it-IT"),
    Japanese("ja-JP"),
    Javanese("jv"),
    Kazakh("kk"),
    Khmer("km-KH"),
    Korean_South_Korea("ko-KR"),
    Lao("lo-LA"),
    Latvian("lv"),
    Lithuanian("lt"),
    Macedonian("mk-MK"),
    Malay("ms"),
    Nepali("ne-NP"),
    Norwegian("no-NO"),
    Persian("fa"),
    Polish("pl-PL"),
    Portuguese_Brazil("pt-BR"),
    Portuguese_Portugal("pt-PT"),
    Romanian("ro"),
    Russian("ru-RU"),
    Serbian("sr"),
    Sinhala("si-LK"),
    Slovak("sk"),
    Slovenian("sl"),
    Spanish_Latin_America("es-419"),
    Spanish_Spain("es-ES"),
    Swedish("sv-SE"),
    Thai("th"),
    Tibetan("bo"),
    Turkish("tr-TR"),
    Ukrainian("uk"),
    Urdu("ur"),
    Uzbek("uz"),
    Vietnamese("vi");

    private String value;

    private LangType(String value) {
        this.value = value;
    }

    public static LangType fromLangTypeName(String typeName) {
        for (LangType type : LangType.values()) {
            if (type.value.equals(typeName)) {
                return type;
            }
        }
        return null;
    }

    public String value() {
        return value;
    }
}
