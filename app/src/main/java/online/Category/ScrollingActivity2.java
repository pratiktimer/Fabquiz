package online.Category;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import github.nisrulz.screenshott.ScreenShott;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Internet.TestApplication;
import google.com.fabquiz.R;
import google.com.fabquiz.database.GamesCreatedByPhoneNumbers;
import google.com.fabquiz.database.GamesReceivedByPhoneNumbers;
import google.com.fabquiz.database.Helper;
import google.com.fabquiz.database.serv;
import google.com.fabquiz.multiplayers.GetMultiplayerResult;
import google.com.fabquiz.recyclerviewsearch.MainActivity;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
public class ScrollingActivity2 extends AppCompatActivity implements View.OnClickListener, ConnectionReceiver.ConnectionReceiverListener {
    int count = 0;
    DatabaseReference  games,games2;
    private String mCurrentid;
    int timerem=0;
    long p;
    FirebaseFirestore mFirestore;
    String username,profile;
    int max;
    int heart=4;
    FrameLayout frameLayout;
    boolean isConnected2=false;
    RelativeLayout rel;
    private Animation mAnim;
    List<Integer> generated;
    ProgressBar barTimer;
    Context context = this;
    ImageView pb,live0,live1,live2;
    MediaPlayer mp;
    public static Bitmap b1;
    public static Bitmap b2;
    public static Bitmap b4;
    public static Bitmap b5;
    public static Bitmap b6;
    int i = 0;
    ImageView image;
    Button b3;
    RadioGroup radio;
    public static File file,file1,file2,file3,file4,file5;
    long min;
    boolean clickhappen = false, clickhappen2 = false, clickhappen3 = false, clickhappen4 = false, clickhappen5 = false;
    int score = 0, highscore = 0;
    public static SharedPreferences sharedPrefs;
    CountDownTimer countDownTimer;
    FirebaseUser user;
    private TextView txtQuestion, total_questions, textTimer;
    private Button rbtnA, rbtnB, rbtnC, rbtnD;
    private int answeredQsNo = 0;
    FirebaseDatabase database;
    DatabaseReference  question1,question2,contref;
    String catname;
    DataSnapshot dataSnapshot2;
    private boolean pressed = false;
    Animation zoomin, zoomout;
    public static ArrayList<String> imageurl;
    private InterstitialAd interstial;
    private int randomIndex;
    @Override
    public void onResume() {
        super.onResume();
        TestApplication.getInstance().setConnectionListener(ScrollingActivity2.this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <=23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);
        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        mFirestore= FirebaseFirestore.getInstance();
        mCurrentid=FirebaseAuth.getInstance().getUid();
        database = FirebaseDatabase.getInstance();
        games = database.getReference("GamesCreatedByPhoneNumbers");
        games2 = database.getReference("GamesReceivedByPhoneNumbers");
        heart=4;
            interstial=new InterstitialAd(this);
        interstial.setAdUnitId("ca-app-pub-5932395848907967/7031066571");
        AdRequest request=new AdRequest.Builder()
               // .addTestDevice("8394008E53F3F5505656A39C59D55341")

                    .build();
            interstial.loadAd(request);


      //  }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                new LoadDataForActivity().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                ;
            }
        });

    }
    public void updateStatusBarColor(String color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }
    private class LoadDataForActivity extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
        @Override
        protected void onPreExecute() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateStatusBarColor("#211e1c");
                    checkConnection();
                }
            });


        }

        @Override
        protected void onPostExecute(Void result) {
            isConnected2=ConnectionReceiver.isConnected();
            if(isConnected2) {
                buttonprev();
            }

        }

    }
    public static File savePic(Bitmap bm){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File sdCardDirectory =  new File(Environment.getExternalStorageDirectory() + "/Foldername");

        if (!sdCardDirectory.exists()) {
            sdCardDirectory.mkdirs();
        }

        try {
            file = new File(sdCardDirectory, "o" + ".jpg");
            if(file.exists())
                file.delete();
            file.createNewFile();
            new FileOutputStream(ScrollingActivity2.file).write(bytes.toByteArray());
            Log.d("Fabsolute", "File Saved::--->" + file.getAbsolutePath());
            Log.d("Sabsolute", "File Saved::--->" + sdCardDirectory.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
    private void getcatured() {
        if(Helper.hasPermissions4(ScrollingActivity2.this)) {
            if (android.os.Build.VERSION.SDK_INT < 23) {

            } else {
                if (clickhappen) {
                    image.setImageResource(android.R.color.transparent);
                    image.setVisibility(ImageView.GONE);
                    if (i == 0) {
                        total_questions.setVisibility(TextView.INVISIBLE);
                        barTimer.setVisibility(ProgressBar.INVISIBLE);
                        textTimer.setVisibility(TextView.INVISIBLE);
                        try{
                            b1 = ScreenShott.getInstance().takeScreenShotOfView(rel);
                            file1 = savePic(b1);
                            b1 = decodeFile(file1);}
                        catch (Throwable e) {
                            // Several error may come out with file handling or DOM
                            e.printStackTrace();
                        }
                        imageurl.add(dataSnapshot2.child("IsImageQuestion").getValue(String.class));
                        barTimer.setVisibility(ProgressBar.VISIBLE);
                        textTimer.setVisibility(TextView.VISIBLE);
                        total_questions.setVisibility(TextView.VISIBLE);
                        i++;

                    } else if (i == 1) {
                        total_questions.setVisibility(TextView.INVISIBLE);
                        barTimer.setVisibility(ProgressBar.INVISIBLE);
                        textTimer.setVisibility(TextView.INVISIBLE);
                        try{
                            b2 = ScreenShott.getInstance().takeScreenShotOfView(rel);

                            file2 = savePic(b2);
                            b2 = decodeFile(file2);}catch (Throwable e) {
                            // Several error may come out with file handling or DOM
                            e.printStackTrace();
                        }
                        imageurl.add(dataSnapshot2.child("IsImageQuestion").getValue(String.class));
                        barTimer.setVisibility(ProgressBar.VISIBLE);
                        textTimer.setVisibility(TextView.VISIBLE);
                        total_questions.setVisibility(TextView.VISIBLE);
                        i++;
                    } else if (i == 2) {
                        total_questions.setVisibility(TextView.INVISIBLE);
                        barTimer.setVisibility(ProgressBar.INVISIBLE);
                        textTimer.setVisibility(TextView.INVISIBLE);
                        try{
                            b4 = ScreenShott.getInstance().takeScreenShotOfView(rel);

                            file3 = savePic(b4);
                            b4 = decodeFile(file3);}catch (Throwable e) {
                            // Several error may come out with file handling or DOM
                            e.printStackTrace();
                        }
                        imageurl.add(dataSnapshot2.child("IsImageQuestion").getValue(String.class));
                        barTimer.setVisibility(ProgressBar.VISIBLE);
                        textTimer.setVisibility(TextView.VISIBLE);
                        total_questions.setVisibility(TextView.VISIBLE);
                        i++;
                    } else if (i == 3) {
                        total_questions.setVisibility(TextView.INVISIBLE);
                        barTimer.setVisibility(ProgressBar.INVISIBLE);
                        textTimer.setVisibility(TextView.INVISIBLE);
                        try{
                            b5 = ScreenShott.getInstance().takeScreenShotOfView(rel);

                            file4 = savePic(b5);
                            b5 = decodeFile(file4);}catch (Throwable e) {
                            // Several error may come out with file handling or DOM
                            e.printStackTrace();
                        }
                        imageurl.add(dataSnapshot2.child("IsImageQuestion").getValue(String.class));
                        barTimer.setVisibility(ProgressBar.VISIBLE);
                        textTimer.setVisibility(TextView.VISIBLE);
                        total_questions.setVisibility(TextView.VISIBLE);
                        i++;
                    } else {
                        total_questions.setVisibility(TextView.INVISIBLE);
                        barTimer.setVisibility(ProgressBar.INVISIBLE);
                        try{
                            b6 = ScreenShott.getInstance().takeScreenShotOfView(rel);

                            file5 = savePic(b6);
                            b6 = decodeFile(file5);
                            deletefile5();}catch (Throwable e) {
                            // Several error may come out with file handling or DOM
                            e.printStackTrace();
                        }
                        imageurl.add(dataSnapshot2.child("IsImageQuestion").getValue(String.class));
                        barTimer.setVisibility(ProgressBar.VISIBLE);
                        textTimer.setVisibility(TextView.VISIBLE);
                        total_questions.setVisibility(TextView.VISIBLE);

                        i++;
                    }

                }

            }
        }
    }
    private void deletefile5() {

        if(file.exists())
            file.delete();
    }
    private Bitmap decodeFile(File f)  {
        Bitmap b = null;


        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BitmapFactory.decodeStream(fis, null, o);
        try {
            if(fis!=null)
                fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int scale = 1;
        if (o.outHeight > 500 || o.outWidth > 500) {
            scale = (int)Math.pow(2, (int) Math.ceil(Math.log(500 /
                    (double) Math.max(o.outHeight, o.outWidth)) / Math.log(0.5)));
        }

        //Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        try {
            fis = new FileInputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        b = BitmapFactory.decodeStream(fis, null, o2);
        try {
            if(fis!=null)
                fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return b;
    }
    private void mytry(final int questionId) {
        question2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot2 = dataSnapshot;
                setQuestionsView(questionId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void hideverything() {
        frameLayout.setVisibility(FrameLayout.INVISIBLE);
        total_questions.setVisibility(TextView.INVISIBLE);
        radio.setVisibility(RadioGroup.INVISIBLE);
        rbtnD.setVisibility(Button.INVISIBLE);
        rbtnC.setVisibility(Button.INVISIBLE);
        rbtnD.setVisibility(Button.INVISIBLE);
        rbtnA.setVisibility(Button.INVISIBLE);
        txtQuestion.setVisibility(TextView.INVISIBLE);
        b3.setVisibility(Button.INVISIBLE);
        //skip.setVisibility(TextView.INVISIBLE);
        barTimer.setVisibility(ProgressBar.INVISIBLE);
        textTimer.setVisibility(TextView.INVISIBLE);
    }
    private void startTimer(final int minuti) {
        pb.setVisibility(ImageView.VISIBLE);
        countDownTimer = new CountDownTimer((60 * minuti * 1000) / 3, 100) {

            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                min = leftTimeInMilliseconds;
                long seconds = leftTimeInMilliseconds / 1000 + 1;
                textTimer.setText(String.format("%02d", seconds % 60));
                timerem= Integer.parseInt(String.format("%02d",seconds%60));
                int progress = (int) (leftTimeInMilliseconds / 100);
                barTimer.setProgress(progress);


                Rect bounds = barTimer.getProgressDrawable().getBounds();
                if(timerem>15)
                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress));
                else if(timerem<15&&timerem>7)
                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));
                else if(timerem<7)
                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress3));
                barTimer.getProgressDrawable().setBounds(bounds);


                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                if (textTimer.getText().equals("01")) {
                    heart--;
                    hideheart();
                    barTimer.setVisibility(ImageView.INVISIBLE);
                    pb.setVisibility(ImageView.INVISIBLE);
                    textTimer.setText("Time's Up!!!!");
                    Rect bounds = barTimer.getProgressDrawable().getBounds();

                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));

                    barTimer.getProgressDrawable().setBounds(bounds);
                    disablebuttons();
                    late();

                } else {

                    textTimer.setText("20");
                    // get progress bar bounds.
                    Rect bounds = barTimer.getProgressDrawable().getBounds();

                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));

                    barTimer.getProgressDrawable().setBounds(bounds);
                }
            }
        }.start();

    }
    private void startTimer2(long milli) {
        countDownTimer = new CountDownTimer(milli, 100) {

            // 500 means, onTick function will be called at every 500 milliseconds

            @Override
            public void onTick(long leftTimeInMilliseconds) {
                min = leftTimeInMilliseconds;
                long seconds = leftTimeInMilliseconds / 1000 + 1;
                textTimer.setText(String.format("%02d", seconds % 60));
                int progress = (int) (leftTimeInMilliseconds / 100);
                barTimer.setProgress(progress);


                Rect bounds = barTimer.getProgressDrawable().getBounds();

                barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress));

                barTimer.getProgressDrawable().setBounds(bounds);


                // format the textview to show the easily readable format

            }

            @Override
            public void onFinish() {
                if (textTimer.getText().equals("01")) {
                    heart--;
                    hideheart();
                    textTimer.setText("Time's Up!!!!");
                    Rect bounds = barTimer.getProgressDrawable().getBounds();

                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));

                    barTimer.getProgressDrawable().setBounds(bounds);
                    disablebuttons();
                    late();

                } else {
                    textTimer.setText("20");
                    // get progress bar bounds.
                    Rect bounds = barTimer.getProgressDrawable().getBounds();

                    barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));

                    barTimer.getProgressDrawable().setBounds(bounds);
                }
            }
        }.start();

    }
    private void setQuestionsView(int questionId) {
        if (dataSnapshot2 != null) {
            removeBlinkingText(rbtnA);
            removeBlinkingText(rbtnB);
            removeBlinkingText(rbtnC);
            removeBlinkingText(rbtnD);
            if (countDownTimer != null) {
                countDownTimer.cancel();
            }
            image.setVisibility(ImageView.GONE);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if ((dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals("false")) | (dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals(""))|(dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals(null))) {

                        image.setImageResource(android.R.color.transparent);
                        startTimer(1);
                    } else {
                        Picasso.with(ScrollingActivity2.this).load(dataSnapshot2.child("IsImageQuestion").getValue(String.class)).transform(new CropCircleTransformation()).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
                            @Override
                            public void onSuccess() {
                                zoomin();
                                image.startAnimation(zoomout);
                                image.startAnimation(zoomin);
                                startTimer(1);
                            }

                            @Override
                            public void onError() {
                                Picasso.with(ScrollingActivity2.this).load(dataSnapshot2.child("IsImageQuestion").getValue(String.class)).transform(new CropCircleTransformation()).into(image);
                                zoomin();
                                image.startAnimation(zoomout);
                                image.startAnimation(zoomin);
                                startTimer(1);
                            }

                        });


                    }
                    ;

                }
            });



            rbtnA.setBackgroundDrawable(rbtnA.getResources().getDrawable(R.drawable.button3));
            rbtnB.setBackgroundDrawable(rbtnB.getResources().getDrawable(R.drawable.button3));
            rbtnC.setBackgroundDrawable(rbtnC.getResources().getDrawable(R.drawable.button3));
            rbtnD.setBackgroundDrawable(rbtnD.getResources().getDrawable(R.drawable.button3));

            if (dataSnapshot2.child("OptC").getValue(String.class).equals("")) {
                rbtnC.setBackgroundDrawable(rbtnC.getResources().getDrawable(R.drawable.button6));
                rbtnC.setVisibility(Button.GONE);

            }
            if (dataSnapshot2.child("OptD").getValue(String.class).equals("")) {
                rbtnD.setBackgroundDrawable(rbtnD.getResources().getDrawable(R.drawable.button6));
                rbtnD.setVisibility(Button.GONE);
            } else {

                rbtnC.setVisibility(Button.VISIBLE);
                rbtnD.setVisibility(Button.VISIBLE);
                rbtnA.setBackgroundDrawable(rbtnA.getResources().getDrawable(R.drawable.button3));
                rbtnB.setBackgroundDrawable(rbtnB.getResources().getDrawable(R.drawable.button3));
                rbtnC.setBackgroundDrawable(rbtnC.getResources().getDrawable(R.drawable.button3));
                rbtnD.setBackgroundDrawable(rbtnD.getResources().getDrawable(R.drawable.button3));

            }

            total_questions.setText(Integer.toString(answeredQsNo + 1) + "/5");

            animbutton(rbtnA, 1000);
            animbutton(rbtnB, 1200);
            animbutton(rbtnC, 1400);
            animbutton(rbtnD, 1600);

            txtQuestion.setText(dataSnapshot2.child("Question").getValue(String.class));
            rbtnA.setText(dataSnapshot2.child("OptA").getValue(String.class));
            rbtnB.setText(dataSnapshot2.child("OptB").getValue(String.class));
            rbtnC.setText(dataSnapshot2.child("OptC").getValue(String.class));
            rbtnD.setText(dataSnapshot2.child("OptD").getValue(String.class));

            RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(670);
            rotate.setRepeatCount(23);
            rotate.setInterpolator(new LinearInterpolator());
            pb.startAnimation(rotate);
            rotate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Animation bounce2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shake);
                    pb.startAnimation(bounce2);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            clickhappen = false;
            clickhappen2 = false;
            clickhappen3 = false;
            clickhappen4 = false;
            clickhappen5 = false;

        }
        else {
            if(heart>=0){buttonnext();}

        }

    }
    private void timer2() {
        if(heart>=0)
        buttonnext();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    @Override
    protected void onStart() {

        super.onStart();
    }
    @Override
    protected void onPause() {
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        soundtry2();
//        Intent conceptIntent=new Intent(ScrollingActivity2.this, GetCategory.class);
//        conceptIntent.putExtra("position", 1);
//        conceptIntent.putExtra("multiplayers",);
//        startActivity(conceptIntent);
//        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
//        finish();
    }
    private void buttonnext() {
       // Toast.makeText(ScrollingActivity2.this,""+heart,Toast.LENGTH_SHORT).show();
        if(heart==0){finishcontent();}
        else{
        boolean isConnected = ConnectionReceiver.isConnected();
        if (!isConnected) {
           // hideverything();
            no_internet_dialog();

        }
        if (count >= max-1) {
            //    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            generated.clear();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        count++;
        soundtry2();


        Random random = new Random();
        if (answeredQsNo < 5) {

            randomIndex = random.nextInt(max-1)+1;
            if (!generated.contains(randomIndex)) {
                generated.add(randomIndex);
            } else {
                while (generated.contains(randomIndex)) {
                    randomIndex = random.nextInt(max-1)+1;
                }
                generated.add(randomIndex);

            }
            question2 = question1.child(catname).child(String.valueOf(randomIndex));
            question2.keepSynced(true);
            mytry(randomIndex);


        } else {

            finishcontent();

        }}
    }
    private void enablebuttons() {
        barTimer.setVisibility(ProgressBar.VISIBLE);
        rbtnA.setEnabled(true);
        rbtnA.setClickable(true);
        rbtnB.setEnabled(true);
        rbtnB.setClickable(true);
        rbtnC.setEnabled(true);
        rbtnC.setClickable(true);
        rbtnD.setEnabled(true);
        rbtnD.setClickable(true);

    }
    private void disablebuttons() {
        rbtnA.setEnabled(false);
        rbtnA.setClickable(false);
        rbtnB.setEnabled(false);
        rbtnB.setClickable(false);
        rbtnC.setEnabled(false);
        rbtnC.setClickable(false);
        rbtnD.setEnabled(false);
        rbtnD.setClickable(false);

    }
    protected void animbutton(final Button B, int Duration) {
        Animation animation = new TranslateAnimation(500, 4, 0, 0);
        animation.setDuration(Duration);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                disablebuttons();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(B.equals(rbtnD)) {
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                    enablebuttons();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        B.startAnimation(animation);

    }
    protected void setBlinkingText(Button textView) {
        myanim(textView);
    }
    protected void removeBlinkingText(final Button textView) {
        myanim(textView);
    }
    public  void myanim(final Button textView){
        disablebuttons();
        mAnim = new AlphaAnimation(0.0f, 1.0f);
        mAnim.setDuration(600); // Time of the blink
        mAnim.setStartOffset(20);
        mAnim.setRepeatMode(Animation.REVERSE);
        mAnim.setRepeatCount(Animation.INFINITE);
        textView.startAnimation(mAnim);
        mAnim.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO set params of the view to required position
                textView.clearAnimation();
                enablebuttons();
            }
        });
    }
    public void late() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                getcatured();
                timer2();
            }
        }, 1800);
    }
    private void soundtry2() {
        try {
            if (mp.isPlaying()) {
                mp.stop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void soundtry() {
        try {
            if (mp.isPlaying()) {
                mp.stop();

            } else {
                mp.release();
                // if (n == 1) {
                mp = MediaPlayer.create(context, R.raw.correct);
                mp.setVolume(50,50);
                mp.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void soundtry3() {
        try {
            if (mp.isPlaying()) {
                mp.stop();

            } else {
                mp.release();

                mp = MediaPlayer.create(context, R.raw.wrong);
                mp.setVolume(50,50);
                mp.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            refresh();
        }
    }
    private void refresh() {

        boolean isConnected=ConnectionReceiver.isConnected();
        if (!isConnected) {

            no_internet_dialog();

        } else {
            Intent start = new Intent(ScrollingActivity2.this, ScrollingActivity2.class);

            start.putExtra("catname",catname);

            startActivity(start);

        }


    }
    @Override
    public void onClick(View view) {
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }

        if(clickhappen==true)
        {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            switch (view.getId()) {
//                case R.id.Skip:
//                    buttonnext();
//                    break;
                case R.id.button3:
                    clickhappen=true;
                    finishcontent();
                    break;
                case R.id.radio0:
                    rbtnA.setClickable(false);
                    clickhappen=true;
                    clickhappen2=true;
                    answeredQsNo++;
                    rbtnA(rbtnA);
                    //  mytry();
                    break;
                case R.id.radio1:  rbtnB.setClickable(false);
                    clickhappen=true;
                    clickhappen3=true;
                    answeredQsNo++;
                    rbtnA(rbtnB);
                    //    mytry();
                    break;
                case R.id.radio2:rbtnC.setClickable(false);
                    clickhappen=true;
                    clickhappen4=true;
                    answeredQsNo++;
                    rbtnA(rbtnC);
                    //  mytry();
                    break;
                case R.id.radio3:rbtnD.setClickable(false);
                    clickhappen=true;
                    clickhappen5=true;
                    answeredQsNo++;
                    rbtnA(rbtnD);
                    //   mytry();
                    break;
                case R.id.imageView2:
                    if(!pressed) {
                        view.startAnimation(zoomout);
                        pressed = !pressed;
                        zoomout();
                        if(countDownTimer!=null) {
                            countDownTimer.cancel();
                            startTimer2(min);
                        }
                        // setContentView(R.layout.activity_image);
                        //   startTimer2(min);

                    } else {
                        view.startAnimation(zoomin);
                        zoomin();
                        if(countDownTimer!=null) {
                            countDownTimer.cancel();
                            startTimer2(min);
                        }
                        // countDownTimer.cancel();

                        pressed = !pressed;
                    }
                    break;

            }
        }
    }
    private void zoomout() {

        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
                300,
                300);


        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        image.setLayoutParams(layoutParams);
//        rbtnA.setClickable(false);
//        rbtnB.setClickable(false);
//        rbtnC.setClickable(false);
//        rbtnD.setClickable(false);
//        //skip.setClickable(false);
//        b3.setClickable(false);
    }
    private void zoomin() {

        final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
        int pixels = (int) (100* scale + 0.5f);
        int marginend=(int)(28*scale+0.5f);
        RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
                pixels,
                pixels);
        layoutParams.setMargins(0,0,marginend,0);
        layoutParams.addRule(RelativeLayout.ABOVE,R.id.constraintLayout);

        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
        layoutParams.addRule(RelativeLayout.CENTER_VERTICAL,0);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,0);

        image.setLayoutParams(layoutParams);
        image.setLayoutParams(layoutParams);
//        rbtnA.setClickable(true);
//        rbtnB.setClickable(true);
//        rbtnC.setClickable(true);
//        rbtnD.setClickable(true);
//        //skip.setClickable(true);
//        b3.setClickable(true);


    }
    private void rbtnA(Button answer) {



        int t = 10;
try {
    if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(answer.getText())) {
        if (timerem > 17) {
            highscore = highscore + 20;
        } else {
            highscore = highscore + timerem;
        }
        score++;
        answer.setBackgroundDrawable(this.rbtnA.getResources().getDrawable(R.drawable.button4));
        answer.setTextColor(Color.WHITE);
        answer.setTypeface(null, Typeface.NORMAL);
        sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        boolean p = sharedPrefs.getBoolean("NameOfThingToSave", true);
        if (p)
            soundtry();

        setBlinkingText(answer);

        late();

    } else {
        heart--;
        hideheart();
        if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(rbtnA.getText())) {
            rbtnA.setBackgroundDrawable(rbtnA.getResources().getDrawable(R.drawable.button4));
            setBlinkingText(rbtnA);
        }
        if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(rbtnB.getText())) {
            rbtnB.setBackgroundDrawable(rbtnB.getResources().getDrawable(R.drawable.button4));
            setBlinkingText(rbtnB);
        }
        if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(rbtnC.getText())) {
            rbtnC.setBackgroundDrawable(rbtnC.getResources().getDrawable(R.drawable.button4));
            setBlinkingText(rbtnC);
        }
        if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(rbtnD.getText())) {
            rbtnD.setBackgroundDrawable(rbtnD.getResources().getDrawable(R.drawable.button4));
            setBlinkingText(rbtnD);
        }
        sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        boolean p = sharedPrefs.getBoolean("NameOfThingToSave", true);
        if (p)
            soundtry3();

        answer.setBackgroundDrawable(this.rbtnA.getResources().getDrawable(R.drawable.button5));
        late();
    }
    answer.setEnabled(false);
}catch (Exception e){}
    }
    private void hideheart() {
        if(heart==3){
            live2.setVisibility(ImageView.INVISIBLE);}
        if(heart==2){live1.setVisibility(ImageView.INVISIBLE);}
        if(heart==1){live0.setVisibility(ImageView.INVISIBLE);}
     //  if(heart<0){finishcontent();}
    }
    private void finishcontent() {
       gamesplayed();
        if(countDownTimer!=null) {
            countDownTimer.cancel();}
       // if(addscount==3){
          //  addscount=0;
            if (interstial.isLoaded()) {
                interstial.show();
                interstial.setAdListener(new AdListener() {
                    @Override
                    public void onAdLoaded() {
                        // Code to be executed when an ad finishes loading.
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // Code to be executed when an ad request fails.
                       reallyfinish();
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }

                    @Override
                    public void onAdClosed() {
                        // Code to be executed when when the interstitial ad is closed.
                      reallyfinish();
                    }
                });
            } else {

                reallyfinish();

            }
      //  }else
        //    reallyfinish();


    }
    private void gamesplayed() {
        sharedPrefs = getApplicationContext().getSharedPreferences("google.com.fabquiz", getApplicationContext().MODE_PRIVATE);
        int gamesplayed=sharedPrefs.getInt("gamesplayed", 0);
        if(gamesplayed<=140){
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("google.com.fabquiz",                 getApplicationContext().MODE_PRIVATE).edit();
        editor.putInt("gamesplayed", gamesplayed+1);
        editor.commit();}
    }
    public void reallyfinish(){
        soundtry2();
        if(GetCategory.position.equals("1")) {
            Intent conceptIntent = new Intent(ScrollingActivity2.this, OnlineResult.class);
            conceptIntent.putExtra("position", score);
            conceptIntent.putExtra("ansqno" +
                    "", answeredQsNo);
            conceptIntent.putExtra("highscore", highscore);
            conceptIntent.putExtra("catname", catname);
            Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_up, R.anim.shake).toBundle();
try {
    startActivity(conceptIntent, bundle);
    finishAffinity();
}catch (Exception e){}

        }
        else{
            multiplayer();
            sendnotification();
        }
    }
    private void multiplayer() {
        database = FirebaseDatabase.getInstance();

        // games.child(String.valueOf((1))).setValue(game);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        final String getEmailId = user.getPhoneNumber();
        if (getEmailId != null && (!getEmailId.equals(""))) {

            games.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    p =(dataSnapshot.getChildrenCount());
                    String p2= String.valueOf(dataSnapshot.getChildrenCount());
                    //  Toast.makeText(Login.this,""+p,Toast.LENGTH_SHORT).show();
                    for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
                        if (p2.equals(zoneSnapshot.child("gameid").getValue(String.class))) {

                            p2=p2+1;
                        }

                    }
                    games.orderByChild(user.getPhoneNumber()).getRef().runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            if (mutableData.getValue() == null) {
                                GamesCreatedByPhoneNumbers game=new GamesCreatedByPhoneNumbers("0", MainActivity.contact2.getFullname(),highscore,-2,MainActivity.contact2.getGetprofile());
                                GamesReceivedByPhoneNumbers game2=new GamesReceivedByPhoneNumbers("0",username,highscore,-2,profile,user.getPhoneNumber(),MainActivity.contact2.getPhonenumber(),catname);
                                games.child(getEmailId).child("0").setValue(game);
                                games2.child(MainActivity.contact2.getPhonenumber()).child("0").setValue(game2);                                //                               Games game=new Games("0",username,MainActivity.contact2.getFullname(),String.valueOf(highscore),"",getEmailId, MainActivity.contact2.getPhonenumber(),catname,profile,MainActivity.contact2.getGetprofile());

//                            mutableData.child(user.getPhoneNumber()).setValue(0);
                            } else {
                                String count = String.valueOf(mutableData.child(user.getPhoneNumber()).getChildrenCount()+1);
                                GamesCreatedByPhoneNumbers game=new GamesCreatedByPhoneNumbers(count,MainActivity.contact2.getFullname(),highscore,-2,MainActivity.contact2.getGetprofile());
                                GamesReceivedByPhoneNumbers game2=new GamesReceivedByPhoneNumbers(count,username,highscore,-2,profile,user.getPhoneNumber(),MainActivity.contact2.getPhonenumber(),catname);
                                games.child(getEmailId).child(count).setValue(game);
                                games2.child(MainActivity.contact2.getPhonenumber()).child(count).setValue(game2);
                                // mutableData.setValue(count + 1);
//                             Games game=new Games(count,username,MainActivity.contact2.getFullname(),String.valueOf(highscore),"",getEmailId, MainActivity.contact2.getPhonenumber(),catname,profile,MainActivity.contact2.getGetprofile());
//                                games.child(getEmailId).child(count).setValue(game);
                            }

                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                            // Analyse databaseError for any error during increment
                        }
                    });






                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
    private void sendnotification() {
        String msg=""+username+" challenges you in "+catname;
        Map<String,Object> notificationmsg=new HashMap<>();
        notificationmsg.put("message",msg);
        notificationmsg.put("from",mCurrentid);
        notificationmsg.put("to",MainActivity.contact2.getUserId());
        mFirestore.collection("Users/"+MainActivity.contact2.getUserId()+"/Notifications").add(notificationmsg).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                //  Toast.makeText(ScrollingActivity2.this,"Notification send"+MainActivity.contact2.getFullname(),Toast.LENGTH_SHORT).show();
            }
        });
        Intent gotoonlineresult=new Intent(ScrollingActivity2.this, GetMultiplayerResult.class);
        gotoonlineresult.putExtra("highscore","");
        startActivity(gotoonlineresult);
        finish();

    }
    private void checkConnection() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        stopService(new Intent(ScrollingActivity2.this, serv.class));
        database = FirebaseDatabase.getInstance();
        catname = getIntent().getExtras().get("catname").toString();

        mp = MediaPlayer.create(context, R.raw.correct);
        question1 = database.getReference("Questions");
        rel = (RelativeLayout) findViewById(R.id.constraintLayout);
        generated = new ArrayList<Integer>();
        imageurl = new ArrayList<String>();
        radio = (RadioGroup) findViewById(R.id.radioGroup1);
       live0=(ImageView) findViewById(R.id.live1);
        live1=(ImageView) findViewById(R.id.live2);
        live2=(ImageView) findViewById(R.id.live3);
        txtQuestion = (TextView) findViewById(R.id.tvQuestion);
        rbtnA = (Button) findViewById(R.id.radio0);
        rbtnB = (Button) findViewById(R.id.radio1);
        pb = (ImageView) findViewById(R.id.pb);
        rbtnC = (Button) findViewById(R.id.radio2);
        rbtnD = (Button) findViewById(R.id.radio3);
        total_questions = (TextView) findViewById(R.id.totalquestions);
       // skip = (TextView) findViewById(R.id.Skip);
        b3 = (Button) findViewById(R.id.button3);
        b3.setVisibility(Button.VISIBLE);
        barTimer = (ProgressBar) findViewById(R.id.barTimer);
        textTimer = (TextView) findViewById(R.id.textTimer);
        image = (ImageView) findViewById(R.id.imageView2);
        b3.setOnClickListener(this);
        rbtnA.setOnClickListener(this);
        rbtnB.setOnClickListener(this);
        rbtnC.setOnClickListener(this);
        rbtnD.setOnClickListener(this);
        //skip.setOnClickListener(this);
        image.setOnClickListener(this);
        zoomin = AnimationUtils.loadAnimation(ScrollingActivity2.this, R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(ScrollingActivity2.this, R.anim.zoom_out);

    }
    private void no_internet_dialog () {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(ScrollingActivity2.this).create();

            alertDialog.setTitle("Info");
            alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
            alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
//                    Intent goonlinecategories = new Intent(ScrollingActivity2.this, GetCategory.class);
//                    goonlinecategories.putExtra("position", 1);
//                    goonlinecategories.putExtra("multiplayers", 0);
//                    startActivity(goonlinecategories);
//                    finishAffinity();


                }
            });

            alertDialog.show();
        } catch (Exception e) {

        }
    }
    private void buttonprev () {
        contref = question1.child(catname).getRef();
        contref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                smfun((int) dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void smfun(int max2) {
        max=max2;
        buttonnext();
    }
}
