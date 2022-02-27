package icu.helltab.itool.result.web;

import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;

/**
 * Topic about paged
 *
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 20:33
 */
public class HttpPaged {
    /**
     * 数据分页大小上限
     */
    public static final int MAX_PAGE_SIZE = 100;
    /**
     * 数据分页大小下限
     */
    public static final int MIN_PAGE_SIZE = 1;
    /**
     * 起始分页
     */
    public static final int DEFAULT_PAGE_NUM = 1;
    /**
     * 默认数据最大SIZE
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    public static final String KEY_PAGE_NUM = "pageNum";
    public static final String KEY_PAGE_SIZE = "pageSize";

    private int pageNum;
    private int pageSize;
    private int total;
    private int from;
    private int to;

    public HttpPaged(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    /**
     * only total can be modify
     *
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public int getFrom() {
        return (pageNum - 1) * pageSize;
    }

    public int getTo() {
        return pageNum * pageSize;
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
