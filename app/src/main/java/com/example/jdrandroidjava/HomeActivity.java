package com.example.jdrandroidjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private CharacterWithItemsViewModel mCharacterViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.characterRecyclerView);
        final CharacterWithItemsAdapter adapter = new CharacterWithItemsAdapter(new CharacterWithItemsAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setClickable(true);
        recyclerView.setLongClickable(true);
        mCharacterViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
        mCharacterViewModel.getmAllCharactersWithItems().observe(this, charactersWithItems -> {
            adapter.submitList(charactersWithItems);
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView,
                                   int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                CharacterWithItemsAdapter adapter = (CharacterWithItemsAdapter) recyclerView.getAdapter();
                adapter.updateLayoutVisibility(View.VISIBLE, View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }
        });

    }


    public void goToPage2(View view) {
        Intent k = new Intent(HomeActivity.this, InventoryActivity.class);
        startActivity(k);
    }
}