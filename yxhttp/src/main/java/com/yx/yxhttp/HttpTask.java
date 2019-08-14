package com.yx.yxhttp;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * Author by YX, Date on 2019/8/9.
 */
public class HttpTask implements Runnable, Delayed {

    private IHttpRequest iHttpRequest;
    private long delayTime;
    private int retryCount;
    private CallBackListener callBackListener;
    private boolean isRetry;
    public long getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(long delayTime) {
        this.delayTime = System.currentTimeMillis()+delayTime;
    }

    public int getRetryCount() {
        return retryCount;

    }

    public boolean isRetry() {
        return isRetry;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public HttpTask(String url, Map map, boolean isRetry, IHttpRequest iHttpRequest, CallBackListener callBackListener, String requestMethod) {
        this.callBackListener = callBackListener;
         this.iHttpRequest = iHttpRequest;
         this.isRetry = isRetry;
         iHttpRequest.setUrl(url);
         iHttpRequest.setListener(callBackListener);
         iHttpRequest.setData(map);
         iHttpRequest.setRequestMethod(requestMethod);

    }

    @Override
    public void run() {

        try{
            this.iHttpRequest.execute();
        }catch (Exception e){
            if(isRetry){
                ThreadPoolManager.getInstance().addDelayTask(this);
                if(this.retryCount==3){
                    callBackListener.onFailure(e);
                }
            }else {
                callBackListener.onFailure(e);
            }
        }
    }

    @Override
    public long getDelay(@NonNull TimeUnit timeUnit) {
        return timeUnit.convert(this.delayTime-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
    }

    @Override
    public int compareTo(@NonNull Delayed delayed) {
        return 0;
    }
}
