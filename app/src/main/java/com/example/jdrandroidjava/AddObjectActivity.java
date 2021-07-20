package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

public class AddObjectActivity extends AppCompatActivity {

    private CharacterWithItems characterWithItems;

    private CharacterWithItemsViewModel mCharacterWithItemsViewModel;
    private ItemViewModel mItemViewModel;
    private Item updatedItem;
    private int characterId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getIntent().getExtras();
        if(b != null){
            characterId = b.getInt("characterId");

            updatedItem = (Item) b.getSerializable("item");

            mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
            mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);
            mCharacterWithItemsViewModel.getCharacterWithItems(characterId).observe(this, characterWithItemsValue -> {
                characterWithItems = characterWithItemsValue;
                setViewValue();
            });
        }else{
            Intent intent = new Intent(AddObjectActivity.this, InventoryActivity.class);
            startActivity(intent);
        }
            setContentView(R.layout.activity_addobject);

    }

    private void setViewValue(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(characterWithItems.character.getName() + " " + characterWithItems.getActualStorageToString());
        actionBar.show();

        if (updatedItem != null){
            Button sauvegarder = findViewById(R.id.item_validate_update);
            sauvegarder.setVisibility(View.VISIBLE);

            Button ajouter = findViewById(R.id.item_add);
            ajouter.setVisibility(View.GONE);

            TextInputLayout itemText = findViewById(R.id.itemName);
            itemText.getEditText().setText(updatedItem.getName());
            /* String itemName = itemText.getEditText().getText().toString();*/

            itemText = findViewById(R.id.itemDescription);
            itemText.getEditText().setText(updatedItem.getDescription());
            /* String itemDescription = itemText.getEditText().getText().toString();*/

            itemText = findViewById(R.id.itemSize);
            itemText.getEditText().setText(String.valueOf(updatedItem.getSize()));
                /*String itemSizeString = itemText.getEditText().getText().toString();
                itemSizeValue = Integer.parseInt(itemSizeString);*/

        }
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

        TextInputLayout itemName = findViewById(R.id.itemName);

        String itemNameValue = itemName.getEditText().getText().toString();

        if(itemNameValue.trim().isEmpty()){
            itemName.setError(getResources().getString(R.string.item_name));
            isError = true;
        }else{
            itemName.setError(null);
        }

        TextInputLayout itemDescription = findViewById(R.id.itemDescription);
        String itemDescriptionValue = itemDescription.getEditText().getText().toString();

        TextInputLayout itemSize = findViewById(R.id.itemSize);
        String itemSizeString = itemDescription.getEditText().getText().toString();

        if(itemSizeString.isEmpty() || itemSizeString == "0"){
            itemSize.setError(getResources().getString(R.string.item_size));
            isError = true;
        }else{
            itemSize.setError(null);
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
                Item item = new Item(itemNameValue, itemDescriptionValue, itemSizeValue, characterWithItems.character.getId());
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

    public void updateItem(View view){

        boolean isError = false;
        int itemSizeValue = 0;

        TextInputLayout itemName = findViewById(R.id.itemName);

        String itemNameValue = itemName.getEditText().getText().toString();

        if(itemNameValue.trim().isEmpty()){
            itemName.setError(getResources().getString(R.string.item_name));
            isError = true;
        }else{
            itemName.setError(null);
        }

        TextInputLayout itemDescription = findViewById(R.id.itemDescription);
        String itemDescriptionValue = itemDescription.getEditText().getText().toString();

        TextInputLayout itemSize = findViewById(R.id.itemSize);
        String itemSizeString = itemSize.getEditText().getText().toString();

        if(itemSizeString.isEmpty() || itemSizeString == "0"){
            itemSize.setError(getResources().getString(R.string.item_size));
            isError = true;
        }else{
            if(itemSizeString != "0"){
                itemSizeValue = Integer.parseInt(itemSizeString);
                if(itemSizeValue == 0){
                    itemSize.setError(getResources().getString(R.string.item_size));
                    isError = true;
                }
            }
        }

        if(!isError){
            if(!characterWithItems.canHaveThisItem(itemSizeValue - updatedItem.getSize())){
                itemSize.getResources().getString(R.string.item_too_big);
                isError = true;
            }

            if(!isError){
                Item item = new Item(itemNameValue, itemDescriptionValue, itemSizeValue, updatedItem.getId());
                mItemViewModel.update(item);

                Intent intent = new Intent(this, InventoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("characterId", characterWithItems.character.getId());
                b.putSerializable("event", ItemActionEnum.UPDATE);
                intent.putExtras(b);
                startActivity(intent);
            }

        }

    }
}