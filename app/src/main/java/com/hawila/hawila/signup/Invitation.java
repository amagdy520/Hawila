package com.hawila.hawila.signup;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.R;

/**
 * Created by Ahmed Magdy on 9/15/2017.
 */

public class Invitation extends Activity {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    TextView mInvitation;
    ImageView mCopy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mInvitation = (TextView) findViewById(R.id.input_Invitation);
        mCopy = (ImageView) findViewById(R.id.copy);
        mDatabase.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String invite = (String) dataSnapshot.child("Invitation Code").getValue();
                mInvitation.setText(invite);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                copyToClipboard(mInvitation.getText().toString());
            }
        });
    }
    public void copyToClipboard(String copyText) {
        ClipboardManager clipboard = (ClipboardManager) this.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("url", copyText);
        clipboard.setPrimaryClip(clip);
        Toast toast = Toast.makeText(this, "Invitation Code is copied", Toast.LENGTH_SHORT);
        toast.show();
    }
}
