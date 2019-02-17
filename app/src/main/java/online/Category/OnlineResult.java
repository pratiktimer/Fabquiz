package online.Category;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import google.com.fabquiz.MainActivity;
import google.com.fabquiz.R;
import google.com.fabquiz.ScreenshotAnswer.CheckAnswer;


public class OnlineResult extends AppCompatActivity {
    TextView t1,t8,t9;
    Button b6,b7;
    private int mRequestedScore = 0;
    String position,total,highscore;
    private LeaderboardsClient mLeaderboardsClient;
    ImageButton imagebutton2;
    ImageView imageView;
    private String ansqno,catname;
    GoogleSignInAccount googleSignInAccount;
    Boolean change;
    private final AccomplishmentsOutbox mOutbox = new AccomplishmentsOutbox();
    private SharedPreferences sharedPrefs;

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
        sharedPrefs = getApplicationContext().getSharedPreferences("google.com.fabquiz0", getApplicationContext().MODE_PRIVATE);
        int gamesplayed=sharedPrefs.getInt("gamesplayed", 0);

        try{
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
//    public interface Callback {
//        // called when the user presses the okay button to submit a score
//        void onEnteredScore(int score);
//    }
//
//    private Callback mCallback = null;
//    public void setCallback(Callback callback) {
//        mCallback = callback;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <=23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);

        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_result);
        }catch (OutOfMemoryError error){}
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
        }
        AdView adview=(AdView) findViewById(R.id.adView);
        AdRequest request=new AdRequest.Builder()
                .build();
        if(request!=null)
        adview.loadAd(request);
        position=getIntent().getExtras().get("position").toString();
        ansqno=getIntent().getExtras().get("ansqno").toString();
        highscore=getIntent().getExtras().get("highscore").toString();


        catname=getIntent().getExtras().get("catname").toString();

        t8=(TextView) findViewById(R.id.textView8);
        t9=(TextView) findViewById(R.id.textView9);
        if (android.os.Build.VERSION.SDK_INT <23) {
            t9.setVisibility(TextView.GONE);
        }
        else if (ActivityCompat.checkSelfPermission(OnlineResult.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            t9.setVisibility(TextView.GONE);
        }
        else if (ActivityCompat.checkSelfPermission(OnlineResult.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            t9.setVisibility(TextView.GONE);
        }
        imageView=(ImageView) findViewById(R.id.profilepic);
        imagebutton2=(ImageButton) findViewById(R.id.imageButton2);
        b6=(Button) findViewById(R.id.button6);
        b7=(Button) findViewById(R.id.button7);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearbitmaps();
                Intent i =new Intent(OnlineResult.this,MainActivity.class);
                i.putExtra("position", 2);
                startActivity(i);
                finish();
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearbitmaps();
                Intent conceptIntent = new Intent(OnlineResult.this,
                        ScrollingActivity2.class);
                conceptIntent.putExtra("catname",catname);
                startActivity(conceptIntent);
                finish();
            }
        });
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_up, R.anim.left_out).toBundle();
                Intent goonlinecategories = new Intent(getApplicationContext(), GetCategory.class);
                goonlinecategories.putExtra("position", 1);
                goonlinecategories.putExtra("multiplayers", 0);
                try{
                    startActivity( goonlinecategories, bundle);
                    finish();}
                catch (Exception e){}
            }
        });
        t9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent tomy=new Intent(OnlineResult.this,CheckAnswer.class);
                tomy.putExtra("scroll1",2);
                startActivity(tomy);
            }
        });
        t1=(TextView)findViewById(R.id.textView1);





        if(highscore.equals("100")){
            t8.setVisibility(TextView.GONE);
            t1.setText("HIGH SCORE : "  +highscore);
            //imageView.setImageResource(R.drawable.cup);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);

        }
        else if(Integer.parseInt(highscore)>0&&Integer.parseInt(highscore)<30){
            t8.setText("Score :"+highscore);
            imageView.setImageResource(R.drawable.neutral);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);
            t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);
        }
        else if(Integer.parseInt(highscore)==0){

            t8.setText("Score :"+highscore);
            imageView.setImageResource(R.drawable.dissatisfied);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);
            t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);
        }
        else if(Integer.parseInt(highscore)>=30&&Integer.parseInt(highscore)<=50){
            t8.setText("Score :"+highscore);
            imageView.setImageResource(R.drawable.satisfied);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);
            t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);
        }
        else if(Integer.parseInt(highscore)>=51&&Integer.parseInt(highscore)<=70){
            t8.setText("Score :"+highscore);
            imageView.setImageResource(R.drawable.satisfied);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);
            t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);}
            else{
            t8.setText("Score :"+highscore);
            imageView.setImageResource(R.drawable.verysatisfied);
            Animation RightSwipe = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
            imageView.startAnimation(RightSwipe);
            t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);}

      //  t2.setText("outof:"+total.toString());
        onEnteredScore(Integer.parseInt(highscore));
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.slide_up, R.anim.left_out).toBundle();
        Intent goonlinecategories = new Intent(getApplicationContext(), GetCategory.class);
        goonlinecategories.putExtra("position", 1);
        goonlinecategories.putExtra("multiplayers", 0);
        try{
            startActivity( goonlinecategories, bundle);
        finish();}
        catch (Exception e){}

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

    private void achievementToast(String achievement) {
        // Only show toast if not signed in. If signed in, the standard Google Play
        // toasts will appear, so we don't need to show our own.
        if (!isSignedIn()) {
            Toast.makeText(this, getString(R.string.achievement) + ": " + achievement,
                    Toast.LENGTH_LONG).show();
        }
    }
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
}
