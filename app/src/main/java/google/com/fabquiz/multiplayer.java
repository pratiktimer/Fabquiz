/* Copyright (C) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package google.com.fabquiz;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.KeyEvent;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.android.gms.games.GamesCallbackStatusCodes;
import com.google.android.gms.games.GamesClient;
import com.google.android.gms.games.GamesClientStatusCodes;
import com.google.android.gms.games.InvitationsClient;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.RealTimeMultiplayerClient;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.InvitationCallback;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.OnRealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateCallback;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import github.nisrulz.screenshott.ScreenShott;
import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.ScreenshotAnswer.CheckAnswer;
import google.com.fabquiz.database.Helper;
import google.com.fabquiz.database.serv;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import online.Category.GetCategory;

import online.Category.ScrollingActivity2;

public class multiplayer extends Activity implements
        View.OnClickListener, ConnectionReceiver.ConnectionReceiverListener {
  TextView t1,t8,t9;
  int score1;
  Button b6,b7;
  int cheated=0;

  String position,highscore;
  private LeaderboardsClient mLeaderboardsClient;
  ImageButton imagebutton2;
  ImageView imageView;
  private String ansqno,catname;
  GoogleSignInAccount googleSignInAccount;

  private final AccomplishmentsOutbox mOutbox = new AccomplishmentsOutbox();
  private SharedPreferences sharedPrefs;
  int count = 0;
  int timerem=0;
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
  public static Bitmap d0;
  public static Bitmap d1;
  public static Bitmap d2;
  public static Bitmap d3;
  public static Bitmap d4;
  int i = 0;
  ImageView image;
  RadioGroup radio;
  public static File file,file1,file2,file3,file4,file5;
  long min;
  boolean clickhappen = false, clickhappen2 = false, clickhappen3 = false, clickhappen4 = false, clickhappen5 = false;
  int score = 0;

  CountDownTimer countDownTimer;
  FirebaseUser user;
  private TextView txtQuestion, total_questions, textTimer;
  private Button rbtnA, rbtnB, rbtnC, rbtnD;
  private int answeredQsNo = 0;
  FirebaseDatabase database;
  DatabaseReference question1,question2,contref;

  DataSnapshot dataSnapshot2;
  private boolean pressed = false;
  Animation zoomin, zoomout;
  public static ArrayList<String> imageurl;
  private InterstitialAd interstial;
  private int randomIndex;
  private int msecodrplayer;
  private int oppscore;

  private class AccomplishmentsOutbox {

    int mBoredSteps = 0;
    int mEasyModeScore = -1;


    boolean isEmpty() {
      return mBoredSteps == 0 && mEasyModeScore < 0;
    }

  }
  private boolean isSignedIn() {
    return GoogleSignIn.getLastSignedInAccount(this) != null;
  }
  /*
   * API INTEGRATION SECTION. This section contains the code that integrates
   * the game with the Google Play game services API.
   */

  final static String TAG = "Fab Quiz";

  // Request codes for the UIs that we show with startActivityForResult:
  final static int RC_SELECT_PLAYERS = 10000;
  final static int RC_INVITATION_INBOX = 10001;
  final static int RC_WAITING_ROOM = 10002;

  // Request code used to invoke sign in user interactions.
  private static final int RC_SIGN_IN = 9001;

  // Client used to sign in with Google APIs
  private GoogleSignInClient mGoogleSignInClient = null;

  // Client used to interact with the real time multiplayers system.
  private RealTimeMultiplayerClient mRealTimeMultiplayerClient = null;

  // Client used to interact with the Invitation system.
  private InvitationsClient mInvitationsClient = null;

  // Room ID where the currently active game is taking place; null if we're
  // not playing.
  String mRoomId = null;

  // Holds the configuration of the current room.
  RoomConfig mRoomConfig;

  // Are we playing in multiplayers mode?
  boolean mMultiplayer = false;

  // The participants in the currently active game
  ArrayList<Participant> mParticipants = null;

  // My participant ID in the currently active game
  String mMyId = null;

  // If non-null, this is the id of the invitation we received via the
  // invitation listener
  String mIncomingInvitationId = null;

  // Message buffer for sending messages
  byte[] mMsgBuf = new byte[3];

  @Override
  public void onCreate(Bundle savedInstanceState) {
    if (Build.VERSION.SDK_INT <23) {
      setTheme(R.style.Theme_AppCompat_NoActionBar);
    }
    else{
      setTheme(R.style.AppTheme);
    }
    super.onCreate(savedInstanceState);
    setContentView(R.layout.multiplayer);
    AdView adview=(AdView) findViewById(R.id.adView);


    // Create the client used to sign in.
    mGoogleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);

    // set up a click listener for everything we care about
    for (int id : CLICKABLES) {
      findViewById(id).setOnClickListener(this);
    }

    switchToMainScreen();
    checkPlaceholderIds();

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


          ;
        }
      });


    }

    @Override
    protected void onPostExecute(Void result) {
      isConnected2= ConnectionReceiver.isConnected();
      if(isConnected2) {
        buttonprev();
      }

    }

  }
  private void checkConnection() {
    frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
    stopService(new Intent(multiplayer.this, serv.class));
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
    // b3 = (Button) findViewById(R.id.button3);
//    b3.setVisibility(Button.VISIBLE);
    barTimer = (ProgressBar) findViewById(R.id.barTimer);
    textTimer = (TextView) findViewById(R.id.textTimer);
    image = (ImageView) findViewById(R.id.imageView2);
    //  b3.setOnClickListener(this);
    rbtnA.setOnClickListener(this);
    rbtnB.setOnClickListener(this);
    rbtnC.setOnClickListener(this);
    rbtnD.setOnClickListener(this);
    //skip.setOnClickListener(this);
    image.setOnClickListener(this);
    zoomin = AnimationUtils.loadAnimation(multiplayer.this, R.anim.zoom_in);
    zoomout = AnimationUtils.loadAnimation(multiplayer.this, R.anim.zoom_out);

  }
  private void no_internet_dialog () {
    try {
      AlertDialog alertDialog = new AlertDialog.Builder(multiplayer.this).create();

      alertDialog.setTitle("Info");
      alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
      alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
      alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          Intent goonlinecategories = new Intent(multiplayer.this, GetCategory.class);
          goonlinecategories.putExtra("position", 1);
          startActivity(goonlinecategories);
          finishAffinity();


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
  private void buttonnext() {
    // Toast.makeText(ScrollingActivity2.this,""+heart,Toast.LENGTH_SHORT).show();

    boolean isConnected = ConnectionReceiver.isConnected();
    if (!isConnected) {
      //  hideverything();
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
    // if (answeredQsNo < 5) {

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


    //  }
    //  else
    //  {
//          broadcastScore(true);
//          gamesplayed();
//          reallyfinish();
    //  }
    answeredQsNo++;
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
    if(Helper.hasPermissions4(multiplayer.this)) {
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
              d0= ScreenShott.getInstance().takeScreenShotOfView(rel);
              file1 = savePic(d0);
              d0= decodeFile(file1);}
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
              d1 = ScreenShott.getInstance().takeScreenShotOfView(rel);

              file2 = savePic(d1);
              d1 = decodeFile(file2);}catch (Throwable e) {
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
              d2 = ScreenShott.getInstance().takeScreenShotOfView(rel);

              file3 = savePic(d2);
              d2 = decodeFile(file3);}catch (Throwable e) {
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
              d3 = ScreenShott.getInstance().takeScreenShotOfView(rel);

              file4 = savePic(d3);
              d3 = decodeFile(file4);}catch (Throwable e) {
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
              d4 = ScreenShott.getInstance().takeScreenShotOfView(rel);

              file5 = savePic(d4);
              d4 = decodeFile(file5);
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
  private void clearbitmaps() {
    if (android.os.Build.VERSION.SDK_INT <23) {

    }
    else {
      if (ScrollingActivity2.b1 != null) {
        ScrollingActivity2.b1.recycle();
        ScrollingActivity2.b1 = null;
      }
      else if (ScrollingActivity2.b2 != null) {
        ScrollingActivity2.b2.recycle();
        ScrollingActivity2.b2 = null;
      }
      else if (ScrollingActivity2.b4 != null) {
        ScrollingActivity2.b4.recycle();
        ScrollingActivity2.b4 = null;
      }
      else if (ScrollingActivity2.b5 != null) {
        ScrollingActivity2.b5.recycle();
        ScrollingActivity2.b5 = null;
      }
      else if (ScrollingActivity2.b6 != null) {
        ScrollingActivity2.b6.recycle();
        ScrollingActivity2.b6 = null;
      }


    }
  }
  private void gamesplayed() {
    sharedPrefs = getApplicationContext().getSharedPreferences("google.com.fabquiz0", getApplicationContext().MODE_PRIVATE);
    int gamesplayed=sharedPrefs.getInt("gamesplayed", 0);
    if(gamesplayed<=140){
      SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences("google.com.fabquiz0",                 getApplicationContext().MODE_PRIVATE).edit();
      editor.putInt("gamesplayed", gamesplayed+1);
      editor.commit();}
  }
  public void reallyfinish(){
    switchToScreen(R.id.screen_result);
    AdView adview=(AdView) findViewById(R.id.adView);
    AdRequest request=new AdRequest.Builder()
            .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
            .build();
    adview.loadAd(request);
    t8=(TextView) findViewById(R.id.textView8);
    t9=(TextView) findViewById(R.id.textView9);
    imageView=(ImageView) findViewById(R.id.profilepic);
    imagebutton2=(ImageButton) findViewById(R.id.imageButton2);
    b6=(Button) findViewById(R.id.button6);
    b7=(Button) findViewById(R.id.button7);
    imagebutton2.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
       // clearbitmaps();
        Intent i =new Intent(multiplayer.this,MainActivity.class);
        i.putExtra("position", 2);
        startActivity(i);
        finish();
      }
    });
    b6.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        resetGameVars();
       startQuickGame();
      }
    });
    b7.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
