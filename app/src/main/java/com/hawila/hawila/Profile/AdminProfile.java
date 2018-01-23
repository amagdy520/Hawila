package com.hawila.hawila.Profile;

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

public class AdminProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    ImageView mProfile;
    TextView mName , mWork , mWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        mProfile = (ImageView) findViewById(R.id.profile_show);
        mName = (TextView) findViewById(R.id.name_show);
        mWork = (TextView) findViewById(R.id.work_show);
        mWord = (TextView) findViewById(R.id.word_show);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = (String) dataSnapshot.child("Profile").getValue();
                Picasso.with(AdminProfile.this).load(image).into(mProfile);
                String name = (String) dataSnapshot.child("Name").getValue();
                mName.setText(name);
                String work = (String) dataSnapshot.child("Work").getValue();
                mWork.setText(work);
                String word = (String) dataSnapshot.child("Word").getValue();
                mWord.setText(word);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
