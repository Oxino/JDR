package com.example.jdrandroidjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.Collections;
import java.util.List;

public class InventoryActivity extends AppCompatActivity {

    private CharacterWithItems characterWithItems;

    private CharacterWithItemsViewModel mCharacterWithItemsViewModel;

    private LinearLayout order;
    private ImageView arrowOrder;
    private boolean orderAsc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Bundle b = getIntent().getExtras();
        if(b != null){
            int id = b.getInt("characterId");
            mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
            mCharacterWithItemsViewModel.getCharacterWithItems(id).observe(this, characterWithItemsValue -> {
                characterWithItems = characterWithItemsValue;
                setOrderItems();
                setViewValue();
            });
        }else{
            Intent intent = new Intent(InventoryActivity.this, HomeActivity.class);
            startActivity(intent);
        }
    }

    private void setViewValue(){
        arrowOrder = findViewById(R.id.arrow_order);
        order = findViewById(R.id.order);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(characterWithItems.character.getName() + " " + characterWithItems.getActualStorageToString());
        actionBar.show();

        RecyclerView recyclerView = findViewById(R.id.item_recyclerview);
        final ItemAdapter adapter = new ItemAdapter(new ItemAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setClickable(true);
        adapter.submitList(characterWithItems.items);

        if(characterWithItems.items.size() == 0){
            findViewById(R.id.nodata).setVisibility(View.VISIBLE);
            order.setVisibility(View.GONE);
        }

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderAsc = !orderAsc;
                float rotateValue = orderAsc ? -90 : 90;
                arrowOrder.setRotation(rotateValue);
                setOrderItems();
                adapter.submitList(characterWithItems.items);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void setOrderItems(){
        characterWithItems.items.sort(new SortByAlphabet());
        if(!orderAsc){
            Collections.reverse(characterWithItems.items);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(InventoryActivity.this, HomeActivity.class);
        startActivity(intent);

        return super.onOptionsItemSelected(item);
    }
}