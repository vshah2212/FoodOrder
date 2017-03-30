package com.example.vaibhavshah.seapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PaymentPortal extends AppCompatActivity {
    TextView print,txtViewS;
    EditText id,pass;
    Button b,bexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_portal);

        print = (TextView) findViewById(R.id.textViewPrint);
        id = (EditText) findViewById(R.id.editTextID);
        pass = (EditText) findViewById(R.id.editTextPin);
        b = (Button) findViewById(R.id.button2);
        txtViewS = (TextView) findViewById(R.id.textViewStatus);
        bexit = (Button) findViewById(R.id.buttonExit);

        final String amt = getIntent().getStringExtra("amt");
        final String id1 = getIntent().getStringExtra("id");

        print.setText("Amount to be paid: Rs. "+amt+"");


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String card = id.getText().toString();
                final String pin = pass.getText().toString();
                final String send = card+","+pin+","+amt+"";
                Log.i("log_tag", "onCreate: "+card+","+pin+","+amt);
                Log.i("log_tag", "onCreate: "+send);
                new ChkData(txtViewS).execute(send);
                ButtonToggle(id1);
            }
        });



    }
    void ButtonToggle(String id)
    {
        final String id1 = id;
        txtViewS = (TextView) findViewById(R.id.textViewStatus);
        bexit = (Button) findViewById(R.id.buttonExit);
        if((txtViewS.getText().toString()).equalsIgnoreCase("Transaction successful"))
        {
            bexit.setVisibility(View.VISIBLE);
        }
        bexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link;
                if(id1.equals("1")) {
                    link = "http://phpdatabaseandroid.esy.es/AddOrder/DeleteOrder1.php";
                }
                else {
                    link = "http://phpdatabaseandroid.esy.es/AddOrder/DeleteOrder2.php";
                }

                new DeleteData().execute(link);
                Toast.makeText(PaymentPortal.this, "Order List Refreshed", Toast.LENGTH_LONG).show();
                finish();
                moveTaskToBack(true);
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}
