package com.shichen.scenicsport.util;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Global executor pools for the whole application.
 * <p>
 * Grouping tasks like this avoids the effects of task starvation (e.g. disk reads don't wait behind
 * webservice requests).
 */
public class AppExecutors {
    private static final int THREAD_COUNT = 3;
    private final Executor diskIO;
    private final Executor netWorkIO;
    private final Executor mainThread;

    @VisibleForTesting
    AppExecutors(Executor diskIO, Executor netWorkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.netWorkIO = netWorkIO;
        this.mainThread = mainThread;
    }

    public AppExecutors(){
        this(new DiskIOThreadExecutor(), Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor());
    }

    public Executor diskIO() {
        return diskIO;
    }

    public Executor netWorkIO(){
        return netWorkIO;
    }

    public Executor mainThread(){
        return mainThread;
    }

    private static class MainThreadExecutor implements Executor{
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }
}
