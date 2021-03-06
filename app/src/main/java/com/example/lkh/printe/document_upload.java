package com.example.lkh.printe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tom_roush.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;
import java.net.URI;

public class document_upload extends AppCompatActivity implements View.OnClickListener {


    private String printer_location;
    private String shop_ID;
    public static String document_link = "";
    private String document_name;
    private FirebaseAuth firebaseAuth;
    private TextView path_name;
    private String full_name;
    private int flag1;
    private Uri uri;
    private String path;
    private String pages = "Contact shop";

    final static int PICK_PDF_CODE = 2342;


    TextView textViewStatus;
    ProgressBar progressBar;

    //the firebase objects for storage and database
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_upload);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        firebaseAuth = FirebaseAuth.getInstance();
        printer_location = getIntent().getExtras().getString("selected-item");
        shop_ID = getIntent().getExtras().getString("shop_ID");

        path_name = (TextView) findViewById(R.id.path);
        flag1 = 0;

        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(firebaseAuth.getCurrentUser().getUid().toString() + "/");


        textViewStatus = (TextView) findViewById(R.id.textViewStatus);
        progressBar = (ProgressBar) findViewById(R.id.progressbar);


        findViewById(R.id.browse).setOnClickListener(this);



    }



    private void getPDF() {
        //for greater than lolipop versions we need the permissions asked on runtime
        //so if the permission is not available user will go to the screen to allow storage permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivity(intent);
            return;
        }

        //creating an intent for file chooser
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //when the user choses the file
        if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //if a file is selected
            if (data.getData() != null) {
                //uploading the file
                {

                    uri= data.getData();
                    try {
                        document_name = getFileName(uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                    Log.e("name",getFileName(uri));
                    uploadFile(data.getData());
                    flag1 = 1;
                    textViewStatus.setText("Selected...");

                }
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }
    }




    private void uploadFile(Uri data) {
        progressBar.setVisibility(View.VISIBLE);

        String[] temp = document_name.split(".pdf");


        full_name = firebaseAuth.getCurrentUser().getUid().toString() + "/" + temp[0] + "_" + System.currentTimeMillis() + ".pdf";
        StorageReference sRef = mStorageReference.child(full_name);
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        progressBar.setVisibility(View.GONE);
                        path_name.setText(document_name);
                        textViewStatus.setText("File Uploaded Successfully");
                        document_link = taskSnapshot.getDownloadUrl().toString();

//                        Upload upload = new Upload("dummy", taskSnapshot.getDownloadUrl().toString());
//                        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @SuppressWarnings("VisibleForTests")
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading...");

                    }
                });


    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.browse:
                if (flag1 == 0)
                {
                    getPDF();
                }
                else
                {
                    textViewStatus.setText("Already uploaded....");
                }
                break;

        }

    }
    public void go_next (View v) throws InterruptedException {
        if (flag1 == 1)
        {

            Intent intent = new Intent(document_upload.this, other_options.class);
            intent.putExtra("printer_location",printer_location);
            intent.putExtra("document_name", document_name);
            intent.putExtra("document_lin", document_link);
            intent.putExtra("shop_ID",shop_ID);
            intent.putExtra("full_link",full_name);
            intent.putExtra("pages",pages);
            startActivity(intent);
//            Thread.sleep(8000);
            finish();
        }
        else
        {
            textViewStatus.setText("Please upload document first...");
        }

    }


    public String getFileName(Uri uri) throws IOException {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    Log.e("path1",result);

                }
            } finally {
                cursor.close();
            }
        }

        if (result == null) {
            result = uri.getPath();
            Log.e("path2",result);
            path = result;
            PDDocument myDocument = PDDocument.load(new File(path));
            int numPages = myDocument.getNumberOfPages();
            Log.e("No. of pages",Integer.toString(numPages));
            pages = Integer.toString(numPages);
            Log.e("No.","sayali");
 
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }






}
