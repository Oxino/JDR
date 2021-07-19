package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.example.jdrandroidjava.ItemRepository;

public class AddObjectActivity extends AppCompatActivity {

    private CharacterWithItems characterWithItems;

    private CharacterWithItemsViewModel mCharacterWithItemsViewModel;
    private ItemViewModel mItemViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b != null){
            int id = b.getInt("characterId");
            mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
            mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
            mCharacterWithItemsViewModel.getCharacterWithItems(id).observe(this, characterWithItemsValue -> {
                characterWithItems = characterWithItemsValue;
                setViewValue();
            });
        }else{
            Intent intent = new Intent(AddObjectActivity.this, HomeActivity.class);
            startActivity(intent);
        }

        setContentView(R.layout.activity_addobject);

    }

    private void setViewValue(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(characterWithItems.character.getName() + " " + characterWithItems.getActualStorageToString());
        actionBar.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(AddObjectActivity.this, InventoryActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }


    public void addItem(View view) {
        boolean isError = false;
        int itemSizeValue = 0;

        TextInputLayout itemText = findViewById(R.id.itemName);
        String itemName = itemText.getEditText().getText().toString();

        if(itemName.trim().isEmpty()){
            itemText.setError("Nom requis, sans caractères speciaux!" ); // ressource string value
            isError = true;
        }else{
            itemText.setError(null);
        }

        itemText = findViewById(R.id.itemDescription);
        String itemDescription = itemText.getEditText().getText().toString();

        itemText = findViewById(R.id.itemClutter);
        String itemSizeString = itemText.getEditText().getText().toString();

        if(itemSizeString.isEmpty()){
            itemText.setError("Encombrement requis!");
            isError = true;
        }else{
            itemText.setError(null);
            itemSizeValue = Integer.parseInt(itemSizeString);
        }

        if(!isError){
            if(characterWithItems.canHaveThisItem(itemSizeValue)){
                String snackbarTest = getResources().getString(R.string.item_too_big);
                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackbarTest, Snackbar.LENGTH_LONG);
                snackbar.show();
                isError = true;
            }

            if(!isError){
                Item item = new Item(itemName, itemDescription, itemSizeValue, characterWithItems.character.getId());
                mItemViewModel.insert(item);

                Intent intent = new Intent(this, InventoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("characterId", characterWithItems.character.getId());
                b.putSerializable("event", ItemActionEnum.ADD);
                intent.putExtras(b);
                startActivity(intent);
            }

        }



    }
}