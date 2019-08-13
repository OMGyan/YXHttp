package com.yx.yxhttp;

import java.util.Map;

/**
 * Author by YX, Date on 2019/8/9.
 */
public interface IHttpRequest {

    void setUrl(String url);

    void setData(Map map);

    void setRequestMethod(String method);

    void setListener(CallBackListener callbacklistener);

    void execute();

}
