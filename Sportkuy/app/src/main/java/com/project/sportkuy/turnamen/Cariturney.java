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
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.sportkuy.DaftarTeamFutsal.DatasetDaftarTeam;
import com.project.sportkuy.Fiturcarilawan;
import com.project.sportkuy.Fiturturnamen;
import com.project.sportkuy.R;
import com.project.sportkuy.carilawanfutsal.CariLawan;
import com.project.sportkuy.carilawanfutsal.Detailcarilawan;
import com.project.sportkuy.carilawanfutsal.Formlatih;
import com.project.sportkuy.carilawanfutsal.holder;
import com.project.sportkuy.editfutsal.EditFutsal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Cariturney extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DatasetDaftarTeam> arrayList;
    private FirebaseRecyclerOptions<DatasetDaftarTeam> options;
    private FirebaseRecyclerAdapter<DatasetDaftarTeam, holder> adapter;
    private DatabaseReference databasereference,daa;
    FirebaseDatabase database;
    DatabaseReference myref;
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
        arrayList = new ArrayList<DatasetDaftarTeam>();
        databasereference = FirebaseDatabase.getInstance().getReference().child("turnamen");
        databasereference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<DatasetDaftarTeam>().setQuery(databasereference, DatasetDaftarTeam.class).build();
        adapter = new FirebaseRecyclerAdapter<DatasetDaftarTeam, holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull DatasetDaftarTeam model) {
                holder.jumlahanggota.setText(model.getTangal());
                holder.namateam.setText(model.getNamateam());
                Picasso.get().load(model.getGambar()).into(holder.img);
                holder.alamatteam.setText(model.getLokasi());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final Dialog dialog = new Dialog(Cariturney.this);
                        dialog.setContentView(R.layout.dialog2);
                        dialog.setTitle("Pilih Aksi");
                        Button team = (Button) dialog.findViewById(R.id.turneylisttim);
                        Button daftar = (Button) dialog.findViewById(R.id.turneydaftar);
                        Button tutup = (Button) dialog.findViewById(R.id.turneytutup);
                        Button turney = (Button) dialog.findViewById(R.id.turneydetail);
                        dialog.show();
                        team.setEnabled(true);
                        tutup.setEnabled(true);
                        if (model.getEmail().equals(currentUser.getEmail())){
                            tutup.setEnabled(true);
                        } else {
                            tutup.setEnabled(false);
                        }
                        if(model.getStatus().equals("buka")){
                            daftar.setEnabled(true);
                        } else {
                            daftar.setEnabled(false);
                            tutup.setEnabled(false);
                        }
                        daftar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent form = new Intent(Cariturney.this,Formturnamen.class);
                                form.putExtra("pid",model.getPid());
                                startActivity(form);
                            }
                        });

                        team.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent team = new Intent(Cariturney.this,Teamturnamen.class);
                                team.putExtra("pid",model.getPid());
                                startActivity(team);
                            }
                        });

                        tutup.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                database = FirebaseDatabase.getInstance();
                                myref = database.getReference();
                                myref.child("turnamen").child(model.getPid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        snapshot.getRef().child("status").setValue("tutup");
                                        toast();
                                        Intent sudah = new Intent(Cariturney.this, Fiturturnamen.class);
                                        startActivity(sudah);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        toast2();
                                    }
                                });
                            }
                        });
                        turney.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent in = new Intent(Cariturney.this,DetailTurnamen.class);
                                in.putExtra("namatimlawan",model.getNamateam());
                                in.putExtra("emaillawan",model.getEmail());
                                in.putExtra("gambarlawan",model.getGambar());
                                in.putExtra("jumlahlawan",model.getJumlahanggota());
                                in.putExtra("lokasilawan",model.getLokasi());
                                in.putExtra("namalawan",model.getNamakamu());
                                in.putExtra("tanggaltanding",model.getTangal());
                                in.putExtra("waktutanding",model.getWaktu());
                                in.putExtra("nohp",model.getNohp());
                                in.putExtra("type",type);
                                in.putExtra("pid",model.getPid());
                                in.putExtra("email",currentUser.getEmail());
                                startActivity(in);
                            }
                        });
                    }
                });
            }

            @NonNull
            @Override
            public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new holder(LayoutInflater.from(Cariturney.this).inflate(R.layout.row_carilawan,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
    private void toast(){
        Toast.makeText(this, "Turnamen sudah di tutup", Toast.LENGTH_SHORT).show();
    }
    private void toast2(){
        Toast.makeText(this, "Turnamen gagal di tutup", Toast.LENGTH_SHORT).show();
    }
}