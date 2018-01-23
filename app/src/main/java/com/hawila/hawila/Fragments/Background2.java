package com.hawila.hawila.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hawila.hawila.Adapter.BaseFragment;
import com.hawila.hawila.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Ahmed Magdy on 9/12/2017.
 */

public class Background2 extends BaseFragment {

    ImageView back2;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    public static Background2 create(){
        return new Background2();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.background2;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        back2 = (ImageView) root.findViewById(R.id.img2);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BackImages");
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image2 = (String) dataSnapshot.child("Image2").getValue();
                Picasso.with(getActivity()).load(image2).into(back2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
