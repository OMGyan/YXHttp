package com.yx.yxhttp;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.yx.yxhttp.YXHttp.TAG;

/**
 * Author by YX, Date on 2019/8/9.
 */
public class ThreadPoolManager {
    //创建存放所有网络请求的队列
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();
    //创建延迟请求队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();
    //创建线程池
    private ThreadPoolExecutor mThreadPoolExecutor;
    //创建核心线程
    private Runnable coreThread = new Runnable() {
        Runnable runnable = null;
        @Override
        public void run() {
            while (true){
                try {
                    runnable = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runnable);
            }
        }
    };

    private Runnable delayThread = new Runnable() {
        HttpTask httpTask = null;
        @Override
        public void run() {
            while (true){
                try {
                    httpTask = mDelayQueue.take();
                    if(httpTask.getRetryCount()<3){
                        mThreadPoolExecutor.execute(httpTask);
                        httpTask.setRetryCount(httpTask.getRetryCount()+1);
                        Log.e(TAG, "====重试机制====重试第"+httpTask.getRetryCount()+"次");
                    }else {
                        Log.e(TAG, "====重试机制====重试结束,服务器估计是挂了！！！");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public static ThreadPoolManager getInstance(){
        return SingleThreadPoolManager.tpManager;
    }

    private static class SingleThreadPoolManager{
        private static final ThreadPoolManager tpManager = new ThreadPoolManager();
    }

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 5, 15, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
                          addTask(runnable);
                    }
                });
        //将核心线程交给线程池管理
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //将请求任务存放到队列
    public void addTask(Runnable runnable){
        if(runnable!=null){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void addDelayTask(HttpTask httpTask){
        if(httpTask!=null){
            httpTask.setDelayTime(3000);
            mDelayQueue.offer(httpTask);
        }
    }
}
