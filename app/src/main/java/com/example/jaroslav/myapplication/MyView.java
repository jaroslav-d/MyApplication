package com.example.jaroslav.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;


public class MyView extends View {
    Handler myHandler;
    int width;
    int height;
    MatrixPosition myMatrix;
    Rect myFrame;
    Paint myPaint;
    
    MyView(Context context) {
        super(context);
    }

    public void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        myHandler = new Handler();
        width = canvas.getWidth();
        height = canvas.getHeight();
        myMatrix = new MatrixPosition(width,height,25);
        myFrame = new Rect(myMatrix.leftField,
                            myMatrix.topField,
                            myMatrix.rightField,
                            myMatrix.bottomField
        );
        myPaint = new Paint();

        // paint frame
        canvas.drawRGB(0,0,0);

        // paint field
        myPaint.setARGB(255,255,255,255);
        canvas.drawRect(myFrame,myPaint);


        // znacheniya

        /*
        myPaint.setARGB(255,0,255,0);
        myPaint.setTextSize(27);
        String myTextOne = "" + myMatrix.leftField + "//" + myMatrix.topField +
                "//" + myMatrix.rightField + "//" + myMatrix.bottomField;
        String myTextTwo = "" + width + "//" + myMatrix.axisY[2];
        canvas.drawText(myTextOne,10, myPaint.getTextSize(), myPaint);
        canvas.drawText(myTextTwo,10, myPaint.getTextSize()*2, myPaint);

        /*
        headS.offset(head*myRand.nextInt(widthField/head),head*myRand.nextInt(heightField/head));
        //headS.offset(head*var,0);
        myPaint.setARGB(255,255,0,255);
        canvas.drawRect(headS,myPaint);
        */

        //MyThread myThread = new MyThread();
        //myThread.viewInThread = this;
        //myThread.start();

    }
}