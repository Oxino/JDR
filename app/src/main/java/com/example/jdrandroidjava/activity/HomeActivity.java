package com.example.jdrandroidjava.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.jdrandroidjava.bottomSheetFragment.BottomSheetCharacterFragment;
import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItemsAdapter;
import com.example.jdrandroidjava.characterWithItemsClass.CharacterWithItemsViewModel;
import com.example.jdrandroidjava.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

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
        CharacterWithItemsViewModel mCharacterWithItemsViewModel = new ViewModelProvider(this).get(CharacterWithItemsViewModel.class);
        mCharacterWithItemsViewModel.getmAllCharactersWithItems().observe(this, adapter::submitList);


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