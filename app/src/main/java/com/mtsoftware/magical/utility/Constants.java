package com.mtsoftware.magical.utility;

import com.mtsoftware.magical.CardDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by NESEA on 21/11/2017.
 */

public class Constants {

    //Search Crtiteria
    public static final String START_SEARCH_PRICE = "Tendenza di prezzo";
    public static final String END_SEARCH_PRICE = "\">";
    public static final String SPACE = " ";

    //Errors
    public static final String PRICE_NOT_AVAILABLE_ERROR = "Prezzo non disponibile";

    // URLs
    public static final String URL_MKM = "https://www.cardmarket.com/it/Magic/Products/Singles/";
    public static final String URL_MTS = "http://www.mediateamsoftware.com/Magical/";
    public static final String URL_MTG = "http://mtgdb.net/_dbimg/";

    //Constants DB
    public static final String DB_NAME = "MAGICAL_DB";
    public static final String CARDS_TABLE = "CARDS";
    public static final String SETS_TABLE = "SETS";

    //SETS - Columns
    public static final String SET_CODE_COLUMN = "setCode";
    public static final String SET_DESC_COLUMN = "setDesc";

    //CARDS - Columns
    public static final String CARD_NAME_COLUMN = "cardName";
    public static final String CARD_COLOR_COLUMN = "cardColor";
    public static final String CARD_TYPE_COLUMN = "cardType";
    public static final String CARD_MANA_COST_COLUMN = "cardManaCost";
    public static final String CARD_FC_COLUMN ="cardFC";
    public static final String CARD_TEXT_COLUMN = "cardText";
    public static final String CARD_SET_COLUMN = "cardSet";
    public static final String CARD_RARITY_COLUMN = "cardRarity";
    public static final String CARD_CODE_COLUMN = "cardCode";
    public static final String CARD_ENG_NAME_COLUMN = "cardEngName";
    public static final String CARD_FLIP_COLUMN = "cardFlip";
    public static final String CARD_ID_COLUMN ="cardID";

    //UTILITY LOG
    public static final String TAG = "####---->>>>";

    //Characters
    public static final String CURRENCY = "€";

    //Set
    public static Properties setsNameProp;

    //Cache Cards
    //public static ArrayList<CardDTO> cardDTOS = new ArrayList<CardDTO>();
    public static HashMap<String,CardDTO> cardsMap = new HashMap<String,CardDTO>();
}
