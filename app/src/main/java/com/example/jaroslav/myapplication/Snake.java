package com.example.jaroslav.myapplication;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

public class Snake extends Observable {
    public Rect head;
    //public Rect[] body;
    public ArrayList<Rect> body;
    public int bodyLen;
    int dx;
    int dy;
    boolean newapple = false;


    Snake(int length, MatrixPosition matrixPosition) {
        bodyLen = length;
        addObserver(matrixPosition);
        setChanged();
        notifyObservers("SnakeHere");
        int axisX = matrixPosition.getHeadX();
        int axisY = matrixPosition.getHeadY();

        int headLen = matrixPosition.cellLen;
        head = new Rect(axisX,
                        axisY,
                        axisX + headLen,
                        axisY + headLen
        );
        body = new ArrayList<>();
        for (int i = length+1; i > 1; i--) {
            body.add(new Rect(axisX,
                    axisY + headLen*(i-1),
                    axisX + headLen,
                    axisY + headLen*(i-1) + headLen));
        }
    }

    void bindWay(String route) {
        dx = 0;
        dy = 0;
        int step = head.height();
        switch (route) {
            case "up": dx = 0; dy = (-1)*step; break;
            case "down": dx = 0; dy = step; break;
            case "left": dx = (-1)*step; dy = 0; break;
            case "right": dx = step; dy = 0; break;
        }
        setChanged();
        notifyObservers();
    }

    void creep(int indexZero){
        Rect nRect = body.get(indexZero);
        nRect.set(head);
        body.set(indexZero, nRect);
        head.offset(dx, dy);
    }

    void die() {
        return;
    }

    void eat(){
        Rect nRect = new Rect(head);
        body.add(nRect);
        head.offset(dx,dy);
        bodyLen++;
        newapple = true;
    }
}