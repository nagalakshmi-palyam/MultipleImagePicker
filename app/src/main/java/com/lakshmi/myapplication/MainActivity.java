package com.lakshmi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jaiselrahman.filepicker.activity.FilePickerActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnslect;
    private TextView path;
    String pdfPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnslect=findViewById(R.id.selectpdf);
        path=findViewById(R.id.tvtext);
        btnslect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllowRunTimePermission();
                selectPdf();

            }
        });
    }


    private void selectPdf()
  {
//        Intent intent = new Intent();
//        intent.setType("*/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Pdf"),1);

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

           Uri uri=data.getData();
//           final String id= DocumentsContract.getDocumentId(uri);
//            final Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(id));
//            String[] projection={MediaStore.Images.Media.DATA};
//            Cursor cursor=getContentResolver().query(contentUri,projection,null,null,null);
//            int column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            pdfPath= cursor.getString(column_index);
           pdfPath=FilePath.getPath(this,uri);
            Log.d("pdf",pdfPath);
            Toast.makeText(this,pdfPath, Toast.LENGTH_LONG).show();
            path.setText(pdfPath);

        }
    }


    public void AllowRunTimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
        {

            Toast.makeText(this,"READ_EXTERNAL_STORAGE permission Access Dialog", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(this,new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] Result) {

        switch (RC) {

            case 1:

                if (Result.length > 0 && Result[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("onRequest","onrequest");
                    Toast.makeText(this,"Permission Granted", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(this,"Permission Canceled", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }
}