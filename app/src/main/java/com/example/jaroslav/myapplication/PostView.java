package com.example.jaroslav.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class PostView extends MyView {
    boolean firstCreate = true;
    Snake mySnake;
    Rect myApple;
    MyThread myThread;
    //Handler myHandler;

    PostView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstCreate == true) {
            mySnake = new Snake(3, myMatrix);
            myApple = myMatrix.createApple();
            /*
            myHandler = new Handler() {
                @Override
                public void publish(LogRecord record) {}
                @Override
                public void flush() {post(myThread);}
                @Override
                public void close() throws SecurityException {}
            };
            */
            myThread = new MyThread(this);
            firstCreate = false;
        }

        myPaint.setARGB(255,0,0,255);
        canvas.drawRect(mySnake.head,myPaint);

        myPaint.setARGB(255,0,127,0);
        for (int i = 0; i < mySnake.bodyLen; i++) {
            canvas.drawRect(mySnake.body.get(i),myPaint);
        }

        myPaint.setARGB(255,255,0,0);
        canvas.drawRect(myApple,myPaint);

        mySnake.creep("left");


        String myTextSeven = "" + myMatrix.twoAxis;
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*7, myPaint);
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*8, myPaint);
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*9, myPaint);
        //myHandler.flush();
        myHandler.post(myThread);
        myThread.start();
    }
}