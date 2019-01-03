package com.bangyou.dcom.common.root;

import com.bangyou.dcom.common.face.IRootApiInput;
import com.bangyou.dcom.common.model.DcomLoginInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author radiance
 * root api input
 */
public class RootApiInput implements IRootApiInput {

    @ApiModelProperty(value = "")
    private DcomLoginInfo loginInfo = new DcomLoginInfo();


    public DcomLoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(DcomLoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }
}
