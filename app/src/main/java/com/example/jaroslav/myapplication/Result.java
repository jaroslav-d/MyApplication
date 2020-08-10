package com.example.jaroslav.myapplication;

import android.support.annotation.NonNull;
import java.util.UUID;

public class Result implements Comparable<Result> {
    private UUID mRid;
    private long mDate;
    private int mSpeed;
    private int mPoint;

    public Result(){this (UUID.randomUUID());}

    public Result(UUID rid){
        mRid = rid;
    }

    public UUID getRid() {
        return mRid;
    }

    public void setRid(UUID rid) {
        mRid = rid;
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public int getSpeed() {
        return mSpeed;
    }

    public void setSpeed(int speed) {
        mSpeed = speed;
    }

    public int getPoint() {
        return mPoint;
    }

    public void setPoint(int point) {
        mPoint = point;
    }

    @Override
    public int compareTo(@NonNull Result result) {
        if (mSpeed != result.getSpeed()) {
            return mSpeed - result.getSpeed();
        } else {
            return mPoint - result.getPoint();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return mDate == result.mDate &&
                mSpeed == result.mSpeed &&
                mPoint == result.mPoint &&
                mRid.equals(result.mRid);
    }

//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public int hashCode() {
//        return Objects.hash(mRid, mDate, mSpeed, mPoint);
//    }
}
