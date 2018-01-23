package com.hawila.hawila.Modify;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.hawila.hawila.Accounts.Account;
import com.hawila.hawila.Accounts.Accounts;
import com.hawila.hawila.Backgrond.ChooseBackground;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.R;
import com.hawila.hawila.UploadNew.NewPost;
import com.hawila.hawila.UploadNew.NewPrice;

public class Modify extends AppCompatActivity {

    RelativeLayout R1 , R2 , R3 , R4 , R5 , R6;
    private Animation myBounce;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        myBounce = AnimationUtils.loadAnimation(Modify.this, R.anim.bounce);
        R1 = (RelativeLayout) findViewById(R.id.edit_profile);
        R1.setAnimation(myBounce);
        R2 = (RelativeLayout) findViewById(R.id.edit_background);
        R2.setAnimation(myBounce);
        R3 = (RelativeLayout) findViewById(R.id.edit_social);
        R3.setAnimation(myBounce);
        R4 = (RelativeLayout) findViewById(R.id.post);
        R4.setAnimation(myBounce);
        R5 = (RelativeLayout) findViewById(R.id.post_price);
        R5.setAnimation(myBounce);
        R6 = (RelativeLayout) findViewById(R.id.acc_manage);
        R6.setAnimation(myBounce);

        R1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, EditProfile.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        R2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, ChooseBackground.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        R3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, SocialNetwork.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        R4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, NewPost.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        R5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, NewPrice.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        R6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Modify.this, Accounts.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Modify.this, Launcher.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
