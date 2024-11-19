package com.project.sportkuy.carilawanfutsal;

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
import com.project.sportkuy.MainActivity;
import com.project.sportkuy.R;
import com.project.sportkuy.editbola.EditBola;
import com.project.sportkuy.editfutsal.EditFutsal;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CariLawan extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ArrayList<DatasetDaftarTeam> arrayList;
    private FirebaseRecyclerOptions<DatasetDaftarTeam> options;
    private FirebaseRecyclerAdapter<DatasetDaftarTeam,holder> adapter;
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
        arrayList = new ArrayList<DatasetDaftarTeam>();
        type = getIntent().getStringExtra("type");
        databasereference = FirebaseDatabase.getInstance().getReference().child(type);
        databasereference.keepSynced(true);
        options = new FirebaseRecyclerOptions.Builder<DatasetDaftarTeam>().setQuery(databasereference, DatasetDaftarTeam.class).build();
        adapter = new FirebaseRecyclerAdapter<DatasetDaftarTeam, holder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull holder holder, int position, @NonNull DatasetDaftarTeam model) {
                holder.jumlahanggota.setText(model.getTangal());
                holder.namateam.setText(model.getNamateam());
                Picasso.get().load(model.getGambar()).into(holder.img);
                holder.alamatteam.setText(model.getLokasi());

                if (type.equals("Futsal")){
                    holder.alamatteam.setText(model.getLokasi());
                } else {
                    holder.alamatteam.setText(model.getWaktu());
                }

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent in = new Intent(CariLawan.this,Detailcarilawan.class);
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
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final Dialog dialog = new Dialog(CariLawan.this);
                        dialog.setContentView(R.layout.dialog_view);
                        dialog.setTitle("Pilih Aksi");
                        dialog.show();
                        Button editButton = (Button) dialog.findViewById(R.id.bt_edit_data);
                        Button delButton = (Button) dialog.findViewById(R.id.bt_delete_data);
                        editButton.setEnabled(false);
                        delButton.setEnabled(false);
                        if (model.getEmail().equals(currentUser.getEmail())){
                            editButton.setEnabled(true);
                            delButton.setEnabled(true);
                        }else {
                            editButton.setEnabled(false);
                            delButton.setEnabled(false);
                        }
                        editButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        dialog.dismiss();
                                        if(type.equals("Futsal")){
                                            Intent in = new Intent(CariLawan.this, EditFutsal.class);
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
                                            in.putExtra("type",type);
                                            startActivity(in);
                                        } else {
                                            Intent in = new Intent(CariLawan.this, EditBola.class);
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
                                    }
                                }

                        );
                        delButton.setOnClickListener(
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        daa = FirebaseDatabase.getInstance().getReference()
                                                .child(type).child(model.getPid());
                                        daa.removeValue();
                                        dialog.dismiss();
                                    }
                                }
                        );
                        return true;
                    }
                });
            }

            @NonNull
            @Override
            public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new holder(LayoutInflater.from(CariLawan.this).inflate(R.layout.row_carilawan,parent,false));
            }
        };
        recyclerView.setAdapter(adapter);
    }
}