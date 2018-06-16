package com.mtsoftware.magical.utility;

import android.util.Log;

import java.util.regex.Pattern;

/**
 * Created by NESEA on 06/12/2017.
 */

public class UtilityClass {

    static public String formatImageSearchString(String input){

        String output = null;

        String exp = input.substring(0,input.length()-3);
        String cod = input.substring(input.length()-3);

        output = exp + "/" + cod;

        System.out.print(output);

        return output;

    }

    public String formatURLtoMKM(String firstCard,String flipCard){

        String urlOutput;

        if(null!=flipCard) {

            urlOutput = firstCard + "+%2F%2F+" + flipCard;

        }
        else{
            urlOutput = firstCard.replace(" ","+").replace(",","%2C");

        }

        return urlOutput;


    }

}
