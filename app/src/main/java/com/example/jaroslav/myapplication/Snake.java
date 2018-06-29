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
    MatrixPosition matrix;
    boolean newapple = false;


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
        /*
        for (int i = 2; i < length+2; i++) {
            body.add(new Rect(axisX,
                            axisY + headLen*(i-1),
                            axisX + headLen,
                            axisY + headLen*(i-1) + headLen));
        }
        */
        for (int i = length+1; i > 1; i--) {
            body.add(new Rect(axisX,
                    axisY + headLen*(i-1),
                    axisX + headLen,
                    axisY + headLen*(i-1) + headLen));
        }
        bodyLen = length;
        matrix.createField(pointOX,pointOY,length);
    }

    void creep(String route){
        dx = 0;
        dy = 0;
        int step = head.height();
        int indexZero = matrix.loopArray.returnElementArray(0);
        int indexEnd = matrix.loopArray.returnElementArray(0); //matrix.loopArray.lengthData
        //int index = bodyLen - superindex;
        switch (route) {
            case "up": dx = 0; dy = (-1)*step; break;
            case "down": dx = 0; dy = step; break;
            case "left": dx = (-1)*step; dy = 0; break;
            case "right": dx = step; dy = 0; break;
        }

        switch (matrix.checkCreep(dx,dy)) {
            case "body": die(); break;
            case "apple": eat(); break;
            case "outside": die(); break;
            default:
                if (!dead) {
                    /*
                    Rect nRect = body.get(index);
                    nRect.set(head);
                    body.set(index, nRect);
                    if (superindex != bodyLen) {
                        superindex++;
                    } else {
                        superindex = 1;
                    }
                    head.offset(dx, dy);
                    */

                    Rect nRect = body.get(indexZero);
                    nRect.set(head);
                    body.set(indexZero, nRect);
                    head.offset(dx, dy);
                }
                break;
        }
    }

    void die() {
        dead = true;
    }

    void eat(){
        Rect nRect = new Rect(head);
        body.add(nRect);
        head.offset(dx,dy);
        bodyLen++;
        newapple = true;
    }
}