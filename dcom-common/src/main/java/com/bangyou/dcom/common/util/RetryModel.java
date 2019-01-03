package com.bangyou.dcom.common.util;

import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * 重试响应信息
 * @author radiance
 */
public class RetryModel {

    private boolean retry;

    private Response response;

    private Request request;

    private IOException exception;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public boolean isRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public IOException getException() {
        return exception;
    }

    public void setException(IOException exception) {
        this.exception = exception;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
