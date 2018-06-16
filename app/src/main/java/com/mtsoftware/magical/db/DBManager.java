package com.mtsoftware.magical.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mtsoftware.magical.utility.Constants;

import java.io.BufferedReader;

import java.io.IOException;

import static com.mtsoftware.magical.utility.Constants.DB_NAME;


/**
 * Created by NESEA on 21/11/2017.
 */

public class DBManager {

    SQLiteDatabase db;
    DBHelper helper;
    Context ctx;

    public DBManager(Context ctx){

        this.ctx = ctx;
        helper = new DBHelper(ctx);

    }

    public void open(){

        db=helper.getWritableDatabase();

    }

    public void close(){

        db.close();

    }

    public void insertIntoSets(String[] record){

        ContentValues cv = new ContentValues();

        cv.put("setCode",record[0]);
        cv.put("setDesc",record[1]);

        db.insert(Constants.SETS_TABLE,null,cv);

    }

    public void insertIntoCards(String[] record){

        ContentValues cv = new ContentValues();

        cv.put("cardName", record[0]);
        cv.put("cardColor", record[1]);
        cv.put("cardType", record[2]);
        cv.put("cardManaCost", record[3]);
        cv.put("cardFC", record[4]);
        cv.put("cardText", record[5]);
        cv.put("cardSet", record[6]);
        cv.put("cardRarity", record[7]);
        cv.put("cardCode", record[8]);
        cv.put("cardEngName", record[9]);
        cv.put("cardFlip", record[10]);
        cv.put("cardID",record[11]);

        db.insert(Constants.CARDS_TABLE,null,cv);

    }


    public Cursor fetchAllDataCards(){

        return  db.query(Constants.CARDS_TABLE,null,null,null,null,null,null);

    }

    public Cursor fetchAllSets(){

        return db.query(Constants.SETS_TABLE,null,null,null,null,null,null);

    }

    public void cleanTable (String table){

        db.execSQL("DELETE FROM " + table);

    }

    private static final String CARDS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Constants.CARDS_TABLE + "("
            + Constants.CARD_NAME_COLUMN + " text not null , "
            + Constants.CARD_COLOR_COLUMN + " text, "
            + Constants.CARD_TYPE_COLUMN + " text not null, "
            + Constants.CARD_MANA_COST_COLUMN + " text, "
            + Constants.CARD_FC_COLUMN + " text, "
            + Constants.CARD_TEXT_COLUMN + " text, "
            + Constants.CARD_SET_COLUMN + " text not null, "
            + Constants.CARD_RARITY_COLUMN + " text not null, "
            + Constants.CARD_CODE_COLUMN + " text not null , "
            + Constants.CARD_ENG_NAME_COLUMN + " text,  "
            + Constants.CARD_FLIP_COLUMN + " text, "
            + Constants.CARD_ID_COLUMN + " text unique )";

    private static final String SETS_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + Constants.SETS_TABLE + "("
            + Constants.SET_CODE_COLUMN + " text not null unique, "
            + Constants.SET_DESC_COLUMN + " text not null )";

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context){

            super(context,DB_NAME,null,1);

        }

        @Override
        public void onCreate(SQLiteDatabase db){

            Log.w("###-->>","Creo il DB");

            db.execSQL(CARDS_CREATE_TABLE);
            db.execSQL(SETS_CREATE_TABLE);

            Log.w("####--->>>>","Creo le Tabelle");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion){

        }

    }

}
