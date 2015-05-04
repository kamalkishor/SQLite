package com.example.kamal.flat;

/**
 * Created by Kamal on 16-Apr-15.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "messageDB.db";
    private static final String TABLE_MESSAGES = "messages";

    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_TYPE = "type";

    public MyDBHandler(Context context, String name,
                       SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_MESSAGES_TABLE = "CREATE TABLE " +
                TABLE_MESSAGES + "("
                + COLUMN_TIMESTAMP + " TEXT," + COLUMN_ID + " TEXT," + COLUMN_DATA
                + " TEXT," + COLUMN_TYPE + " TEXT" + ")";
        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        onCreate(db);
    }

    public void addMessage(Message message) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, message.getTimeStamp());
        values.put(COLUMN_ID, message.getID());
        values.put(COLUMN_DATA, message.getData());
        values.put(COLUMN_TYPE, message.getType());

        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_MESSAGES, null, values);
     //  System.out.println(" ");
        //db.close();
    }

    public Cursor findMessage() {
       // String query = "Select * FROM TABLE_MESSAGES";

        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.query(TABLE_MESSAGES, new String[] { COLUMN_TIMESTAMP,  COLUMN_ID, COLUMN_DATA, COLUMN_TYPE } ,
                null, null, null, null , null );

         return cursor;
    }


}
