package com.example.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDB {

    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "person_name";
    public static final String KEY_CELL = "_cell";

    private final String DATABASE_NAME = "ContactsDB";
    private final String TABLE_NAME = "ContactTable";
    private final int DATABASE_VERSION = 1;

    private DBHelper ourHelper;
    private Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context)
    {
        ourContext = context;
    }
    public class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            /*
            CREATE TABLE table_name (
                    column_1 data_type PRIMARY KEY,
                    column_2 data_type NOT NULL,
                    column_3 data_type DEFAULT 0,
                    table_constraints
            ) [WITHOUT ROWID];
            */
            String sqlCode = "CREATE TABLE " + TABLE_NAME + " ( " +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_CELL + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            
        }
    }
    public ContactsDB open() throws SQLException
    {
        ourHelper = new DBHelper((ourContext));
        ourDatabase = ourHelper.getWritableDatabase();
        return this;

    }
    public void close()
    {
        ourHelper.close();
    }
    public long createEntry(String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CELL, cell);
        return ourDatabase.insert(TABLE_NAME, null, cv);

    }
    public String getData()
    {
        String [] columns = new String [] {KEY_ROWID, KEY_NAME, KEY_CELL};
        Cursor c = ourDatabase.query(TABLE_NAME, columns, null, null, null, null, null);

        int iRowId = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iCell = c.getColumnIndex(KEY_CELL);

        String result = "";

        for( c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
        {
            result = result + c.getString(iRowId) + " " + c.getString(iName)
                    + " " + c.getString(iCell) + "\n";
        }
        return result;
    }
    public long deleteEntry(String rowId)
    {
        return ourDatabase.delete(TABLE_NAME, KEY_ROWID + "=?" , new String[] {rowId});

    }
    public long updateEntry(String rowId, String name, String cell)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_CELL, cell);
        return ourDatabase.update(TABLE_NAME, cv, KEY_ROWID + "=?", new String [] {rowId});
    }

}
