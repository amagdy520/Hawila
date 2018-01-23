package com.hawila.hawila.Backgrond;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.R;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class ChooseBackground extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private TextView mSave;
    private ImageView choose1, choose2, choose3, choose4, choose5, choose6, choose7, choose8, choose9, choose10;
    private final int GALLERY_REQUEST=1;
    private Uri mImageUri1, mImageUri2, mImageUri3, mImageUri4, mImageUri5, mImageUri6, mImageUri7, mImageUri8, mImageUri9, mImageUri10;
    private int check ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_background);
        //******************************************************************************************
        mDatabase = FirebaseDatabase.getInstance().getReference().child("BackImages");
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgress = new ProgressDialog(this);
        //******************************************************************************************
        mSave   = (TextView) findViewById(R.id.save);
        choose1 = (ImageView) findViewById(R.id.ChooseImage1);
        choose2 = (ImageView) findViewById(R.id.ChooseImage2);
        choose3 = (ImageView) findViewById(R.id.ChooseImage3);
        choose4 = (ImageView) findViewById(R.id.ChooseImage4);
        choose5 = (ImageView) findViewById(R.id.ChooseImage5);
        choose6 = (ImageView) findViewById(R.id.ChooseImage6);
        choose7 = (ImageView) findViewById(R.id.ChooseImage7);
        choose8 = (ImageView) findViewById(R.id.ChooseImage8);
        choose9 = (ImageView) findViewById(R.id.ChooseImage9);
        choose10 =(ImageView)findViewById(R.id.ChooseImage10);
        //******************************************************************************************
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String image1 = (String) dataSnapshot.child("Image1").getValue();
                Picasso.with(ChooseBackground.this).load(image1).into(choose1);
                //*******************
                String image2 = (String) dataSnapshot.child("Image2").getValue();
                Picasso.with(ChooseBackground.this).load(image2).into(choose2);
                //********************
                String image3 = (String) dataSnapshot.child("Image3").getValue();
                Picasso.with(ChooseBackground.this).load(image3).into(choose3);
                //********************
                String image4 = (String) dataSnapshot.child("Image4").getValue();
                Picasso.with(ChooseBackground.this).load(image4).into(choose4);
                //********************
                String image5 = (String) dataSnapshot.child("Image5").getValue();
                Picasso.with(ChooseBackground.this).load(image5).into(choose5);
                //*********************
                String image6 = (String) dataSnapshot.child("Image6").getValue();
                Picasso.with(ChooseBackground.this).load(image6).into(choose6);
                //*********************
                String image7 = (String) dataSnapshot.child("Image7").getValue();
                Picasso.with(ChooseBackground.this).load(image7).into(choose7);
                //*********************
                String image8 = (String) dataSnapshot.child("Image8").getValue();
                Picasso.with(ChooseBackground.this).load(image8).into(choose8);
                //*********************
                String image9 = (String) dataSnapshot.child("Image9").getValue();
                Picasso.with(ChooseBackground.this).load(image9).into(choose9);
                //**********************
                String image10 = (String) dataSnapshot.child("Image10").getValue();
                Picasso.with(ChooseBackground.this).load(image10).into(choose10);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //******************************************************************************************
        choose1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 1;
            }
        });
        choose2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 2;
            }
        });
        choose3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 3;
            }
        });
        choose4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 4;
            }
        });
        choose5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 5;
            }
        });
        choose6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 6;
            }
        });
        choose7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 7;
            }
        });
        choose8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 8;
            }
        });
        choose9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 9;
            }
        });
        choose10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST);
                check = 10;
            }
        });
        //******************************************************************************************
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
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 1) {
            mImageUri1 = data.getData();
            CropImage.activity(mImageUri1)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 1) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri1 = result.getUri();
                choose1.setImageURI(mImageUri1);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath1 = mStorage.child("ImageBackground").child("Image1").child(mImageUri1.getLastPathSegment());
                            filepath1.putFile(mImageUri1).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image1").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 2) {
            mImageUri2 = data.getData();
            CropImage.activity(mImageUri2)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 2) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri2 = result.getUri();
                choose2.setImageURI(mImageUri2);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath2 = mStorage.child("ImageBackground").child("Image2").child(mImageUri2.getLastPathSegment());
                            filepath2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image2").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 3) {
            mImageUri3 = data.getData();
            CropImage.activity(mImageUri3)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 3) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri3 = result.getUri();
                choose3.setImageURI(mImageUri3);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath3 = mStorage.child("ImageBackground").child("Image3").child(mImageUri3.getLastPathSegment());
                            filepath3.putFile(mImageUri3).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image3").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 4) {
            mImageUri4 = data.getData();
            CropImage.activity(mImageUri4)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 4) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri4 = result.getUri();
                choose4.setImageURI(mImageUri4);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath4 = mStorage.child("ImageBackground").child("Image4").child(mImageUri4.getLastPathSegment());
                            filepath4.putFile(mImageUri4).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image4").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 5) {
            mImageUri5 = data.getData();
            CropImage.activity(mImageUri5)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 5) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri5 = result.getUri();
                choose5.setImageURI(mImageUri5);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath5 = mStorage.child("ImageBackground").child("Image5").child(mImageUri5.getLastPathSegment());
                            filepath5.putFile(mImageUri5).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image5").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 6) {
            mImageUri6 = data.getData();
            CropImage.activity(mImageUri6)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 6) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri6 = result.getUri();
                choose6.setImageURI(mImageUri6);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath6 = mStorage.child("ImageBackground").child("Image6").child(mImageUri6.getLastPathSegment());
                            filepath6.putFile(mImageUri6).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image6").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 7) {
            mImageUri7 = data.getData();
            CropImage.activity(mImageUri7)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 7) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri7 = result.getUri();
                choose7.setImageURI(mImageUri7);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath7 = mStorage.child("ImageBackground").child("Image7").child(mImageUri7.getLastPathSegment());
                            filepath7.putFile(mImageUri7).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image7").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 8) {
            mImageUri8 = data.getData();
            CropImage.activity(mImageUri8)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 8) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri8 = result.getUri();
                choose8.setImageURI(mImageUri8);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath8 = mStorage.child("ImageBackground").child("Image8").child(mImageUri8.getLastPathSegment());
                            filepath8.putFile(mImageUri8).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image8").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 9) {
            mImageUri9 = data.getData();
            CropImage.activity(mImageUri9)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 9) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri9 = result.getUri();
                choose9.setImageURI(mImageUri9);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath9 = mStorage.child("ImageBackground").child("Image9").child(mImageUri9.getLastPathSegment());
                            filepath9.putFile(mImageUri9).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image9").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        //******************************************************************************************
        if(requestCode == GALLERY_REQUEST && resultCode == RESULT_OK && check == 10) {
            mImageUri10 = data.getData();
            CropImage.activity(mImageUri10)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && check == 10) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mImageUri10 = result.getUri();
                choose10.setImageURI(mImageUri10);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Thread.sleep(300);
                            StorageReference filepath10 = mStorage.child("ImageBackground").child("Image10").child(mImageUri10.getLastPathSegment());
                            filepath10.putFile(mImageUri10).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                                    mDatabase.child("Image10").setValue(downloadUrl.toString());
                                }
                            });
                        }catch (Exception e){

                        }
                    }
                }).start();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
    //**********************************************************************************************
    public void StartSave(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ChooseBackground.this);
        builder.setMessage("Are You Sure!?")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("Save Changes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mProgress.setMessage("Saving...");
                        mProgress.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    mProgress.dismiss();
                                    startActivity(new Intent(ChooseBackground.this, Launcher.class));
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                }catch (Exception e){

                                }
                            }
                        }).start();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
