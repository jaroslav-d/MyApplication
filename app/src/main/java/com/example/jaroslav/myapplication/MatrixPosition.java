package com.example.jaroslav.myapplication;

import android.graphics.Rect;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

public class MatrixPosition implements Observer {
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
    int headX;
    int headY;
    int pointOX = 0;
    int pointOY = 0;
    int [][] fieldPhantom;
    ArrayList<Integer> snakePhantomX = new ArrayList<>();
    ArrayList<Integer> snakePhantomY = new ArrayList<>();
    ClosedArray loopArray;
    boolean haveApple = true;

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
        loopArray = new ClosedArray((axisX.length*axisY.length)/2);
        pointOX = OX;
        pointOY = OY;
        fieldPhantom[pointOX][pointOY] = SNAKE;
        snakePhantomX.add(pointOX);
        snakePhantomY.add(pointOY);
        for (int i = 1; i < length+1; i++) {
            fieldPhantom[pointOX][pointOY+i] = SNAKE;
            snakePhantomX.add(pointOX);
            snakePhantomY.add(pointOY+length+1-i);
            loopArray.add();
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
        haveApple = true;
        return new Rect(axisX[randAppleX],
                axisY[randAppleY],
                axisX[randAppleX] + cellLen,
                axisY[randAppleY] + cellLen);
    }

    String checkCreep(int dx, int dy, Snake snake) {
        int pointOXBuffer = pointOX;
        int pointOYBuffer = pointOY;
        pointOX = pointOX + dx/cellLen;
        pointOY = pointOY + dy/cellLen;
        if (pointOX < 0 | pointOX > axisX.length-1 |
                pointOY < 0 | pointOY > axisY.length-1) {
            return "outside";
        }
        snakePhantomX.set(0,pointOX);
        snakePhantomY.set(0,pointOY);
        switch (fieldPhantom[pointOX][pointOY]) {
            case EARTH:
                fieldPhantom[pointOX][pointOY] = SNAKE;
                int indexZero = loopArray.returnElementArray(0);
                creepSnake(pointOXBuffer,pointOYBuffer);
                snake.creep(indexZero);
                break;
            case APPLE:
                fieldPhantom[pointOX][pointOY] = SNAKE;
                eatApple(pointOXBuffer,pointOYBuffer);
                snake.eat();
                haveApple = false;
                return "apple";
            case SNAKE:
                snake.die();
                return "body";
        }
        return "next";
    }

    void eatApple(int pointOXBuffer, int pointOYBuffer) {
        int index = loopArray.returnElementArray(loopArray.lengthData);
        fieldPhantom[snakePhantomX.get(index)][snakePhantomY.get(index)] = SNAKE;
        snakePhantomX.add(pointOXBuffer);
        snakePhantomY.add(pointOYBuffer);
        loopArray.add();
    }

    void creepSnake(int pointOXBuffer, int pointOYBuffer) {
        int index = loopArray.returnElementArray(0)+1;
        fieldPhantom[snakePhantomX.get(index)][snakePhantomY.get(index)] = EARTH;
        snakePhantomX.set(index,pointOXBuffer);
        snakePhantomY.set(index,pointOYBuffer);
        loopArray.up();
    }

    @Override
    public void update(Observable observable, Object o) {
        Snake snake = (Snake) observable;
        String arg = (String) o;
        if ("SnakeHere".equals(arg)) {
            Random myRand = new Random();
            int pointOX, X;
            int pointOY, Y;
            do{
                pointOX = myRand.nextInt(axisX.length);
                pointOY = myRand.nextInt(axisY.length);
                X = axisX[pointOX];
                Y = axisY[pointOY];
            } while (Y+cellLen*(snake.bodyLen+1) > bottomField | Y < axisY[2]);
            headX = X;
            headY = Y;
            createField(pointOX, pointOY, snake.bodyLen);
            return;
        }
        checkCreep(snake.dx, snake.dy, snake);
    }

    public int getHeadX() {
        return headX;
    }

    public int getHeadY() {
        return headY;
    }

    public boolean isHaveApple() {
        return haveApple;
    }
}