package com.project.sportkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.project.sportkuy.DaftarTeamFutsal.Daftarteam;
import com.project.sportkuy.carilawanfutsal.CariLawan;
import com.project.sportkuy.pertandingan.Jadwal;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView btn_daftar = findViewById(R.id.daftarteam);
        ImageView btn_carilawan = findViewById(R.id.carilawan);
        ImageView btn_jadwal = findViewById(R.id.jadwal);
        ImageView btn_turnamen = findViewById(R.id.turnamenmain);
        btn_daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fiturdaftarteam.class);
                startActivity(intent);
            }
        });
        btn_carilawan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Fiturcarilawan.class);
                startActivity(intent);
            }
        });
        btn_jadwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Jadwal.class);
                startActivity(intent);
            }
        });
        btn_turnamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tent = new Intent(MainActivity.this,Fiturturnamen.class);
                startActivity(tent);
            }
        });
    }
}