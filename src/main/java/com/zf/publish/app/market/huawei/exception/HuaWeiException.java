package com.zf.publish.app.market.huawei.exception;

import com.zf.publish.app.market.huawei.Result;

public class HuaWeiException extends RuntimeException {

    Result result;

    public HuaWeiException(Result result) {
        super(result.toString());;
    }

    public Result getResult() {
        return result;
    }
}
