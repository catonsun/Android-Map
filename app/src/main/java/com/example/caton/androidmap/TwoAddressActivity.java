package com.example.caton.androidmap;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.util.Log;

public class TwoAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_address);

    }

    public void sendAddresses(View view) {
        EditText editText1 = findViewById(R.id.address_one_text);
        EditText editText2 = findViewById(R.id.address_two_text);

        //if one of the text boxes is empty
        if((editText1.getText() == null || editText1.getText().toString().equals("")) ||
                editText2.getText() == null || editText2.getText().toString().equals("")) {
            Context context = getApplicationContext();
            CharSequence text = "Please enter two locations!";
            int duration = Toast.LENGTH_SHORT;

            Toast.makeText(context, text, duration).show();

        }
        else {
            Log.e("TwoAddressActivity","starting two map activity");
            String addr1 = editText1.getText().toString();
            String addr2 = editText2.getText().toString();
            Intent intent = new Intent(this, TwoMapActivity.class);
            intent.putExtra("addr1", addr1);
            intent.putExtra("addr2", addr2);
            startActivity(intent);
        }
    }
}
