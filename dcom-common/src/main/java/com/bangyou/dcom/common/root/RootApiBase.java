package com.bangyou.dcom.common.root;

import com.bangyou.dcom.common.face.IRootApi;
import com.bangyou.dcom.common.face.IRootApiInput;
import com.bangyou.dcom.common.face.IRootApiResult;

/**
 * 接口基类 不校验授权
 * @param <I>
 *     输入参数
 * @param <R>
 *     输出结果
 */
public abstract class RootApiBase<I extends IRootApiInput, R extends IRootApiResult > extends RootApi<I,R> implements IRootApi {



}
