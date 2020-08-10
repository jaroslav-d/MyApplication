package com.example.jaroslav.myapplication.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.jaroslav.myapplication.Result;
import com.example.jaroslav.myapplication.database.DbSchema.ResultTable;
import com.example.jaroslav.myapplication.database.DbSchema.ResultTable.Cols;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class ResultsDBManager {
    SQLiteDatabase db;

    public ResultsDBManager(Context context) {
        db = new ResultsBaseHelper(context.getApplicationContext()).getWritableDatabase();
    }

    public List<Result> getResultsList() {
        ArrayList<Result> results = new ArrayList<>();
        getResults(results);
        Collections.sort(results);
        return results;
    }

    public TreeSet<Result> getResults() {
        TreeSet<Result> results = new TreeSet<>();
        getResults(results);
        return results;
    }

    private void getResults(Collection<Result> results) {

        ResultsWrapper cursor = new ResultsWrapper(
                db.query(
                        ResultTable.NAME,
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
    }

    public void addResult(Result result) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Cols.RID, result.getRid().toString());
        contentValues.put(Cols.DATE, result.getDate());
        contentValues.put(Cols.SPEED, result.getSpeed());
        contentValues.put(Cols.POINTS, result.getPoint());

        db.insert(ResultTable.NAME, null, contentValues);
    }

    public void deleteResult(Result result) {
        String uuidString = result.getRid().toString();

        db.delete(
                ResultTable.NAME,
                Cols.RID + " = ?",
                new String[] {uuidString}
        );
    }
}
