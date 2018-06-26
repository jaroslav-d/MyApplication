package com.example.jaroslav.myapplication;

/**
 * Created by 808310 on 20.06.2018.
 */

public class ClosedArray {
    int[] array;
    int counter;
    int lengthArray;
    int lengthData;

    ClosedArray (int length) {
        lengthArray = length;
        array = new int[lengthArray];
        counter = 0;
        lengthData = 0;
    }

    void add() {
        array[(counter+lengthData)%lengthArray] = lengthData;
        lengthData++;
    }

    void add(int data) {
        array[(counter+lengthData)%lengthArray] = data;
        lengthData++;
    }

    boolean up() {
        if (lengthData >= lengthArray) {
            return false;
        }
        array[(counter+lengthData)%lengthArray] = array[counter%lengthArray];
        array[counter%lengthArray] = 0;
        counter++;
        return true;
    }

    int[] returnArray() {
        int[] returnArray = new int[lengthData];
        for (int i = 0; i < lengthData; i++) {
            returnArray[i] = array[(counter+i)%lengthArray];
        }
        return returnArray;
    }

    int returnElementArray(int i) {
        return array[(counter+i)%lengthArray];
    }
}

