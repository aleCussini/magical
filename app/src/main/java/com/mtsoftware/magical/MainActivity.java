package com.mtsoftware.magical;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mtsoftware.magical.tasks.DownloadImageFromMTGDBTask;
import com.mtsoftware.magical.tasks.LoadCardsDTOTask;
import com.mtsoftware.magical.tasks.ReadFromWebTask;
import com.mtsoftware.magical.tasks.UpdateDBTask;
import com.mtsoftware.magical.utility.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.mtsoftware.magical.R.id.cardNameAutoComplete;
import static com.mtsoftware.magical.R.id.imageView;
import static com.mtsoftware.magical.utility.Constants.cardsMap;
public class MainActivity extends Activity {

    Context ctx;
    ProgressDialog progressDialog;
    Button btnFlip;
    ArrayAdapter<String> autoCompleteAdapter;
    DatabaseReference magicalDBReference;
    AutoCompleteTextView autoCompleteTextView;
    TextView nestedCardIDView;
    ImageView imageView;

    public MainActivity() throws IOException {
    }

    @Override
    public AssetManager getAssets() {
        return super.getAssets();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        nestedCardIDView = findViewById(R.id.cardID);
        btnFlip = findViewById(R.id.btnFlip);
        imageView = findViewById(R.id.imageView);
        autoCompleteTextView = findViewById(cardNameAutoComplete);
        final TextView priceTextView = findViewById(R.id.price);

        magicalDBReference = FirebaseDatabase.getInstance().getReference();

        Log.w(Constants.TAG,"Lancio il task di caricamento dal DB");
        LoadCardsDTOTask loadCardsDTOTask = new LoadCardsDTOTask();

        try {

            cardsMap = (HashMap<String, CardDTO>) loadCardsDTOTask.execute(ctx,autoCompleteTextView).get();

            Log.w(Constants.TAG,cardsMap.size() + "");

            populateAutoComplete();

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                btnFlip.setVisibility(View.INVISIBLE);
                priceTextView.setText("...");

                String name = ((TextView)view).getText().toString();

                for (CardDTO dto : cardsMap.values()){

                    if(dto.getCardName().equals(name)){

                        ReadFromWebTask readFromWebTask = new ReadFromWebTask();
                        readFromWebTask.execute(dto,priceTextView,ctx,imageView,nestedCardIDView,btnFlip);

                    }

                }

            }

        });

    }

    public void createNewPost(View view){

        Intent createPostIntent = new Intent(this,CreatePostActivity.class);

        startActivity(createPostIntent);

    }

    public void updateDB(View view) throws IOException, ExecutionException, InterruptedException {

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Aggiornamento del DB... al termine riaprire l'App"); // Setting Message
        progressDialog.setTitle("Attendere"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);

        Button btnUpdate = findViewById(R.id.btnUpdateDB);
        btnUpdate.setEnabled(false);

        UpdateDBTask task = new UpdateDBTask();
        task.execute(ctx,autoCompleteAdapter,progressDialog);


    }

    public void populateAutoComplete(){

        ArrayList<String> cardNames = new ArrayList<>();

        for (CardDTO dto : cardsMap.values()){

            cardNames.add(dto.getCardName());
        }

        autoCompleteAdapter = new ArrayAdapter<>(getBaseContext(),android.R.layout.simple_dropdown_item_1line, cardNames);
        AutoCompleteTextView textView = findViewById(cardNameAutoComplete);
        textView.setAdapter(autoCompleteAdapter);

        autoCompleteAdapter.notifyDataSetChanged();

    }

    public void flipCard(View view){

        Button flipBtn = findViewById(R.id.btnFlip);
        final TextView nestedCardIDTextView = findViewById(R.id.cardID);

        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codeToSearch;
                CardDTO dto = cardsMap.get(nestedCardIDTextView.getText().toString());
                String code = dto.getCardCode();
                String cardSet = dto.getCardSet();
                int intCode = Integer.valueOf(code);
                String modifiedValue;

                if (intCode>500){
                    modifiedValue = String.format("%03d", (intCode-500));
                    codeToSearch = cardSet + "/" + modifiedValue;
                    nestedCardIDTextView.setText(cardSet + modifiedValue);

                }
                else{
                    modifiedValue = String.format("%03d", (intCode+500));
                    codeToSearch = cardSet + "/" + modifiedValue;
                    nestedCardIDTextView.setText(cardSet + modifiedValue);
                }

                new DownloadImageFromMTGDBTask().execute(nestedCardIDTextView,btnFlip,imageView,codeToSearch);

            }
        });

    }

}
