package com.project.sportkuy.Daftarteambola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
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
import com.project.sportkuy.DaftarTeamFutsal.Daftarteam;
import com.project.sportkuy.MainActivity;
import com.project.sportkuy.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class Daftarteambola extends AppCompatActivity {
    public Button posting,waktu,tanggal;
    public EditText namakamu,namateam,textwaktu,texttanggal,jumlahanggota,nohp,List;
    public ImageView imageView;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    public String productRandomKey, downloadImageUrl,snama,snamateam,swaktu,stanggal,sjumlahanggota
            ,semail,slokasi,saveCurrentDate,saveCurrentTime,snohp;
    public StorageReference ProductImagesRef;
    public DatabaseReference ProductsRef;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private ProgressDialog loadingBar;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarteambola);
        List = findViewById(R.id.listItem1);
        posting = findViewById(R.id.posting1);
        waktu = findViewById(R.id.btn_waktu1);
        tanggal = findViewById(R.id.btn_tanggal1);
        namakamu = findViewById(R.id.namakamu1);
        namateam = findViewById(R.id.namateamkamu1);
        texttanggal = findViewById(R.id.tanggal1);
        textwaktu = findViewById(R.id.waktu1);
        jumlahanggota = findViewById(R.id.jumlah1);
        nohp = findViewById(R.id.nohp1);
        myCalendar = Calendar.getInstance();
        imageView = findViewById(R.id.imageView1);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        loadingBar = new ProgressDialog(this);
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Gambar Team");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Sepak bola");

        semail = currentUser.getEmail();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        posting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProductData();
            }
        });
        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Daftarteambola.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        textwaktu.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        tanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Daftarteambola.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        texttanggal.setText(sdf.format(myCalendar.getTime()));
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
        snama = namakamu.getText().toString();
        snamateam = namateam.getText().toString();
        stanggal = texttanggal.getText().toString();
        swaktu = textwaktu.getText().toString();
        sjumlahanggota = jumlahanggota.getText().toString();
        snohp = nohp.getText().toString();
        slokasi = List.getText().toString();



        if (ImageUri == null)
        {
            Toast.makeText(this, "Gambar atau logo team tidak ada...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(snama))
        {
            Toast.makeText(this, "Tolong Tulis nama anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(snamateam))
        {
            Toast.makeText(this, "Tolong tulis nama team anda...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(stanggal))
        {
            Toast.makeText(this, "tanggal blm di isi...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(swaktu))
        {
            Toast.makeText(this, "waktu belum di isi...", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(sjumlahanggota))
        {
            Toast.makeText(this, "Tolong tulis jumlah anggota team anda...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(snohp))
        {
            Toast.makeText(this, "Tolong tulis nomor handphone yang bisa di hubungi tim lawan...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInformation();
        }
    }
    private void StoreProductInformation()
    {
        loadingBar.setTitle("Posting Taem");
        loadingBar.setMessage("Dear pengguna, Tolong Tunggu Team Sedang di Posting.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());
        String output = currentUser.getEmail();

        output = output.replace(".", "at");

        productRandomKey = output;

        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(Daftarteambola.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Daftarteambola.this, "Team uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(Daftarteambola.this, "gambar team berhasil diupload...", Toast.LENGTH_SHORT).show();

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
        productMap.put("namakamu", snama);
        productMap.put("namateam", snamateam);
        productMap.put("waktu", swaktu);
        productMap.put("tangal", stanggal);
        productMap.put("jumlahanggota", sjumlahanggota);
        productMap.put("lokasi", slokasi);
        productMap.put("gambar", downloadImageUrl);
        productMap.put("email", semail);
        productMap.put("nohp",snohp);

        ProductsRef.child(productRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(Daftarteambola.this, MainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(Daftarteambola.this, "Team berhasil diupload..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(Daftarteambola.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}