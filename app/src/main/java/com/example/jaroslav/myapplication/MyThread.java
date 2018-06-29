package com.example.jaroslav.myapplication;

import java.util.concurrent.TimeUnit;

public class MyThread extends Thread {
    MyView myView;

    MyThread(MyView MyView) {
        this.myView = MyView;
    }

    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(200);
            myView.postInvalidate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}