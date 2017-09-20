package com.rfe_contracts.countdown.CounterDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class CounterDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "CounterDb.db";
    public static final String COUNTER_TABLE_NAME = "counter";
    public static final String COUNTER_COLUMN_ID = "id";
    public static final String COUNTER_COLUMN_NAME = "name";
    public static final String COUNTER_COLUMN_DATE = "date";
    public static final String COUNTER_COLUMN_CATEGORY = "category";
    public static final String COUNTER_COLUMN_DESC = "desc";
    public static final String COUNTER_COLUMN_LOCATION = "location";
    public static final String COUNTER_COLUMN_NOTE = "note";
    public static final String COUNTER_COLUMN_ARCHIVE = "archive";


    public CounterDBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);

    }

    //Note: I have to use cursor.getLong() to retrieve the Date. SQLite integer can be up to 8 bits (which is a long in java)
    //Store the date in a field of the CounterAddActivity class.Update it whenever the date or time is changed.
    //Use Date.getTime() to return the time in milliseconds.
    //If it's too difficult you can just use text "yyyy-MM-dd HH:mm:ss" format.
    //Not sure if the autoincrement is needed? Try anyway.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + COUNTER_TABLE_NAME + "(" +
                        COUNTER_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                                COUNTER_COLUMN_NAME + " TEXT, " +
                                COUNTER_COLUMN_DATE + " INTEGER, " +
                                COUNTER_COLUMN_CATEGORY + " TEXT, " +
                                COUNTER_COLUMN_DESC + " TEXT, " +
                                COUNTER_COLUMN_LOCATION + " TEXT, " +
                                COUNTER_COLUMN_NOTE + " TEXT, " +
                                COUNTER_COLUMN_ARCHIVE + " INTEGER)"
                );
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + COUNTER_TABLE_NAME);
        onCreate(db);
    }

    //Add a new counter to the database.
    public boolean insertCounter(String name, long date, String category, String desc, String location, String note, String archive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNTER_COLUMN_NAME, name);
        contentValues.put(COUNTER_COLUMN_DATE, date);
        contentValues.put(COUNTER_COLUMN_CATEGORY, category);
        contentValues.put(COUNTER_COLUMN_DESC, desc);
        contentValues.put(COUNTER_COLUMN_LOCATION, location);
        contentValues.put(COUNTER_COLUMN_NOTE, note);
        contentValues.put(COUNTER_COLUMN_ARCHIVE, archive);
        db.insert(COUNTER_TABLE_NAME, null, contentValues);

        return true;
    }

    public boolean updateContact(Integer id, String name, long date, String category, String desc, String location, String note, String archive) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COUNTER_COLUMN_NAME, name);
        contentValues.put(COUNTER_COLUMN_DATE, date);
        contentValues.put(COUNTER_COLUMN_CATEGORY, category);
        contentValues.put(COUNTER_COLUMN_DESC, desc);
        contentValues.put(COUNTER_COLUMN_LOCATION, location);
        contentValues.put(COUNTER_COLUMN_NOTE, note);
        contentValues.put(COUNTER_COLUMN_ARCHIVE, archive);

        db.update(COUNTER_TABLE_NAME, contentValues, COUNTER_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );

        return true;
    }


    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs =  db.rawQuery( "select * from " + COUNTER_TABLE_NAME + " where " + COUNTER_COLUMN_ID + "="+id+"", null );
        return curs;
    }

//            public String getColumnString(String colName, int id) {
//                            SQLiteDatabase db = this.getReadableDatabase();
//                            Cursor curs =  db.rawQuery( "select COUNTER_COLUMN_" + colName.toUpperCase() + " from " + COUNTER_TABLE_NAME + " where " + COUNTER_COLUMN_ID + "="+id+"", null );
//                            return curs.getString(curs.getColumnIndex("COUNTER_COLUMN_" + colName.toUpperCase())); //Given only one column, could the index just be 1?
//            }


    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numberOfRows = (int) DatabaseUtils.queryNumEntries(db, COUNTER_TABLE_NAME);
        return numberOfRows;
    }

    public ArrayList<String> getAllData() {
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor curs =  db.rawQuery( "select * from contacts", null );
        curs.moveToFirst();

        while(curs.isAfterLast() == false){
            arrayList.add(curs.getString(curs.getColumnIndex(COUNTER_COLUMN_NAME)));
            curs.moveToNext();
        }
        return arrayList;
    }
}



//Get the date from the date and time pickers:
//•You could create a public method in your DatePickerFragment to return the string.
//•You could have a static variable in your MainActivity that this class writes to.
//•You could use SharedPreferences to store the string.
//I would go with the first option, it's the simplest if your application is basic.
//There are multiple ways of going about this as you can see, so it's important to look at how the user interacts with your app and when that date is needed. The public return method is nice if you hold a reference to the Fragment in MainActivity and you don't need the data ASAP. The static variable is nice if you need the string changed as soon as the user chooses a date. The last method is wasteful and is least recommended, but there is no static "magic" being done.

