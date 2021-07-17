package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.example.jdrandroidjava.ItemRepository;

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
    public void addItem(View view) {

        int encombrement = 0;

        AutoCompleteTextView charText = (AutoCompleteTextView) findViewById(R.id.charId);
        String charId = charText.getText().toString();

        TextInputLayout itemText = findViewById(R.id.itemName);
        String itemName = itemText.getEditText().getText().toString();

        if(itemName.trim().isEmpty()){
            itemText.setError("Nom requis, sans caractères speciaux!" );
        }else{
            itemText.setError(null);
        }

        itemText = findViewById(R.id.itemDescription);
        String itemDescription = itemText.getEditText().getText().toString();

        itemText = findViewById(R.id.itemClutter);
        String itemClutter = itemText.getEditText().getText().toString();

        if(itemClutter.isEmpty()){
            itemText.setError("Encombrement requis!");
        }else{
            itemText.setError(null);
            encombrement = Integer.parseInt(itemClutter);
        }

        int characterId = 1;
        Item item = new Item(itemName, itemDescription, encombrement, characterId);
        ItemRepository itemRepo = new ItemRepository(getApplication());
        itemRepo.insert(item);

        /*Log.v("charId", charId);
        Log.v("itemName", itemName);
        Log.v("itemDescription", itemDescription);
        Log.v("itemClutter", itemClutter);*/

         /*Intent k = new Intent(AddObjectActivity.this, InventoryActivity.class);
        startActivity(k);*/

    }
}