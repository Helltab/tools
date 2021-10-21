package com.helltab.repo.tool.work.valid;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Topic
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 9:47
 * @desc 表单验证
 */
public class VerifyForm implements VerifyInf {
    public List<String> msgList = new ArrayList<>();

    boolean flag = true;

    /**
     * 每一次 and 都调用了 test，计算结果
     * @param verifyInf
     * @return
     */
    @Override
    public VerifyInf and(VerifyInf verifyInf) {
        return innerValid(verifyInf, true);
    }


    /**
     * 每一次 or 都调用了 test，计算结果
     * @param verifyInf
     * @return
     */
    @Override
    public VerifyInf or(VerifyInf verifyInf) {
        return innerValid(verifyInf, false);
    }

    /**
     * 拼接测试结果
     *
     * @param verifyInf
     * @param isAnd
     * @return
     */
    private VerifyInf innerValid(VerifyInf verifyInf, boolean isAnd) {

        VerifyResult result = verifyInf.test();
        if (isAnd) {
            flag = result.isFlag() && flag;
        } else {
            // 短路运算 -- 只是验证结果短路，但是相应的检测信息会加上
            // 在 false or true 时，会返回错误信息，如果 true or false 情况不返回错误信息，就会不一致，所以带上了错误信息，但是以最终检验返回的 true 或 false 为准
            if (flag) {
                return setMsgInfo(verifyInf, result);
            }
            flag = result.isFlag() || flag;
        }
        return setMsgInfo(verifyInf, result);
    }

    /**
     * 汇总信息
     *
     * @param verifyInf
     * @param result
     * @return
     */
    private VerifyInf setMsgInfo(VerifyInf verifyInf, VerifyResult result) {
        List<String> msgs = (verifyInf instanceof VerifyForm)?
                               ((VerifyForm) verifyInf).msgList:
                               result.getMsgList();
        msgs.forEach(m->{
            if (!Objects.isNull(m) && !this.msgList.contains(m)) {
                this.msgList.add(m);
            }
        });
        return this;
    }

    /**
     * 包装类返回当前 bool, 并返回拼接消息列表
     *
     * @return
     */
    @Override
    public VerifyResult test() {
        return new VerifyResult(flag, msgList);
    }

    @Override
    public VerifyInf empty() {
        return this;
    }
}
