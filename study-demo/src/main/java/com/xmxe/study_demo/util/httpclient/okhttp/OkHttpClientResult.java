package com.xmxe.study_demo.util.httpclient.okhttp;

import java.util.List;
import java.util.Map;

public class OkHttpClientResult {
     /**
     * 是否成功
     */
    private boolean success = false;

    /**
     * http级，状态标识码
     */
    private Integer code;

    /**
     * http级，错误信息
     */
    private String message;

    /**
     * http级，返回头部
     */
    private Map<String, List<String>> headers;

    /**
     * http级，返回body
     */
    private byte[] body;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, List<String>> headers) {
        this.headers = headers;
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public OkHttpClientResult() {
    }

    public OkHttpClientResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }
}
