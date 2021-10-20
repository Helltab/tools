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
public class ValidForm implements ValidInf {
    public List<String> msgList = new ArrayList<>();

    boolean flag = true;

    /**
     * 每一次 and 都调用了 test，计算结果
     * @param validInf
     * @return
     */
    @Override
    public ValidInf and(ValidInf validInf) {
        return innerValid(validInf, true);
    }


    /**
     * 每一次 or 都调用了 test，计算结果
     * @param validInf
     * @return
     */
    @Override
    public ValidInf or(ValidInf validInf) {
        return innerValid(validInf, false);
    }

    /**
     * 拼接测试结果
     *
     * @param validInf
     * @param isAnd
     * @return
     */
    private ValidInf innerValid(ValidInf validInf, boolean isAnd) {

        ValidResult result = validInf.test();
        if (isAnd) {
            flag = result.isFlag() && flag;
        } else {
            // 短路运算 -- 只是验证结果短路，但是相应的检测信息会加上
            // 在 false or true 时，会返回错误信息，如果 true or false 情况不返回错误信息，就会不一致，所以带上了错误信息，但是以最终检验返回的 true 或 false 为准
            if (flag) {
                return setMsgInfo(validInf, result);
            }
            flag = result.isFlag() || flag;
        }
        return setMsgInfo(validInf, result);
    }

    /**
     * 汇总信息
     *
     * @param validInf
     * @param result
     * @return
     */
    private ValidInf setMsgInfo(ValidInf validInf, ValidResult result) {
        List<String> msgs = (validInf instanceof ValidForm)?
                               ((ValidForm) validInf).msgList:
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
    public ValidResult test() {
        return new ValidResult(flag, msgList);
    }

    @Override
    public ValidInf empty() {
        return this;
    }
}
