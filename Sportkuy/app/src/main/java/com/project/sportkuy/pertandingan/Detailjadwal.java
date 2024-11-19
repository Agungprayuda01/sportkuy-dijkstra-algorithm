package com.project.sportkuy.pertandingan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sportkuy.MainActivity;
import com.project.sportkuy.R;
import com.squareup.picasso.Picasso;

public class Detailjadwal extends AppCompatActivity {
    TextView t1,t2,t3,t4,t5,t6,t7;
    String pid,gambar,gambaranda,waktu,tanggal,lapangan,namateam,namateamanda,nohpanda,nohp;
    ImageView g1,g2;
    Button batal;
    public DatabaseReference daa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailjadwal);

        t1 = findViewById(R.id.tandingnt1);
        t2 = findViewById(R.id.tandingnt2);
        t3 = findViewById(R.id.tandinglokasi);
        t4 = findViewById(R.id.tandingtgl);
        t5 = findViewById(R.id.tandingwkt);
        t6 = findViewById(R.id.tandinghp1);
        t7 = findViewById(R.id.tandinghp2);


        g1 = findViewById(R.id.tandingg1);
        g2 = findViewById(R.id.tandingg2);

        batal = findViewById(R.id.tandingbatal);

        pid = getIntent().getStringExtra("pid");
        gambar = getIntent().getStringExtra("gambar");
        gambaranda = getIntent().getStringExtra("gambaranda");
        waktu = getIntent().getStringExtra("waktu");
        tanggal = getIntent().getStringExtra("tanggal");
        lapangan = getIntent().getStringExtra("lapangan");
        namateam = getIntent().getStringExtra("namateam");
        namateamanda = getIntent().getStringExtra("namateamanda");
        nohp = getIntent().getStringExtra("nohp");
        nohpanda = getIntent().getStringExtra("nohpanda");

        t1.setText(namateam);
        t2.setText(namateamanda);
        t3.setText(lapangan);
        t4.setText(tanggal);
        t5.setText(waktu);
        t6.setText(nohp);
        t7.setText(nohpanda);

        Picasso.get().load(gambar).into(g1);
        Picasso.get().load(gambaranda).into(g2);

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daa = FirebaseDatabase.getInstance().getReference()
                        .child("pertandingan").child(pid);
                daa.removeValue();

                Intent ten = new Intent(Detailjadwal.this, MainActivity.class);
                startActivity(ten);
            }
        });
    }
}