package com.example.cardgame2.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.cardgame2.models.CardItem;

public class CardDatabaseHelper extends SQLiteOpenHelper {

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

    public CardDatabaseHelper(Context context) {
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

    // Insert a new CardItem into the database
    public void insertCardItem(CardItem cardItem) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cardItem.getName());
        values.put(COLUMN_TYPE, cardItem.getType());
        values.put(COLUMN_HP, cardItem.getHp());
        values.put(COLUMN_ATTACK, cardItem.getAttack());
        values.put(COLUMN_IMAGE_RES, cardItem.getImageRes());
        values.put(COLUMN_BACKGROUND_RES, cardItem.getBackgroundRes());
        values.put(COLUMN_RARITY_RES, cardItem.getRarityRes());

        // Insert the new CardItem into the database
        database.insert(TABLE_CARDS, null, values);
        database.close();
    }

    // Retrieve a CardItem by ID
    public CardItem getCardItem(int id) {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_CARDS,
            new String[]{
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_TYPE,
                COLUMN_HP,
                COLUMN_ATTACK,
                COLUMN_IMAGE_RES,
                COLUMN_BACKGROUND_RES,
                COLUMN_RARITY_RES},
            COLUMN_ID + " = ?", new String[]{String.valueOf(id)},
            null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            // Retrieve each column value
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
            @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE));
            @SuppressLint("Range") int hp = cursor.getInt(cursor.getColumnIndex(COLUMN_HP));
            @SuppressLint("Range") int attack = cursor.getInt(cursor.getColumnIndex(COLUMN_ATTACK));
            @SuppressLint("Range") int imageRes = cursor.getInt(cursor.getColumnIndex(COLUMN_IMAGE_RES));
            @SuppressLint("Range") int backgroundRes = cursor.getInt(cursor.getColumnIndex(COLUMN_BACKGROUND_RES));
            @SuppressLint("Range") int rarityRes = cursor.getInt(cursor.getColumnIndex(COLUMN_RARITY_RES));

            // Create and return a CardItem object
            CardItem cardItem = new CardItem(id, name, type, hp, attack, imageRes, backgroundRes, rarityRes);
            cursor.close();
            database.close();
            return cardItem;
        } else {
            cursor.close();
            database.close();
            return null; // No card found with the given ID
        }
    }

    // Retrieve all CardItems from the database
    public Cursor getAllCardItems() {
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(TABLE_CARDS,
            new String[]{
                COLUMN_ID,
                COLUMN_NAME,
                COLUMN_TYPE,
                COLUMN_HP,
                COLUMN_ATTACK,
                COLUMN_IMAGE_RES,
                COLUMN_BACKGROUND_RES,
                COLUMN_RARITY_RES},
            null, null, null, null, null);

        if (cursor != null) {
            // Debug: Print the column names to check if the query is correct
            String[] columnNames = cursor.getColumnNames();
            for (String columnName : columnNames) {
                Log.d("Cursor Columns", columnName);
            }
        }
        return cursor;
    }

    // Update a CardItem in the database
    public int updateCardItem(CardItem cardItem) {
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, cardItem.getName());
        values.put(COLUMN_TYPE, cardItem.getType());
        values.put(COLUMN_HP, cardItem.getHp());
        values.put(COLUMN_ATTACK, cardItem.getAttack());
        values.put(COLUMN_IMAGE_RES, cardItem.getImageRes());
        values.put(COLUMN_BACKGROUND_RES, cardItem.getBackgroundRes());
        values.put(COLUMN_RARITY_RES, cardItem.getRarityRes());

        // Update the card item with the given ID
        return database.update(TABLE_CARDS, values,
            COLUMN_ID + " = ?", new String[]{String.valueOf(cardItem.getId())});
    }

    // Delete a CardItem by ID
    public void deleteCardItem(int id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_CARDS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        database.close();
    }

    // Delete all CardItems
    public void deleteAllCardItems() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_CARDS, null, null);
        database.close();
    }
}
