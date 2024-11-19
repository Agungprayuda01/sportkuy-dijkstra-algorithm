package com.project.sportkuy.turnamen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sportkuy.DaftarTeamFutsal.Daftarteam;
import com.project.sportkuy.Fiturdaftarteam;
import com.project.sportkuy.Fiturturnamen;
import com.project.sportkuy.R;

public class Formturnamen extends AppCompatActivity {
    EditText nama,namateam,nohp;
    Button daftar;
    String pid,snama,snamateam,snohp;
    private DatabaseReference mDatabase;
    public FirebaseAuth mAuthh;
    public FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formturnamen);

        nama = findViewById(R.id.formturneynama);
        namateam = findViewById(R.id.formturneynamateam);
        nohp = findViewById(R.id.formturneynohp);
        daftar = findViewById(R.id.formturneydaftar);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        pid = getIntent().getStringExtra("pid");
        mAuthh = FirebaseAuth.getInstance();
        currentUser = mAuthh.getCurrentUser();


        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snama = nama.getText().toString();
                snamateam = namateam.getText().toString();
                snohp = nohp.getText().toString();
                String output = currentUser.getEmail();

                output = output.replace(".", "at");
                writeNewUser(output,snama,snamateam ,snohp,currentUser.getEmail());
            }
        });


    }
    public void writeNewUser(String pids,String nama, String namateam,String nohp,String email) {
        final Dialog dialog = new Dialog(Formturnamen.this);
        dialog.setContentView(R.layout.dialog);
        dialog.setTitle("Rekomendasi Lapangan");
        TextView text =(TextView)dialog.findViewById(R.id.text_rekomendasi);
        User user = new User(pids,nama, namateam,nohp,email);
        mDatabase.child("turnamen").child(pid).child("daftarteam").child(pids).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    if (String.valueOf(task.getResult().getValue()).equals("null")){
                        mDatabase.child("turnamen").child(pid).child("daftarteam").child(pids).setValue(user);
                        Intent a = new Intent(Formturnamen.this, Fiturturnamen.class);
                        startActivity(a);
                    } else {
                        text.setText("Anda sudah mendaftarkan team anda");
                        dialog.show();
                    }
                }
            }
        });
    }

}