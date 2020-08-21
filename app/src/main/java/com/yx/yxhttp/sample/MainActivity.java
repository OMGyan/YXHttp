package com.yx.yxhttp.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.yx.yxhttp.IJsonDataTransForm;
import com.yx.yxhttp.YXHttp;


import java.util.LinkedHashMap;
import java.util.Map;
import static com.yx.yxhttp.YXHttp.TAG;

public class MainActivity extends AppCompatActivity {
    private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
    }

    public void goRequest(View view) {

//        Map<String, Object> map2 = new LinkedHashMap<>();
//        map2.put("who","风不会停息1029");
//        map2.put("type","Android");
//        map2.put("desc","Android中NFC标签卡的读取");
//        map2.put("url","https://blog.csdn.net/qq_35189116/article/details/80454677");
//        map2.put("debug",false);

        /**
         * Get请求示例
         */
        YXHttp.sendJsonRequest("https://gank.io/api/v2/data/category/Girl/type/Girl/page/1/count/10",null,
                false,"GET",bean1.class, new IJsonDataTransForm<bean1>() {
            @Override
            public void onSuccess(bean1 m) {

                Glide.with(MainActivity.this).load(m.getData().get(1).getUrl()).into(iv);

            }
            @Override
            public void onFailure(Throwable throwable) { }
        });
    }
}
