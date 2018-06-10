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
    int earthSymbol = 0;
    int snakeSymbol = 1;
    int appleSymbol = 2;
    int [] axisX;
    int [] axisY;
    int pointOX;
    int pointOY;
    int [][] fieldPhantom;
    ArrayList<Integer> snakePhantomX;
    ArrayList<Integer> snakePhantomY;
    int indexTrans = 1;

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
    }


    void createField(int OX, int OY, int length) {
        pointOX = OX;
        pointOY = OY;
        fieldPhantom[pointOX][pointOY] = snakeSymbol;
        snakePhantomX = new ArrayList<>();
        snakePhantomY = new ArrayList<>();
        snakePhantomX.add(pointOX);
        snakePhantomY.add(pointOY);
        for (int i = 1; i < length+2; i++) {
            fieldPhantom[pointOX][pointOY+i] = snakeSymbol;
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
        } while (fieldPhantom[randAppleX][randAppleY] == snakeSymbol);
        fieldPhantom[randAppleX][randAppleY] = appleSymbol;
        return new Rect(axisX[randAppleX],
                axisY[randAppleY],
                axisX[randAppleX] + cellLen,
                axisY[randAppleY] + cellLen);
    }

    void checkCreep(int dx, int dy) {
        int index = snakeLen - indexTrans;
        if (index == 1) {
            indexTrans = 1;
        } else {
            indexTrans++;
        }
        fieldPhantom[snakePhantomX.get(index)][snakePhantomY.get(index)] = earthSymbol;
        snakePhantomX.add(index,pointOX);
        snakePhantomY.add(index,pointOY);
        pointOX = pointOX + dx;
        pointOY = pointOY + dy;
        fieldPhantom[pointOX][pointOY] = snakeSymbol;

    }

}