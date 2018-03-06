package org.hothub.http;

/**
 * Created by opprime on 2018/02/24.
 */
public abstract class OnRequestListener {
    //成功
    public abstract void onSuccess(String response);

    //失败
    public abstract void onFailure(String response);

    //执行中
    public void onProcess(String response) {};
}
