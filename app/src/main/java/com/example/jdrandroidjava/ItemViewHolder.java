package com.example.jdrandroidjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

class ItemViewHolder extends RecyclerView.ViewHolder{

    private final TextView name;
    private final TextView size;
    private final TextView description;
    private final LinearLayout expandedView;
    private final LinearLayout element;
    private final ImageView arrow;
    private final Button deleteBtn;
    private final Button updateBtn;

    private boolean isExpanded = false;

    private ItemViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        size = itemView.findViewById(R.id.size);
        description = itemView.findViewById(R.id.description);
        expandedView = itemView.findViewById(R.id.expanded_view);
        element = itemView.findViewById(R.id.element);
        RelativeLayout layout = itemView.findViewById(R.id.layout);
        arrow = itemView.findViewById(R.id.arrow);
        deleteBtn = itemView.findViewById(R.id.delete_item);
        updateBtn = itemView.findViewById(R.id.update_item);
    }


    private void setListener(Item item){
        element.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                int visibleValue = isExpanded ? View.VISIBLE : View.GONE;
                expandedView.setVisibility(visibleValue);
                float rotateXArrow = isExpanded ? 0 : 180;
                arrow.setRotationX(rotateXArrow);
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                BottomSheetItemFragment bottomSheetItemFragment = BottomSheetItemFragment.getInstance(item, ItemActionEnum.DELETE);
                bottomSheetItemFragment.showNow(activity.getSupportFragmentManager(), BottomSheetCharacterFragment.TAG);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ItemActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("item", item);
                b.putInt("characterId", item.characterId);
                intent.putExtras(b);
                view.getContext().startActivity(intent);
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