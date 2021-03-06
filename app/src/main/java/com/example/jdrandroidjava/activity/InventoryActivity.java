package com.example.jdrandroidjava.activity;

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

import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItems;
import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItemsViewModel;
import com.example.jdrandroidjava.itemClass.ItemActionEnum;
import com.example.jdrandroidjava.itemClass.ItemAdapter;
import com.example.jdrandroidjava.R;
import com.example.jdrandroidjava.sort.SortByAlphabet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;

public class InventoryActivity extends AppCompatActivity {

    private CharacterWithItems characterWithItems;
    private ItemActionEnum itemAction;

    private ImageView arrowOrder;
    private boolean orderAsc = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        Bundle b = getIntent().getExtras();
        if(b != null){
            int id = b.getInt("characterId");
            itemAction = (ItemActionEnum) b.getSerializable("event");
            if(itemAction != null){
                showSnackbar();
            }
            CharacterWithItemsViewModel mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
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

    private void showSnackbar(){
        if(itemAction == ItemActionEnum.ADD){
            String snackbarText = getResources().getString(R.string.item_add);
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackbarText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if(itemAction == ItemActionEnum.UPDATE){
            String snackbarText = getResources().getString(R.string.item_updated);
            Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), snackbarText, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private void setViewValue(){
        arrowOrder = findViewById(R.id.arrow_order);
        LinearLayout order = findViewById(R.id.order);

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
                Intent intent = new Intent(view.getContext(), ItemActivity.class);
                Bundle b = new Bundle();
                b.putInt("characterId", characterWithItems.character.getId());
                intent.putExtras(b);
                view.getContext().startActivity(intent);
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