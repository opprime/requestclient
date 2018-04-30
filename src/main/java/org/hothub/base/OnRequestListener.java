package org.hothub.base;

import org.hothub.response.ResultBody;

/**
 * Created by opprime on 2018/02/24.
 */
public abstract class OnRequestListener {
    //成功
    public abstract void onSuccess(ResultBody resultBody);

    //失败
    public abstract void onFailure(ResultBody resultBody);

    //执行中
    public void onProcess(ResultBody resultBody) {};
}
