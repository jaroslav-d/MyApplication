package com.example.jaroslav.myapplication.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.jaroslav.myapplication.Result;
import com.example.jaroslav.myapplication.database.DbSchema.ResultTable;

import java.util.UUID;

public class ResultsWrapper extends CursorWrapper {

    public ResultsWrapper(Cursor cursor) {
        super(cursor);
    }

    public Result getResult(){

        String rid = getString(getColumnIndex(ResultTable.Cols.RID));
        long date = getLong(getColumnIndex(ResultTable.Cols.DATE));
        int speed = getInt(getColumnIndex(ResultTable.Cols.SPEED));
        int points = getInt(getColumnIndex(ResultTable.Cols.POINTS));


        Result result = new Result();
        result.setRid(UUID.fromString(rid));
        result.setDate(date);
        result.setSpeed(speed);
        result.setPoint(points);

        return result;
    }

    public String getRid(){
        return getString(getColumnIndex(ResultTable.Cols.RID));
    }
}
