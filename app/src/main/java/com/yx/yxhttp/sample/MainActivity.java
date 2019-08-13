package com.yx.yxhttp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.yx.yxhttp.IJsonDataTransForm;
import com.yx.yxhttp.YXHttp;


import java.util.LinkedHashMap;
import java.util.Map;
import static com.yx.yxhttp.YXHttp.TAG;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void goRequest(View view) {
        /**
         * POST请求示例
         */
        Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("who","风不会停息1029");
        map2.put("type","Android");
        map2.put("desc","Android中NFC标签卡的读取");
        map2.put("url","https://blog.csdn.net/qq_35189116/article/details/80454677");
        map2.put("debug",false);

//        YXHttp.sendJsonRequest("https://gank.io/api/add2gank",map2,false,"POST", bean1.class, new IJsonDataTransForm<bean1>() {
//            @Override
//            public void onSuccess(bean1 m) {
//                 Log.i(TAG, "onSuccess: "+m.getMsg());
//            }
//            @Override
//            public void onFailure(Throwable throwable) {}
//        });

        /**
         * Get请求示例
         */
        YXHttp.sendJsonRequest("http:cxxx",null,false,null,bean2.class, new IJsonDataTransForm<bean2>() {
            @Override
            public void onSuccess(bean2 m) {
                Log.i(TAG, "onSuccess: "+m.getResults().get(0).toString());
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.i(TAG, "onFailure: "+throwable.toString());
            }
        });
    }
}
