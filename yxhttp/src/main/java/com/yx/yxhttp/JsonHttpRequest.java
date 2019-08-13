package com.yx.yxhttp;

import android.text.TextUtils;
import android.util.Log;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


import static com.yx.yxhttp.YXHttp.TAG;

/**
 * Author by YX, Date on 2019/8/9.
 */
public class JsonHttpRequest implements IHttpRequest{

    private String url;
    private Map map;
    private CallBackListener callBackListener;
    private String method;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setData(Map map) {
        this.map = map;
    }

    @Override
    public void setRequestMethod(String method ) {
        if(method==null || TextUtils.isEmpty(method)){
            method = "GET";
        }
       this.method = method;
    }

    @Override
    public void setListener(CallBackListener callbacklistener) {
        this.callBackListener = callbacklistener;
    }

    private void requestGet(Map<String, Object> paramsMap,String baseUrl) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            String requestUrl = null;
            if(!(paramsMap==null)&&!(paramsMap.size()<1)){
                for (String key : paramsMap.keySet()) {
                    if (pos > 0) {
                        tempParams.append("&");
                    }
                    tempParams.append(String.format("%s=%s",key,paramsMap.get(key),"utf-8"));
                    pos++;
                }
               requestUrl = baseUrl + tempParams.toString();
            }else {
                requestUrl = baseUrl;
            }
            // 新建一个URL对象
            Log.i(TAG, "requestUrl: "+requestUrl);
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
             urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 获取返回的数据
                callBackListener.onSuccess(urlConn.getInputStream());
            } else {
                Log.e(TAG, "Get方式请求失败"+urlConn.getResponseCode()+"  "+urlConn.getResponseMessage());
                throw new RuntimeException(urlConn.getResponseMessage());
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            throw new RuntimeException(e);
        }
    }

    private void requestPost(Map<String, Object> paramsMap,String baseUrl) {
        try {
            //合成参数
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s",key, paramsMap.get(key),"utf-8"));
                pos++;
            }
            String params =tempParams.toString();
            Log.i(TAG, "requestPost: "+params);
            // 请求的参数转换为byte数组
            byte[] postData = params.getBytes();
            // 新建一个URL对象
            URL url = new URL(baseUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // Post请求必须设置允许输出 默认false
            urlConn.setDoOutput(true);
            //设置请求允许输入 默认是true
            urlConn.setDoInput(true);
            // Post请求不能使用缓存
            urlConn.setUseCaches(false);
            // 设置为Post请求
            urlConn.setRequestMethod("POST");
            //设置本次连接是否自动处理重定向
            urlConn.setInstanceFollowRedirects(true);
            // 开始连接
            urlConn.connect();
            // 发送请求参数
            DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
            dos.write(postData);
            dos.flush();
            dos.close();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // 获取返回的数据
                callBackListener.onSuccess(urlConn.getInputStream());
            } else {
                Log.e(TAG, "Post方式请求失败"+urlConn.getResponseCode()+"  "+urlConn.getResponseMessage());
                throw new RuntimeException(urlConn.getResponseMessage());
            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            throw new RuntimeException(e);
        }
    }



    @Override
    public void execute() {

        if(method.equals("GET")){
            requestGet(map,url);
        }else {
            requestPost(map,url);
        }

    }
}
