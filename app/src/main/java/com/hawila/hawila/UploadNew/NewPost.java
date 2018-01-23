package com.hawila.hawila.UploadNew;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hawila.hawila.Modify.Modify;
import com.hawila.hawila.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewPost extends AppCompatActivity {
    private ImageButton mSelectImage;
    private EditText mPostTitle , mPostDesc;
    private TextView mSubmit;
    private Uri mImageUri = null , mVideoUri = null;
    private ProgressDialog mProgress;
    private final int GALLERY_REQUEST=1;
    private StorageReference mStorage;
    private DatabaseReference mDataBase;
    private int check = 0;
    private VideoView mVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mStorage = FirebaseStorage.getInstance().getReference();

        mDataBase = FirebaseDatabase.getInstance().getReference().child("Blog");

        mSelectImage = (ImageButton) findViewById(R.id.post_image);
        mVideo = (VideoView) findViewById(R.id.post_video);
        mPostTitle = (EditText) findViewById(R.id.post_title);
        mPostDesc = (EditText) findViewById(R.id.post_desc);
        mSubmit = (TextView)findViewById(R.id.submit);

        mVideo.setVisibility(View.INVISIBLE);
        mPostTitle.requestFocus();


        mProgress = new ProgressDialog(this);

        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewPost.this);
                builder.setMessage("You Will Upload")
                        .setNegativeButton("Video", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(intent, "Complete action using"),GALLERY_REQUEST);
                                check = 1;
                            }
                        })
                        .setPositiveButton("Image", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                                galleryIntent.setType("image/*");
                                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                                check = 2;
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPost();
            }
        });


    }

    @Override
    public void onBackPressed() {
        String title_val = mPostTitle.getText().toString().trim();
        String desc_val = mPostDesc.getText().toString().trim();
        if(!TextUtils.isEmpty(title_val) || !TextUtils.isEmpty(desc_val)){
            AlertDialog.Builder builder = new AlertDialog.Builder(NewPost.this);
            builder.setMessage("Your post will Dismiss")
                    .setNegativeButton("Cancel",null)
                    .setPositiveButton("Dismiss Post", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NewPost.super.onBackPressed();
                            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            NewPost.super.onBackPressed();
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
    }

    private void startPost(){
        mProgress.setMessage("Posting ...");
        final String title_val = mPostTitle.getText().toString().trim();
        final String desc_val = mPostDesc.getText().toString().trim();
        final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy  HH:mm");
        final String date = sdf.format(new Date());
        if((!TextUtils.isEmpty(title_val)) && (!TextUtils.isEmpty(desc_val))) {
            mProgress.show();
            StorageReference filepath = mStorage.child("Blog_Images").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    DatabaseReference newPost = mDataBase.push();
                    newPost.child("title").setValue(title_val);
                    newPost.child("description").setValue(desc_val);
                    newPost.child("image").setValue(downloadUrl.toString());
                    newPost.child("post_time").setValue(date);
                    mProgress.dismiss();
                    startActivity(new Intent(NewPost.this, Modify.class));
                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 2) {
            mImageUri = data.getData();
            CropImage.activity(mImageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 2) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri = result.getUri();
                mSelectImage.setImageURI(mImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if(requestCode == GALLERY_REQUEST && check == 1) {
            mVideoUri = data.getData();
            mSelectImage.setVisibility(View.INVISIBLE);
            mVideo.setVisibility(View.VISIBLE);
            mVideo.setVideoURI(mVideoUri);
            mVideo.start();
            mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mp.setLooping(true);
                }
            });
        }
    }
}
