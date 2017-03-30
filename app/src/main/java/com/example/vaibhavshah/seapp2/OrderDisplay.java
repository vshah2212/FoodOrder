package com.example.vaibhavshah.seapp2;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;
import java.util.HashMap;
import android.graphics.Typeface;


public class OrderDisplay extends AppCompatActivity {

    private static final String REGISTER_URL = "http://phpdatabaseandroid.esy.es/AddOrder/AddOrder.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_display);

        final String userid=getIntent().getStringExtra("UserId");

        Bundle extras ;
        extras = getIntent().getExtras();
        int size = extras.size();

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linear);


        for (int i = 1; i <= size; i++) {

            String txt = extras.getString("Item" + i + "");
            TextView textView = new TextView(this);
            textView.setText(txt);
            Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
            textView.setTypeface(boldTypeface);
            textView.setTextSize(20);
            linearLayout.addView(textView);
            registerOrder(txt,userid);
        }

        Button b1=new Button(this);
        b1.setText("BACK TO HOMEPAGE");
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        linearLayout.addView(b1,lp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(OrderDisplay.this,OrderList.class);
                intent.putExtra("Userid",userid);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {

    }

    private void registerOrder(String name, String id) {

        register(name,id);
    }

    private void register(String name,String id) {
        class RegisterOrder extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            AddOrder ruc = new AddOrder();


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(OrderDisplay.this, "Processing Order",null, true, true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
            }

            @Override
            protected String doInBackground(String... params) {

                HashMap<String, String> data = new HashMap<>();
                data.put("name",params[0]);
                data.put("id",params[1]);

                String result = ruc.sendPostRequest(REGISTER_URL,data);

                return  result;
            }
        }

        RegisterOrder ro = new RegisterOrder();
        ro.execute(name,id);
    }


}
