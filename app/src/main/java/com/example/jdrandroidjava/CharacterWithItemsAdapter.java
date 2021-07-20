package com.example.jdrandroidjava;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class CharacterWithItemsAdapter extends ListAdapter<CharacterWithItems, CharacterWithItemsViewHolder> {
    int baseLayoutVisibility = View.VISIBLE;
    int onClickLayoutVisibility = View.INVISIBLE;

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
        holder.bind(current, baseLayoutVisibility, onClickLayoutVisibility);
    }

    public void updateLayoutVisibility(int baseLayoutVisibility, int onClickLayoutVisibility){
        this.baseLayoutVisibility = baseLayoutVisibility;
        this.onClickLayoutVisibility = onClickLayoutVisibility;
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