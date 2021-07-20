package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
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
    private final int importantColor;
    private final int whiteColor;
    private final ImageView edit;
    private final ImageView delete;

    private CharacterWithItemsViewHolder(View itemView) {
        super(itemView);
        characterNameView = itemView.findViewById(R.id.character_name);
        characterNumberItemsView = itemView.findViewById(R.id.character_number_items);
        characterSizeItemsView = itemView.findViewById(R.id.character_size_items);
        characterCardView = itemView.findViewById(R.id.character_card_view);
        baseLayoutView = itemView.findViewById(R.id.base_layout);
        edit = itemView.findViewById(R.id.edit);
        delete = itemView.findViewById(R.id.delete);
        importantColor = ContextCompat.getColor(itemView.getContext(), R.color.important);
        whiteColor = ContextCompat.getColor(itemView.getContext(), R.color.white);
    }

    private void setLayoutVisibility(int baseLayoutVisibility, int onClickLayoutVisibility){
        baseLayoutView.setVisibility(baseLayoutVisibility);
        edit.setVisibility(onClickLayoutVisibility);
        delete.setVisibility(onClickLayoutVisibility);

        if(baseLayoutVisibility == View.VISIBLE){
            characterCardView.setCardBackgroundColor(whiteColor);
        }else{
            characterCardView.setCardBackgroundColor(importantColor);
        }
    }

    private void setListener(CharacterWithItems characterWithItems){
        characterCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setLayoutVisibility(View.INVISIBLE, View.VISIBLE);
                return true;
            }
        });

        characterCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), InventoryActivity.class);
                Bundle b = new Bundle();
                b.putInt("characterId", characterWithItems.character.getId()); //Your id
                intent.putExtras(b); //Put your id to your next Intent
                v.getContext().startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                BottomSheetCharacterFragment bottomSheetCharacterFragment = BottomSheetCharacterFragment.getInstance(characterWithItems, CharacterAction.UPDATE);
                bottomSheetCharacterFragment.showNow(activity.getSupportFragmentManager(), BottomSheetCharacterFragment.TAG);
                setLayoutVisibility(View.VISIBLE, View.INVISIBLE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                BottomSheetCharacterFragment bottomSheetCharacterFragment = BottomSheetCharacterFragment.getInstance(characterWithItems, CharacterAction.DELETE);
                bottomSheetCharacterFragment.showNow(activity.getSupportFragmentManager(), BottomSheetCharacterFragment.TAG);
                setLayoutVisibility(View.VISIBLE, View.INVISIBLE);
            }
        });


    }

    public void bind(CharacterWithItems characterWithItems, int baseLayoutVisibility, int onClickLayoutVisibility) {
        characterNameView.setText(characterWithItems.character.getName());
        characterNumberItemsView.setText(getNumberItems(characterWithItems.items));
        characterSizeItemsView.setText(characterWithItems.getActualStorageToString());
        setLayoutVisibility(baseLayoutVisibility, onClickLayoutVisibility);

        setListener(characterWithItems);
    }

    private String getNumberItems(List<Item> items){
        return String.valueOf(items.size());
    }

    static CharacterWithItemsViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.character_recyclerview_item, parent, false);
        return new CharacterWithItemsViewHolder(view);
    }

}