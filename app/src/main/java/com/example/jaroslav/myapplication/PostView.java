package com.example.jaroslav.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;

import java.util.Arrays;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class PostView extends MyView {
    boolean firstCreate = true;
    Snake mySnake;
    Rect myApple;
    MyThread myThread;
    float actionDownX;
    float actionDownY;
    String touch = "up";
    String arrowignored = "down";
    //Handler myHandler;

    PostView(Context context) {
        super(context);
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstCreate) {
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

        String route = touch;
        if (route.compareTo(arrowignored) == 0) {
            route = ignore(route);
        }
        mySnake.creep(route);
        arrowignored = ignore(route);

        if (mySnake.newapple) {
            myApple = mySnake.matrix.createApple();
            mySnake.newapple = false;
        }

        myPaint.setARGB(255,0,0,255);
        canvas.drawRect(mySnake.head,myPaint);

        myPaint.setARGB(255,0,127,0);
        for (int i = 0; i < mySnake.bodyLen; i++) {
            canvas.drawRect(mySnake.body.get(i),myPaint);
        }

        myPaint.setARGB(255,255,0,0);
        canvas.drawRect(myApple,myPaint);


        /*
        String myTextSeven = "" + Arrays.toString(myMatrix.loopArray.returnArray());
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*7, myPaint);
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*8, myPaint);
        canvas.drawText(myTextSeven,25, myPaint.getTextSize()*9, myPaint);
        //myHandler.flush();
        */

        /*
        for (int i = 0; i < myMatrix.axisY.length; i++) {
            for (int k = 0; k < myMatrix.axisX.length; k++) {
                String myTextSeven = "" + mySnake.matrix.fieldPhantom[k][i];
                canvas.drawText(myTextSeven,25+myPaint.getTextSize()*k, 125+myPaint.getTextSize()*i, myPaint);
            }
        }
        for (int i = 0; i < mySnake.matrix.loopArray.lengthData; i++) {
            String myTextSeven = "" + mySnake.matrix.loopArray.returnElementArray(i);
            canvas.drawText(myTextSeven,
                    25+myPaint.getTextSize()*i,
                    125+myPaint.getTextSize()*myMatrix.axisY.length,
                    myPaint);
        }
        /*
        */

        myHandler.post(myThread);
        myThread.start();
    }

    String ignore (String input) {
        switch (input) {
            case "up": return "down";
            case "down": return "up";
            case "left": return "right";
            case "right": return "left";
        }
        return "nothing";
    }
}