package com.example.jdrandroidjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class BottomSheetCharacterFragment extends BottomSheetDialogFragment {

    public static   final String TAG="ModalBottomSheetFragmentMenu";

    private CharacterViewModel mCharacterViewModel;

    private TextInputLayout inputName;
    private TextInputLayout inputSize;
    private TextInputEditText inputNameEdit;
    private TextInputEditText inputSizeEdit;
    private TextView titleAdd;
    private TextView titleUpdate;
    private TextView titleDelete;
    private MaterialButton addBtn;
    private MaterialButton updateBtn;
    private MaterialButton deleteBtn;
    private MaterialButton cancelBtn;
    private MaterialButton addImageBtn;
    private LinearLayout updateAddLayout;
    private LinearLayout deleteLayout;
    private RelativeLayout updateAddButtons;
    private ImageView previewCharacterImage;
    private Uri uriToSave;

    int SELECT_PICTURE = 200;

    public static BottomSheetCharacterFragment getInstance(Character character, CharacterAction action){
        BottomSheetCharacterFragment bottomSheetCharacterFragment = new BottomSheetCharacterFragment();
        Bundle args = new Bundle();
        args.putSerializable("character", character);
        args.putSerializable("action", action);
        bottomSheetCharacterFragment.setArguments(args);
        return bottomSheetCharacterFragment;
    }

    public static BottomSheetCharacterFragment getInstance(){
        BottomSheetCharacterFragment bottomSheetCharacterFragment = new BottomSheetCharacterFragment();
        return bottomSheetCharacterFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.bottom_sheet_character_fragment, container,false);
        addBtn = view.findViewById(R.id.add_character);
        updateBtn = view.findViewById(R.id.update_character);
        deleteBtn = view.findViewById(R.id.delete_character);
        cancelBtn = view.findViewById(R.id.cancel_character);
        addImageBtn = view.findViewById(R.id.add_character_img);

        inputName = view.findViewById(R.id.nameInputLayout);
        inputSize = view.findViewById(R.id.sizeInputLayout);
        inputNameEdit = view.findViewById(R.id.nameInputEdit);
        inputSizeEdit = view.findViewById(R.id.sizeInputEdit);
        titleAdd = view.findViewById(R.id.title_add);
        titleUpdate = view.findViewById(R.id.title_update);
        titleDelete = view.findViewById(R.id.title_delete);

        updateAddLayout = view.findViewById(R.id.update_add_layout);
        deleteLayout = view.findViewById(R.id.delete_layout);

        updateAddButtons = view.findViewById(R.id.update_add_buttons);

        previewCharacterImage = view.findViewById(R.id.preview_character_image);

        mCharacterViewModel = new ViewModelProvider(this).get(CharacterViewModel.class);



        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNameValue = inputName.getEditText().getText().toString();
                String inputSizeString = inputSize.getEditText().getText().toString();
                InputStream iStream = null;
                byte[] avatarImage = null;
                Character newCharacter;
                if (uriToSave != null) {
                    try {
                        iStream = getContext().getContentResolver().openInputStream(uriToSave);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    avatarImage = new byte[0];
                    try {
                        avatarImage = getBytes(iStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                int inputSizeValue = 0;

                if(!hasInputError(inputNameValue, inputSizeString)){
                    inputSizeValue = Integer.parseInt(inputSizeString);
                    if(avatarImage != null) {
                        newCharacter = new Character(inputNameValue, inputSizeValue, avatarImage);
                    }else{
                        newCharacter = new Character(inputNameValue, inputSizeValue);
                    }
                    mCharacterViewModel.insert(newCharacter);
                    String snackbarTest = getResources().getString(R.string.confirm_common) + " " + inputNameValue + " "+ getResources().getString(R.string.confirm_add);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), snackbarTest, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dismiss();
                }
            }
        });

        Bundle bundle = this.getArguments();
        Character receivedCharacter;
        CharacterAction receivedAction;

        if (bundle != null) {
            try {
                receivedCharacter = (Character) bundle.getSerializable("character");
                receivedAction = (CharacterAction) bundle.getSerializable("action");

                if(receivedCharacter != null){
                    if(receivedAction == CharacterAction.UPDATE){
                        setUpdateFragment(receivedCharacter);
                    }
                    if(receivedAction == CharacterAction.DELETE){
                        setDeleteFragment(receivedCharacter);
                    }
                }
            }catch (Exception e){
            }

        }

        return view;
    }


    void imageChooser() {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    String test = selectedImageUri.getPath();
                    uriToSave = selectedImageUri;
                    previewCharacterImage.setImageURI(selectedImageUri);
                    previewCharacterImage.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void setUpdateFragment(Character character){
        inputNameEdit.setText(character.getName());
        inputSizeEdit.setText(String.valueOf(character.getStorage()));
        addBtn.setVisibility(View.GONE);
        updateBtn.setVisibility(View.VISIBLE);
        titleAdd.setVisibility(View.GONE);
        titleUpdate.setVisibility(View.VISIBLE);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputNameValue = inputName.getEditText().getText().toString();
                String inputSizeString = inputSize.getEditText().getText().toString();
                int inputSizeValue = 0;

                if(!hasInputError(inputNameValue, inputSizeString)){
                    Character newCharacter = new Character(inputNameValue, inputSizeValue, character.getId());
                    mCharacterViewModel.update(newCharacter);
                    String snackbarTest = getResources().getString(R.string.confirm_common) + " " + inputNameValue + " "+ getResources().getString(R.string.confirm_update);
                    Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), snackbarTest, Snackbar.LENGTH_LONG);
                    snackbar.show();
                    dismiss();
                }
            }
        });
    }

    private void setDeleteFragment(Character character){
        deleteLayout.setVisibility(View.VISIBLE);
        updateAddLayout.setVisibility(View.GONE);
        updateAddButtons.setVisibility(View.GONE);
        titleAdd.setVisibility(View.GONE);
        titleDelete.setVisibility(View.VISIBLE);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCharacterViewModel.delete(character.getId());
                String snackbarTest = getResources().getString(R.string.confirm_common) + " " + character.getName() + " "+ getResources().getString(R.string.confirm_delete);
                Snackbar snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), snackbarTest, Snackbar.LENGTH_LONG);
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

    private boolean hasInputError(String name, String size){
        int inputSizeValue = 0;
        boolean isError = false;

        if(!name.isEmpty() && size != "0"){
            inputSizeValue = Integer.parseInt(size);
            if(inputSizeValue == 0){
                inputSizeEdit.setError(getResources().getString(R.string.input_error_character_size));
                isError = true;
            }
        }else{
            inputSizeEdit.setError(getResources().getString(R.string.input_error_character_size));
            isError = true;
        }

        if(name.isEmpty()){
            inputNameEdit.setError(getResources().getString(R.string.input_error_character_name));
            isError = true;
        }

        return isError;
    }
}

