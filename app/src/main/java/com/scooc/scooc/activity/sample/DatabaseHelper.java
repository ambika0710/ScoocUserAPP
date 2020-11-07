package com.scooc.scooc.activity.sample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "RecurringRidesNew.db";
    public static final String TABLE_NAME = "recurring_rides_table_new";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "SOURCE";
    public static final String COL_3 = "DESTINATION";
    public static final String COL_4 = "PICKUPTIME";
    public static final String COL_5 = "STARTDATE";
    public static final String COL_6 = "ENDDATE";
    public static final String COL_7 = "MULINTENT";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,SOURCE TEXT,DESTINATION TEXT,PICKUPTIME TEXT,STARTDATE TEXT,ENDDATE TEXT,MULINTENT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String source,String destinstion,String pickuptime,String startdate,String enddate,int mulintent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,source);
        contentValues.put(COL_3,destinstion);
        contentValues.put(COL_4,pickuptime);
        contentValues.put(COL_5,startdate);
        contentValues.put(COL_6,enddate);
        contentValues.put(COL_7,mulintent);
        //put request code
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
    // Get User Details
    public ArrayList<HashMap<String, String>> GetUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT * FROM "+ TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("ID",cursor.getString(cursor.getColumnIndex(COL_1)));
            user.put("SOURCE",cursor.getString(cursor.getColumnIndex(COL_2)));
            user.put("DESTINATION",cursor.getString(cursor.getColumnIndex(COL_3)));
            user.put("PICKUPTIME",cursor.getString(cursor.getColumnIndex(COL_4)));
            user.put("STARTDATE",cursor.getString(cursor.getColumnIndex(COL_5)));
            user.put("ENDDATE",cursor.getString(cursor.getColumnIndex(COL_6)));
            user.put("MULINTENT",cursor.getString(cursor.getColumnIndex(COL_7)));
            userList.add(user);
        }
        return  userList;
    }

    public boolean updateData(String id,String name,String surname,String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,surname);
        contentValues.put(COL_4,marks);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] { id });
        return true;
    }

    public Integer deleteData (String pickuptime) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "PICKUPTIME = ?",new String[] {pickuptime});
    }
}