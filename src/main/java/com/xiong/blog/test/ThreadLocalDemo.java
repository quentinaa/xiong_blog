package com.xiong.blog.test;

public class ThreadLocalDemo {

    private static String name = "";

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {
//        name = Thread.currentThread().getName();
        threadLocal.set(Thread.currentThread().getName());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 把name的值设置成当前线程的名称
                threadLocal.set(Thread.currentThread().getName());
                // 输出name的值
                System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
            }, "线程-" + i).start();
        }
        System.out.println(Thread.currentThread().getName() + ":" + threadLocal.get());
    }
}
