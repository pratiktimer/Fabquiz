package google.com.fabquiz.ScreenshotAnswer;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import google.com.fabquiz.MainActivity;
import google.com.fabquiz.R;
//import google.com.fabquiz0.ScrollingActivity;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import online.Category.ScrollingActivity2;

public class CheckAnswer extends AppCompatActivity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static Bitmap[] IMAGES;
    ImageButton imagebutton2;
    private  int dotscount;
    int i=0;
    private ImageView[] dots;
    LinearLayout sliderDotspanel;
    private ArrayList<Bitmap> ImagesArray = new ArrayList<Bitmap>();
    ImageView image;
    private boolean pressed=false;
    int val;
    Animation zoomin,zoomout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <= 23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);


        }
        else{
            setTheme(R.style.AppTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_answer);
        AdView adview2=(AdView) findViewById(R.id.adView2);
        AdRequest request=new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)


                .build();
        adview2.loadAd(request);
        val=getIntent().getIntExtra("scroll1",val);

        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#211e1c"));
        }

        init();
    }
    private void init() {
        imagebutton2=(ImageButton) findViewById(R.id.imageButton2);
        zoomin = AnimationUtils.loadAnimation(this, R.anim.zoom_in);
        zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        image=(ImageView) findViewById(R.id.imageView3);
        imagebutton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearbitmaps();
                Bundle bundle = ActivityOptions.makeCustomAnimation(CheckAnswer.this, R.anim.slide_up, R.anim.bounce).toBundle();
                Intent i =new Intent(CheckAnswer.this,MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("position", 2);
                ActivityCompat.startActivity(CheckAnswer.this, i, bundle);
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!pressed) {
                    view.startAnimation(zoomout);
                    pressed = !pressed;


                } else {
                    view.startAnimation(zoomin);
                    pressed = !pressed;

                }



            }

        });

        sliderDotspanel=(LinearLayout) findViewById(R.id.SliderDots);
        if(val==1) {
                     image.setVisibility(ImageView.GONE);
            for (int i = 0; i <IMAGES.length; i++)


                ImagesArray.add(IMAGES[i]);
        }
        else {
            IMAGES = new Bitmap[]{ScrollingActivity2.b1,ScrollingActivity2.b2,ScrollingActivity2.b4,ScrollingActivity2.b5,ScrollingActivity2.b6};
            try{
            for (int i = 0; i <ScrollingActivity2.imageurl.size(); i++)
                ImagesArray.add(IMAGES[i]);}
                catch (Exception e){}
        }


        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(CheckAnswer.this, ImagesArray));
        NUM_PAGES =IMAGES.length;
        if(val==2) {
            if (ScrollingActivity2.imageurl.size() > 0) {
                Picasso.with(CheckAnswer.this).load(ScrollingActivity2.imageurl.get(0)).transform(new CropCircleTransformation()).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(CheckAnswer.this).load(ScrollingActivity2.imageurl.get(0)).transform(new CropCircleTransformation()).into(image);

                    }
                });
            } else {

            }
        }

        dotscount=IMAGES.length;
        dots=new ImageView[dotscount];
        for( i=0;i<dotscount;i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, float positionOffset, int positionOffsetPixels) {

                if(val==2)
                    mytry(position);
            }

            @Override
            public void onPageSelected(final int position) {




                currentPage = position;
                for(i=0;i<dotscount;i++){

                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dots));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(final int state) {


            }
        });


    }
    private void mytry(final int position) {
        if((position<ScrollingActivity2.imageurl.size())) {
            if (!(ScrollingActivity2.imageurl.get(position).equals(null))) {
                Picasso.with(CheckAnswer.this).load(ScrollingActivity2.imageurl.get(position)).transform(new CropCircleTransformation()).networkPolicy(NetworkPolicy.OFFLINE).into(image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(CheckAnswer.this).load(ScrollingActivity2.imageurl.get(position)).transform(new CropCircleTransformation()).into(image);

                    }
                });
            }else{

            }
        }
        else{

        }
    }
    @Override
    public void onBackPressed() {
       clearbitmaps();

        Bundle bundle = ActivityOptions.makeCustomAnimation(CheckAnswer.this, R.anim.slide_up, R.anim.left_out).toBundle();
        Intent i =new Intent(CheckAnswer.this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("position", 2);
        ActivityCompat.startActivity(CheckAnswer.this, i, bundle);
        finish();
    }
    private void clearbitmaps() {
        if(val==2) {
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

}
