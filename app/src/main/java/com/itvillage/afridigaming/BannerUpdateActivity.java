package com.itvillage.afridigaming;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itvillage.afridigaming.services.BannerUploadService;
import com.itvillage.afridigaming.services.SignUpService;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BannerUpdateActivity extends AppCompatActivity {
    private Button update_slider_1_but,update_slider_2_but;
    private TextView status_1,status_2;
    private EditText web_url_1,web_url_2;
    private static final int SELECT_PICTURE = 1;
    private static final int SELECT_PICTURE_2 = 2;
    private String selectedImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_update);

        update_slider_1_but = findViewById(R.id.update_slider_1_but);
        update_slider_2_but = findViewById(R.id.update_slider_2_but);

        web_url_1 = findViewById(R.id.web_url_1);
        web_url_2 = findViewById(R.id.web_url_1);

        status_1 = findViewById(R.id.status_1);
        status_2 = findViewById(R.id.status_2);
        status_1.setText("Update Banner One Banner");
        status_2.setText("Update Banner Two Banner");

        update_slider_1_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, SELECT_PICTURE);

            }
        });

        update_slider_2_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, SELECT_PICTURE_2);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK ) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            BannerUploadService bannerUploadService = new BannerUploadService(getApplicationContext());
            //  Observable<SignUpResponse> observable = createSignUpService.createPatientWithSignUP("fdg5645yt","fdgdf@gmai.com","123456ghjmj");
            Observable<String> observable = bannerUploadService.uplaoad(picturePath,web_url_1.getText().toString(),"Slider_One");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(signUpPatient -> {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();
                        Log.e("err", throwable.getMessage());
                        throwable.printStackTrace();
                    }, () -> {

                    });
         Toast.makeText(getApplicationContext(),picturePath,Toast.LENGTH_LONG).show();

        }
        else if (requestCode == SELECT_PICTURE_2 && resultCode == RESULT_OK ) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            BannerUploadService bannerUploadService = new BannerUploadService(getApplicationContext());
            Observable<String> observable = bannerUploadService.uplaoad(picturePath,web_url_2.getText().toString(),"Slider_Two");
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(signUpPatient -> {

                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

                    }, throwable -> {
                        Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();
                        Log.e("err", throwable.getMessage());
                        throwable.printStackTrace();
                    }, () -> {

                    });
         Toast.makeText(getApplicationContext(),picturePath,Toast.LENGTH_LONG).show();

        }


    }

}