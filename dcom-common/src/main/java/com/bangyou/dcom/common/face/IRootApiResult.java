package com.bangyou.dcom.common.face;

/**
 * @author radiance
 * api result
 */
public interface IRootApiResult {

    /**
     * 获取状态码
     * @return 状态码
     */
    public int getStatus();

    /**
     * 设置状态码
     * @param status
     */
    public void setStatus(int status);

    /**
     * 获取提示信息
     * @return 提示信息
     */
    public String getMsg();

    /**
     * 设置提示信息
     * @param msg
     */
    public void setMsg(String msg);

}
