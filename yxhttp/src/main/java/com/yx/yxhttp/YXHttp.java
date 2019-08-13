package com.yx.yxhttp;

import java.util.Map;

/**
 * Author by YX, Date on 2019/8/9.
 */
public class YXHttp {

    public static final String TAG = "YXHttp";

    public static<M> void sendJsonRequest(String url,Map requestInfo,boolean isRetry,String requestMethod, Class<M> reponse, IJsonDataTransForm<M> listener){
        IHttpRequest httpRequest = new JsonHttpRequest();
        CallBackListener callBackListener =  new JsonCallListener<>(reponse,listener);
        HttpTask httpTask = new HttpTask(url,requestInfo,isRetry,httpRequest,callBackListener,requestMethod);
        ThreadPoolManager.getInstance().addTask(httpTask);
    }

}
