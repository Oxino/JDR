package com.example.jdrandroidjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
// cool
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToPage2(View view) {
        Intent k = new Intent(HomeActivity.this, InventoryActivity.class);
        startActivity(k);
    }
}