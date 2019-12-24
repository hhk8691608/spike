package com.ace.study.spike.Controller.threadPool;

import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***
 *
 *@Author Mark
 *@Date 2019/12/24 10 38
 *@Desciption
 */
public class ThreadPoolService {

    private static ExecutorService pool = null;

    private  ThreadPoolService threadPoolService = new ThreadPoolService();

    private ThreadPoolService(){}

    public static ExecutorService getThreadPool(){
        if(pool == null){
            pool = Executors.newCachedThreadPool();
        }
        return pool;
    }



}
