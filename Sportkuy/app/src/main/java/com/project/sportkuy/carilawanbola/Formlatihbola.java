package com.project.sportkuy.carilawanbola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.project.sportkuy.MainActivity;
import com.project.sportkuy.R;
import com.project.sportkuy.carilawanfutsal.Formlatih;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Formlatihbola extends AppCompatActivity {
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    public StorageReference ProductImagesRef;
    public DatabaseReference ProductsRef,daa;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private ProgressDialog loadingBar;
    public EditText namatimanda,jumlahanggotaanda,nohpanda;
    public ImageView imageView;
    public Button rekomendasi,post;
    public Spinner lokasi;
    public String snamatimlawan,semaillawan,sgambarlawan,sjumlahlawan,slokasilawan,
            snamalawan,stanggaltanding,swaktutanding,snohp,slokasianda,
            snamatimanda,sjumlahanggotaanda,snohpanda,saveCurrentDate,saveCurrentTime,semailanda
            ,productRandomKey,downloadImageUrl,spid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formlatihbola);
        lokasi = findViewById(R.id.spinnerlokasi1);
        namatimanda = findViewById(R.id.namatimanda1);
        jumlahanggotaanda = findViewById(R.id.jumlahanggotaanda1);
        nohpanda = findViewById(R.id.nohpanda1);
        imageView = findViewById(R.id.imagelatih1);
        post = findViewById(R.id.post1);

        snamatimlawan = getIntent().getStringExtra("namatimlawan");
        semaillawan = getIntent().getStringExtra("emaillawan");
        sgambarlawan = getIntent().getStringExtra("gambarlawan");
        sjumlahlawan = getIntent().getStringExtra("jumlahlawan");
        slokasilawan = getIntent().getStringExtra("lokasilawan");
        snamalawan = getIntent().getStringExtra("namalawan");
        stanggaltanding = getIntent().getStringExtra("tanggaltanding");
        swaktutanding = getIntent().getStringExtra("waktutanding");
        snohp = getIntent().getStringExtra("nohp");
        spid = getIntent().getStringExtra("pid");
        slokasianda = lokasi.getSelectedItem().toString();

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        semailanda = currentUser.getEmail();
        loadingBar = new ProgressDialog(this);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Gambar tanding");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("pertandingan");

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
//                daa = FirebaseDatabase.getInstance().getReference()
//                        .child("Sepak bola").child(spid);
//                daa.removeValue();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
    }
    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            imageView.setImageURI(ImageUri);
        }
    }
    private void ValidateProductData()
    {
        snamatimanda = namatimanda.getText().toString();
        sjumlahanggotaanda = jumlahanggotaanda.getText().toString();
        snohpanda = nohpanda.getText().toString();



        if (ImageUri == null)
        {
            Toast.makeText(this, "Gambar atau logo team tidak ada...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(snamatimanda))
        {
            Toast.makeText(this, "Tolong Tulis nama tim anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(snohpanda))
        {
            Toast.makeText(this, "Tolong Tulis nama tim anda...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Posting Taem");
        loadingBar.setMessage("Dear pengguna, Tolong Tunggu");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productRandomKey = saveCurrentDate + saveCurrentTime + "Sportkuy" + currentUser.getDisplayName();


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(Formlatihbola.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Formlatihbola.this, "Team uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(Formlatihbola.this, "gambar team berhasil diupload...", Toast.LENGTH_SHORT).show();

                            SaveProductInfoToDatabase();
                        }
                    }
                });
            }
        });
    }
    private void SaveProductInfoToDatabase()
    {
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid", productRandomKey);
        productMap.put("nama", snamalawan);
        productMap.put("namateam", snamatimlawan);
        productMap.put("waktu", swaktutanding);
        productMap.put("tangal", stanggaltanding);
        productMap.put("jumlahanggota", sjumlahlawan);
        productMap.put("lokasi", slokasilawan);
        productMap.put("gambar", sgambarlawan);
        productMap.put("gambaranda", downloadImageUrl);
        productMap.put("namateamanda", snamatimanda);
        productMap.put("lokasianda", slokasianda);
        productMap.put("lapangan", slokasilawan);
        productMap.put("jumlahanggotaanda", sjumlahanggotaanda);
        productMap.put("nohpanda", snohpanda);
        productMap.put("email", semaillawan);
        productMap.put("emailanda", semailanda);
        productMap.put("nohp",snohp);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(Formlatihbola.this, MainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(Formlatihbola.this, "Team berhasil diupload..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(Formlatihbola.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}