package com.example.vaibhavshah.seapp2;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.HttpClientBuilder;

public class OrderList extends Activity
{

    String set;
    TextView textView;
    Button placeOrder;
    Button exit;

    @Override
    public void onBackPressed() {

    }

    public void onCreate(Bundle savedInstanceState)

    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);

        textView= (TextView) findViewById(R.id.name);
        placeOrder= (Button) findViewById(R.id.placeOrder);
        exit= (Button) findViewById(R.id.buttonexit);

        Bundle extras=getIntent().getExtras();
        boolean chk = extras.containsKey("Userid");
        if(extras!=null && !chk) {
            if(extras.containsKey("key1")) {
                String nm = extras.getString("key1");
                set = nm;
                textView.setText("Welcome:" + set);
            }
        }
        else
        {
            textView.setText("Welcome Back");
            String userId= getIntent().getStringExtra("Userid");
            if(userId.equals("1"))
            {
                set="Table No.1";
            }
            else if(userId.equals("2"))
            {
                set="Table No.2";
            }

        }
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //display menu for placing order
                String userId="0";
                if(set.equals("Table No.1"))
                    userId="1";
                else if(set.equals("Table No.2"))
                    userId="2";
                startActivity(new Intent(OrderList.this,MenuDisplay.class).putExtra("UserId",userId));
            }
        });

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Button button = (Button) findViewById(R.id.button1);


        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                String result = null;
                InputStream is = null;

                try{
                    HttpClient httpclient = HttpClientBuilder.create().build();
                    HttpPost httppost;
                    if(set.equals("Table No.1")) {
                        httppost = new HttpPost("http://phpdatabaseandroid.esy.es/AddOrder/ListOrder1.php");
                    }
                    else {
                        httppost = new HttpPost("http://phpdatabaseandroid.esy.es/AddOrder/ListOrder2.php");
                    }
                    HttpResponse response = httpclient.execute(httppost);
                    HttpEntity entity = response.getEntity();
                    is = entity.getContent();

                 //   Log.e("log_tag", "connection success ");
                    //   Toast.makeText(getApplicationContext(), "pass", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                 //   Log.e("log_tag", "Error in http connection "+e.toString());
                  //  Toast.makeText(getApplicationContext(), "Connection fail", Toast.LENGTH_SHORT).show();

                }
                //convert response to string
                try
                {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                        //  Toast.makeText(getApplicationContext(), "Input Reading pass", Toast.LENGTH_SHORT).show();
                    }
                    is.close();

                    result=sb.toString();
                }
                catch(Exception e)
                {
                 //   Log.e("log_tag", "Error converting result "+e.toString());
                 //   Toast.makeText(getApplicationContext(), " Input reading fail", Toast.LENGTH_SHORT).show();

                }

                //parse json data
                try
                {

                    JSONArray jArray = new JSONArray(result);


                    String re=jArray.getString(jArray.length()-1);


                    TableLayout tv=(TableLayout) findViewById(R.id.table);
                    tv.removeAllViewsInLayout();




                    int flag=1;

                    for(int i=-1;i<jArray.length()-1;i++)

                    {




                        TableRow tr=new TableRow(OrderList.this);

                        tr.setLayoutParams(new LayoutParams(
                                LayoutParams.MATCH_PARENT,
                                LayoutParams.WRAP_CONTENT));




                        if(flag==1)
                        {

                            TextView b6=new TextView(OrderList.this);
                            b6.setPadding(0,0,0,0);
                            b6.setText("OrderName");
                            b6.setTextColor(Color.BLUE);
                            b6.setTextSize(15);
                            tr.addView(b6);


                            TextView b19=new TextView(OrderList.this);
                            b19.setPadding(40,0, 0, 0);
                            b19.setTextSize(15);
                            b19.setText("Status");
                            b19.setTextColor(Color.BLUE);
                            tr.addView(b19);


                            tv.addView(tr);

                            final View vline = new View(OrderList.this);
                            vline.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
                            vline.setBackgroundColor(Color.BLUE);



                            tv.addView(vline);
                            flag=0;


                        }

                        else
                        {



                            JSONObject json_data = jArray.getJSONObject(i);

                            Log.i("log_tag","OrderName: "+json_data.getString("OrderName")+
                                    ", Status: "+json_data.getString("Status"));




                            TextView b1=new TextView(OrderList.this);
                            b1.setPadding(10, 0, 0, 0);
                            b1.setTextSize(15);
                            String stime1=json_data.getString("OrderName");
                            b1.setText(stime1);
                            b1.setTextColor(Color.RED);
                            tr.addView(b1);

                            TextView b2=new TextView(OrderList.this);
                            b2.setPadding(40, 0, 0, 0);
                            String stime2=json_data.getString("Status");
                            b2.setText(stime2);
                            b2.setTextColor(Color.RED);
                            b2.setTextSize(15);
                            tr.addView(b2);

                            tv.addView(tr);


                            final View vline1 = new View(OrderList.this);
                            vline1.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, 1));
                            vline1.setBackgroundColor(Color.WHITE);
                            tv.addView(vline1);


                        }

                    }



                }
                catch(JSONException e)
                {
                //    Log.e("log_tag", "Error parsing data "+e.toString());
                 //   Toast.makeText(getApplicationContext(), "JsonArray fail", Toast.LENGTH_SHORT).show();
                }




            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        /*        String link;
                if(set.equals("Table No.1")) {
                    link = "http://phpdatabaseandroid.esy.es/AddOrder/DeleteOrder1.php";
                }
                else {
                    link = "http://phpdatabaseandroid.esy.es/AddOrder/DeleteOrder2.php";
                }

                new DeleteData().execute(link);
                Toast.makeText(OrderList.this, "Order List Refreshed", Toast.LENGTH_LONG).show(); */

                Intent intent=new Intent(OrderList.this,PaymentActivity.class);
                if(set.equals("Table No.1")) {
                    intent.putExtra("id","1");
                }
                else {
                    intent.putExtra("id","2");;
                }
                startActivity(intent);
            }
        });

    }


}
