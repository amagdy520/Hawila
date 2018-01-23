package com.hawila.hawila.Modify;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.R;

public class SocialNetwork extends AppCompatActivity {

    DatabaseReference mDatabase;
    RelativeLayout R1 , R2 , R3 , R4;
    private Animation myBounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_network);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        myBounce = AnimationUtils.loadAnimation(SocialNetwork.this, R.anim.bounce);
        R1 = (RelativeLayout) findViewById(R.id.facebook);
        R1.setAnimation(myBounce);
        R2 = (RelativeLayout) findViewById(R.id.instagram);
        R2.setAnimation(myBounce);
        R3 = (RelativeLayout) findViewById(R.id.whats);
        R3.setAnimation(myBounce);
        R4 = (RelativeLayout) findViewById(R.id.mobile);
        R4.setAnimation(myBounce);
        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(SocialNetwork.this);
                LayoutInflater inflater = SocialNetwork.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.edit1);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String face = (String) dataSnapshot.child("Facebook").getValue();
                        edt.setText(face);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogBuilder.setTitle("Facebook");
                dialogBuilder.setIcon(R.drawable.face2);
                dialogBuilder.setMessage("Enter Page Id");
                dialogBuilder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String fb = edt.getText().toString().trim();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mDatabase.child("Facebook").setValue(fb);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
                dialogBuilder.setNegativeButton("Cancel", null);
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });
        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder2 = new AlertDialog.Builder(SocialNetwork.this);
                LayoutInflater inflater2 = SocialNetwork.this.getLayoutInflater();
                View dialogView = inflater2.inflate(R.layout.custom_dialog2, null);
                dialogBuilder2.setView(dialogView);

                final EditText edt2 = (EditText) dialogView.findViewById(R.id.edit1);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String insta = (String) dataSnapshot.child("Instagram").getValue();
                        edt2.setText(insta);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogBuilder2.setTitle("Instagram");
                dialogBuilder2.setIcon(R.drawable.ins);
                dialogBuilder2.setMessage("Enter Your Instagram Account");
                dialogBuilder2.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String ins = edt2.getText().toString().trim();
                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                mDatabase.child("Instagram").setValue(ins);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
                dialogBuilder2.setNegativeButton("Cancel", null);
                AlertDialog b2 = dialogBuilder2.create();
                b2.show();
            }
        });
        //******************************************************************************************
        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder3 = new AlertDialog.Builder(SocialNetwork.this);
                LayoutInflater inflater3 = SocialNetwork.this.getLayoutInflater();
                View dialogView = inflater3.inflate(R.layout.custom_dialog, null);
                dialogBuilder3.setView(dialogView);

                final EditText edt3 = (EditText) dialogView.findViewById(R.id.edit1);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String num1 = (String) dataSnapshot.child("WhatsApp").getValue();
                        edt3.setText(num1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogBuilder3.setTitle("WhatsApp");
                dialogBuilder3.setIcon(R.drawable.whas);
                dialogBuilder3.setMessage("Enter Your WhatsApp Number");
                dialogBuilder3.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String whs = edt3.getText().toString().trim();
                        if(whs.length()!=11){
                            Toast.makeText(SocialNetwork.this,"Please Check Phone Number!",Toast.LENGTH_SHORT).show();
                        }else {
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mDatabase.child("WhatsApp").setValue(whs);
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
                dialogBuilder3.setNegativeButton("Cancel", null);
                AlertDialog b3 = dialogBuilder3.create();
                b3.show();
            }
        });
        //********************************************************************************************************
        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialogBuilder4 = new AlertDialog.Builder(SocialNetwork.this);
                LayoutInflater inflater4 = SocialNetwork.this.getLayoutInflater();
                View dialogView = inflater4.inflate(R.layout.custom_dialog, null);
                dialogBuilder4.setView(dialogView);

                final EditText edt4 = (EditText) dialogView.findViewById(R.id.edit1);

                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String num2 = (String) dataSnapshot.child("Mobile").getValue();
                        edt4.setText(num2);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                dialogBuilder4.setTitle("Mobile Number");
                dialogBuilder4.setIcon(R.drawable.mobile);
                dialogBuilder4.setMessage("Enter Your Mobile Number");
                dialogBuilder4.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        final String mb = edt4.getText().toString().trim();
                        if(mb.length()!=11){
                            Toast.makeText(SocialNetwork.this,"Please Check Phone Number!",Toast.LENGTH_SHORT).show();
                        }else {
                            mDatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    mDatabase.child("Mobile").setValue(mb);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                });
                dialogBuilder4.setNegativeButton("Cancel", null);
                AlertDialog b4 = dialogBuilder4.create();
                b4.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
