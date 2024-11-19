package com.project.sportkuy.carilawanfutsal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Formlatih extends AppCompatActivity {
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    public StorageReference ProductImagesRef;
    public DatabaseReference ProductsRef,daa;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    private ProgressDialog loadingBar;
    public EditText namatimanda,jumlahanggotaanda,nohpanda,lapangan;
    public ImageView imageView;
    public Button rekomendasi,post;
    public Spinner lokasi;
    public String snamatimlawan,semaillawan,sgambarlawan,sjumlahlawan,slokasilawan,
            snamalawan,stanggaltanding,swaktutanding,snohp,slokasianda,slapangan,
            snamatimanda,sjumlahanggotaanda,snohpanda,saveCurrentDate,saveCurrentTime,semailanda
            ,productRandomKey,downloadImageUrl,spid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formlatih);
        lokasi = findViewById(R.id.spinnerlokasi);
        lapangan = findViewById(R.id.spinnerlapangan);
        namatimanda = findViewById(R.id.namatimanda);
        jumlahanggotaanda = findViewById(R.id.jumlahanggotaanda);
        nohpanda = findViewById(R.id.nohpanda);
        imageView = findViewById(R.id.imagelatih);
        rekomendasi = findViewById(R.id.rekomendasi);
        post = findViewById(R.id.post);

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
        slapangan = lapangan.getText().toString();

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
//                        .child("Futsal").child(spid);
//                daa.removeValue();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
        rekomendasi.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String a = lokasi.getSelectedItem().toString();
                final Dialog dialog = new Dialog(Formlatih.this);
                dialog.setContentView(R.layout.dialog);
                dialog.setTitle("Rekomendasi Lapangan");
                TextView text =(TextView)dialog.findViewById(R.id.text_rekomendasi);


                Vertex LampungFutsal = new Vertex("Lampung Futsal");
                Vertex FamilyFutsal = new Vertex("Family Futsal");
                Vertex TransFutsal = new Vertex("Trans Futsal");
                Vertex FutsalJempol = new Vertex("Futsal Jempol");
                Vertex GhinanFutsal = new Vertex("Ghinan Futsal");
                Vertex SakubaFutsal = new Vertex("Sakuba Futsal");
                Vertex SrikandiFutsal = new Vertex("Srikandi Futsal");
                Vertex AlshaFutsal = new Vertex("Alsha Futsal");
                Vertex TwinFutsal = new Vertex("Twin Futsal");
                Vertex HarmoniFutsal = new Vertex("Harmoni Futsal");
                Vertex LightFutsal = new Vertex("Light Futsal");
                Vertex RayaFutsal = new Vertex("Raya Futsal");
                Vertex StarFutsal = new Vertex("Star Futsal");

                LampungFutsal.addNeighbour(new Edge(2020,LampungFutsal,GhinanFutsal));
                GhinanFutsal.addNeighbour(new Edge(2020,GhinanFutsal,LampungFutsal));

                LampungFutsal.addNeighbour(new Edge(2060,LampungFutsal,SakubaFutsal));
                SakubaFutsal.addNeighbour(new Edge(2060,SakubaFutsal,LampungFutsal));

                SakubaFutsal.addNeighbour(new Edge(1120,SakubaFutsal,GhinanFutsal));
                GhinanFutsal.addNeighbour(new Edge(1120,GhinanFutsal,SakubaFutsal));

                SakubaFutsal.addNeighbour(new Edge(969,SakubaFutsal,FamilyFutsal));
                FamilyFutsal.addNeighbour(new Edge(969,FamilyFutsal,SakubaFutsal));

                LampungFutsal.addNeighbour(new Edge(2000,LampungFutsal,FamilyFutsal));
                FamilyFutsal.addNeighbour(new Edge(2000,FamilyFutsal,LampungFutsal));

                FamilyFutsal.addNeighbour(new Edge(348,FamilyFutsal,TransFutsal));
                TransFutsal.addNeighbour(new Edge(348,TransFutsal,FamilyFutsal));

                FamilyFutsal.addNeighbour(new Edge(1760,FamilyFutsal,FutsalJempol));
                FutsalJempol.addNeighbour(new Edge(1760,FutsalJempol,FamilyFutsal));

                TransFutsal.addNeighbour(new Edge(1740,TransFutsal,FutsalJempol));
                FutsalJempol.addNeighbour(new Edge(1740,FutsalJempol,TransFutsal));

                LampungFutsal.addNeighbour(new Edge(2000,LampungFutsal,SrikandiFutsal));
                SrikandiFutsal.addNeighbour(new Edge(2000,SrikandiFutsal,LampungFutsal));

                FamilyFutsal.addNeighbour(new Edge(1190,FamilyFutsal,SrikandiFutsal));
                SrikandiFutsal.addNeighbour(new Edge(1190,SrikandiFutsal,FamilyFutsal));

                SrikandiFutsal.addNeighbour(new Edge(1190,SrikandiFutsal,SakubaFutsal));
                SakubaFutsal.addNeighbour(new Edge(1190,SakubaFutsal,SrikandiFutsal));

                SrikandiFutsal.addNeighbour(new Edge(1570,SrikandiFutsal,AlshaFutsal));
                AlshaFutsal.addNeighbour(new Edge(1570,AlshaFutsal,SrikandiFutsal));

                AlshaFutsal.addNeighbour(new Edge(2510,AlshaFutsal,LampungFutsal));
                LampungFutsal.addNeighbour(new Edge(2510,LampungFutsal,AlshaFutsal));

                LampungFutsal.addNeighbour(new Edge(3770,LampungFutsal,TwinFutsal));
                TwinFutsal.addNeighbour(new Edge(3770,TwinFutsal,LampungFutsal));

                AlshaFutsal.addNeighbour(new Edge(2230,AlshaFutsal,TwinFutsal));
                TwinFutsal.addNeighbour(new Edge(2230,TwinFutsal,AlshaFutsal));

                TwinFutsal.addNeighbour(new Edge(176,TwinFutsal,HarmoniFutsal));
                HarmoniFutsal.addNeighbour(new Edge(176,HarmoniFutsal,TwinFutsal));

                LightFutsal.addNeighbour(new Edge(3360,LightFutsal,LampungFutsal));
                LampungFutsal.addNeighbour(new Edge(3360,LampungFutsal,LightFutsal));

                LightFutsal.addNeighbour(new Edge(2280,LightFutsal,RayaFutsal));
                RayaFutsal.addNeighbour(new Edge(2280,RayaFutsal,LightFutsal));

                LightFutsal.addNeighbour(new Edge(2670,LightFutsal,StarFutsal));
                StarFutsal.addNeighbour(new Edge(2670,StarFutsal,LightFutsal));

                RayaFutsal.addNeighbour(new Edge(1540,RayaFutsal,StarFutsal));
                StarFutsal.addNeighbour(new Edge(1540,StarFutsal,RayaFutsal));

                FutsalJempol.addNeighbour(new Edge(3510,FutsalJempol,RayaFutsal));
                RayaFutsal.addNeighbour(new Edge(3510,RayaFutsal,FutsalJempol));

                FutsalJempol.addNeighbour(new Edge(3970,FutsalJempol,StarFutsal));
                StarFutsal.addNeighbour(new Edge(3970,StarFutsal,FutsalJempol));


                DijkstraShortestPath shortestPath = new DijkstraShortestPath();
                switch (a) {
                    case "Lampung Futsal":
                        shortestPath.computeShortestPaths(LampungFutsal);
                        break;
                    case "Family Futsal":
                        shortestPath.computeShortestPaths(FamilyFutsal);
                        break;
                    case "Trans Futsal":
                        shortestPath.computeShortestPaths(TransFutsal);
                        break;
                    case "Futsal Jempol":
                        shortestPath.computeShortestPaths(FutsalJempol);
                        break;
                    case "Ghinan Futsal":
                        shortestPath.computeShortestPaths(GhinanFutsal);
                        break;
                    case "Sakuba Futsal":
                        shortestPath.computeShortestPaths(SakubaFutsal);
                        break;
                    case "Srikandi Futsal":
                        shortestPath.computeShortestPaths(SrikandiFutsal);
                        break;
                    case "Alsha Futsal":
                        shortestPath.computeShortestPaths(AlshaFutsal);
                        break;
                    case "Twin Futsal":
                        shortestPath.computeShortestPaths(TwinFutsal);
                        break;
                    case "Harmoni Futsal":
                        shortestPath.computeShortestPaths(HarmoniFutsal);
                        break;
                    case "Light Futsal":
                        shortestPath.computeShortestPaths(LightFutsal);
                        break;
                    case "Raya Futsal":
                        shortestPath.computeShortestPaths(RayaFutsal);
                        break;
                    case "Star Futsal":
                        shortestPath.computeShortestPaths(StarFutsal);
                        break;
                }
                switch (slokasilawan) {
                    case "Lampung Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(LampungFutsal));
                        dialog.show();
                        break;
                    case "Family Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(FamilyFutsal));
                        dialog.show();
                        break;
                    case "Trans Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(TransFutsal));
                        dialog.show();
                        break;
                    case "Futsal Jempol":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(FutsalJempol));
                        dialog.show();
                        break;
                    case "Ghinan Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(GhinanFutsal));
                        dialog.show();
                        break;
                    case "Sakuba Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(SakubaFutsal));
                        dialog.show();
                        break;
                    case "Srikandi Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(SrikandiFutsal));
                        dialog.show();
                        break;
                    case "Alsha Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(AlshaFutsal));
                        dialog.show();
                        break;
                    case "Twin Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(TwinFutsal));
                        dialog.show();
                        break;
                    case "Harmoni Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(HarmoniFutsal));
                        dialog.show();
                        break;
                    case "Light Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(LightFutsal));
                        dialog.show();
                        break;
                    case "Raya Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(RayaFutsal));
                        dialog.show();
                        break;
                    case "Star Futsal":
                        text.setText("Lapangan yang kami rekomendasikan adalah:"+shortestPath.getShortestPathTo(StarFutsal));
                        dialog.show();
                        break;
                }
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

        String a = Objects.requireNonNull(currentUser.getEmail()).replace("@","");
        productRandomKey = saveCurrentDate + saveCurrentTime + "Sportkuy" + currentUser.getDisplayName();


        final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(Formlatih.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(Formlatih.this, "Team uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                            Toast.makeText(Formlatih.this, "gambar team berhasil diupload...", Toast.LENGTH_SHORT).show();

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
        productMap.put("lapangan", lapangan.getText().toString());
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
                            Intent intent = new Intent(Formlatih.this, MainActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(Formlatih.this, "Team berhasil diupload..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(Formlatih.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}