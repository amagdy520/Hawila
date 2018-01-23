package com.hawila.hawila.Accounts;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.R;
import com.hawila.hawila.ShowPosts.Blog;
import com.hawila.hawila.ShowPosts.posts_view;

/**
 * Created by Ahmed Magdy on 9/15/2017.
 */

public class account_view extends Activity {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);
        mBlogList = (RecyclerView) findViewById(R.id.account_list);
        mBlogList.setHasFixedSize(true);
        /*LinearLayoutManager layoutManager = new LinearLayoutManager(account_view.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);*/
    }
    @Override
    protected void onStart() {
        super.onStart();
        Query query = mDatabase.orderByChild("Invite").orderByValue();
        FirebaseRecyclerAdapter<Account, account_view.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Account, account_view.BlogViewHolder>(
                Account.class,
                R.layout.account_row,
                account_view.BlogViewHolder.class,
                query
        ){
            @Override
            protected void populateViewHolder(final account_view.BlogViewHolder viewHolder, Account model, int position) {
                viewHolder.setEmail(model.getEmail());
                viewHolder.setName(model.getName());
                viewHolder.setMobile(model.getMobile());
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        DatabaseReference mDatabase;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        }
        public void setEmail(String email){
            TextView mEmail = (TextView) mView.findViewById(R.id.ShowpEmail);
            mEmail.setText(email);
        }
        public void setName(String name){
            TextView mName = (TextView) mView.findViewById(R.id.ShowpName);
            mName.setText(name);
        }
        public void setMobile(String mobile){
            TextView mMobile = (TextView) mView.findViewById(R.id.ShowpMobile);
            mMobile.setText(mobile);
        }
    }
}
