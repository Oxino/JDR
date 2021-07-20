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

import com.google.android.material.textfield.TextInputLayout;

public class ItemActivity extends AppCompatActivity {

    private CharacterWithItems characterWithItems;

    private CharacterWithItemsViewModel mCharacterWithItemsViewModel;
    private ItemViewModel mItemViewModel;
    private Item updatedItem;
    private int characterId;
    private boolean isError = false;
    private int itemSizeValue = 0;

    private TextInputLayout itemName;
    private String itemNameValue;
    private TextInputLayout itemDescription;
    private String itemDescriptionValue;
    private TextInputLayout itemSize;
    private String itemSizeString;


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
            Intent intent = new Intent(ItemActivity.this, InventoryActivity.class);
            startActivity(intent);
        }
            setContentView(R.layout.activity_item);

    }

    private void setViewValue(){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(characterWithItems.character.getName() + " " + characterWithItems.getActualStorageToString());
        actionBar.show();

        if (updatedItem != null){
            Button itemUpdate = findViewById(R.id.item_validate_update);
            itemUpdate.setVisibility(View.VISIBLE);

            Button itemAdd = findViewById(R.id.item_add);
            itemAdd.setVisibility(View.GONE);

            TextInputLayout itemText = findViewById(R.id.itemName);
            itemText.getEditText().setText(updatedItem.getName());

            itemText = findViewById(R.id.itemDescription);
            itemText.getEditText().setText(updatedItem.getDescription());

            itemText = findViewById(R.id.itemSize);
            itemText.getEditText().setText(String.valueOf(updatedItem.getSize()));

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(this, InventoryActivity.class);
        Bundle b = new Bundle();
        b.putInt("characterId", characterWithItems.character.getId());
        intent.putExtras(b);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }

    public void getItemValues(){
        isError = false;
        itemSizeValue = 0;

        itemName = findViewById(R.id.itemName);

        itemNameValue = itemName.getEditText().getText().toString();

        if(itemNameValue.trim().isEmpty()){
            itemName.setError(getResources().getString(R.string.item_name));
            isError = true;
        }else{
            itemName.setError(null);
        }

        itemDescription = findViewById(R.id.itemDescription);
        itemDescriptionValue = itemDescription.getEditText().getText().toString();

        itemSize = findViewById(R.id.itemSize);
        itemSizeString = itemSize.getEditText().getText().toString();

        if(itemSizeString.isEmpty() || itemSizeString == "0"){
            itemSize.setError(getResources().getString(R.string.item_size_required));
            isError = true;
        }else{
            if(itemSizeString != "0"){
                itemSizeValue = Integer.parseInt(itemSizeString);
                if(itemSizeValue == 0){
                    itemSize.setError(getResources().getString(R.string.item_size_required));
                    isError = true;
                }
            }
        }

    }

    public void addItem(View view) {

        getItemValues();

        if(!isError){
            if(!characterWithItems.canHaveThisItem(itemSizeValue)){
                itemSize.setError(itemSize.getResources().getString(R.string.item_too_big));
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

        getItemValues();

        if(!isError){
            if(!characterWithItems.canHaveThisItem(itemSizeValue - updatedItem.getSize())){
                itemSize.setError(itemSize.getResources().getString(R.string.item_too_big));
                isError = true;
            }
            if(!isError){
                Item item = new Item(itemNameValue, itemDescriptionValue, itemSizeValue, updatedItem.getCharacterId());
                item.setId(updatedItem.getId());
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