package com.example.jaroslav.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import static com.example.jaroslav.myapplication.database.DbSchema.ResultTable.Cols;
import static com.example.jaroslav.myapplication.database.DbSchema.ResultTable.NAME;

public class ResultsBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DB_NAME = "mySnakeDB.db";

    public ResultsBaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(
                "create table " + NAME + "(" +
                        " _id integer primary key autoincrement, " +
                        Cols.RID + ", " +
                        Cols.DATE + ", " +
                        Cols.SPEED + ", " +
                        Cols.POINTS +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
