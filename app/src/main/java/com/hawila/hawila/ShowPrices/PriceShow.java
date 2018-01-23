package com.hawila.hawila.ShowPrices;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.Modify.Modify;
import com.hawila.hawila.R;
import com.hawila.hawila.UploadNew.NewPrice;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Ahmed Magdy on 9/13/2017.
 */

public class PriceShow extends Activity {
    ImageButton delete_session;
    private RecyclerView mBlogList;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_price);mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Prices");
        mDatabase.keepSynced(true);
        mBlogList = (RecyclerView) findViewById(R.id.prices_list);
        mBlogList.setHasFixedSize(true);
        user = FirebaseAuth.getInstance().getCurrentUser();
        delete_session = (ImageButton) findViewById(R.id.delete_session);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PriceShow.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);
        //mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<offer, PriceShow.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<offer, PriceShow.BlogViewHolder>(
                offer.class,
                R.layout.price_row,
                PriceShow.BlogViewHolder.class,
                mDatabase
        ){
            @Override
            protected void populateViewHolder(final PriceShow.BlogViewHolder viewHolder, offer model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setDetails(model.getDetails());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setNote(model.getNote());
                viewHolder.setDate(model.getDate());
                viewHolder.setImage(PriceShow.this.getApplicationContext(), model.getImage());
                PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(viewHolder.Session);
                photoViewAttacher.update();
                if(!mAuth.getCurrentUser().getEmail().equals("admin@hawila.com")){
                    viewHolder.delete_session.setVisibility(View.INVISIBLE);
                    viewHolder.delete_session.setEnabled(false);
                    viewHolder.mSessionNew.setVisibility(View.INVISIBLE);
                    viewHolder.mSessionNew.setEnabled(false);
                }
                viewHolder.delete_session.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PriceShow.this);
                        builder.setMessage("Are you Sure?")
                                .setNegativeButton("Cancel",null)
                                .setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        viewHolder.mDatabase.child(post_key).removeValue();
                                    }
                                });

                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                });
                viewHolder.mSessionNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PriceShow.this, NewPrice.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        FirebaseUser user;
        ImageButton delete_session , mSessionNew;
        DatabaseReference  mDatabase;
        FirebaseAuth mAuth;
        ImageView Session;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Prices");
            mAuth = FirebaseAuth.getInstance();
            user = FirebaseAuth.getInstance().getCurrentUser();
            delete_session = (ImageButton) mView.findViewById(R.id.delete_session);
            mSessionNew = (ImageButton) mView.findViewById(R.id.new_session);
            Session = (ImageView) mView.findViewById(R.id.SessionImage);
        }
        public void setName(String name){
            TextView name_post = (TextView) mView.findViewById(R.id.SessionTitle);
            name_post.setText(name);
        }
        public void setDetails(String details){
            TextView desc_post = (TextView) mView.findViewById(R.id.SessionDetails);
            desc_post.setText(details);
        }
        public void setPrice(String price){
            TextView desc_post = (TextView) mView.findViewById(R.id.SessionPrice);
            desc_post.setText(price);
        }
        public void setNote(String note){
            TextView desc_post = (TextView) mView.findViewById(R.id.SessionNote);
            desc_post.setText(note);
        }
        public void setDate(String date) {
            TextView offer_date = (TextView) mView.findViewById(R.id.OfferTime);
            offer_date.setText(date);
        }
        public void setImage(final Context ctx, final String image){
            final ImageView image_post = (ImageView) mView.findViewById(R.id.SessionImage);
            //Picasso.with(ctx).load(image).into(image_post);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(image_post, new Callback() {
                @Override
                public void onSuccess() {
                }
                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(image_post);
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent price = new Intent(PriceShow.this, Launcher.class);
        startActivity(price);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
}
