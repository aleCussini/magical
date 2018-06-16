package com.mtsoftware.magical.tasks;

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

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import static com.mtsoftware.magical.utility.Constants.cardsMap;

/**
 * Created by NESEA on 07/12/2017.
 */

public class DownloadImageFromMTGDBTask extends AsyncTask{
    Bitmap bmp;
    TextView nestedCardIDTextView;
    Button btnFlip;
    ImageView imageView;
    CardDTO dto;
    String codeToSearch;

    @Override
    protected Object doInBackground(Object[] objects) {

        nestedCardIDTextView = (TextView) objects[0];
        btnFlip = (Button)objects[1];
        imageView = (ImageView)objects[2];
        codeToSearch = (String)objects[3];


        dto = cardsMap.get(nestedCardIDTextView.getText());

        URL url;
        InputStream is = null;
        BufferedReader dis;
        String line;
        String out = "";

        url = null;

        String serverAddress = "http://mtgdb.net/_dbimg/";

        try

        {
            url = new URL(serverAddress + codeToSearch + ".jpg");
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onPostExecute(Object o) {

        Log.w(Constants.TAG,"E' andata tutto ok: " + dto.getCardId());


        imageView.setImageBitmap(bmp);

        if(Integer.valueOf(dto.getCardCode()) > 500 | (!dto.getCardFlip().equals("null")) ){
            btnFlip.setVisibility(View.VISIBLE);
        }

        nestedCardIDTextView.setText(dto.getCardId());

    }
}
