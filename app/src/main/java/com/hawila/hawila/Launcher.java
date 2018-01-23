package com.hawila.hawila;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hawila.hawila.Adapter.BuilderManager;
import com.hawila.hawila.Adapter.MainPagerAdapter;
import com.hawila.hawila.Modify.Modify;
import com.hawila.hawila.Profile.AdminProfile;
import com.hawila.hawila.ShowPosts.PostShow;
import com.hawila.hawila.ShowPrices.PriceShow;
import com.hawila.hawila.signup.Invitation;
import com.hawila.hawila.signup.SignupActivity;
import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.OnBMClickListener;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.ButtonEnum;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;
import com.facebook.FacebookSdk;

import java.util.Timer;
import java.util.TimerTask;

public class Launcher extends AppCompatActivity {
    private BoomMenuButton mHome, mAdmin;
    private ViewPager viewPager;
    private MainPagerAdapter viewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    AlertDialog dialog;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG = "Launcher";
    private FloatingActionButton mLogin;
    private Animation myAnim , myBounce;
    private DatabaseReference mDatabase;
    private static String Facebook;
    private String Instagram;
    private String WhatsApp;
    private String Phone;
    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        myAnim = AnimationUtils.loadAnimation(Launcher.this, R.anim.shake);
        myBounce = AnimationUtils.loadAnimation(Launcher.this, R.anim.bounce);
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-7936396332846954/1932187836");
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //******************************************************************************************
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Admin");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Facebook = (String) dataSnapshot.child("Facebook").getValue();
                Instagram = (String)dataSnapshot.child("Instagram").getValue();
                WhatsApp = (String) dataSnapshot.child("WhatsApp").getValue();
                Phone = (String) dataSnapshot.child("Mobile").getValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //******************************************************************************************
        mLogin = (FloatingActionButton) findViewById(R.id.login_but);
        mHome = (BoomMenuButton) findViewById(R.id.home_menu);
        mAdmin = (BoomMenuButton) findViewById(R.id.home_admin);
        mAdmin.setVisibility(View.INVISIBLE);
        mHome.setVisibility(View.INVISIBLE);
        //******************************************************************************************
        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //CHECKING USER PRESENCE
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if ((user != null)) {
                    if (user.getEmail().equals("admin@hawila.com")) {
                        mLogin.setVisibility(View.INVISIBLE);
                        mAdmin.setVisibility(View.VISIBLE);
                        mAdmin.setAnimation(myBounce);
                    } else {
                        if(user.isEmailVerified()) {
                            mLogin.setVisibility(View.INVISIBLE);
                            mHome.setVisibility(View.VISIBLE);
                            mHome.setAnimation(myBounce);
                        }else{
                            sendVerificationEmail();
                        }
                    }
                }

            }
        };
        mAuth.addAuthStateListener(mAuthListener);
        //******************************************************************************************
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Launcher.this);
                View mView = getLayoutInflater().inflate(R.layout.activity_login, null);
                final EditText mEmail = (EditText) mView.findViewById(R.id.input_email);
                final EditText mPassword = (EditText) mView.findViewById(R.id.input_password);
                Button mLogin = (Button) mView.findViewById(R.id.btn_login);
                TextView mSignUpLink = (TextView) mView.findViewById(R.id.link_signup);
                TextView mForget = (TextView) mView.findViewById(R.id.forget_password);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = mEmail.getText().toString();
                        String password = mPassword.getText().toString();
                        if (email.isEmpty() || password.isEmpty()) {
                            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                mEmail.setError("enter a valid email address");
                            } else {
                                mEmail.setError(null);
                            }
                            if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
                                mPassword.setError("between 4 and 10 alphanumeric characters");
                            } else {
                                mPassword.setError(null);
                            }
                        } else {
                            Login(mEmail.getText().toString(), mPassword.getText().toString());
                        }
                    }
                });
                mSignUpLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Start the Signup activity
                        Intent intent = new Intent(Launcher.this, SignupActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                    }
                });
                mForget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final ProgressDialog progressDialog = new ProgressDialog(Launcher.this,
                                R.style.AppTheme_Dark_Dialog);
                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Sending Response Code...");
                        progressDialog.show();
                        String email_check = mEmail.getText().toString().trim();
                        mAuth.sendPasswordResetEmail(email_check).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Launcher.this, "Successfully send you response code to your email!", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                } else {
                                    Toast.makeText(Launcher.this, "Failed to Send", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            }
                        });
                    }
                });
                mBuilder.setView(mView);
                dialog = mBuilder.create();
                dialog.show();
            }
        });
        //******************************************************************************************
        //Main Menu Definition
        assert mHome != null;
        mHome.setButtonEnum(ButtonEnum.TextOutsideCircle);
        mHome.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        mHome.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        for (int j = 0; j < mHome.getPiecePlaceEnum().pieceNumber(); j++) {
            mHome.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    Intent post = new Intent(Launcher.this, PostShow.class);
                                    startActivity(post);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 1:
                                    Intent price = new Intent(Launcher.this, PriceShow.class);
                                    startActivity(price);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 2:
                                    FirebaseAuth.getInstance().signOut();
                                    mHome.setVisibility(View.INVISIBLE);
                                    mLogin.setVisibility(View.VISIBLE);
                                    mLogin.setAnimation(myAnim);
                                    break;
                                case 3:
                                    Intent fb = openFacebook(Launcher.this);
                                    startActivity(fb);
                                    break;
                                case 4:
                                    Uri uri = Uri.parse("http://instagram.com/_u/"+Instagram);
                                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                    likeIng.setPackage("com.instagram.android");
                                    try {
                                        startActivity(likeIng);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+Instagram)));
                                    }
                                    break;
                                case 5:
                                    Uri whs = Uri.parse("smsto:" + WhatsApp);
                                    Intent i = new Intent(Intent.ACTION_SENDTO, whs);
                                    i.setPackage("com.whatsapp");
                                    startActivity(Intent.createChooser(i, ""));
                                    break;
                                case 6:
                                    Intent ph = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Phone, null));
                                    startActivity(ph);
                                    break;
                                case 7:
                                    Intent prof = new Intent(Launcher.this, AdminProfile.class);
                                    startActivity(prof);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 8:
                                    Intent invite = new Intent(Launcher.this, Invitation.class);
                                    startActivity(invite);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                            }
                        }
                    }));
        }
        //******************************************************************************************
        //Admin Home Button
        assert mAdmin != null;
        mAdmin.setButtonEnum(ButtonEnum.TextOutsideCircle);
        mAdmin.setPiecePlaceEnum(PiecePlaceEnum.DOT_9_1);
        mAdmin.setButtonPlaceEnum(ButtonPlaceEnum.SC_9_1);
        for (int j = 0; j < mAdmin.getPiecePlaceEnum().pieceNumber(); j++) {
            mAdmin.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilderAdmin()
                    .listener(new OnBMClickListener() {
                        @Override
                        public void onBoomButtonClick(int index) {
                            switch (index) {
                                case 0:
                                    Intent post = new Intent(Launcher.this, PostShow.class);
                                    startActivity(post);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 1:
                                    Intent price = new Intent(Launcher.this, PriceShow.class);
                                    startActivity(price);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 2:
                                    FirebaseAuth.getInstance().signOut();
                                    mAdmin.setVisibility(View.INVISIBLE);
                                    mLogin.setVisibility(View.VISIBLE);
                                    mLogin.setAnimation(myAnim);
                                    break;
                                case 3:
                                    Intent fb = openFacebook(Launcher.this);
                                    startActivity(fb);
                                    break;
                                case 4:
                                    Uri uri = Uri.parse("http://instagram.com/_u/"+Instagram);
                                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                                    likeIng.setPackage("com.instagram.android");
                                    try {
                                        startActivity(likeIng);
                                    } catch (ActivityNotFoundException e) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/"+Instagram)));
                                    }
                                    break;
                                case 5:
                                    Uri whs = Uri.parse("smsto:" + WhatsApp);
                                    Intent i = new Intent(Intent.ACTION_SENDTO, whs);
                                    i.setPackage("com.whatsapp");
                                    startActivity(Intent.createChooser(i, ""));
                                    break;
                                case 6:
                                    Intent ph = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", Phone, null));
                                    startActivity(ph);
                                    break;
                                case 7:
                                    Intent prof = new Intent(Launcher.this, AdminProfile.class);
                                    startActivity(prof);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                                case 8:
                                    Intent intent = new Intent(Launcher.this, Modify.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                            }
                        }
                    }));
        }
        //******************************************************************************************
        //ViewPager & Dots Definition
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.background1,
                R.layout.background2,
                R.layout.background3,
                R.layout.background4,
                R.layout.background5,
                R.layout.background6,
                R.layout.background7,
                R.layout.background8,
                R.layout.background9,
                R.layout.background10
        };

        // adding bottom dots
        addBottomDots(0);
        viewPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        Timer tm = new Timer();
        tm.scheduleAtFixedRate(new MyTimer(), 5000, 3000);
        //******************************************************************************************
        //******************************************************************************************/
    }

    //**********************************************************************************************
    //**********************************************************************************************
    //ViewPager Auto Change Listener
    public class MyTimer extends TimerTask {

        @Override
        public void run() {
            Launcher.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    switch (viewPager.getCurrentItem()) {
                        case 0:
                            viewPager.setCurrentItem(1);
                            break;
                        case 1:
                            viewPager.setCurrentItem(2);
                            break;
                        case 2:
                            viewPager.setCurrentItem(3);
                            break;
                        case 3:
                            viewPager.setCurrentItem(4);
                            break;
                        case 4:
                            viewPager.setCurrentItem(5);
                            break;
                        case 5:
                            viewPager.setCurrentItem(6);
                            break;
                        case 6:
                            viewPager.setCurrentItem(7);
                            break;
                        case 7:
                            viewPager.setCurrentItem(8);
                            break;
                        case 8:
                            viewPager.setCurrentItem(9);
                            break;
                        case 9:
                            viewPager.setCurrentItem(10);
                            break;
                        case 10:
                            viewPager.setCurrentItem(0);
                            break;
                        default:
                            viewPager.setCurrentItem(0);
                            break;
                    }
                }
            });

        }
    }

    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {
                viewPager.setCurrentItem(0);
            } else {

            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    //**********************************************************************************************
    //Bottom Dots Function
    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
    }

    //**********************************************************************************************
    //ViewPager Adapter Class
    public class ViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;


        public ViewPagerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    //**********************************************************************************************
    public void Login(String e, String p) {
        Log.d(TAG, "Login");

        final String email = e;
        final String password = p;
        final ProgressDialog progressDialog = new ProgressDialog(Launcher.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                            mAuth.signInWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        if (user.getEmail().equals("admin@hawila.com")) {
                                            mLogin.setVisibility(View.INVISIBLE);
                                            mAdmin.setVisibility(View.VISIBLE);
                                            mAdmin.setAnimation(myAnim);
                                            dialog.dismiss();
                                            progressDialog.dismiss();
                                        } else {
                                            if (user.isEmailVerified())
                                            {
                                                mLogin.setVisibility(View.INVISIBLE);
                                                mHome.setVisibility(View.VISIBLE);
                                                mHome.setAnimation(myAnim);
                                                dialog.dismiss();
                                                progressDialog.dismiss();
                                            }
                                            else
                                            {
                                                sendVerificationEmail();
                                                progressDialog.dismiss();
                                            }
                                        }
                                    } else {
                                        Toast.makeText(Launcher.this, "Unable to login user", Toast.LENGTH_LONG).show();
                                        progressDialog.dismiss();
                                    }
                                }
                            });
                        } else {
                            Toast.makeText(Launcher.this, "Please enter user email and password", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }
                    }
                }, 1500);
    }

    //**********************************************************************************************
    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Launcher.this,"We Send You Verification Code to Your Email!",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Launcher.this,"Error in Internet Connection!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    //**********************************************************************************************
    public static Intent openFacebook(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/"+Facebook));

        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/AbdoHawilaphotography"));
        }

    }
    //**********************************************************************************************
}
