# YXHttp
一个简单易用的网络通信framework ,当前只支持基本get,post请求

 核心api(sendJsonRequest): 具体用法请参考app模块
  * @param url 请求全路径
  * @param requestInfo 请求参数,可以为空
  * @param isRetry 是否需要重试
  * @param requestMethod 请求方式默认为GET
  * @param reponse 数据接收的bean类
  * @param listener 请求结果回调
  
