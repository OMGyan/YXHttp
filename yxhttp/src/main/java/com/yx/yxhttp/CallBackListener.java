package com.yx.yxhttp;

import java.io.InputStream;

/**
 * Author by YX, Date on 2019/8/9.
 */
public interface CallBackListener {

    void onSuccess(InputStream inputStream);

    void onFailure(Throwable throwable);

}
