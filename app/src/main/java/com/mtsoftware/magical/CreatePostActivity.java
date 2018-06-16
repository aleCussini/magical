package com.mtsoftware.magical;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CreatePostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        Button      btnCreatePost   = findViewById(R.id.btnCreatePost);

        btnCreatePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView    cardName        = findViewById(R.id.cardNameAutoComplete);
                TextView    amount          = findViewById(R.id.amount);
                CheckBox    foil            = findViewById(R.id.foil);
                CheckBox    altered         = findViewById(R.id.altered);
                TextView    cardPrice       = findViewById(R.id.price);

                CardPostBean cardPost = new CardPostBean();

                cardPost.setAltered(altered.isChecked());
                cardPost.setFoil(foil.isChecked());
                cardPost.setCardName(cardName.toString());
                cardPost.setCardPrice(Integer.valueOf(cardPrice.toString()));
                cardPost.setAmount(Integer.valueOf(amount.toString()));

                Log.e("-->> name",cardPost.getCardName());
                Log.e("-->> price",cardPost.getCardPrice() + "");
                Log.e("-->> amount",cardPost.getAmount() + "");
                Log.e("-->> is altered",cardPost.isAltered() + "");
                Log.e("-->> is foil",cardPost.isFoil() + "");

            }
        });


    }

}
