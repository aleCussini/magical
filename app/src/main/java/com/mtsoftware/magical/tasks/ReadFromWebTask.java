package com.mtsoftware.magical.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mtsoftware.magical.CardDTO;

import com.mtsoftware.magical.utility.Constants;
import com.mtsoftware.magical.utility.UtilityClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.URL;

import static com.mtsoftware.magical.utility.Constants.cardsMap;
import static com.mtsoftware.magical.utility.Constants.setsNameProp;

/**
 * Created by NESEA on 05/12/2017.
 */

public class ReadFromWebTask extends AsyncTask {


    UtilityClass utilityClass;
    TextView priceTW;
    String price;
    Context ctx;
    ImageView imageView;
    Bitmap bmp;
    TextView nestedcardIDView;
    CardDTO dto;
    Button btnFlip;

    @Override
    protected Object doInBackground(Object[] objects) {



        dto = (CardDTO)objects[0];
        priceTW = (TextView)objects[1];
        ctx = (Context)objects[2];
        imageView = (ImageView)objects[3];
        nestedcardIDView = (TextView) objects[4];
        btnFlip = (Button) objects[5];

        nestedcardIDView.setText(dto.getCardId());

        String codeToSearch = dto.getCardSet() +"/"+ dto.getCardCode();

        new DownloadImageFromMTGDBTask().execute(nestedcardIDView,btnFlip,imageView,codeToSearch);

        utilityClass = new UtilityClass();

        String inputCardName = dto.getCardName();
        String formattedMkmName;

        if(Integer.valueOf(dto.getCardCode()) > 500){

            Log.w(Constants.TAG,"E' una carta flippata");
            int primaryCardCode = Integer.valueOf(dto.getCardCode()) - 500;
            Log.w(Constants.TAG,String.format("%03d", primaryCardCode));
            String primaryCardName = cardsMap.get(dto.getCardSet() + String.format("%03d", primaryCardCode)).getCardName();
            formattedMkmName = utilityClass.formatURLtoMKM(primaryCardName,inputCardName);
            Log.w(Constants.TAG , formattedMkmName);

        }

        else{

            if(!dto.getCardFlip().equals("null")){

                Log.w(Constants.TAG,"E' una carta che flippa");
                String flipCardName = cardsMap.get(dto.getCardSet() + dto.getCardFlip()).getCardName();
                formattedMkmName = utilityClass.formatURLtoMKM(inputCardName,flipCardName);
                Log.w(Constants.TAG , formattedMkmName);


            }

            else{

                Log.w(Constants.TAG,"E' una carta normale");
                formattedMkmName = utilityClass.formatURLtoMKM(dto.getCardName(),null);
                Log.w(Constants.TAG,formattedMkmName);
            }

        }

        String property = setsNameProp.getProperty(dto.getCardSet());

        Log.w(Constants.TAG,"property" + property);

        if (property != null && !property.equals("")) {

            String formattedExpansion = property.replace(" ", "+");
            String completeEngPathName = formattedExpansion + "/" + formattedMkmName;
            Log.w(Constants.TAG, completeEngPathName);

            URL url;
            InputStream is = null;
            BufferedReader dis;
            String line;
            String out = "";

            try {
                url = new URL(Constants.URL_MKM + completeEngPathName);
                is = url.openStream();  // throws an IOException
                dis = new BufferedReader(new InputStreamReader(is));

                boolean cycle = true;
                while ((line = dis.readLine()) != null && cycle) {

                    int indexs = line.indexOf(Constants.START_SEARCH_PRICE);
                    if (indexs > -1) {

                        int index = line.indexOf(Constants.END_SEARCH_PRICE, indexs) + 2;
                        int startSubString = index; //+ searchStart.length();
                        int endSubString = line.indexOf(Constants.SPACE, startSubString);

                        price = line.substring(startSubString, endSubString);

                        cycle = false;

                    }
                }


//                url = null;
//                String serverAddress = "http://mtgdb.net/_dbimg/";
//
//                url = new URL(serverAddress + dto.getCardSet() + "/" + dto.getCardCode() + ".jpg");
//
//                bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (RuntimeException | IOException e) {

                return null;

            } finally {
                try {
                    is.close();
                } catch (IOException ioe) {

                }
            }

        }

        return null;

    }

    @Override
    protected void onPostExecute(Object o) {

        if (price!=null)
            priceTW.setText(price + Constants.SPACE + Constants.CURRENCY);
        else
            priceTW.setText(Constants.PRICE_NOT_AVAILABLE_ERROR);



//        imageView.setImageBitmap(bmp);
//
//
//        if(Integer.valueOf(dto.getCardCode()) > 500 | (!dto.getCardFlip().equals("null")) ){
//            btnFlip.setVisibility(View.VISIBLE);
//        }
    }
}
