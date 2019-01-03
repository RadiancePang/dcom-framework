package com.bangyou.dcom.common.model;

import com.bangyou.dcom.common.face.IRootApiResult;
import io.swagger.annotations.ApiModelProperty;

public class DcomResult implements IRootApiResult {

    @ApiModelProperty(value = "状态码", required = true, notes = "默认值为1，不为1表示为异常处理信息")
    private int status = 1;

    @ApiModelProperty(value = "提示信息")
    private String msg = "";

    @Override
    public int getStatus() {
        return status;
    }

    @Override
    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
