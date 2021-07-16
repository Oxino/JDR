package com.example.jdrandroidjava;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// cool
public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Button addPlayer = (Button) findViewById(R.id.addPlayer);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomeActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_addplayer, null);
                EditText mName = (EditText) mView.findViewById(R.id.editName);
                EditText mNumberMax =  (EditText) mView.findViewById(R.id.editEncombrementMax);
                Button mAdd = (Button) mView.findViewById(R.id.add);

                mAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(HomeActivity.this, "ajout du personnage reussi", Toast.LENGTH_SHORT).show();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();
            }
        });
    }

    public void goToPage2(View view) {
        Intent k = new Intent(HomeActivity.this, InventoryActivity.class);
        startActivity(k);
    }
}