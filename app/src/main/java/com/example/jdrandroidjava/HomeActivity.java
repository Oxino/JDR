package com.example.jdrandroidjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private CharacterWithItemsViewModel mCharacterWithItemsViewModel;

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
        mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
        mCharacterWithItemsViewModel.getmAllCharactersWithItems().observe(this, charactersWithItems -> {
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

        FloatingActionButton fab = findViewById(R.id.add);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetCharacterFragment bottomSheetCharacterFragment = BottomSheetCharacterFragment.getInstance();
                bottomSheetCharacterFragment.showNow(getSupportFragmentManager(), BottomSheetCharacterFragment.TAG);
            }
        });

    }
}