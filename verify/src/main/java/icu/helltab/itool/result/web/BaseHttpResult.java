package icu.helltab.itool.result.web;

import icu.helltab.itool.result.BaseResult;

/**
 * Topic
 * <p>web response result util
 * <p>if you want to use it
 * <p>1. extend it
 * <p>2. create enum implement HttpStatusInf
 * <p>3. define its successStatus and failStatus
 *
 *
 * <p>
 *
 * @author helltab
 * @version 1.0
 * @date 2022/2/27 17:58
 */
public abstract class BaseHttpResult<T> extends BaseResult {
    private int code;
    private HttpPaged paged;
    /**
     * generic is unused
     */
    private T data;
    private final HttpStatusInf successStatus;
    private final HttpStatusInf failStatus;

    /*
     * EXAMPLE:
      class HttpResult extends BaseHttpResult{
              @Override
              protected HttpStatusInf initSuccessStatus() {
                  return HttpStatus.SUCCESS;
              }

              @Override
              protected HttpStatusInf initFailStatus() {
                  return HttpStatus.FAIL;
              }
          }
      enum HttpStatus implements HttpStatusInf {
              SUCCESS(8000, "success"), FAIL(8999, "fail");

              private final int code;
              private final String desc;
              HttpStatus(int code, String desc) {
                  this.code = code;
                  this.desc = desc;
              }
              @Override
              public int getCode() {
                  return code;
              }
              @Override
              public String getDesc() {
                  return desc;
              }
          }
     *
     */


    protected abstract HttpStatusInf initSuccessStatus();

    protected abstract HttpStatusInf initFailStatus();

    public BaseHttpResult() {
        successStatus = initSuccessStatus();
        failStatus = initFailStatus();
        code = successStatus.getCode();
        msg = successStatus.getDesc();
    }

    @Override
    protected void errorDetail() {
        this.code = failStatus.getCode();
        this.msg = failStatus.getDesc();
    }

    public BaseResult setStatus(HttpStatusInf status) {
        this.code = status.getCode();
        this.msg = status.getDesc();
        return this;
    }

    public int getCode() {
        return code;
    }

    public BaseResult setCode(int code) {
        this.code = code;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseResult setData(T data) {
        this.data = data;
        return this;
    }

    public HttpPaged getPaged() {
        return paged;
    }

    /**
     * set it if you need
     * @param paged
     */
    public void setPaged(HttpPaged paged) {
        this.paged = paged;
    }
}
