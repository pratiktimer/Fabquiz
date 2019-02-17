package google.com.fabquiz;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Internet.TestApplication;



import google.com.fabquiz.Nougat.NetworkSchedulerService;
import google.com.fabquiz.database.serv;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static java.lang.Thread.sleep;
public class SplahScreen extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {
    FirebaseDatabase database;
    ProgressBar pb2;
    int max2;
    Integer p2;
    public static SharedPreferences sharedPrefs;
    public static DatabaseReference ref, media2;
    private ImageView iv;
    DataSnapshot dataSnapshot2;
    boolean net=false;
    ImageView image;
    boolean isConnected;
    int randomIndex = 0;
    private IntentFilter intentFilter;
    private ConnectionReceiver receiver;
    private void init() {
        pb2 = (ProgressBar) findViewById(R.id.Progressbar2);
        pb2.setVisibility(ProgressBar.INVISIBLE);
        image = (ImageView) findViewById(R.id.image);
        image.setVisibility(ImageView.VISIBLE);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        updateStatusBarColor("#070E42");
        iv = (ImageView) findViewById(R.id.iv);
        Glide.with(this).load(R.mipmap.fqlogo4).into(iv);
        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.mytransiton);
        sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        boolean p = sharedPrefs.getBoolean("NameOfThingToSave2", true);
        if (p)
            startService(new Intent(SplahScreen.this, serv.class));
        iv.startAnimation(myanim);
    }
    private void checkConnection2(int max) {
        max2 = max;
        sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        p2 = sharedPrefs.getInt("max", 0);

        if (p2 == max2) {
            pb2.setVisibility(ProgressBar.INVISIBLE);
            func();

        }
            else {
            pb2.setVisibility(ProgressBar.VISIBLE);
            randomIndex = p2;
            buttonnext();


        }

    }
    private void setQuestionsView(final int questionId) {
        if (questionId == max2) {
            SharedPreferences.Editor editor = getSharedPreferences("google.com.fabquiz0", getApplicationContext().MODE_PRIVATE).edit();
            editor.putInt("max", max2);
            editor.commit();
            pb2.setVisibility(ProgressBar.INVISIBLE);
            func();
        }
        else {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ((dataSnapshot2 == null)) {
                            buttonnext();
                        } else {
                            Picasso.with(SplahScreen.this).load(dataSnapshot2.child("link").getValue(String.class)).transform(new CropCircleTransformation()).into(image, new Callback() {
                                @Override
                                public void onSuccess() {

                                    buttonnext();
                                }

                                @Override
                                public void onError() {

                                    buttonnext();
                                }

                            });
                        }


                    }
                });
                // randomIndex++;
                SharedPreferences.Editor editor = getSharedPreferences("google.com.fabquiz0", getApplicationContext().MODE_PRIVATE).edit();
                editor.putInt("max", questionId);
                editor.commit();

            }

    }

    private void mytry(final int questionId) {
        media2.addListenerForSingleValueEvent(new ValueEventListener() {
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

    private void buttonnext() {

        randomIndex++;
        ref = database.getReference();
        ref.keepSynced(true);

        media2 = ref.child("Media").child("Category").child(String.valueOf(randomIndex));
        media2.keepSynced(true);
        mytry(randomIndex);

    }

    private void refresh() {
        isConnected=ConnectionReceiver.isConnected2(getApplicationContext());
        String message = isConnected ? "Good! Connected to Internet" : "Sorry! Not connected to internet";
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        if (isConnected) {
            Intent splah = new Intent(getApplicationContext(), SplahScreen.class);
            startActivity(splah);
        }
        else
        {
            no_internet_dialog();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT <= 23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);
        } else {
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_splash);
        }catch (OutOfMemoryError e){

        }


        checkConnection();
        if (android.os.Build.VERSION.SDK_INT > 23) {
             scheduleJob();
        }

    }

    private void checkConnection() {

        init();
        isConnected = ConnectionReceiver.isConnected2(getApplicationContext());
        if (!isConnected) {
                    no_internet_dialog();
        } else {
            func();
            //buttonprev();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000)
                .setOverrideDeadline(2000)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();
        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

    private void no_internet_dialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.CustomDialogTheme);
        alertDialogBuilder

                .setMessage("Internet not available, Cross check your internet connectivity and try again")
                .setCancelable(false)
                .setTitle("Info")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(" Refresh ", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        refresh();
                    }
                });

               AlertDialog alert = alertDialogBuilder.create();
        try{
            alert.show();}catch (Exception e){}
        Button buttonbackground = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground.setBackgroundResource(R.drawable.button6);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_down,R.anim.stay);
        stopService(new Intent(SplahScreen.this, serv.class));
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        stopService(new Intent(SplahScreen.this, serv.class));
        super.onPause();

    }
    public void updateStatusBarColor(String color) {
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
        }

    }
    @Override
    public void onResume() {

        super.onResume();
        if (Build.VERSION.SDK_INT <= 23) {
            TestApplication.getInstance().setConnectionListener(SplahScreen.this);
        }

    }
    private void buttonprev() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Media").child("Category");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkConnection2((int) dataSnapshot.getChildrenCount());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);


    }
    private void func() {
        final Intent i = new Intent(this, MainActivity.class);
        Thread timer = new Thread() {
            public void run() {
                try {

                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    i.putExtra("position", 2);
                    Bundle bundle = ActivityOptions.makeCustomAnimation(SplahScreen.this, R.anim.fui_slide_in_right, R.anim.left_out).toBundle();
                    if(getApplicationContext()!=null) {
                        try {
                            startActivity(i, bundle);
                            finish();
                        }catch (Exception e){}
                    }
                }
            }
        };
        timer.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (android.os.Build.VERSION.SDK_INT > 23) {
            stopService(new Intent(this, NetworkSchedulerService.class));
       }

    }
    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.slide_up,R.anim.stay);
        // Start service and provide it a way to communicate with this class.
       if (android.os.Build.VERSION.SDK_INT > 23) {
            Intent startServiceIntent = new Intent(this, NetworkSchedulerService.class);
            startService(startServiceIntent);
       }
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
    }

    @Override
    public void onClick(View v) {

    }


}




