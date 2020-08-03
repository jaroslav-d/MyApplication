package com.example.jaroslav.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.example.jaroslav.myapplication.database.DbSchema;
import com.example.jaroslav.myapplication.database.ResultsBaseHelper;
import com.example.jaroslav.myapplication.database.ResultsWrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;


public class PostView extends View{
    Snake mySnake;
    int speedSnake;
    Rect myApple;
    MatrixPosition myMatrix;
    Thread myThread;
    Handler myHandler;
    float actionDownX;
    float actionDownY;
    String touch = "up";
    String arrowignored = "down";
    int width;
    int height;
    Rect myFrame;
    Paint myPaint;
    SQLiteDatabase myDatabase;

    public PostView(Context context) {
        this(context,null);
    }

    public PostView(final Context context, AttributeSet attr) {
        super(context, attr);

        myDatabase = new ResultsBaseHelper(context.getApplicationContext()).getWritableDatabase();

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.speed_snake), Context.MODE_PRIVATE);
        int defaultSpeed = getResources().getInteger(R.integer.default_speed);
        int defaultLength = getResources().getInteger(R.integer.default_length_snake);
        final int maxSpeed = getResources().getInteger(R.integer.max_speed);
        speedSnake = sharedPref.getInt("speed", defaultSpeed);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
        myMatrix = new MatrixPosition(width,height,25);
        myFrame = new Rect(myMatrix.leftField,
                myMatrix.topField,
                myMatrix.rightField,
                myMatrix.bottomField
        );
        myPaint = new Paint();
        mySnake = new Snake(defaultLength, myMatrix);
        myApple = myMatrix.createApple();
        myHandler = new Handler();
        myThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.MILLISECONDS.sleep((maxSpeed-speedSnake)*100);
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
        arrowignored = ignore(route);
        mySnake.bindWay(route);
        myApple = myMatrix.isHaveApple() ? myApple : myMatrix.createApple();

        // paint head snake
        myPaint.setARGB(255,0,0,255);
        canvas.drawRect(mySnake.head,myPaint);

        // paint body snake
        myPaint.setARGB(255,0,127,0);
        for (int i = 0; i < mySnake.bodyLen; i++) {
            canvas.drawRect(mySnake.body.get(i),myPaint);
        }

        // paint apple
        myPaint.setARGB(255,255,0,0);
        canvas.drawRect(myApple,myPaint);

        // run new circle
        if (!mySnake.isDead()) {
            myHandler.post(myThread);
        } else {
            Result result = new Result();
            result.setPoint(mySnake.bodyLen);
            result.setSpeed(speedSnake);
            result.setDate(new Date().getTime());
            TreeSet<Result> resultList = getResults();
            resultList.add(result);
//            deleteResult(resultList.first());
//            addResult(result);
        }
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

    private TreeSet<Result> getResults(){
        TreeSet<Result> results = new TreeSet<>();

        ResultsWrapper cursor = new ResultsWrapper(
                myDatabase.query(
                        DbSchema.ResultTable.NAME,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        );
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                results.add(cursor.getResult());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return results;
    }

    private void addResult(Result result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.ResultTable.Cols.RID, result.getRid().toString());
        contentValues.put(DbSchema.ResultTable.Cols.DATE, result.getDate());
        contentValues.put(DbSchema.ResultTable.Cols.SPEED, result.getSpeed());
        contentValues.put(DbSchema.ResultTable.Cols.POINTS, result.getPoint());

        myDatabase.insert(DbSchema.ResultTable.NAME, null, contentValues);
    }

    private void deleteResult(Result result) {
        String uuidString = result.getRid().toString();

        myDatabase.delete(
                DbSchema.ResultTable.NAME,
                DbSchema.ResultTable.Cols.RID + " = ?",
                new String[] {uuidString}
                );
    }
}