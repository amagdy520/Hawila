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

public class Background8 extends BaseFragment {

    ImageView back8;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    public static Background8 create(){
        return new Background8();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.background8;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        back8 = (ImageView) root.findViewById(R.id.img8);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BackImages");
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image8 = (String) dataSnapshot.child("Image8").getValue();
                Picasso.with(getActivity()).load(image8).into(back8);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
