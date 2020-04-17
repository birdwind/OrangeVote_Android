package com.orange.orangetvote.basic.base2;

import java.io.Serializable;

public class BaseModel<T> implements Serializable {
    private boolean status;
    private int errorCode;
    private String errorMsg;
    private int httpStatus;
    private String requestUrl;
    private String method;
    private String resourceName;
    private T response;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public boolean getStatus() {
        return status;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getResourceName() {
        return resourceName;
    }

    public T getResponse() {
        return response;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
