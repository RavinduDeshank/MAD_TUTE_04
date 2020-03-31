package com.example.tutorial_04.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHandler( Context context) {
        super(context, DATABASE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UserMaster.Users.TABLE_NAME +
                "(" + UserMaster.Users._ID + "INTEGER PRIMARY KEY," +
                    UserMaster.Users.COLUMN_NAME_USERNAME + "TEXT," +
                    UserMaster.Users.COLUMN_NAME_PASSWORD + "TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ UserMaster.Users.TABLE_NAME);
        onCreate(db);
    }
    public void AddInfo(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_USERNAME,username);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD,password);

        long newRowID = db.insert(UserMaster.Users.TABLE_NAME,null,values);
    }

    public List<String> readAllInfo(){
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };
        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + "DESC";

        Cursor cursor = db.query(
                UserMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );
        List userName = new ArrayList<>();
        List password = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String passw = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));


                userName.add(username);
                userName.add(passw);

        }
        cursor.close();
        return userName;
    }
    public void deleteInfo(String userName){
        SQLiteDatabase db = getReadableDatabase();
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        String[] selectionArgs = {userName};
        db.delete(UserMaster.Users.TABLE_NAME,selection,selectionArgs);
    }
    public void updateInfo(String userName,String password){
        SQLiteDatabase db = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD,password);

        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + "LIKE ?";
        String[] selectionArgs = {userName};

        int count = db.update(
                UserMaster.Users.TABLE_NAME,values,selection,selectionArgs);
    }


    public List<String> readInfo(String userName, String UserPassword){
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(UserMaster.Users.TABLE_NAME,
                projection,
                null, null, null , null,
                sortOrder
        );

        List<String> userNames = new ArrayList<>();
        List<String> passwords = new ArrayList<>();

        while (cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String pass = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));

            if(userName.equals(userName) && pass.equals(UserPassword)){
                userNames.add(username);
                userNames.add(pass);
            }

        }

        cursor.close();
        return userNames;
    }


}
