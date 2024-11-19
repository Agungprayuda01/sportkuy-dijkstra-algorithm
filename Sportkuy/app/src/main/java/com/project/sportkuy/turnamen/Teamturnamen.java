package com.project.sportkuy.turnamen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.sportkuy.DaftarTeamFutsal.DatasetDaftarTeam;
import com.project.sportkuy.R;
import com.project.sportkuy.carilawanfutsal.holder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Teamturnamen extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<User> arrayList;
    private FirebaseRecyclerOptions<User> options;
    private FirebaseRecyclerAdapter<User, holder> adapter;
    private DatabaseReference databasereference,daa;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    public String type;

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
        setContentView(R.layout.activity_cari_lawan);
        recyclerView = findViewById(R.id.recyclerview);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        arrayList = new ArrayList<User>();
        String pid = getIntent().getStringExtra("pid");
        databasereference = FirebaseDatabase.getInstance().getReference().child("turnamen").child(pid).child("daftarteam");
        databasereference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<User>().setQuery(databasereference, User.class).build();
        adapter = new FirebaseRecyclerAdapter<User, holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull User model) {
                holder.jumlahanggota.setText(model.nohp);
                holder.namateam.setText(model.getNamateam());
                Picasso.get().load(R.drawable.atas).into(holder.img);
                holder.alamatteam.setText(model.getNama());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(Teamturnamen.this);
                        dialog.setContentView(R.layout.dialog_batal);
                        dialog.setTitle("Pilih Aksi");
                        Button team = (Button) dialog.findViewById(R.id.batal_turnamen);
                        dialog.show();
                        team.setEnabled(true);
                        if(model.email.equals(currentUser.getEmail())){
                            team.setEnabled(true);
                        } else {
                            team.setEnabled(false);
                        }
                        team.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                daa = FirebaseDatabase.getInstance().getReference()
                                        .child("turnamen").child(pid).child("daftarteam").child(model.getPid());
                                daa.removeValue();
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new holder(LayoutInflater.from(Teamturnamen.this).inflate(R.layout.row_carilawan,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
}