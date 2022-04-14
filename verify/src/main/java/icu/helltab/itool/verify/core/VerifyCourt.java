package icu.helltab.itool.verify.core;

import icu.helltab.itool.object.ThreadLocalUtil;
import icu.helltab.itool.result.BaseResult;

/**
 * 校验法庭
 * 承担校验的规则输入|流程控制|最终结果输出
 *
 * @author helltab
 */
public class VerifyCourt {
    private Object param;
    private VerifyInf<Object> verifier;

    static {
        ThreadLocalUtil.set(new Result());
    }

    public static Result result() {
        return ThreadLocalUtil.get(new Result());
    }

    protected <T> VerifyCourt(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
        BaseVerifyRule<T> instance = BaseVerifyRule.getInstance(ruleClazz);
        // 默认为 true, 添加到校验头节点
        if (null == this.verifier) {
            this.verifier = o -> true;
        }
        this.verifier = this.verifier.and(instance.getVerifier(), instance.getMsg(), param);
    }

    protected VerifyCourt() {
        this.verifier = o -> true;
    }


    /**
     * 串行连接新的校验法庭
     *
     * @param court
     * @return
     */
    public VerifyCourt and(VerifyCourt court) {
        this.verifier = this.verifier.and(court.verifier, null, null);
        return this;
    }

    /**
     * 并行连接新的校验法庭
     *
     * @param court
     * @return
     */
    public VerifyCourt or(VerifyCourt court) {
        this.verifier = this.verifier.or(court.verifier, null, null);
        return this;
    }

    /**
     * 串行连接规则
     *
     * @param ruleClazz
     * @param param
     * @return
     */
    public <T> VerifyCourt and(Class<? extends BaseVerifyRule<T>> ruleClazz, T param)  {
        try {
            BaseVerifyRule<T> rule = BaseVerifyRule.build(ruleClazz);
            this.verifier = this.verifier.and(rule.verifier, rule.msg, param);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }


    /**
     * 并行连接规则
     *
     * @param ruleClazz
     * @param param
     * @return
     */
    public <T> VerifyCourt or(Class<? extends BaseVerifyRule<T>> ruleClazz, T param)  {
        try {
            BaseVerifyRule<T> rule = BaseVerifyRule.build(ruleClazz);
            this.verifier = this.verifier.or(rule.verifier, rule.msg, param);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * 串行连接规则
     *
     * @param rule
     * @param param
     * @return
     */
    public <T> VerifyCourt and(BaseVerifyRule<T> rule, T param) {
        this.verifier = this.verifier.and(rule.verifier, rule.msg, param);
        return this;
    }

    /**
     * 串行连接规则
     *
     * @param rule
     * @param param
     * @return
     */
    public VerifyCourt or(BaseVerifyRule<Object> rule, Object param) {
        this.verifier = this.verifier.or(rule.verifier, rule.msg, param);
        return this;
    }

    /**
     * 执行惰性校验
     *
     * @return
     */
    public Result judge() {
        this.verifier.test(param);
        return result();
    }

    public Object getParam() {
        return param;
    }

    public VerifyInf<Object> getVerifier() {
        return verifier;
    }



    /**
     * 校验结果
     * 因校验结果需要阻塞处理, 检验过程不会存在多线程的场景, 因此校验产生的错误信息记录到本地线程空间中
     */
    public static class Result extends BaseResult {
        private boolean pass;
        @Override
        public void errorDetail() {
            this.pass = false;
        }

        public Result() {
            pass = true;
        }
        public boolean isPass() {
            return pass;
        }

    }
}
