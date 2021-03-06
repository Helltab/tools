package icu.helltab.itool.verify.core;


import icu.helltab.itool.object.ThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * 校验父类
 * 所有的校验规则都应该继承这个类
 */
public abstract class BaseVerifyRule<T> {
    private static final Map<String, BaseVerifyRule<?>> RULE_MAP;
    static {
        RULE_MAP = new HashMap<>();
    }

    public static <T> BaseVerifyRule<T> build(Class<? extends BaseVerifyRule<T>> clazz) {
        try {
            return ThreadLocalUtil.get(clazz.newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BaseVerifyRule<T>() {
            @Override
            protected void initMsg() { }

            @Override
            protected void initVerify() { }
        };
    }
    public void setParamName(String paramName) {

    }
    /**
     * 获取规则
     * 简单单例模式
     * @param rule
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> BaseVerifyRule<T> getInstance(Class<? extends BaseVerifyRule<T>> rule) {
        String simpleName = rule.getSimpleName();
        BaseVerifyRule<?> verifyRule = RULE_MAP.get(simpleName);
        if(verifyRule == null) {
            try {
                verifyRule = rule.newInstance();
            } catch (Exception ignore) { }
            RULE_MAP.put(simpleName, verifyRule);
        }
        return (BaseVerifyRule<T>) verifyRule;
    }
    protected VerifyInf<T> verifier;
    protected String msg;


    protected BaseVerifyRule() {
        initMsg();
        initVerify();
    }

    /**
     * 子类必须要设定验证错误信息
     */
    protected abstract void initMsg();

    /**
     * 子类必须实现校验逻辑
     */
    protected abstract void initVerify();

    public VerifyInf<T> getVerifier() {
        return verifier;
    }

    public String getMsg() {
        return msg;
    }

}
