package com.project.sportkuy.editbola;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sportkuy.Daftarteambola.Daftarteambola;
import com.project.sportkuy.Fiturcarilawan;
import com.project.sportkuy.R;
import com.project.sportkuy.editfutsal.EditFutsal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditBola extends AppCompatActivity {
    public String snamatimlawan,semaillawan,sgambarlawan,sjumlahlawan,slokasilawan,
            snamalawan,stanggaltanding,swaktutanding,snohp,type,pid,semail;
    public Button posting,waktu,tanggal;
    public EditText namakamu,namateam,textwaktu,texttanggal,jumlahanggota,nohp,List;
    public ImageView imageView;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    FirebaseDatabase database;
    DatabaseReference myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftarteambola);

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
        type = getIntent().getStringExtra("type");

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

        namakamu.setText(snamalawan);
        texttanggal.setText(stanggaltanding);
        namateam.setText(snamatimlawan);
        textwaktu.setText(swaktutanding);
        jumlahanggota.setText(sjumlahlawan);
        List.setText(slokasilawan);
        nohp.setText(snohp);

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
                String pid2 = getIntent().getStringExtra("pid");
                String type2 = getIntent().getStringExtra("type");
                database = FirebaseDatabase.getInstance();
                myref = database.getReference();
                myref.child(type2).child(pid2).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().child("jumlahanggota").setValue(jumlahanggota.getText().toString());
                        snapshot.getRef().child("lokasi").setValue(List.getText().toString());
                        snapshot.getRef().child("nama").setValue(namakamu.getText().toString());
                        snapshot.getRef().child("namateam").setValue(namateam.getText().toString());
                        snapshot.getRef().child("nohp").setValue(nohp.getText().toString());
                        snapshot.getRef().child("tangal").setValue(texttanggal.getText().toString());
                        snapshot.getRef().child("waktu").setValue(textwaktu.getText().toString());
                        toast();
                        Intent sudah = new Intent(EditBola.this, Fiturcarilawan.class);
                        startActivity(sudah);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        toast2();
                    }
                });
            }
        });
        waktu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(EditBola.this, new TimePickerDialog.OnTimeSetListener() {

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
                new DatePickerDialog(EditBola.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        texttanggal.setText(sdf.format(myCalendar.getTime()));
    }
    private void toast(){
        Toast.makeText(this, "Data sudah di update", Toast.LENGTH_SHORT).show();
    }
    private void toast2(){
        Toast.makeText(this, "Data Gagal di update", Toast.LENGTH_SHORT).show();
    }
}