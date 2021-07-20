package com.example.jdrandroidjava;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

public class BottomSheetItemFragment extends BottomSheetDialogFragment {

    public static final String TAG="ModalBottomSheetItemFragment";

    private Item item;
    private ItemActionEnum action;

    private ItemViewModel mItemViewModel;


    private Button cancelBtn;
    private Button deleteBtn;
    private TextView deleteTitle;

    public static BottomSheetItemFragment getInstance(Item item, ItemActionEnum action){
        BottomSheetItemFragment bottomSheetItemFragment = new BottomSheetItemFragment();
        Bundle args = new Bundle();
        args.putSerializable("item", item);
        args.putSerializable("action", action);
        bottomSheetItemFragment.setArguments(args);
        return bottomSheetItemFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.bottom_sheet_item_fragment, container,false);

        deleteBtn = view.findViewById(R.id.delete_item);
        cancelBtn = view.findViewById(R.id.cancel_item);
        deleteTitle = view.findViewById(R.id.title_delete);

        mItemViewModel = new ViewModelProvider(this).get(ItemViewModel.class);


        Bundle bundle = this.getArguments();

        if (bundle != null) {
            try {
                item = (Item) bundle.getSerializable("item");
                action = (ItemActionEnum) bundle.getSerializable("action");
            }catch (Exception e){
            }
        }

        setDeleteFragment();
        return view;
    }

    private void setDeleteFragment(){
        deleteTitle.setText(getResources().getString(R.string.delete_item) + " " + item.getName() + " " + getResources().getString(R.string.question));
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemViewModel.delete(item.getId());
                String snackbarText = getResources().getString(R.string.confirm_common_item) + " " + item.getName() + " "+ getResources().getString(R.string.confirm_delete);
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), snackbarText, Snackbar.LENGTH_LONG);
                snackbar.show();
                dismiss();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}

