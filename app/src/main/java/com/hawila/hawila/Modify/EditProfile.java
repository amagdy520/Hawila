package com.hawila.hawila.Modify;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hawila.hawila.Backgrond.ChooseBackground;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class EditProfile extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    TextView mSave;
    ImageView mProfile;
    EditText mName , mWork , mWord;
    private ProgressDialog mProgress;
    private final int GALLERY_REQUEST=1;
    private Uri mImageUri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        mStorage = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.activity_edit_profile);
        mSave = (TextView) findViewById(R.id.save_profile);
        mProfile = (ImageView) findViewById(R.id.profile_image);
        mName = (EditText) findViewById(R.id.name);
        mWork = (EditText) findViewById(R.id.work);
        mWord = (EditText) findViewById(R.id.word);
        mProgress = new ProgressDialog(this);

        mName.requestFocus();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image = (String) dataSnapshot.child("Profile").getValue();
                Picasso.with(EditProfile.this).load(image).into(mProfile);
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

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
            }
        });

        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartSave();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mProfile.setImageURI(mImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    public void StartSave(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setMessage("Are You Sure!?")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgress.setMessage("Saving...");
                        mProgress.show();
                        final String name = mName.getText().toString().trim();
                        final String work = mWork.getText().toString().trim();
                        final String word = mWord.getText().toString().trim();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(mImageUri != null) {
                                    StorageReference filepath = mStorage.child("AdminProfile").child(mImageUri.getLastPathSegment());
                                    filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                            mDatabase.child("Profile").setValue(downloadUrl.toString());
                                            mDatabase.child("Name").setValue(name);
                                            mDatabase.child("Work").setValue(work);
                                            mDatabase.child("Word").setValue(word);
                                            mProgress.dismiss();
                                            startActivity(new Intent(EditProfile.this, Modify.class));
                                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                        }
                                    });
                                }else{
                                    mDatabase.child("Name").setValue(name);
                                    mDatabase.child("Work").setValue(work);
                                    mDatabase.child("Word").setValue(word);
                                    mProgress.dismiss();
                                    startActivity(new Intent(EditProfile.this, Modify.class));
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
