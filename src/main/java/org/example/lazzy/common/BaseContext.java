package org.example.lazzy.common;

/**
 * ThreadLocal is a local variable of Thread.
 * Each request uses a separate Thread.
 * ThreadLocal provides a shared space for a Thread. It is separated from other Threads.
 * method for ThreadLocal
 *      public void set(T value);
 *      public T get();
 *
 */
public class BaseContext {
    public static final ThreadLocal<Long> threadLocal = new ThreadLocal();
    // store id in threadLocal
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    // get id from the threadLocal
    public static Long getCurrentId(){
        return threadLocal.get();
    }

}
