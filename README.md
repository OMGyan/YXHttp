# YXHttp
一个简单易用的网络通信framework ,当前只支持基本get,post请求

 核心api(sendJsonRequest):
 /**
  * @param url 请求全地址
  * @param requestInfo 请求参数,可以为空
  * @param isRetry 是否需要重试
  * @param requestMethod 请求方式默认为GET
  * @param reponse 数据接收的bean类
  * @param listener 请求结果回调
  */
public static<M> void sendJsonRequest(String url,Map requestInfo,boolean isRetry,String requestMethod, Class<M> reponse, IJsonDataTransForm<M> listener){ }
  
  具体用法如下:
  1.POST:
  Map<String, Object> map2 = new LinkedHashMap<>();
        map2.put("who","风不会停息1029");
        map2.put("type","Android");
        map2.put("desc","Android中NFC标签卡的读取");
        map2.put("url","https://blog.csdn.net/qq_35189116/article/details/80454677");
        map2.put("debug",false);

        YXHttp.sendJsonRequest("https://gank.io/api/add2gank",map2,false,"POST", bean1.class, new IJsonDataTransForm<bean1>() {
            @Override
            public void onSuccess(bean1 m) {
                 Log.i(TAG, "onSuccess: "+m.getMsg());
            }
            @Override
            public void onFailure(Throwable throwable) {
                Log.i(TAG, "onFailure: "+throwable.toString());
            }
        });
   2.GET:
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
