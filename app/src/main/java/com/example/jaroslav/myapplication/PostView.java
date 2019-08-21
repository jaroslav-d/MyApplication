package com.example.jaroslav.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.concurrent.TimeUnit;


public class PostView extends View{
    Snake mySnake;
    Rect myApple;
    Thread myThread;
    float actionDownX;
    float actionDownY;
    String touch = "up";
    String arrowignored = "down";
    Handler myHandler;
    int width;
    int height;
    MatrixPosition myMatrix;
    Rect myFrame;
    Paint myPaint;

    public PostView(Context context) {
        this(context,null);
    }

    public PostView(final Context context, AttributeSet attr) {
        super(context, attr);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        myHandler = new Handler();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        myMatrix = new MatrixPosition(width,height,25);
        myFrame = new Rect(myMatrix.leftField,
                myMatrix.topField,
                myMatrix.rightField,
                myMatrix.bottomField
        );
        myPaint = new Paint();
        mySnake = new Snake(3, myMatrix);
        myApple = myMatrix.createApple();
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep(200);
                    postInvalidate();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // paint frame
        canvas.drawRGB(0,0,0);

        // paint field
        myPaint.setARGB(255,255,255,255);
        canvas.drawRect(myFrame,myPaint);

        String route = touch;
        if (route.equals(arrowignored)) {
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

        myHandler.post(myThread);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                actionDownX = event.getAxisValue(event.AXIS_X,0);
                actionDownY = event.getAxisValue(event.AXIS_Y,0);
                return true;


            case MotionEvent.ACTION_MOVE:

                if (Math.abs(actionDownY - event.getAxisValue(event.AXIS_Y)) >
                        Math.abs(actionDownX - event.getAxisValue(event.AXIS_X))) {
                    if (actionDownY - event.getAxisValue(event.AXIS_Y) < 0) {
                        touch = "down"; break;
                    } else {
                        touch = "up"; break;
                    }
                } else {
                    if (actionDownX - event.getAxisValue(event.AXIS_X) < 0) {
                        touch = "right"; break;
                    } else {
                        touch = "left"; break;
                    }
                }

            case MotionEvent.ACTION_UP:
                actionDownX = 0;
                actionDownY = 0;
        }
        return true;
    }
}