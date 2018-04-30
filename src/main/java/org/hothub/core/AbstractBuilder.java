package org.hothub.core;

import okhttp3.*;
import org.hothub.base.RequestMethod;
import org.hothub.base.OnRequestListener;
import org.hothub.manager.ContextManager;
import org.hothub.response.ResultBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class AbstractBuilder<T extends AbstractBuilder> extends AbstractBuilderChain<T> {


    private static final Logger logger = LoggerFactory.getLogger(AbstractBuilder.class);


    public AbstractBuilder() {}




    /**
     * 同步，带返回值
     * */
    public ResultBody execute() {
        RequestBuild requestBuild = new RequestBuild(this);

        Request request = requestBuild.buildRequest();
        OkHttpClient okHttpClient = requestBuild.getOkHttpClient();

        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        List<Cookie> cookieList = requestBuild.getResponseCookie();
        ContextManager.remove();

        return new ResultBody(request, response, cookieList);
    }



    /**
     * 异步，无返回值
     * */
    public void execute(OnRequestListener onRequestListener) {
        RequestBuild requestBuild = new RequestBuild(this);

        Request request = requestBuild.buildRequest();
        OkHttpClient okHttpClient = requestBuild.getOkHttpClient();

        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            private ResultBody getResultBody(Response response) {
                List<Cookie> cookieList = requestBuild.getResponseCookie();
                ContextManager.remove();

                return new ResultBody(request, response, cookieList);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                if (onRequestListener != null) {
                    onRequestListener.onFailure(getResultBody(null));
                }
            }

            @Override
            public void onResponse(Call call, Response response) {
                if (onRequestListener != null) {
                    onRequestListener.onSuccess(getResultBody(response));
                }
            }
        });
    }





    public abstract RequestMethod getRequestMethod();


}
