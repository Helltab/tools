package icu.helltab.itool.verify.normal;

import icu.helltab.itool.verify.core.BaseVerifyRule;
import icu.helltab.itool.object.NullableUtil;

import java.util.Set;

/**
 * 校验多个参数不能为空
 */
public class NotNullRule extends BaseVerifyRule<Set<Object>> {
    @Override
    protected void initMsg() {
        this.msg = "参数为空";
    }

    public void setParamName(String paramName) {
        this.msg = "参数[" + paramName + "]为空";
    }

    @Override
    protected void initVerify() {
        this.verifier = objs -> {
            for (Object obj : objs) {
                if (NullableUtil.isNull(obj)) return false;
            }
            return true;
        };
    }
}
