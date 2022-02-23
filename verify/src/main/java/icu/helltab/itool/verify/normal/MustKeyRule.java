package icu.helltab.itool.verify.normal;

import icu.helltab.itool.verify.core.BaseVerifyRule;

/**
 * 不能单独校验, 只是辅助校验的
 */
public class MustKeyRule extends BaseVerifyRule<String> {
    @Override
    protected void initMsg() {
        this.msg = "-";
    }

    public void setFieldName(String msg) {
        this.msg = "这些字段不能同时为空[" + msg + "]";
    }

    @Override
    protected void initVerify() {
        this.verifier = p -> false;
    }
}
