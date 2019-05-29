package com.example.news.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

//后台运行线程
class DiskIOThreadExecutor implements Executor {
    private final Executor mDiskIO;

    public DiskIOThreadExecutor(){
        mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(Runnable runnable) {
        mDiskIO.execute(runnable);
    }
}
