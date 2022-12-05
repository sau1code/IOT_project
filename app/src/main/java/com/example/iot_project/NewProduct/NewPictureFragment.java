package com.example.iot_project.NewProduct;

import static android.app.Activity.RESULT_OK;


import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iot_project.DBHelper;
import com.example.iot_project.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPictureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPictureFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    private ImageView imageView_newPicture;
    private NewProductActivity newProductActivity;
    public static final int PICK_PHOTO = 102;
    private Uri imageUri;

    public NewPictureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment newPictureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPictureFragment newInstance(String param1, String param2) {
        NewPictureFragment fragment = new NewPictureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_new_picture, container, false);
        newProductActivity = (NewProductActivity) getActivity();
        imageView_newPicture = (ImageView) v.findViewById(R.id.ImageView_newPicture);


        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    imageView_newPicture.setImageBitmap(bitmap);

                    DBHelper dbHelper = new DBHelper(newProductActivity);
                    SQLiteDatabase picdataBase = dbHelper.getWritableDatabase();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] bytes = baos.toByteArray();

                    ContentValues cv = new ContentValues();
                    cv.put("goodsPicture",bytes);
                    long id = picdataBase.update("goodsPic",cv,"fragPic ='"+mParam2+"';",null);
                    picdataBase.close();
                    dbHelper.close();

                }
            }
        });
        Intent intent = new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if (intent.resolveActivity(newProductActivity.getPackageManager()) != null) {
            activityResultLauncher.launch(intent);
        } else {
            Toast.makeText(newProductActivity, "no app support", Toast.LENGTH_SHORT).show();
        }
        return v;
    }


}