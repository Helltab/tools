package com.helltab.repo.tool.work.valid;

import cn.hutool.json.JSONUtil;
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
public class ValidResult {
    private boolean flag = true;
    private List<String> msgList = new ArrayList<>();


    public String getResultMsg() {
        Map<String, List<String>> tempWrap = new HashMap<>();
        tempWrap.put("[srv.formvalid.warn]", msgList);
        return JSONUtil.toJsonStr(tempWrap);
    }

    public ValidResult(boolean flag, List<String> msgList) {
        this.flag = flag;
        this.msgList = msgList;
    }

    public ValidResult(boolean flag, String msg) {
        this.flag = flag;
        this.msgList.add(msg);
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
