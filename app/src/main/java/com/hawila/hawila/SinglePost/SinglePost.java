package com.hawila.hawila.SinglePost;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.Modify.EditProfile;
import com.hawila.hawila.R;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

public class SinglePost extends AppCompatActivity {

    ImageView mSingle;
    private String post = null;
    private DatabaseReference mDatabase;
    TextView mTitle,mDesc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        post = getIntent().getExtras().getString("key");
        mSingle = (ImageView) findViewById(R.id.singleImage);
        mTitle = (TextView) findViewById(R.id.tit);
        mDesc = (TextView) findViewById(R.id.desc);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(mSingle);
        photoViewAttacher.update();
        mDatabase.child(post).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = (String) dataSnapshot.child("image").getValue();
                Picasso.with(SinglePost.this).load(image).into(mSingle);
                String tit = (String) dataSnapshot.child("title").getValue();
                String des = (String) dataSnapshot.child("description").getValue();
                mTitle.setText(tit);
                mDesc.setText(des);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
