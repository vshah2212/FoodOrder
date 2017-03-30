package com.example.vaibhavshah.seapp2;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class ChkData extends AsyncTask<String, Void, String> {
    private TextView display;

    ChkData(TextView view){
        this.display = view;
        display = (TextView) view.findViewById(R.id.textViewStatus);
    }



    protected String doInBackground(String... message){
        HttpClient httpclient;
        HttpGet request;
        HttpResponse response = null;
        String result = "";
        try{
            String send = message[0];
            String[] rec = send.split(",");
            String card = rec[0];

            String pin = rec[1];

            String amt = rec[2];

            Log.i("log_tag", "doInBackground: "+card+" "+pin+" "+amt);
            httpclient = new DefaultHttpClient();
            request = new HttpGet("http://paymentserver96.esy.es/Payment/payment.php?card="+card+"&pin="+pin+"&amt="+amt);
            response = httpclient.execute(request);
        } catch (Exception e){
            result = "error1";
        }

        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            String line="";
            while((line = rd.readLine()) != null){
                result = result + line;
            }
        } catch(Exception e){
            result = "error2";
        }
        return result;
    }

    protected void onPostExecute(String result){

        if(result.equalsIgnoreCase("Valid")) {
            this.display.setTextColor(Color.GREEN);
            this.display.setText("Transaction successful");
        }
        else if(result.equalsIgnoreCase("NoBal")) {
            this.display.setTextColor(Color.RED);
            this.display.setText("Insufficient balance");
        }
        else {
            this.display.setTextColor(Color.RED);
            this.display.setText("Invalid Data");
        }

    }
}

