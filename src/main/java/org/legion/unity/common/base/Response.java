package org.legion.unity.common.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

@ApiModel(value = "全局统一返回值")
public class Response <T> implements Serializable {

    @ApiModelProperty(value = "自定义响应状态码", required = true, example = "201")
    private int statusCode;
    @ApiModelProperty(value = "响应数据")
    private T data;
    @ApiModelProperty(value = "请求时间", example = "2020-01-01 08:17:23:175")
    private Date requestAt;
    @ApiModelProperty(value = "响应时间", example = "2020-01-01 08:17:23:504")
    private Date respondAt;

    public Response () {

    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Date getRequestAt() {
        return requestAt;
    }

    public void setRequestAt(Date requestAt) {
        this.requestAt = requestAt;
    }

    public Date getRespondAt() {
        return respondAt;
    }

    public void setRespondAt(Date respondAt) {
        this.respondAt = respondAt;
    }
}
