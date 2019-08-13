package com.yx.yxhttp;


/**
 * Author by YX, Date on 2019/8/9.
 */
public interface IJsonDataTransForm<T> {

     void onSuccess(T m);

     void onFailure(Throwable throwable);

}
