package com.example.jdrandroidjava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class CharacterWithItemsViewHolder extends RecyclerView.ViewHolder{
    private final TextView characterNameView;
    private final TextView characterNumberItemsView;
    private final TextView characterSizeItemsView;
    private final CardView characterCardView;
    private final LinearLayout baseLayoutView;
    private final LinearLayout onClickLayoutView;
    private final int importantColor;
    private final int whiteColor;

    private CharacterWithItemsViewHolder(View itemView) {
        super(itemView);
        characterNameView = itemView.findViewById(R.id.character_name);
        characterNumberItemsView = itemView.findViewById(R.id.character_number_items);
        characterSizeItemsView = itemView.findViewById(R.id.character_size_items);
        characterCardView = itemView.findViewById(R.id.character_card_view);
        baseLayoutView = itemView.findViewById(R.id.base_layout);
        onClickLayoutView = itemView.findViewById(R.id.onClick_layout);

        importantColor = ContextCompat.getColor(itemView.getContext(), R.color.important);
        whiteColor = ContextCompat.getColor(itemView.getContext(), R.color.white);

        setListener();
    }

    private void setLayoutVisibility(int isBaseLayoutVisible, int isOnClickLayoutVisible){
        baseLayoutView.setVisibility(isBaseLayoutVisible);
        onClickLayoutView.setVisibility(isOnClickLayoutVisible);

        if(isBaseLayoutVisible == View.VISIBLE){
            characterCardView.setCardBackgroundColor(whiteColor);
        }else{
            characterCardView.setCardBackgroundColor(importantColor);
        }
    }

    private void setListener(){
        characterCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                characterCardView.setCardBackgroundColor(ContextCompat.getColor(v.getContext(), R.color.important));
                baseLayoutView.setVisibility(View.INVISIBLE);
                onClickLayoutView.setVisibility(View.VISIBLE);
                return true;
            }
        });
    }

    public void bind(CharacterWithItems characterWithItems, int isBaseLayoutVisible, int isOnClickLayoutVisible) {
        characterNameView.setText(characterWithItems.character.getName());
        characterNumberItemsView.setText(getNumberItems(characterWithItems.items));
        characterSizeItemsView.setText(getSizeItems(characterWithItems));

        setLayoutVisibility(isBaseLayoutVisible, isOnClickLayoutVisible);
    }

    private String getNumberItems(List<Item> items){
        return String.valueOf(items.size());
    }

    private String getSizeItems(CharacterWithItems characterWithItems){
        int size = 0;
        List<Item> items = characterWithItems.items;
        for (Item item: items) {
            size += item.size;
        }
        return size + "/" + characterWithItems.character.storage;
    }


    static CharacterWithItemsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_recyclerview_item, parent, false);
        return new CharacterWithItemsViewHolder(view);
    }

}