package com.example.jdrandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class InventoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
    }

    public void goToPage1(View view) {
        Intent k = new Intent(InventoryActivity.this, HomeActivity.class);
        startActivity(k);
    }
}