package com.mtsoftware.magical.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.mtsoftware.magical.CreatePostActivity;
import com.mtsoftware.magical.MainActivity;
import com.mtsoftware.magical.db.DBManager;
import com.mtsoftware.magical.utility.Constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import static android.support.v4.content.ContextCompat.startActivity;
import static com.mtsoftware.magical.utility.Constants.setsNameProp;

/**
 * Created by NESEA on 05/12/2017.
 */

public class UpdateDBTask extends AsyncTask {

    Context ctx;
    ArrayAdapter autoCompleteAdapter;
    ProgressDialog progressDialog;

    @Override
    protected Object doInBackground(Object[] objects) {

        ctx = (Context)objects[0];
        autoCompleteAdapter = (ArrayAdapter) objects[1];
        progressDialog = (ProgressDialog) objects[2];

        setsNameProp = new Properties();

        String cardListLine = null;
        URL cardListUrl = null;

        String propertiesLine;
        URL propertiesURL;

        try {

            propertiesURL   = new URL(Constants.URL_MTS + "sets.txt");
            cardListUrl     = new URL(Constants.URL_MTS + "cardlist.txt");

            DBManager db = new DBManager(ctx);

            db.open();

            db.cleanTable(Constants.SETS_TABLE);
            db.cleanTable(Constants.CARDS_TABLE);


            BufferedReader cardsReader;
            BufferedReader setsReader;
            try {

                setsReader = new BufferedReader(new InputStreamReader(propertiesURL.openStream()));
                while ((propertiesLine = setsReader.readLine()) != null){

                    //creo il record
                    String[] recordToInsert = propertiesLine.split("=");
                    db.insertIntoSets(recordToInsert);

                }

                cardsReader = new BufferedReader(new InputStreamReader(cardListUrl.openStream()));
                while ((cardListLine = cardsReader.readLine()) != null){

                    //creo il record
                    String[] recordToInsert = cardListLine.split("\t");

                    db.insertIntoCards(recordToInsert);

                }

                db.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return true;

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        progressDialog.dismiss();

    }


}