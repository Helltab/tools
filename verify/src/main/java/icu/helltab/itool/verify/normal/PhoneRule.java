package icu.helltab.itool.verify.normal;

import icu.helltab.itool.verify.core.BaseVerifyRule;

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
