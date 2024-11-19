package com.project.sportkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.project.sportkuy.carilawanfutsal.CariLawan;

public class Fiturcarilawan extends AppCompatActivity {
    public ImageView futsal,bola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiturcarilawan);
        futsal = findViewById(R.id.carilawanfutsal);
        bola = findViewById(R.id.carilawanbola);

        futsal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Fiturcarilawan.this, CariLawan.class);
                in.putExtra("type","Futsal");
                startActivity(in);
            }
        });

        bola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Fiturcarilawan.this, CariLawan.class);
                in.putExtra("type","Sepak bola");
                startActivity(in);
            }
        });
    }
}