//        clearbitmaps();
        Intent intent=new Intent(multiplayer.this,GetCategory.class);
        intent.putExtra("position", 1);
        intent.putExtra("multiplayers",2);
        startActivity(intent);
        finish();
      }
    });

    t1=(TextView)findViewById(R.id.textView1);
    highscore= String.valueOf(mScore);
    if(cheated==1){
      gameswon();
      t8.setText("Your Score : " + highscore + "   " + "Player 2 Score: " + oppscore);
      t1.setText("Player Left You Win :)");
      imageView.setImageResource(R.drawable.verysatisfied);
      Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
      imageView.startAnimation(RightSwipe);
    }
    else {
      if (mScore > oppscore) {
        gameswon();
        t8.setText("Your Score : " + highscore + "   " + "Opponent's Score: " + oppscore);
        t1.setText("You Won By :" + (mScore - oppscore));
        imageView.setImageResource(R.drawable.verysatisfied);
        Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        imageView.startAnimation(RightSwipe);
      } else if (mScore == oppscore) {
        t8.setText("Your Score : " + highscore + "   " + "Opponent's Score: " + oppscore);
        t1.setText("Match Tied!!!!");
        imageView.setImageResource(R.drawable.satisfied);
        Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        imageView.startAnimation(RightSwipe);

      } else {
        t8.setText("Your Score :" + highscore + "   " + "Opponent's Score:" + oppscore);
        t1.setText("You Lost By :" + (oppscore-mScore));

        imageView.setImageResource(R.drawable.dissatisfied);
        Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
        imageView.startAnimation(RightSwipe);
      }
    }

    onEnteredScore(Integer.parseInt(highscore));
  }

  private void gameswon() {
    if (!isSignedIn()) {
      // can't push to the cloud, try again later
      return;
    }
    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
            .increment(getString(R.string.achievement_real_fighter), 1);
    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
            .increment(getString(R.string.achievement_real_fighter_ii), 1);
    Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
            .increment(getString(R.string.achievement_real_fighter_iii), 1);

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
  // Check the sample to ensure all placeholder ids are are updated with real-world values.
  // This is strictly for the purpose of the samples; you don't need this in a production
  // application.
  private void setQuestionsView(int questionId) {
    if (dataSnapshot2 != null) {
      removeBlinkingText(rbtnA);
      removeBlinkingText(rbtnB);
      removeBlinkingText(rbtnC);
      removeBlinkingText(rbtnD);
//      if (countDownTimer != null) {
//        countDownTimer.cancel();
//      }
      image.setVisibility(ImageView.GONE);

      runOnUiThread(new Runnable() {
        @Override
        public void run() {

          if ((dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals("false")) | (dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals(""))|(dataSnapshot2.child("IsImageQuestion").getValue(String.class).equals(null))) {

            image.setImageResource(android.R.color.transparent);
            // startTimer(1);
          } else {
            Picasso.with(multiplayer.this).load(dataSnapshot2.child("IsImageQuestion").getValue(String.class)).transform(new CropCircleTransformation()).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
              @Override
              public void onSuccess() {
                zoomin();
                image.startAnimation(zoomout);
                image.startAnimation(zoomin);
                //  startTimer(1);
              }

              @Override
              public void onError() {
                Picasso.with(multiplayer.this).load(dataSnapshot2.child("IsImageQuestion").getValue(String.class)).transform(new CropCircleTransformation()).into(image);
                zoomin();
                image.startAnimation(zoomout);
                image.startAnimation(zoomin);
                // startTimer(1);
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

      //total_questions.setText(Integer.toString(answeredQsNo) + "/5");

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


  }
  private void zoomout() {

    RelativeLayout.LayoutParams layoutParams= new RelativeLayout.LayoutParams(
            300,
            300);


    layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
    layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

    image.setLayoutParams(layoutParams);
    rbtnA.setClickable(false);
    rbtnB.setClickable(false);
    rbtnC.setClickable(false);
    rbtnD.setClickable(false);
    //skip.setClickable(false);
    //
    //
    // b3.setClickable(false);
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
    rbtnA.setClickable(true);
    rbtnB.setClickable(true);
    rbtnC.setClickable(true);
    rbtnD.setClickable(true);
    //skip.setClickable(true);
    // b3.setClickable(true);


  }
  private void rbtnA(Button answer) {



    int t = 10;
    try {
      if (dataSnapshot2.child("CorrectAnswer").getValue(String.class).equals(answer.getText())) {
        if (timerem >7) {
          mScore = mScore + 20;
        } else {
          mScore = mScore + timerem;
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
        //  heart--;
        // hideheart();
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
    scoreOnePoint();
  }
  //  private void hideheart() {
//    if(heart==3){
//      live2.setVisibility(ImageView.INVISIBLE);}
//    if(heart==2){live1.setVisibility(ImageView.INVISIBLE);}
//    if(heart==1){live0.setVisibility(ImageView.INVISIBLE);}
//    //  if(heart<0){finishcontent();}
//  }
  public void late() {
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {

       buttonnext();
      }
    }, 1800);
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
      Intent start = new Intent(multiplayer.this, ScrollingActivity2.class);

      start.putExtra("catname",catname);

      startActivity(start);

    }


  }
  //  private void timer2() {
//    if(heart>=0)
//      buttonnext();
//  }
  private void checkPlaceholderIds() {
    StringBuilder problems = new StringBuilder();

    if (getPackageName().startsWith("com.google.")) {
      problems.append("- Package name start with com.google.*\n");
    }

    for (Integer id : new Integer[]{R.string.app_id}) {

      String value = getString(id);

      if (value.startsWith("YOUR_")) {
        // needs replacing
        problems.append("- Placeholders(YOUR_*) in ids.xml need updating\n");
        break;
      }
    }

    if (problems.length() > 0) {
      problems.insert(0, "The following problems were found:\n\n");

      problems.append("\nThese problems may prevent the app from working properly.");
      problems.append("\n\nSee the TODO window in Android Studio for more information");
      (new AlertDialog.Builder(this)).setMessage(problems.toString())
              .setNeutralButton(android.R.string.ok, null).create().show();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume()");

    // Since the state of the signed in user can change when the activity is not active
    // it is recommended to try and sign in silently from when the app resumes.
    signInSilently();
  }

  @Override
  protected void onPause() {
    super.onPause();

    // unregister our listeners.  They will be re-registered via onResume->signInSilently->onConnected.
    if (mInvitationsClient != null) {
      mInvitationsClient.unregisterInvitationCallback(mInvitationCallback);
    }
  }

  @Override
  public void onClick(View v) {
    if(clickhappen==true)
    {
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
              WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
              WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }
    else {
//    try{
      switch (v.getId()) {
        case R.id.imageButton2:
          Mainfun();
          break;
        case R.id.backButton:
          Mainfun();
          break;
        case R.id.radio0:
          rbtnA.setClickable(false);
          clickhappen=true;
          clickhappen2=true;
          //  answeredQsNo++;
          rbtnA(rbtnA);
          //  mytry();
          break;
        case R.id.radio1:  rbtnB.setClickable(false);
          clickhappen=true;
          clickhappen3=true;
          //  answeredQsNo++;
          rbtnA(rbtnB);
          //    mytry();
          break;
        case R.id.radio2:rbtnC.setClickable(false);
          clickhappen=true;
          clickhappen4=true;
          //   answeredQsNo++;
          rbtnA(rbtnC);
          //  mytry();
          break;
        case R.id.radio3:rbtnD.setClickable(false);
          clickhappen=true;
          clickhappen5=true;
          //  answeredQsNo++;
          rbtnA(rbtnD);
          //   mytry();
          break;
        case R.id.imageView2:
          if(!pressed) {
            v.startAnimation(zoomout);
            pressed = !pressed;
            zoomout();


          } else {
            v.startAnimation(zoomin);
            zoomin();


            pressed = !pressed;
          }
          break;



        case R.id.button_invite_players:
          switchToScreen(R.id.screen_wait);

          // show list of invitable players
          mRealTimeMultiplayerClient.getSelectOpponentsIntent(1, 1).addOnSuccessListener(
                  new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                      startActivityForResult(intent, RC_SELECT_PLAYERS);
                    }
                  }
          ).addOnFailureListener(createFailureListener("There was a problem selecting opponents."));
          break;
        case R.id.button_see_invitations:
          switchToScreen(R.id.screen_wait);

          // show list of pending invitations
          mInvitationsClient.getInvitationInboxIntent().addOnSuccessListener(
                  new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                      startActivityForResult(intent, RC_INVITATION_INBOX);
                    }
                  }
          ).addOnFailureListener(createFailureListener("There was a problem getting the inbox."));
          break;
        case R.id.button_accept_popup_invitation:
          // user wants to accept the invitation shown on the invitation popup
          // (the one we got through the OnInvitationReceivedListener).
          acceptInviteToRoom(mIncomingInvitationId);
          mIncomingInvitationId = null;
          break;
        case R.id.button_quick_game:
          // user wants to play against a random opponent right now
          startQuickGame();
          //
          break;
      }
//    }catch (Exception e){}
}
  }

  void startQuickGame() {
    // quick-start a game with 1 randomly selected opponent
    final int MIN_OPPONENTS = 1, MAX_OPPONENTS = 1;
    Bundle autoMatchCriteria = RoomConfig.createAutoMatchCriteria(MIN_OPPONENTS,
            MAX_OPPONENTS, 0);
    switchToScreen(R.id.screen_wait);
    keepScreenOn();
    resetGameVars();

    mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
            .setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
            .setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
            .setAutoMatchCriteria(autoMatchCriteria)
            .build();
    mRealTimeMultiplayerClient.create(mRoomConfig);
  }

  /**
   * Start a sign in activity.  To properly handle the result, call tryHandleSignInResult from
   * your Activity's onActivityResult function
   */
  public void startSignInIntent() {
    startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
  }

  /**
   * Try to sign in without displaying dialogs to the user.
   * <p>
   * If the user has already signed in previously, it will not show dialog.
   */
  public void signInSilently() {
    Log.d(TAG, "signInSilently()");

    mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
            new OnCompleteListener<GoogleSignInAccount>() {
              @Override
              public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                if (task.isSuccessful()) {
                  Log.d(TAG, "signInSilently(): success");
                  onConnected(task.getResult());
                } else {
                  Log.d(TAG, "signInSilently(): failure", task.getException());
                  onDisconnected();
                }
              }
            });
  }

  public void signOut() {
    Log.d(TAG, "signOut()");

    mGoogleSignInClient.signOut().addOnCompleteListener(this,
            new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                  Log.d(TAG, "signOut(): success");
                } else {
                  handleException(task.getException(), "signOut() failed!");
                }

                onDisconnected();
              }
            });
  }

  /**
   * Since a lot of the operations use tasks, we can use a common handler for whenever one fails.
   *
   * @param exception The exception to evaluate.  Will try to display a more descriptive reason for the exception.
   * @param details   Will display alongside the exception if you wish to provide more details for why the exception
   *                  happened
   */
  private void handleException(Exception exception, String details) {
    int status = 0;

    if (exception instanceof ApiException) {
      ApiException apiException = (ApiException) exception;
      status = apiException.getStatusCode();
    }

    String errorString = null;
    switch (status) {
      case GamesCallbackStatusCodes.OK:
        break;
      case GamesClientStatusCodes.MULTIPLAYER_ERROR_NOT_TRUSTED_TESTER:
        errorString = getString(R.string.status_multiplayer_error_not_trusted_tester);
        break;
      case GamesClientStatusCodes.MATCH_ERROR_ALREADY_REMATCHED:
        errorString = getString(R.string.match_error_already_rematched);
        break;
      case GamesClientStatusCodes.NETWORK_ERROR_OPERATION_FAILED:
        errorString = getString(R.string.network_error_operation_failed);
        break;
      case GamesClientStatusCodes.INTERNAL_ERROR:
        errorString = getString(R.string.internal_error);
        break;
      case GamesClientStatusCodes.MATCH_ERROR_INACTIVE_MATCH:
        errorString = getString(R.string.match_error_inactive_match);
        break;
      case GamesClientStatusCodes.MATCH_ERROR_LOCALLY_MODIFIED:
        errorString = getString(R.string.match_error_locally_modified);
        break;
      default:
        errorString = getString(R.string.unexpected_status, GamesClientStatusCodes.getStatusCodeString(status));
        break;
    }

    if (errorString == null) {
      return;
    }

    String message = getString(R.string.status_exception_error, details, status, exception);

    new AlertDialog.Builder(multiplayer.this)
            .setTitle("Error")
            .setMessage(message + "\n" + errorString)
            .setNeutralButton(android.R.string.ok, null)
            .show();
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent intent) {

    if (requestCode == RC_SIGN_IN) {

      Task<GoogleSignInAccount> task =
              GoogleSignIn.getSignedInAccountFromIntent(intent);

      try {
        GoogleSignInAccount account = task.getResult(ApiException.class);
        onConnected(account);
      } catch (ApiException apiException) {
        String message = apiException.getMessage();
        if (message == null || message.isEmpty()) {
          message = getString(R.string.signin_other_error);
        }

        onDisconnected();

        new AlertDialog.Builder(this)
                .setMessage(message)
                .setNeutralButton(android.R.string.ok, null)
                .show();
      }
    } else if (requestCode == RC_SELECT_PLAYERS) {
      // we got the result from the "select players" UI -- ready to create the room
      handleSelectPlayersResult(resultCode, intent);

    } else if (requestCode == RC_INVITATION_INBOX) {
      // we got the result from the "select invitation" UI (invitation inbox). We're
      // ready to accept the selected invitation:
      handleInvitationInboxResult(resultCode, intent);

    } else if (requestCode == RC_WAITING_ROOM) {
      // we got the result from the "waiting room" UI.
      if (resultCode == Activity.RESULT_OK) {

        // ready to start playing
        Log.d(TAG, "Starting game (waiting room returned OK).");

        startGame(true);
      } else if (resultCode == GamesActivityResultCodes.RESULT_LEFT_ROOM) {

        // player indicated that they want to leave the room
        leaveRoom();
      } else if (resultCode == Activity.RESULT_CANCELED) {
        // Dialog was cancelled (user pressed back key, for instance). In our game,
        // this means leaving the room too. In more elaborate games, this could mean
        // something else (like minimizing the waiting room UI).
        leaveRoom();
      }
    }
    super.onActivityResult(requestCode, resultCode, intent);
  }

  // Handle the result of the "Select players UI" we launched when the user clicked the
  // "Invite friends" button. We react by creating a room with those players.

  private void handleSelectPlayersResult(int response, Intent data) {
    if (response != Activity.RESULT_OK) {
      Log.w(TAG, "*** select players UI cancelled, " + response);
      switchToMainScreen();
      return;
    }

    Log.d(TAG, "Select players UI succeeded.");

    // get the invitee list
    final ArrayList<String> invitees = data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);
    Log.d(TAG, "Invitee count: " + invitees.size());

    // get the automatch criteria
    Bundle autoMatchCriteria = null;
    int minAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
    int maxAutoMatchPlayers = data.getIntExtra(Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
    if (minAutoMatchPlayers > 0 || maxAutoMatchPlayers > 0) {
      autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
              minAutoMatchPlayers, maxAutoMatchPlayers, 0);
      Log.d(TAG, "Automatch criteria: " + autoMatchCriteria);
    }

    // create the room
    Log.d(TAG, "Creating room...");
    switchToScreen(R.id.screen_wait);

    keepScreenOn();

    resetGameVars();
    mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
            .addPlayersToInvite(invitees)
            .setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
            .setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
            .setAutoMatchCriteria(autoMatchCriteria).build();
    mRealTimeMultiplayerClient.create(mRoomConfig);
    Log.d(TAG, "Room created, waiting for it to be ready...");
  }

  // Handle the result of the invitation inbox UI, where the player can pick an invitation
  // to accept. We react by accepting the selected invitation, if any.
  private void handleInvitationInboxResult(int response, Intent data) {
    if (response != Activity.RESULT_OK) {
      Log.w(TAG, "*** invitation inbox UI cancelled, " + response);
      switchToMainScreen();
      return;
    }

    Log.d(TAG, "Invitation inbox UI succeeded.");
    Invitation invitation = data.getExtras().getParcelable(Multiplayer.EXTRA_INVITATION);

    // accept invitation
    if (invitation != null) {
      acceptInviteToRoom(invitation.getInvitationId());
    }
  }

  // Accept the given invitation.
  void acceptInviteToRoom(String invitationId) {
    // accept the invitation
    Log.d(TAG, "Accepting invitation: " + invitationId);

    mRoomConfig = RoomConfig.builder(mRoomUpdateCallback)
            .setInvitationIdToAccept(invitationId)
            .setOnMessageReceivedListener(mOnRealTimeMessageReceivedListener)
            .setRoomStatusUpdateCallback(mRoomStatusUpdateCallback)
            .build();

    switchToScreen(R.id.screen_wait);
    keepScreenOn();
    resetGameVars();

    mRealTimeMultiplayerClient.join(mRoomConfig)
            .addOnSuccessListener(new OnSuccessListener<Void>() {
              @Override
              public void onSuccess(Void aVoid) {
                Log.d(TAG, "Room Joined Successfully!");
              }
            });
  }

  // Activity is going to the background. We have to leave the current room.
  @Override
  public void onStop() {
    Log.d(TAG, "**** got onStop");

    // if we're in a room, leave it.
    leaveRoom();

    // stop trying to keep the screen on
    stopKeepingScreenOn();

    switchToMainScreen();

    super.onStop();
  }

  // Handle back key to make sure we cleanly leave a game if we are in the middle of one
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent e) {
    if (keyCode == KeyEvent.KEYCODE_BACK && mCurScreen == R.id.screen_game) {
      cheated=1;
      try{
        broadcastScore(true);
      }
      catch (Exception e2){}
      finally {
        leaveRoom();
      }

      return true;
    }
    else if (keyCode == KeyEvent.KEYCODE_BACK && mCurScreen == R.id.screen_result) {
      resetGameVars();
      leaveRoom();
      return true;
    }
    else if (keyCode == KeyEvent.KEYCODE_BACK && mCurScreen == R.id.screen_main) {
      resetGameVars();
      leaveRoom();
     Mainfun();
      return true;
    }
    return super.onKeyDown(keyCode, e);
  }

  private void Mainfun() {
    final Intent i = new Intent(this, MainActivity.class);
    i.putExtra("position", 2);
    Bundle bundle = ActivityOptions.makeCustomAnimation(multiplayer.this, R.anim.fui_slide_in_right, R.anim.left_out).toBundle();
    startActivity( i, bundle);
    finish();
  }

  // Leave the room.
  void leaveRoom() {
    Log.d(TAG, "Leaving room.");
    mSecondsLeft = 0;
    stopKeepingScreenOn();
    if (mRoomId != null) {
      mRealTimeMultiplayerClient.leave(mRoomConfig, mRoomId)
              .addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                  mRoomId = null;
                  mRoomConfig = null;
                }
              });
      switchToScreen(R.id.screen_wait);
    } else {
      switchToMainScreen();
    }
  }

  // Show the waiting room UI to track the progress of other players as they enter the
  // room and get connected.
  void showWaitingRoom(Room room) {
    // minimum number of players required for our game
    // For simplicity, we require everyone to join the game before we start it
    // (this is signaled by Integer.MAX_VALUE).
    final int MIN_PLAYERS = Integer.MAX_VALUE;
    mRealTimeMultiplayerClient.getWaitingRoomIntent(room, MIN_PLAYERS)
            .addOnSuccessListener(new OnSuccessListener<Intent>() {
              @Override
              public void onSuccess(Intent intent) {
                // show waiting room UI
                startActivityForResult(intent, RC_WAITING_ROOM);
              }
            })
            .addOnFailureListener(createFailureListener("There was a problem getting the waiting room!"));
  }

  private InvitationCallback mInvitationCallback = new InvitationCallback() {
    // Called when we get an invitation to play a game. We react by showing that to the user.
    @Override
    public void onInvitationReceived(@NonNull Invitation invitation) {
      // We got an invitation to play a game! So, store it in
      // mIncomingInvitationId
      // and show the popup on the screen.
      mIncomingInvitationId = invitation.getInvitationId();
      ((TextView) findViewById(R.id.incoming_invitation_text)).setText(
              invitation.getInviter().getDisplayName() + " " +
                      getString(R.string.is_inviting_you));
      switchToScreen(mCurScreen); // This will show the invitation popup
    }

    @Override
    public void onInvitationRemoved(@NonNull String invitationId) {

      if (mIncomingInvitationId.equals(invitationId) && mIncomingInvitationId != null) {
        mIncomingInvitationId = null;
        switchToScreen(mCurScreen); // This will hide the invitation popup
      }
    }
  };



  private String mPlayerId;

  // The currently signed in account, used to check the account has changed outside of this activity when resuming.
  GoogleSignInAccount mSignedInAccount = null;

  private void onConnected(GoogleSignInAccount googleSignInAccount) {
    Log.d(TAG, "onConnected(): connected to Google APIs");
    if (mSignedInAccount != googleSignInAccount) {

      mSignedInAccount = googleSignInAccount;

      // update the clients
      mRealTimeMultiplayerClient = Games.getRealTimeMultiplayerClient(this, googleSignInAccount);
      mInvitationsClient = Games.getInvitationsClient(multiplayer.this, googleSignInAccount);

      // get the playerId from the PlayersClient
      PlayersClient playersClient = Games.getPlayersClient(this, googleSignInAccount);
      playersClient.getCurrentPlayer()
              .addOnSuccessListener(new OnSuccessListener<Player>() {
                @Override
                public void onSuccess(Player player) {
                  mPlayerId = player.getPlayerId();

                  switchToMainScreen();
                }
              })
              .addOnFailureListener(createFailureListener("There was a problem getting the player id!"));
    }

    // register listener so we are notified if we receive an invitation to play
    // while we are in the game
    mInvitationsClient.registerInvitationCallback(mInvitationCallback);

    // get the invitation from the connection hint
    // Retrieve the TurnBasedMatch from the connectionHint
    GamesClient gamesClient = Games.getGamesClient(multiplayer.this, googleSignInAccount);
    gamesClient.getActivationHint()
            .addOnSuccessListener(new OnSuccessListener<Bundle>() {
              @Override
              public void onSuccess(Bundle hint) {
                if (hint != null) {
                  Invitation invitation =
                          hint.getParcelable(Multiplayer.EXTRA_INVITATION);

                  if (invitation != null && invitation.getInvitationId() != null) {
                    // retrieve and cache the invitation ID
                    Log.d(TAG, "onConnected: connection hint has a room invite!");
                    acceptInviteToRoom(invitation.getInvitationId());
                  }
                }
              }
            })
            .addOnFailureListener(createFailureListener("There was a problem getting the activation hint!"));
  }

  private OnFailureListener createFailureListener(final String string) {
    return new OnFailureListener() {
      @Override
      public void onFailure(@NonNull Exception e) {
        handleException(e, string);
      }
    };
  }

  public void onDisconnected() {
    Log.d(TAG, "onDisconnected()");

    mRealTimeMultiplayerClient = null;
    mInvitationsClient = null;
    switchToMainScreen();
  }

  private RoomStatusUpdateCallback mRoomStatusUpdateCallback = new RoomStatusUpdateCallback() {
    // Called when we are connected to the room. We're not ready to play yet! (maybe not everybody
    // is connected yet).
    @Override
    public void onConnectedToRoom(Room room) {
      Log.d(TAG, "onConnectedToRoom.");

      //get participants and my ID:
      mParticipants = room.getParticipants();
      mMyId = room.getParticipantId(mPlayerId);

      // save room ID if its not initialized in onRoomCreated() so we can leave cleanly before the game starts.
      if (mRoomId == null) {
        mRoomId = room.getRoomId();
      }

      // print out the list of participants (for debug purposes)
      Log.d(TAG, "Room ID: " + mRoomId);
      Log.d(TAG, "My ID " + mMyId);
      Log.d(TAG, "<< CONNECTED TO ROOM>>");
    }

    // Called when we get disconnected from the room. We return to the main screen.
    @Override
    public void onDisconnectedFromRoom(Room room) {
      mSecondsLeft=0;
      mRoomId = null;
      mRoomConfig = null;
      switchToMainScreen2();
    }


    // We treat most of the room update callbacks in the same way: we update our list of
    // participants and update the display. In a real game we would also have to check if that
    // change requires some action like removing the corresponding player avatar from the screen,
    // etc.
    @Override
    public void onPeerDeclined(Room room, @NonNull List<String> arg1) {
      updateRoom(room);
    }

    @Override
    public void onPeerInvitedToRoom(Room room, @NonNull List<String> arg1) {
      updateRoom(room);
    }

    @Override
    public void onP2PDisconnected(@NonNull String participant) {
    }

    @Override
    public void onP2PConnected(@NonNull String participant) {
    }

    @Override
    public void onPeerJoined(Room room, @NonNull List<String> arg1) {
      updateRoom(room);
    }

    @Override
    public void onPeerLeft(Room room, @NonNull List<String> peersWhoLeft) {
      updateRoom(room);
    }

    @Override
    public void onRoomAutoMatching(Room room) {
      updateRoom(room);
    }

    @Override
    public void onRoomConnecting(Room room) {
      updateRoom(room);
    }

    @Override
    public void onPeersConnected(Room room, @NonNull List<String> peers) {
      updateRoom(room);
    }

    @Override
    public void onPeersDisconnected(Room room, @NonNull List<String> peers) {
      updateRoom(room);
    }
  };

  // Show error message about game being cancelled and return to main screen.
  void showGameError() {
    new AlertDialog.Builder(this)
            .setMessage(getString(R.string.game_problem))
            .setNeutralButton(android.R.string.ok, null).create();

   switchToMainScreen();
  }

  private RoomUpdateCallback mRoomUpdateCallback = new RoomUpdateCallback() {

    // Called when room has been created
    @Override
    public void onRoomCreated(int statusCode, Room room) {
      Log.d(TAG, "onRoomCreated(" + statusCode + ", " + room + ")");
      if (statusCode != GamesCallbackStatusCodes.OK) {
        Log.e(TAG, "*** Error: onRoomCreated, status " + statusCode);
        showGameError();
        return;
      }

      // save room ID so we can leave cleanly before the game starts.
      mRoomId = room.getRoomId();

      // show the waiting room UI
      showWaitingRoom(room);
    }

    // Called when room is fully connected.
    @Override
    public void onRoomConnected(int statusCode, Room room) {
      Log.d(TAG, "onRoomConnected(" + statusCode + ", " + room + ")");
      if (statusCode != GamesCallbackStatusCodes.OK) {
        Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
        showGameError();
        return;
      }
      updateRoom(room);
    }

    @Override
    public void onJoinedRoom(int statusCode, Room room) {
      Log.d(TAG, "onJoinedRoom(" + statusCode + ", " + room + ")");
      if (statusCode != GamesCallbackStatusCodes.OK) {
        Log.e(TAG, "*** Error: onRoomConnected, status " + statusCode);
        showGameError();
        return;
      }

      // show the waiting room UI
      showWaitingRoom(room);
    }

    // Called when we've successfully left the room (this happens a result of voluntarily leaving
    // via a call to leaveRoom(). If we get disconnected, we get onDisconnectedFromRoom()).
    @Override
    public void onLeftRoom(int statusCode, @NonNull String roomId) {
      // we have left the room; return to main screen.
      Log.d(TAG, "onLeftRoom, code " + statusCode);
      switchToMainScreen();
    }
  };

  void updateRoom(Room room) {
    if (room != null) {
      mParticipants = room.getParticipants();
    }
    if (mParticipants != null) {
      updatePeerScoresDisplay();
    }
  }

  /*
   * GAME LOGIC SECTION. Methods that implement the game's rules.
   */

  // Current state of the game:
  int mSecondsLeft = -1; // how long until the game ends (seconds)
  static int GAME_DURATION =50; // game duration, seconds.
  int mScore = 0; // user's current score
  // Reset game variables in preparation for a new game.
  void resetGameVars() {
    oppscore=0;
    score=0;
    answeredQsNo=0;
    mSecondsLeft =GAME_DURATION;
    mScore = 0;
    mParticipantScore.clear();
    mFinishedParticipants.clear();
      try{
          generated.clear();}catch (Exception e){}

  }

  // Start the gameplay phase of the game.
  void startGame(boolean multiplayer) {
    mMultiplayer = multiplayer;
    updateScoreDisplay();
    broadcastScore(false);
    switchToScreen(R.id.screen_game);
    interstial=new InterstitialAd(this);
    interstial.setAdUnitId("ca-app-pub-5932395848907967/7031066571");
    AdRequest request=new AdRequest.Builder()

            .build();
    interstial.loadAd(request);
    final Handler h = new Handler();
    h.postDelayed(new Runnable() {
      @Override
      public void run() {
        if (mSecondsLeft <= 0) {
          return;
        }
        gameTick();
        h.postDelayed(this, 1000);
      }
    }, 1000);

    runOnUiThread(new Runnable() {
      @Override
      public void run() {

        new LoadDataForActivity().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        ;
      }
    });
  }

  //  // Game tick -- update countdown, check if game ended.
  void gameTick() {

    if (mSecondsLeft > 1) {
      --mSecondsLeft;
    }
    tickit();
    if (mSecondsLeft <= 1) {


      broadcastScore(true);
      gamesplayed();
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
    }
  }
  void tickit(){
    textTimer.setText(String.valueOf(mSecondsLeft));
    timerem= mSecondsLeft;
    barTimer.setProgress(mSecondsLeft);
    Rect bounds = barTimer.getProgressDrawable().getBounds();
    if(timerem>=30)
      barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress));
    else if(timerem>=15&&timerem<=29)
      barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress2));
    else if(timerem<=14)
      barTimer.setProgressDrawable(getResources().getDrawable(R.drawable.circularprogress3));
    barTimer.getProgressDrawable().setBounds(bounds);}


  // indicates the player scored one point
  void scoreOnePoint() {
    if (mSecondsLeft <= 0) {
      return; // too late!
    }
    //++mScore;
    updateScoreDisplay();
    updatePeerScoresDisplay();

    // broadcast our new score to our peers
    broadcastScore(false);
  }

  /*
   * COMMUNICATIONS SECTION. Methods that implement the game's network
   * protocol.
   */

  // Score of other participants. We update this as we receive their scores
  // from the network.
  Map<String, Integer> mParticipantScore = new HashMap<>();
  Map<String, Integer> mParticipantcheat= new HashMap<>();

  // Participants who sent us their final score.
  Set<String> mFinishedParticipants = new HashSet<>();

  // Called when we receive a real-time message from the network.
  // Messages in our game are made up of 2 bytes: the first one is 'F' or 'U'
  // indicating
  // whether it's a final or interim score. The second byte is the score.
  // There is also the
  // 'S' message, which indicates that the game should start.
  OnRealTimeMessageReceivedListener mOnRealTimeMessageReceivedListener = new OnRealTimeMessageReceivedListener() {
    @Override
    public void onRealTimeMessageReceived(@NonNull RealTimeMessage realTimeMessage) {
      byte[] buf = realTimeMessage.getMessageData();
      String sender = realTimeMessage.getSenderParticipantId();

      Log.d(TAG, "Message received: " + (char) buf[0] + "/" + (int) buf[1]);

      if (buf[0] == 'F' || buf[0] == 'U') {
        // score update.
        int existingScore = mParticipantScore.containsKey(sender) ?
                mParticipantScore.get(sender) : 0;
        int thisScore = (int) buf[1];

        cheated=(int) buf[2];
        mParticipantcheat.put(sender,cheated);
        if (thisScore > existingScore) {
          // this check is necessary because packets may arrive out of
          // order, so we
          // should only ever consider the highest score we received, as
          // we know in our
          // game there is no way to lose points. If there was a way to
          // lose points,
          // we'd have to add a "serial number" to the packet.
          mParticipantScore.put(sender, thisScore);

        }

        // update the scores on the screen
        updatePeerScoresDisplay();

        // if it's a final score, mark this participant as having finished
        // the game
        if ((char) buf[0] == 'F') {
          mFinishedParticipants.add(realTimeMessage.getSenderParticipantId());
        }
      }
    }
  };

  // Broadcast my score to everybody else.
  void broadcastScore(boolean finalScore) {
    if (!mMultiplayer) {
      // playing single-player mode
      return;
    }

    // First byte in message indicates whether it's a final score or not
    mMsgBuf[0] = (byte) (finalScore ? 'F' : 'U');

    // Second byte is the score.
    mMsgBuf[1] = (byte) mScore;

    mMsgBuf[2]=(byte) cheated;

//

    // Send to every other participant.
    for (Participant p : mParticipants) {
      if (p.getParticipantId().equals(mMyId)) {
        continue;
      }
      if (p.getStatus() != Participant.STATUS_JOINED) {
        continue;
      }
      if (finalScore) {
        // final score notification must be sent via reliable message
        mRealTimeMultiplayerClient.sendReliableMessage(mMsgBuf,
                mRoomId, p.getParticipantId(), new RealTimeMultiplayerClient.ReliableMessageSentCallback() {
                  @Override
                  public void onRealTimeMessageSent(int statusCode, int tokenId, String recipientParticipantId) {
                    Log.d(TAG, "RealTime message sent");
                    Log.d(TAG, "  statusCode: " + statusCode);
                    Log.d(TAG, "  tokenId: " + tokenId);
                    Log.d(TAG, "  recipientParticipantId: " + recipientParticipantId);
                  }
                })
                .addOnSuccessListener(new OnSuccessListener<Integer>() {
                  @Override
                  public void onSuccess(Integer tokenId) {
                    Log.d(TAG, "Created a reliable message with tokenId: " + tokenId);
                  }
                });
      } else {
        // it's an interim score notification, so we can use unreliable
        mRealTimeMultiplayerClient.sendUnreliableMessage(mMsgBuf, mRoomId,
                p.getParticipantId());
      }
    }
  }

  /*
   * UI SECTION. Methods that implement the game's UI.
   */

  // This array lists everything that's clickable, so we can install click
  // event handlers.
  final static int[] CLICKABLES = {
          R.id.button_accept_popup_invitation, R.id.button_invite_players,
          R.id.button_quick_game, R.id.button_see_invitations,
          R.id.radio0,R.id.radio1,R.id.radio2,R.id.radio3, R.id.imageView2,R.id.backButton,R.id.imageButton2
  };


  // This array lists all the individual screens our game has.
  final static int[] SCREENS = {
          R.id.screen_game, R.id.screen_main,
          R.id.screen_wait,R.id.screen_result
  };
  int mCurScreen = -1;

  void switchToScreen(int screenId) {
    // make the requested screen visible; hide all others.
    for (int id : SCREENS) {
      findViewById(id).setVisibility(screenId == id ? View.VISIBLE : View.GONE);
    }
    mCurScreen = screenId;

    // should we show the invitation popup?
    boolean showInvPopup;
    if (mIncomingInvitationId == null) {
      // no invitation, so no popup
      showInvPopup = false;
    } else if (mMultiplayer) {
      // if in multiplayers, only show invitation on main screen
      showInvPopup = (mCurScreen == R.id.screen_main);
    } else {
      // single-player: show on main screen and gameplay screen
      showInvPopup = (mCurScreen == R.id.screen_main || mCurScreen == R.id.screen_game);
    }
    findViewById(R.id.invitation_popup).setVisibility(showInvPopup ? View.VISIBLE : View.GONE);
  }

  void switchToMainScreen() {

    if (mRealTimeMultiplayerClient != null) {

       switchToScreen(R.id.screen_main);}
    else {
     // switchToScreen(R.id.screen_sign_in);
    }
  }
  void switchToMainScreen2() {

    if (mRealTimeMultiplayerClient != null) {

     // Toast.makeText(getApplicationContext(),"Disconnected",+Toast.LENGTH_SHORT).show();
      gamesplayed();
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
      }
    else {
    //  switchToScreen(R.id.screen_sign_in);
    }
  }

  // updates the label that shows my score
  void updateScoreDisplay() {
    ((TextView) findViewById(R.id.my_score)).setText(formatScore(mScore));
  }

  // formats a score as a three-digit number
  String formatScore(int i) {
    if (i < 0) {
      i = 0;
    }
    String s = String.valueOf(i);
    return s.length() == 1 ? "" + s : s.length() == 2 ? "" + s : s;
  }

  // updates the screen with the scores from our peers
  void updatePeerScoresDisplay() {
    ((TextView) findViewById(R.id.my_score)).setText(
            getString(R.string.score_label, formatScore(mScore)));
    int[] arr = {
            R.id.score1
    };


    if (mRoomId != null) {
      for (Participant p : mParticipants) {
        String pid = p.getParticipantId();
        if (pid.equals(mMyId)) {
          continue;
        }
        if (p.getStatus() != Participant.STATUS_JOINED) {
          continue;
        }
        oppscore = mParticipantScore.containsKey(pid) ? mParticipantScore.get(pid) :0;
        cheated = mParticipantcheat.containsKey(pid) ? mParticipantcheat.get(pid) :2;
     //   Toast.makeText(getApplicationContext(),""+cheated,Toast.LENGTH_SHORT).show();
        ((TextView) findViewById(R.id.score1)).setText(formatScore(oppscore) + " - " +
                "Opp");

      }
    }

    for (; i < arr.length; ++i) {
      ((TextView) findViewById(arr[i])).setText("");
    }
  }


  void keepScreenOn() {
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }

  // Clears the flag that keeps the screen on.
  void stopKeepingScreenOn() {
    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
  }
  //  @Override
  public void onEnteredScore(int score) {
    // Compute final score (in easy mode, it's the requested score; in hard mode, it's half)
    int finalScore = score;

    //mWinFragment.setScore(finalScore);
    //mWinFragment.setExplanation(mHardMode ? getString(R.string.hard_mode_explanation) :
    //getString(R.string.easy_mode_explanation));

    // check for achievements
    checkForAchievements(score,finalScore);

    // update leaderboards
    updateLeaderboards(finalScore);

    // push those accomplishments to the cloud, if signed in
    pushAccomplishments();

    // switch to the exciting "you won" screen
    //switchToFragment(mWinFragment);

    //mEventsClient.increment(getString(R.string.event_number_chosen), 1);
  }

  private void pushAccomplishments() {
    if (!isSignedIn()) {
      // can't push to the cloud, try again later
      return;
    }
    mLeaderboardsClient = Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this));

    achievement();

    if (mOutbox.mEasyModeScore >= 0) {
      leaderboard();
    }

    mOutbox.mEasyModeScore = -1;

  }

  private void leaderboard() {
    if(catname.equals("English")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_english),
              mOutbox.mEasyModeScore);}
    else if(catname.equals("Hollywood")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_hollywood),
              mOutbox.mEasyModeScore);}
    else if(catname.equals("Bollywood")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_bollywood),
              mOutbox.mEasyModeScore);}
    else if(catname.equals("Computer Science")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_computer_science),
              mOutbox.mEasyModeScore);}
    else if(catname.equals("General Knowledge")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_general_knowledge),
              mOutbox.mEasyModeScore);}
    else if(catname.equals("Cricket")){
      mLeaderboardsClient.submitScore(getString(R.string.leaderboard_cricket),
              mOutbox.mEasyModeScore);
    }}

  private void achievement() {
    sharedPrefs = getApplicationContext().getSharedPreferences("google.com.fabquiz", getApplicationContext().MODE_PRIVATE);
    int gamesplayed=sharedPrefs.getInt("gamesplayed", 0);

//    int gameswon=sharedPrefs.getInt("gameswon", 0);

    try{
//      if(gameswon==3){}
//      else if(gameswon==5){}
//      else if(gameswon==10){}
      if(highscore.equals("100")){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_challenge));}
      Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
              .increment(getString(R.string.achievement_starter), 1);
      if(gamesplayed==5){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_starter_ii));}
      if(gamesplayed==10){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_starter_iii));}
      if(gamesplayed==20){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_fighter_i));}
      if(gamesplayed==40){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_fighter_ii));}
      if(gamesplayed==120){
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .unlock(getString(R.string.achievement_fighter_iii));}}
    catch (Exception e){}
  }

  /**
   * Update leaderboards with the user's score.
   *
   * @param finalScore The score the user got.
   */
  private void updateLeaderboards(int finalScore) {
    //if (mHardMode && mOutbox.mHardModeScore < finalScore) {
    //mOutbox.mHardModeScore = finalScore;
    //} else if (!mHardMode && mOutbox.mEasyModeScore < finalScore) {
    mOutbox.mEasyModeScore = finalScore;
    //}
  }
  /**
   * Check for achievements and unlock the appropriate ones.
   *
   * @param requestedScore the score the user requested.
   * @param finalScore     the score the user got.
   */
  private void checkForAchievements(int requestedScore, int finalScore) {
    // Check if each condition is met; if so, unlock the corresponding
    // achievement.
    achievement();
    mOutbox.mBoredSteps++;
  }


//  @Override
//  public void onBackPressed() {
//    super.onBackPressed();

//    clearbitmaps();
//  }
}
