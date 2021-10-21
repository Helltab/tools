package com.helltab.repo.tool.work.valid;

import cn.hutool.json.JSONUtil;
import com.helltab.repo.tool.work.str.NullableUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Topic
 * @author helltab
 * @version 1.0
 * @date 2021/2/24 11:16
 * @desc 验证结果
 */
@Data
public class VerifyResult {
    private boolean flag = true;
    private List<String> msgList = new ArrayList<>();


    public String getResultMsg() {
        Map<String, List<String>> tempWrap = new HashMap<>();
        tempWrap.put("[srv.formvalid.warn]", msgList);
        return JSONUtil.toJsonStr(tempWrap);
    }

    public VerifyResult(boolean flag, List<String> msgList) {
        this.flag = flag;
        this.msgList = msgList;
    }

    public VerifyResult(boolean flag, String msg) {
        this.flag = flag;
        if(NullableUtil.isNotNull(msg)) {
            this.msgList.add(msg);
        }
    }

    @Override
    public String toString() {
        return "ValidResult{" +
                       "flag=" + flag +
                       ", msgList=" + msgList +
                       '}';
    }

    public boolean isFlag() {
        return this.flag;
    }
}
