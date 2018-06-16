package com.mtsoftware.magical.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.AutoCompleteTextView;

import com.mtsoftware.magical.CardDTO;
import com.mtsoftware.magical.db.DBManager;
import com.mtsoftware.magical.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import static com.mtsoftware.magical.utility.Constants.cardsMap;
import static com.mtsoftware.magical.utility.Constants.setsNameProp;

/**
 * Created by NESEA on 05/12/2017.
 */

public class LoadCardsDTOTask extends AsyncTask {

    Context ctx;
    AutoCompleteTextView autoCompleteTextView;
    HashMap<String,CardDTO> cardMap = new HashMap<>();

    @Override
    protected Object doInBackground(Object[] objects) {

        Cursor cursor;
        ctx = (Context) objects[0];
        autoCompleteTextView = (AutoCompleteTextView) objects[1];

        setsNameProp = new Properties();

        DBManager dbManager = new DBManager(ctx);

        dbManager.open();

        cursor = dbManager.fetchAllDataCards();

        while(cursor.moveToNext()){

            CardDTO cardDTO = new CardDTO();

            cardDTO.setCardName(cursor.getString(cursor.getColumnIndex(Constants.CARD_NAME_COLUMN)));
            cardDTO.setCardColor(cursor.getString(cursor.getColumnIndex(Constants.CARD_COLOR_COLUMN)));
            cardDTO.setCardEngName(cursor.getString(cursor.getColumnIndex(Constants.CARD_ENG_NAME_COLUMN)));
            cardDTO.setCardFC(cursor.getString(cursor.getColumnIndex(Constants.CARD_FC_COLUMN)));
            cardDTO.setCardManaCost(cursor.getString(cursor.getColumnIndex(Constants.CARD_MANA_COST_COLUMN)));
            cardDTO.setCardRarity(cursor.getString(cursor.getColumnIndex(Constants.CARD_RARITY_COLUMN)));
            cardDTO.setCardSet(cursor.getString(cursor.getColumnIndex(Constants.CARD_SET_COLUMN)));
            cardDTO.setCardCode(cursor.getString(cursor.getColumnIndex(Constants.CARD_CODE_COLUMN)));
            cardDTO.setCardText(cursor.getString(cursor.getColumnIndex(Constants.CARD_TEXT_COLUMN)));
            cardDTO.setCardType(cursor.getString(cursor.getColumnIndex(Constants.CARD_TYPE_COLUMN)));
            cardDTO.setCardFlip(cursor.getString(cursor.getColumnIndex(Constants.CARD_FLIP_COLUMN)));
            cardDTO.setCardId(cursor.getString(cursor.getColumnIndex(Constants.CARD_ID_COLUMN)));

            cardMap.put(cardDTO.getCardId(),cardDTO);

        }

        cursor = dbManager.fetchAllSets();

        while(cursor.moveToNext()){

            setsNameProp.setProperty(cursor.getString(cursor.getColumnIndex(Constants.SET_CODE_COLUMN)) , cursor.getString(cursor.getColumnIndex(Constants.SET_DESC_COLUMN)));

        }

        return cardMap;
    }

}