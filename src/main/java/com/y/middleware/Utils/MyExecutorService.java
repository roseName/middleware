package com.y.middleware.Utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @类名: MyExecutorService.java
 * @包名： com.gd123.healthmanager.helper
 * @创建人： liu
 * @创建时间： 2015-10-14 上午9:51:10
 * @描述： 线程池管理
 */
public class MyExecutorService {
    private static final MyExecutorService instance = new MyExecutorService();
    private ExecutorService executor;

    private MyExecutorService() {

    }

    public void initExecutor(int threads) {
        executor = Executors.newFixedThreadPool(threads);
    }

    public static MyExecutorService getInstance() {
        return instance;
    }

    public ExecutorService getExecutorService() {
        return executor;
    }

    public void shutdown() throws InterruptedException {
        executor.shutdown();
        boolean b = executor.awaitTermination(2, TimeUnit.MILLISECONDS);
        if (!b) {
            executor.shutdownNow();
        }
    }

}
