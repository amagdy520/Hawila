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
import com.hawila.hawila.Launcher;
import com.hawila.hawila.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Ahmed Magdy on 9/12/2017.
 */

public class Background1 extends BaseFragment {

    ImageView back1;
    DatabaseReference mDatabase;
    StorageReference mStorage;
    public static Background1 create(){
        return new Background1();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.background1;
    }

    @Override
    public void inOnCreateView(View root, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        back1 = (ImageView) root.findViewById(R.id.img1);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BackImages");
        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image1 = (String) dataSnapshot.child("Image1").getValue();
                Picasso.with(getActivity()).load(image1).into(back1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
