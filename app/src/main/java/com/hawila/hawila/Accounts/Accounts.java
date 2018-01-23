package com.hawila.hawila.Accounts;

import android.support.v7.app.AppCompatActivity;
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

public class Accounts extends AppCompatActivity {
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
        LinearLayoutManager layoutManager = new LinearLayoutManager(Accounts.this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mBlogList.setLayoutManager(layoutManager);
        //mBlogList.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Query query = mDatabase.orderByChild("Invite");
        FirebaseRecyclerAdapter<Account, Accounts.BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Account, Accounts.BlogViewHolder>(
                Account.class,
                R.layout.account_row,
                Accounts.BlogViewHolder.class,
                query
        ){
            @Override
            protected void populateViewHolder(final Accounts.BlogViewHolder viewHolder, Account model, int position) {
                final String post_key = getRef(position).getKey();
                viewHolder.setEmail(model.getEmail());
                viewHolder.setName(model.getName());
                viewHolder.setMobile(model.getMobile());
                viewHolder.mDatabase.child(post_key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        viewHolder.count.setText((String.valueOf(dataSnapshot.child("Invite").getChildrenCount())));
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        };
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }
    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        DatabaseReference mDatabase;
        TextView count;
        public BlogViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
            count = (TextView) mView.findViewById(R.id.Invite_num);
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