package com.example.jaroslav.myapplication;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class MatrixPosition {
    int leftField;
    int topField;
    int rightField;
    int bottomField;
    int cellLen;
    int snakeLen;
    final int EARTH = 0;
    final int SNAKE = 1;
    final int APPLE = 2;
    int [] axisX;
    int [] axisY;
    int pointOX = 0;
    int pointOY = 0;
    int [][] fieldPhantom;
    ArrayList<Integer> snakePhantomX = new ArrayList<>();
    ArrayList<Integer> snakePhantomY = new ArrayList<>();
    int indexTrans = 1;
    int twoAxis;

    MatrixPosition(int width, int height, int sampleRate) {
        cellLen = height/sampleRate;
        int widthField = width - width % cellLen;
        int heightField = height - height % cellLen;
        int widthBalance = width % cellLen;
        int heightBalance = height % cellLen;
        leftField = widthBalance/2;
        topField = heightBalance/2;
        rightField = width - widthBalance/2 - widthBalance%2;
        bottomField = height - heightBalance/2 - heightBalance%2;

        axisX = new int[width/cellLen];
        axisY = new int[sampleRate];
        for (int i = 0; i < width/cellLen; i++) {
            axisX[i] = i*cellLen + leftField;
        }
        for (int i = 0; i < sampleRate; i++) {
            axisY[i] = i*cellLen + topField;
        }

        fieldPhantom = new int[axisX.length][axisY.length];
        twoAxis = axisY[0];
    }


    void createField(int OX, int OY, int length) {
        pointOX = OX;
        pointOY = OY;
        fieldPhantom[pointOX][pointOY] = SNAKE;
        snakePhantomX.add(pointOX);
        snakePhantomY.add(pointOY);
        for (int i = 1; i < length+1; i++) {
            fieldPhantom[pointOX][pointOY+i] = SNAKE;
            snakePhantomX.add(pointOX);
            snakePhantomY.add(pointOY+i);
        }
        snakeLen = length + 1;
    }

    Rect createApple() {
        Random myRand = new Random();
        int randAppleX;
        int randAppleY;
        do {
            randAppleX = myRand.nextInt(axisX.length);
            randAppleY = myRand.nextInt(axisY.length);
        } while (fieldPhantom[randAppleX][randAppleY] == SNAKE);
        fieldPhantom[randAppleX][randAppleY] = APPLE;
        return new Rect(axisX[randAppleX],
                axisY[randAppleY],
                axisX[randAppleX] + cellLen,
                axisY[randAppleY] + cellLen);
    }

    String checkCreep(int dx, int dy) {
        int index = snakeLen - indexTrans;
        if (index == 1) {
            indexTrans = 1;
        } else {
            indexTrans++;
        }
        if (pointOX < 0 | pointOX > axisX.length |
                pointOY < 0 | pointOY > axisY.length) {
            return "outside";
        }
        fieldPhantom[snakePhantomX.get(index)][snakePhantomY.get(index)] = EARTH;
        snakePhantomX.set(index,pointOX);
        snakePhantomY.set(index,pointOY);
        pointOX = pointOX + dx/cellLen;
        pointOY = pointOY + dy/cellLen;
        snakePhantomX.set(0,pointOX);
        snakePhantomY.set(0,pointOY);
        if (pointOX < 0 | pointOX > axisX.length |
                pointOY < 0 | pointOY > axisY.length) {
            return "outside";
        }
        switch (fieldPhantom[pointOX][pointOY]) {
            case EARTH: fieldPhantom[pointOX][pointOY] = SNAKE; break;
            case APPLE: return "apple";
            case SNAKE: return "body";
        }
        return "next";
    }
}