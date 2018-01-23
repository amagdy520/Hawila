package com.hawila.hawila.ShowPosts;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.Launcher;
import com.hawila.hawila.Modify.Modify;
import com.hawila.hawila.R;
import com.hawila.hawila.ShowPrices.PriceShow;
import com.hawila.hawila.SinglePost.SinglePost;
import com.hawila.hawila.UploadNew.NewPost;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Ahmed Magdy on 9/13/2017.
 */

public class PostShow extends Activity {
    FirebaseUser user;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase , mDatabaseLike ;
    private FirebaseAuth mAuth;
    private boolean mProcess=false;
    private TextView count;
    private ImageButton delete , upload;
    private TextView date;
    private AdView mAdView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7936396332846954/1932187836");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
        mDatabase.keepSynced(true);
        mDatabaseLike.keepSynced(true);
        count = (TextView) findViewById(R.id.count);
        mBlogList = (RecyclerView) findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(PostShow.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);
        //mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));
        user = FirebaseAuth.getInstance().getCurrentUser();
        delete = (ImageButton) findViewById(R.id.delete_post);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent price = new Intent(PostShow.this, Launcher.class);
        startActivity(price);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Blog, PostShow.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, PostShow.BlogViewHolder>(
                Blog.class,
                R.layout.blog_row,
                PostShow.BlogViewHolder.class,
                mDatabase
        ){
            @Override
            protected void populateViewHolder(final PostShow.BlogViewHolder viewHolder, Blog model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDescription(model.getDescription());
                viewHolder.setPost_time(model.getPost_time());
                viewHolder.setImage(PostShow.this, model.getImage());
                viewHolder.set_like_icon(post_key);
                //PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(viewHolder.mShow);
                //photoViewAttacher.update();
                viewHolder.mShow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent single = new Intent(PostShow.this, SinglePost.class);
                        single.putExtra("key",post_key);
                        startActivity(single);
                        overridePendingTransition(R.anim.bounce, R.anim.bounce);
                    }
                });
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent single = new Intent(PostShow.this, SinglePost.class);
                        single.putExtra("key",post_key);
                        startActivity(single);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
                if(!mAuth.getCurrentUser().getEmail().equals("admin@hawila.com")){
                    viewHolder.delete.setVisibility(View.INVISIBLE);
                    viewHolder.delete.setEnabled(false);
                    viewHolder.mPostNew.setVisibility(View.INVISIBLE);
                    viewHolder.mPostNew.setEnabled(false);
                }
                viewHolder.mPostNew.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PostShow.this, NewPost.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
                viewHolder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(PostShow.this);
                        builder.setMessage("Are you Sure?")
                                .setNegativeButton("Cancel",null)
                                .setPositiveButton("Yes I'm Sure", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        viewHolder.mDatabase.child(post_key).removeValue();
                                        viewHolder.mDatabaseLike.child(post_key).removeValue();

                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                });
                viewHolder.mDatabaseLike.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        viewHolder.count.setText((String.valueOf(dataSnapshot.child(post_key).getChildrenCount()))+" Likes");
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                viewHolder.mLikebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProcess = true;

                        mDatabaseLike.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(mProcess) {
                                    if (dataSnapshot.child(post_key).hasChild((mAuth.getCurrentUser().getUid()))) {
                                        mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();
                                        mProcess = false;
                                    } else {
                                        mDatabaseLike.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue(mAuth.getCurrentUser().getEmail());
                                        mProcess = false;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        ImageButton mLikebtn ,delete,mPostNew;
        TextView count;
        DatabaseReference mDatabaseLike , mDatabase;
        FirebaseAuth mAuth;
        ImageView mShow;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mShow = (ImageView) mView.findViewById(R.id.PostImage);
            delete = (ImageButton)mView.findViewById(R.id.delete_post);
            mPostNew = (ImageButton)mView.findViewById(R.id.new_post);
            mLikebtn = (ImageButton) mView.findViewById(R.id.like_but);
            count = (TextView)mView.findViewById(R.id.count);
            mDatabaseLike = FirebaseDatabase.getInstance().getReference().child("Likes");
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");
            mAuth = FirebaseAuth.getInstance();
            mDatabaseLike.keepSynced(true);
        }
        public void set_like_icon(final String post_key){
            mDatabaseLike.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())){
                        mLikebtn.setImageResource(R.drawable.like2);
                    }else{
                        mLikebtn.setImageResource(R.drawable.like);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        public void setTitle(String title){
            TextView title_post = (TextView) mView.findViewById(R.id.PostTitle);
            title_post.setText(title);
        }
        public void setDescription(String description){
            TextView desc_post = (TextView) mView.findViewById(R.id.PostDesc);
            desc_post.setText(description);
        }
        public void setPost_time(String post_time){
            TextView date_post = (TextView) mView.findViewById(R.id.PostTime);
            date_post.setText(post_time);
        }
        public void setImage(final Context ctx, final String image){
            final ImageView image_post = (ImageView) mView.findViewById(R.id.PostImage);
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
}
