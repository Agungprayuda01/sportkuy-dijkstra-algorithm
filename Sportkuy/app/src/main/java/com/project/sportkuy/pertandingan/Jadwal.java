package com.project.sportkuy.pertandingan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sportkuy.DaftarTeamFutsal.DatasetDaftarTeam;
import com.project.sportkuy.R;
import com.project.sportkuy.carilawanfutsal.CariLawan;
import com.project.sportkuy.carilawanfutsal.Detailcarilawan;
import com.project.sportkuy.carilawanfutsal.holder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Jadwal extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<Datasetjadwal> arrayList;
    private FirebaseRecyclerOptions<Datasetjadwal> options;
    private FirebaseRecyclerAdapter<Datasetjadwal, holder> adapter;
    private DatabaseReference databasereference,daa;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);
        recyclerView = findViewById(R.id.rc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        arrayList = new ArrayList<Datasetjadwal>();
        databasereference = FirebaseDatabase.getInstance().getReference().child("pertandingan");
        databasereference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<Datasetjadwal>().setQuery(databasereference, Datasetjadwal.class).build();
        adapter = new FirebaseRecyclerAdapter<Datasetjadwal, holder>(options) {
            @SuppressLint("SetTextI18n")
            @Override
            protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull Datasetjadwal model) {
                if(model.getEmail().equals(currentUser.getEmail()) || model.getEmailanda().equals(currentUser.getEmail())){
                    holder.itemView.setVisibility(View.VISIBLE);
                }else {
                    ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
                    params.width = 0;
                    holder.itemView.setLayoutParams(params);
                }
                holder.jumlahanggota.setText(model.namateam+" vs "+model.namateamanda);
                holder.namateam.setText(model.getTangal());
                Picasso.get().load(model.getGambar()).into(holder.img);
                holder.alamatteam.setText(model.getWaktu());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent a = new Intent(Jadwal.this,Detailjadwal.class);
                        a.putExtra("pid",model.pid);
                        a.putExtra("gambar",model.gambar);
                        a.putExtra("gambaranda",model.gambaranda);
                        a.putExtra("waktu",model.waktu);
                        a.putExtra("tanggal",model.tangal);
                        a.putExtra("lapangan",model.lapangan);
                        a.putExtra("namateam",model.namateam);
                        a.putExtra("namateamanda",model.namateamanda);
                        a.putExtra("nohp",model.nohp);
                        a.putExtra("nohpanda",model.nohpanda);
                        startActivity(a);
                    }
                });
            }

            @NonNull
            @Override
            public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new holder(LayoutInflater.from(Jadwal.this).inflate(R.layout.row_carilawan,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
}