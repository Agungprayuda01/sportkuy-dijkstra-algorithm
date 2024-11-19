package com.project.sportkuy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.project.sportkuy.turnamen.Cariturney;
import com.project.sportkuy.turnamen.Formturnamen;
import com.project.sportkuy.turnamen.Turney;

import java.text.Normalizer;

public class Fiturturnamen extends AppCompatActivity {
    Button cariturney,buatturney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiturturnamen);
        cariturney = findViewById(R.id.cariturney);
        buatturney = findViewById(R.id.buatturney);

        cariturney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tent = new Intent(Fiturturnamen.this, Cariturney.class);
                startActivity(tent);
            }
        });

        buatturney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Fiturturnamen.this, Turney.class);
                startActivity(intent);
            }
        });
    }
}