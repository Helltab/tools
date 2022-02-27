package icu.helltab.itool.verify.core;

import icu.helltab.itool.object.ThreadLocalUtil;
import icu.helltab.itool.result.BaseResult;

/**
 * 校验法庭
 * 承担校验的规则输入|流程控制|最终结果输出
 *
 * @author helltab
 */
public class VerifyCourt<T> {
    private T param;
    private VerifyInf<T> verifier;
    private BaseVerifyRule<T> rule;

    static {
        ThreadLocalUtil.set(new Result());
    }

    public static Result result() {
        return ThreadLocalUtil.get(Result.class);
    }

    protected VerifyCourt(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
        this.param = param;
        this.rule = BaseVerifyRule.getInstance(ruleClazz);
        // 默认为 true, 添加到校验头节点
        if (null == this.verifier) {
            this.verifier = o -> true;
        }
        this.verifier = this.verifier.and(this.rule.getVerifier(), this.rule.getMsg(), param);
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
    public VerifyCourt<T> and(VerifyCourt<T> court) {
        this.verifier = this.verifier.and(court.verifier, null, null);
        return this;
    }

    /**
     * 并行连接新的校验法庭
     *
     * @param court
     * @return
     */
    public VerifyCourt<T> or(VerifyCourt<T> court) {
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
    public VerifyCourt<T> a(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
        BaseVerifyRule<T> rule = new VerifyCourt<>(ruleClazz, param).getRule();
        this.verifier = this.verifier.and(rule.verifier, rule.msg, param);
        return this;
    }


    /**
     * 并行连接规则
     *
     * @param ruleClazz
     * @param param
     * @return
     */
    public VerifyCourt<T> o(Class<? extends BaseVerifyRule<T>> ruleClazz, T param) {
        BaseVerifyRule<T> rule = new VerifyCourt<>(ruleClazz, param).getRule();
        this.verifier = this.verifier.or(rule.verifier, rule.msg, param);
        return this;
    }

    /**
     * 串行连接规则
     *
     * @param rule
     * @param param
     * @return
     */
    public VerifyCourt<T> a(BaseVerifyRule<T> rule, T param) {
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
    public VerifyCourt<T> o(BaseVerifyRule<T> rule, T param) {
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

    public T getParam() {
        return param;
    }

    public VerifyInf<T> getVerifier() {
        return verifier;
    }

    public BaseVerifyRule<T> getRule() {
        return rule;
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
