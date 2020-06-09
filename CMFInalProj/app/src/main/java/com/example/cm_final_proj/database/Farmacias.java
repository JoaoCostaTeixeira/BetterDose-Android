package com.example.cm_final_proj.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.example.cm_final_proj.model.Farmacia_model;

import java.util.List;

public final class Farmacias extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "betterdose.db";
    public static final String TABLE_NAME = "farmacias";
    public static final String COLLUMN_ID = "ID";
    public static final String COLLUMN_NAME = "name";
    public static final String COLLUMN_MORADA = "morada";


    public Farmacias(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLLUMN_NAME + " TEXT," +
                COLLUMN_MORADA + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public boolean insertData(String name, String morada){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(COLLUMN_NAME, name);
        content.put(COLLUMN_MORADA, morada);
        long result = db.insert(TABLE_NAME, null, content);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void clearData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, null, null);
    }
}