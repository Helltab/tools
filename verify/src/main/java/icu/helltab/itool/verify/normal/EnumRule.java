package icu.helltab.itool.verify.normal;

import icu.helltab.itool.verify.core.BaseVerifyRule;

public class EmailRule extends BaseVerifyRule<String> {
    @Override
    public void initMsg() {
        this.msg.append("邮箱格式错误");
    }

    @Override
    protected void initVerify() {
        this.verifier = p -> p.matches("\\w+@(\\w+\\.)*\\w+$");
    }
}
