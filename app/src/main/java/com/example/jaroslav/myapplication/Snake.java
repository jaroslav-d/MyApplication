package com.example.jaroslav.myapplication;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class Snake {
    public Rect head;
    //public Rect[] body;
    public ArrayList<Rect> body;
    public int bodyLen;
    private int dx;
    private int dy;
    private int superindex = 1;
    private boolean dead = false;
    private MatrixPosition matrix;


    Snake(int length, MatrixPosition matrixPosition) {
        Random myRand = new Random();
        this.matrix = matrixPosition;
        int axisX;
        int axisY;
        int pointOX;
        int pointOY;
        do{
            pointOX = myRand.nextInt(matrixPosition.axisX.length);
            pointOY = myRand.nextInt(matrixPosition.axisY.length);
            axisX = matrixPosition.axisX[pointOX];
            axisY = matrixPosition.axisY[pointOY];
        } while (axisY+matrixPosition.cellLen*(length+1) > matrixPosition.bottomField | axisY < matrixPosition.axisY[2]);


        int headLen = matrixPosition.cellLen;
        head = new Rect(axisX,
                        axisY,
                        axisX + headLen,
                        axisY + headLen
        );
        /*
        body = new Rect[length];
        for (int i = 2; i < length+2; i++) {
            body[i-2] = new Rect(axisX,
                                axisY + headLen*(i-1),
                                axisX + headLen,
                                axisY + headLen*(i-1) + headLen);
        }
        */
        body = new ArrayList<>();
        for (int i = 2; i < length+2; i++) {
            body.add(new Rect(axisX,
                            axisY + headLen*(i-1),
                            axisX + headLen,
                            axisY + headLen*(i-1) + headLen));
        }
        bodyLen = length;
        matrixPosition.createField(pointOX,pointOY,length);
    }

    void creep(String route){
        dx = 0;
        dy = 0;
        int step = head.height();
        int index = bodyLen - superindex;
        switch (route) {
            case "up": dx = 0; dy = (-1)*step; break;
            case "down": dx = 0; dy = step; break;
            case "left": dx = (-1)*step; dy = 0; break;
            case "right": dx = step; dy = 0; break;
        }
        matrix.checkCreep(dx,dy);
        if (head.left < matrix.leftField | head.top < matrix.topField |
                head.right > matrix.rightField | head.bottom > matrix.bottomField) {
            die();
        }
        if (!dead) {
            Rect nRect = body.get(index);
            nRect.set(head);
            body.set(index,nRect);
            if (superindex != bodyLen) {
                superindex++;
            } else {
                superindex = 1;
            }
            head.offset(dx,dy);
        /*
        for (int i = 0; i < body.length; i++) {
            body[i].offset(dx,dy);
        }
        */
            for (int i = 0; i < bodyLen; i++) {
                nRect = body.get(i);
                if (head.centerX() == nRect.centerX() & head.centerY() == nRect.centerY()) {
                    die();
                }
            }
        }
    }

    void die() {
        dead = true;
    }
/*
    eating(){};
    */
}