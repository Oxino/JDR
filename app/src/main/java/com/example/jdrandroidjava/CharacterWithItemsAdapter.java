package com.example.jdrandroidjava;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CharacterWithItemsAdapter extends ListAdapter<CharacterWithItems, CharacterWithItemsViewHolder> {
    int isBaseLayoutVisible = View.VISIBLE;
    int isOnClickLayoutVisible = View.INVISIBLE;

    public CharacterWithItemsAdapter(@NonNull DiffUtil.ItemCallback<CharacterWithItems> diffCallback) {
        super(diffCallback);
    }

    @Override
    public CharacterWithItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return CharacterWithItemsViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(CharacterWithItemsViewHolder holder, int position) {
        CharacterWithItems current = getItem(position);
        holder.bind(current, isBaseLayoutVisible, isOnClickLayoutVisible);
    }

    public void updateLayoutVisibility(int isBaseLayoutVisible, int isOnClickLayoutVisible){
        this.isBaseLayoutVisible = isBaseLayoutVisible;
        this.isOnClickLayoutVisible = isOnClickLayoutVisible;
    }

    static class WordDiff extends DiffUtil.ItemCallback<CharacterWithItems> {

        @Override
        public boolean areItemsTheSame(@NonNull CharacterWithItems oldItem, @NonNull CharacterWithItems newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull CharacterWithItems oldItem, @NonNull CharacterWithItems newItem) {
            return oldItem.character.getName().equals(newItem.character.getName());
        }
    }
}