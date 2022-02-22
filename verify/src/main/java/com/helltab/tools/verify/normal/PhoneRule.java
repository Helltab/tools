package com.helltab.tools.verify.normal;

import com.helltab.tools.verify.core.BaseVerifyRule;

/**
 * 校验手机号码
 */
public class PhoneRule extends BaseVerifyRule<String> {
    @Override
    protected void initMsg() {
        this.msg = "电话号码格式错误";
    }

    @Override
    protected void initVerify() {
        this.verifier = p -> p.matches("1\\d{10}");
    }
}
