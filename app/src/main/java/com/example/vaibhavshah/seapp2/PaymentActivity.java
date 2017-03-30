package com.example.vaibhavshah.seapp2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PaymentActivity extends AppCompatActivity {

    TextView txtView;
    Button b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        final String id = getIntent().getStringExtra("id");

        txtView = (TextView) findViewById(R.id.textView1);

        new GetData(txtView).execute(id);



        b1 = (Button) findViewById(R.id.button1);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String amount = txtView.getText().toString();
                Intent intent = new Intent(PaymentActivity.this,PaymentPortal.class);
                intent.putExtra("amt",amount);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onBackPressed() {

    }
}
