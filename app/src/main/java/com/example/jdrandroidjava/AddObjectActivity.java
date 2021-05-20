package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class AddObjectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addobject);
    }

    public void goToPage2(View view) {
        Intent k = new Intent(AddObjectActivity.this, InventoryActivity.class);
        startActivity(k);
    }
}