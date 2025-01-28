package com.example.cardgame2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database version and name
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "card_game_db";

    // Table name and column names
    public static final String TABLE_CARDS = "cards";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_HP = "hp";
    public static final String COLUMN_ATTACK = "attack";
    public static final String COLUMN_IMAGE_RES = "image_res";
    public static final String COLUMN_BACKGROUND_RES = "background_res";
    public static final String COLUMN_RARITY_RES = "rarity_res";

    // Create table SQL query
    private static final String CREATE_TABLE_CARDS = "CREATE TABLE " + TABLE_CARDS + " (" +
        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_NAME + " TEXT, " +
        COLUMN_TYPE + " TEXT, " +
        COLUMN_HP + " INTEGER, " +
        COLUMN_ATTACK + " INTEGER, " +
        COLUMN_IMAGE_RES + " INTEGER, " +
        COLUMN_BACKGROUND_RES + " INTEGER, " +
        COLUMN_RARITY_RES + " INTEGER)";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the cards table
        db.execSQL(CREATE_TABLE_CARDS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop the old table if it exists and create a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARDS);
        onCreate(db);
    }
}

