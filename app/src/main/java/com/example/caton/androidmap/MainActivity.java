package com.example.caton.androidmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onClickSingle(View view) {
        Intent intent = new Intent(this, SingleAddressActivity.class);
        startActivity(intent);
    }

    public void onClickTwo(View view){
        Intent intent = new Intent(this, TwoAddressActivity.class);
        startActivity(intent);
    }

}
