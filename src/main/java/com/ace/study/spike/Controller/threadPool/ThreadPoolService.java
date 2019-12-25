package com.ace.study.spike.Controller.threadPool;

import com.ace.study.spike.Controller.threadPool.dateWareHouse.WareHouse;
import org.springframework.stereotype.Service;
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

    private static WareHouse wareHouse = null;

    private  ThreadPoolService threadPoolService = new ThreadPoolService();

    private ThreadPoolService(){}

    public static ExecutorService getThreadPool(){
        if(pool == null){
            pool = Executors.newCachedThreadPool();
        }
        return pool;
    }


    public static WareHouse getWareHouse(){
        if(wareHouse == null){
            wareHouse = new WareHouse();
        }
        return wareHouse;
    }



}
