package icu.helltab.itool.result;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import icu.helltab.itool.object.StrUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Topic about a process result
 *
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 18:42
 */
public abstract class BaseResult implements Serializable {
    /**
     * sub class with use it
     */
    protected String msg;
    private final ArrayList<String> msgList;


    public BaseResult() {
        msgList = new ArrayList<>();
    }

    public BaseResult error(Object... msgArray) {
        errorDetail();
        return setMsg(msgArray);
    }

    public BaseResult errorF(String template, Object... vars) {
        return error(StrUtil.format(template, vars));
    }

    public BaseResult setMsg(Object... msgArray) {
        Arrays.stream(msgArray).distinct().forEach(x -> {
            msgList.add(x.toString());
        });
        return this;
    }

    public BaseResult setMsgF(String template, Object... vars) {
        setMsg(StrUtil.format(template, vars));
        return this;
    }

    /**
     * you must overwrite it to add your personalized logic
     */
    protected abstract void errorDetail();

    /**
     * cannot be overwritten
     *
     * @return
     */
    public final String getMsg() {
        return msgList.isEmpty() ? msg : msgList.toString();
    }

    @Override
    public String toString() {
        JSONConfig jsonConfig = new JSONConfig();
        jsonConfig.setOrder(true);
        jsonConfig.setIgnoreError(true);
        jsonConfig.setIgnoreNullValue(false);
        return JSONUtil.toJsonStr(this, jsonConfig);
    }
}
