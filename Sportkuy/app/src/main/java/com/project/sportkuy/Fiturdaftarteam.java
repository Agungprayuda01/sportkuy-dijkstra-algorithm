package com.project.sportkuy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sportkuy.DaftarTeamFutsal.Daftarteam;
import com.project.sportkuy.Daftarteambola.Daftarteambola;
import com.project.sportkuy.carilawanfutsal.Formlatih;

public class Fiturdaftarteam extends AppCompatActivity {
    public ImageView futsal,bola;
    public FirebaseAuth mAuth;
    public FirebaseUser currentUser;
    public String output;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiturdaftarteam);
        futsal = findViewById(R.id.fiturfutsal);
        bola = findViewById(R.id.fiturbola);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        output = currentUser.getEmail();
        output = output.replace(".", "at");
            final Dialog dialog = new Dialog(Fiturdaftarteam.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Rekomendasi Lapangan");
        TextView text =(TextView)dialog.findViewById(R.id.text_rekomendasi);

        futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Futsal").child(output).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            if (String.valueOf(task.getResult().getValue()).equals("null")){
                                Intent in = new Intent(Fiturdaftarteam.this, Daftarteam.class);
                                startActivity(in);
                            } else {
                                text.setText("Anda sudah mendaftarkan team anda");
                                dialog.show();
                            }
                        }
                    }
                });
            }
        });

        bola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase.child("Sepak bola").child(output).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        }
                        else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            if (String.valueOf(task.getResult().getValue()).equals("null")){
                                Intent in = new Intent(Fiturdaftarteam.this, Daftarteambola.class);
                                startActivity(in);
                            } else {
                                text.setText("Anda sudah mendaftarkan team anda");
                                dialog.show();
                            }
                        }
                    }
                });
            }
        });
    }
}