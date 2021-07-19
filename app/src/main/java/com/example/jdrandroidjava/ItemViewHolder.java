package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class ItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView name;
    private final TextView size;
    private final TextView description;
    private final RelativeLayout expandedView;
    private final RelativeLayout layout;
    private final CardView card;

    private boolean isExpanded = false;

    private ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        size = itemView.findViewById(R.id.size);
        description = itemView.findViewById(R.id.description);
        expandedView = itemView.findViewById(R.id.expanded_view);
        card = itemView.findViewById(R.id.card);
        layout = itemView.findViewById(R.id.layout);
    }


    private void setListener(Item item){
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                int visibleValue = isExpanded ? View.VISIBLE : View.GONE;
                expandedView.setVisibility(visibleValue);
            }
        });
    }

    public void bind(Item item) {
        name.setText(item.getName());
        size.setText(String.valueOf(item.getSize()));
        description.setText(item.getDescription());
        setListener(item);
    }


    static ItemViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

}