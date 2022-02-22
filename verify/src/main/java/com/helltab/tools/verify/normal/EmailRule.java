package com.helltab.tools.verify.normal;

import com.helltab.tools.verify.core.BaseVerifyRule;

public class EmailRule extends BaseVerifyRule<String> {
    @Override
    protected void initMsg() {
        this.msg = "邮箱格式错误";
    }

    @Override
    protected void initVerify() {
        this.verifier = p -> p.matches("\\w+@(\\w+\\.)*\\w+$");
    }
}
