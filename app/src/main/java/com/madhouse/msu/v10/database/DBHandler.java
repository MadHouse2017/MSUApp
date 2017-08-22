package com.madhouse.msu.v10.database;

/**
 * Created by Krishna on 11/2/2016.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.madhouse.msu.v10.bean.Message;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "im";

    // Message Table Columns names
    private static final String MSG_ID = "id";
    private static final String MSG_DATE = "date";
    private static final String MSG_TITLE = "title";
    private static final String MSG_DESC = "desc";
    private static final String MSG_READ = "read";
    private static final String MSG_TYP = "type";
    private static final String MSG_IMG = "img";
    private static final String ERROR_MSG = "msg_err";
    private static final String SENDER_EMPID = "sender_empid";
    private static final String CHAT_ID = "chat_id";
    private static final String SENDER_NAME = "sender_name";
    private static final String SENDER_GCMID = "sender_gcmid";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
