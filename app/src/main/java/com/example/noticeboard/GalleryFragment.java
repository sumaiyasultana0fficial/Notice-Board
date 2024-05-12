package com.example.noticeboard;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        RecyclerView convoRecycler = view.findViewById(R.id.campusRecycler);
        RecyclerView otherRecycler = view.findViewById(R.id.otherRecycler);

        List<String> convoImageList = new ArrayList<>();
        List<String> otherImageList = new ArrayList<>();

        GalleryAdapter convoAdapter = new GalleryAdapter(getContext(), convoImageList);
        GalleryAdapter otherAdapter = new GalleryAdapter(getContext(), otherImageList);

        convoRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        convoRecycler.setAdapter(convoAdapter);

        otherRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        otherRecycler.setAdapter(otherAdapter);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        getImages("Convocation", convoImageList, convoAdapter);
        getImages("Other Events", otherImageList, otherAdapter);

        return view;
    }

    private void getImages(String category, List<String> imageList, GalleryAdapter adapter) {
        reference.child(category).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                imageList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String data = snapshot.getValue(String.class);
                    if (data != null) {
                        imageList.add(data);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
