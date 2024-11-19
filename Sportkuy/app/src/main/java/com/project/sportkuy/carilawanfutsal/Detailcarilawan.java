package com.project.sportkuy.carilawanfutsal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.sportkuy.R;
import com.project.sportkuy.carilawanbola.Formlatihbola;
import com.squareup.picasso.Picasso;

public class Detailcarilawan extends AppCompatActivity {
    public TextView namateamlawan,lokasi,waktu,nohp,tanggal;
    public ImageView img;
    public Button ajaklatihtanding;
    public String snamatimlawan,semaillawan,sgambarlawan,sjumlahlawan,slokasilawan,
    snamalawan,stanggaltanding,swaktutanding,snohp,type,pid,semail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailcarilawan);
        snamatimlawan = getIntent().getStringExtra("namatimlawan");
        semaillawan = getIntent().getStringExtra("emaillawan");
        sgambarlawan = getIntent().getStringExtra("gambarlawan");
        sjumlahlawan = getIntent().getStringExtra("jumlahlawan");
        slokasilawan = getIntent().getStringExtra("lokasilawan");
        snamalawan = getIntent().getStringExtra("namalawan");
        stanggaltanding = getIntent().getStringExtra("tanggaltanding");
        swaktutanding = getIntent().getStringExtra("waktutanding");
        snohp = getIntent().getStringExtra("nohp");
        pid = getIntent().getStringExtra("pid");
        semail = getIntent().getStringExtra("email");
//        type = getIntent().getStringExtra("type");

        namateamlawan = findViewById(R.id.namateamlawan);
        lokasi = findViewById(R.id.lokasiteamlawan);
        waktu = findViewById(R.id.waktutanding);
        nohp = findViewById(R.id.nohplawan);
        tanggal = findViewById(R.id.tanggaltanding);
        img = findViewById(R.id.gambarlawan);

        namateamlawan.setText(snamatimlawan);
        lokasi.setText(slokasilawan);
        waktu.setText(swaktutanding);
        nohp.setText(snohp);
        tanggal.setText(stanggaltanding);

        Picasso.get().load(sgambarlawan).into(img);

        ajaklatihtanding = findViewById(R.id.ajaklatihtanding);
        if (semail.equals(semaillawan)){
            ajaklatihtanding.setEnabled(false);
        } else {
            ajaklatihtanding.setEnabled(true);
        }
        ajaklatihtanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in;
                type = getIntent().getStringExtra("type");
                if (type.equals("Futsal")){
                    in = new Intent(Detailcarilawan.this, Formlatih.class);
                } else {
                    in = new Intent(Detailcarilawan.this, Formlatihbola.class);
                }
                in.putExtra("namatimlawan",snamatimlawan);
                in.putExtra("emaillawan",semaillawan);
                in.putExtra("gambarlawan",sgambarlawan);
                in.putExtra("jumlahlawan",sjumlahlawan);
                in.putExtra("lokasilawan",slokasilawan);
                in.putExtra("namalawan",snamalawan);
                in.putExtra("tanggaltanding",stanggaltanding);
                in.putExtra("waktutanding",swaktutanding);
                in.putExtra("nohp",snohp);
                in.putExtra("pid",pid);
                startActivity(in);
            }
        });
    }
}