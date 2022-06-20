package com.example.androidtv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataHelper extends SQLiteOpenHelper {
    public DataHelper(Context context) {
        super(context, "Userdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table UserInfo(name TEXT primary key, phone TEXT, date_of_birth TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop Table if exists UserInfo");
    }

    public Boolean insert(String name, String phone, String date_of_birth)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("date_of_birth", date_of_birth);

        long result = DB.insert("UserInfo", null, contentValues);

        return result != -1;
    }

    public Cursor getdata()
    {
        SQLiteDatabase Db = this.getWritableDatabase();

        return Db.rawQuery("Select * from UserInfo", null);
    }

    public boolean delete (String name)
    {
        SQLiteDatabase DB = this.getWritableDatabase();

        long result = DB.delete("UserInfo", "name=?", new String[] { name });

        return result != -1;
    }

    public boolean edit (String name, String phone, String date_of_birth)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("date_of_birth", date_of_birth);

        long result = DB.update("UserInfo", contentValues,"name=?", new String[] { name });

        return result != -1;
    }
}
