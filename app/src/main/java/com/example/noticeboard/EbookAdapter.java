package com.example.noticeboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder>{
    private final Context context;
    private final List<UserEbookData> list;

    public EbookAdapter(Context context, List<UserEbookData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout, viewGroup, false);

        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder ebookViewHolder, @SuppressLint("RecyclerView") int i) {

        ebookViewHolder.ebookName.setText(list.get(i).getPdfTitle());
        ebookViewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PdfViewerActivity.class);
            intent.putExtra("pdfUrl",list.get(i).getPdfUrl());
            context.startActivity(intent);
        });
        ebookViewHolder.ebookDownload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(list.get(i).getPdfUrl()));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class EbookViewHolder extends RecyclerView.ViewHolder {
    private final TextView ebookName;
    private final ImageView ebookDownload;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            ebookName=itemView.findViewById(R.id.ebookName);
            ebookDownload=itemView.findViewById(R.id.ebookDownload);
        }
    }
}
