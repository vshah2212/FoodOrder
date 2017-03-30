package com.example.vaibhavshah.seapp2;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

    public class GetData extends AsyncTask<String, Void, String> {
        private TextView display;

        GetData(TextView view){
            this.display = view;
            display = (TextView) view.findViewById(R.id.textView1);
        }

        protected String doInBackground(String... message){
            HttpClient httpclient;
            HttpGet request;
            HttpResponse response = null;
            String result = "";
            try{
                String str = message[0];
                httpclient = new DefaultHttpClient();
                request = new HttpGet("http://phpdatabaseandroid.esy.es/AddOrder/Payment.php?id="+str);
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
            this.display.setText(result);
        }
    }

