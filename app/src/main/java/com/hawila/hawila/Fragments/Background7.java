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

public class Background7 extends BaseFragment {

    ImageView back7;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    public static Background7 create(){
        return new Background7();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.background7;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        back7 = (ImageView) root.findViewById(R.id.img7);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BackImages");
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image7 = (String) dataSnapshot.child("Image7").getValue();
                Picasso.with(getActivity()).load(image7).into(back7);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
