package com.project.sportkuy.carilawanfutsal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.project.sportkuy.R;

public class holder extends RecyclerView.ViewHolder {
    public TextView namateam,alamatteam,jumlahanggota;
    public ImageView img;
    public holder(@NonNull View itemView) {
        super(itemView);
        namateam =itemView.findViewById(R.id.namalawan);
        alamatteam = itemView.findViewById(R.id.alamatteam);
        jumlahanggota = itemView.findViewById(R.id.jumlahanggota);
        img = itemView.findViewById(R.id.imv);
    }
}
