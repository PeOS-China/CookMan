package com.example.administrator.cookman.model.executer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by PeOS on 2016/9/6 0006.
 */
public class JobExecutor implements ThreadExecutor{
    private static final int INITIAL_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;

    // Sets the amount of time an idle thread waits before terminating
    private static final int KEEP_ALIVE_TIME = 10;

    // Sets the Time Unit to seconds
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

    private final BlockingQueue<Runnable> workQueue;


    private final ThreadPoolExecutor threadPoolExecutor;

    private final ThreadFactory threadFactory;

//  private static JobExecutor mJobExecutor;

//  public static JobExecutor instance(){
//    if(mJobExecutor==null){
//      synchronized (JobExecutor.class){
//        if(mJobExecutor==null)
//          mJobExecutor=new JobExecutor();
//      }
//    }
//    return mJobExecutor;
//  }

    private JobExecutor() {
        this.workQueue = new LinkedBlockingQueue<>();
        this.threadFactory = new JobThreadFactory();
        this.threadPoolExecutor = new ThreadPoolExecutor(INITIAL_POOL_SIZE, MAX_POOL_SIZE,
                KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory);
    }

    public static JobExecutor instance() {
        return Holder.INSTANCE;
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("Runnable to execute cannot be null");
        }
        this.threadPoolExecutor.execute(runnable);
    }

    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    private static final class Holder {
        static final JobExecutor INSTANCE = new JobExecutor();
    }

    private static class JobThreadFactory implements ThreadFactory {
        private static final String THREAD_NAME = "android_";
        private static int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            Thread t = new Thread(runnable, THREAD_NAME + counter);
            counter++;
            return t;
        }
    }
}
