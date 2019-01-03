package com.bangyou.dcom.common.root;

import com.bangyou.dcom.common.face.IRootApi;
import com.bangyou.dcom.common.face.IRootApiInput;
import com.bangyou.dcom.common.face.IRootApiResult;
import com.bangyou.dcom.common.model.DcomResult;

/**
 * @author radiance
 * root api
 */
abstract class RootApi<I extends IRootApiInput, R extends IRootApiResult> implements IRootApi {

    public R api(I input){

        return  process(input);

    }

    /**
     * 接口业务处理
     * @param input
     *      输入参数
     * @return 结果
     */
    protected abstract R process(I input);

    /**
     * 检查接口的授权
     *
     * @param input
     *      输入参数
     * @return 结果

    abstract DcomResult checkApi(I input);

     */

}
