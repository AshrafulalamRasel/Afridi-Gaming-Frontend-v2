package com.itvillage.afridigaming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.services.BannerUploadService;
import com.itvillage.afridigaming.services.SignUpService;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BannerUpdateActivity extends AppCompatActivity {
    private Button update_slider_1_but,list_images;
    private TextView status_1;
    private EditText web_url_1;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_PICTURE_2 = 2;
    private String selectedImagePath;
    private static final int STORAGE_PERMISSION_CODE = 101;
    private final String TAG = "Banner Update Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_update);

        update_slider_1_but = findViewById(R.id.update_slider_1_but);
        list_images = findViewById(R.id.list_images);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        web_url_1 = findViewById(R.id.web_url_1);

        status_1 = findViewById(R.id.status_1);
        status_1.setText("Update Banner One Banner");


        update_slider_1_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, SELECT_PICTURE);

            }
        });
        list_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ImagesListActivity.class));

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK ) {
            Uri selectedImage = data.getData();
            String uriString = selectedImage.toString();
           File file = new File(uriString);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            BannerUploadService bannerUploadService = new BannerUploadService(getApplicationContext());
            //  Observable<SignUpResponse> observable = createSignUpService.createPatientWithSignUP("fdg5645yt","fdgdf@gmai.com","123456ghjmj");
            Observable<String> observable = bannerUploadService.uplaoad(new File(picturePath),web_url_1.getText().toString(),"Slider_One");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(signUpPatient -> {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }, throwable -> {
                        Log.e("err", throwable.getMessage());
                        throwable.printStackTrace();
                    }, () -> {

                    });
         Toast.makeText(getApplicationContext(),picturePath,Toast.LENGTH_LONG).show();

        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;

    }
    // Function to check and request permission.
    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(BannerUpdateActivity.this, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(BannerUpdateActivity.this, new String[] { permission }, requestCode);
        }
        else {
        }
    }

    // This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(BannerUpdateActivity.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BannerUpdateActivity.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}