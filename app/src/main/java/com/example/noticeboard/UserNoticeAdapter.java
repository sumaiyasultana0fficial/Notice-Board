package com.example.noticeboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserNoticeAdapter extends RecyclerView.Adapter<UserNoticeAdapter.NoticeViewAdapter> {

    private Context context;
    private ArrayList<UserNoticeData> list;

    public UserNoticeAdapter(Context context, ArrayList<UserNoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.see_notice_item_layout, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {
        final UserNoticeData currentItem = list.get(position);

        // Set title, date, and time
        holder.seeUserNoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getData());
        holder.time.setText(currentItem.getTime());

        // Handle image display based on the availability of the image
        String imageUrl = currentItem.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            holder.seeUserNoticeImage.setVisibility(View.VISIBLE);
            Picasso.get().load(imageUrl).into(holder.seeUserNoticeImage);
        } else {
            holder.seeUserNoticeImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView seeUserNoticeTitle, date, time;
        private ImageView seeUserNoticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            seeUserNoticeTitle = itemView.findViewById(R.id.seeUserNoticeTitle);
            seeUserNoticeImage = itemView.findViewById(R.id.seeUserNoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
