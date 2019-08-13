package com.yx.yxhttp;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static com.yx.yxhttp.YXHttp.TAG;


/**
 * Author by YX, Date on 2019/8/9.
 */
public class JsonCallListener<T> implements CallBackListener{

    private Class<T> responseClass;
    private IJsonDataTransForm iJsonDataTransForm;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    public JsonCallListener(Class<T> responseClass, IJsonDataTransForm iJsonDataTransForm) {
        this.responseClass = responseClass;
        this.iJsonDataTransForm = iJsonDataTransForm;
    }

    @Override
    public void onSuccess(InputStream inputStream) {
       String response  = getContent(inputStream);
        Log.i(TAG, "requestData: "+response);
       final T clazz = JSON.parseObject(response, responseClass);
        mHandler.post(new Runnable() {
            @Override
            public void run() {
              iJsonDataTransForm.onSuccess(clazz);
            }
        });
    }

    private String getContent(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line = null;
        try {
            while ((line=bufferedReader.readLine())!=null){
                builder.append(line+"\n");
            }
        }catch (IOException e){
            System.out.println("Error" + e.toString());
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return builder.toString();
    }

    @Override
    public void onFailure(final Throwable throwable) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                iJsonDataTransForm.onFailure(throwable);
            }
        });
    }

}
