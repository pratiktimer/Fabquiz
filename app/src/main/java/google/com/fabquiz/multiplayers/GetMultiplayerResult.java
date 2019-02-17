package google.com.fabquiz.multiplayers;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import google.com.fabquiz.R;


public class GetMultiplayerResult extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    public  static String catname;
    public static String position;
    TextView created,received;
    ViewPager mPager;
    private PagerViewAdapter mpagerviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT <= 23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);


        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiplayer_result2);
        position = getIntent().getExtras().get("highscore").toString();

        created = (TextView) findViewById(R.id.created);
        received = (TextView) findViewById(R.id.received);
        mPager = (ViewPager) findViewById(R.id.frame_layout);
        mpagerviewAdapter = new PagerViewAdapter(getSupportFragmentManager());
        mPager.setAdapter(mpagerviewAdapter);

        created.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        received.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(1);
            }
        });
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTabs(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
if(!position.isEmpty()){
    mPager.setCurrentItem(1);
}

    }


    @Override
    public void onBackPressed() {
        String goto2= String.valueOf(2);
        Intent i = new Intent(GetMultiplayerResult.this, multiplayers.class);
        i.putExtra("message", goto2);
        startActivity(i);
        overridePendingTransition(R.anim.left_enter, R.anim.right_out);
        finish();
    //    overridePendingTransition(R.anim.left_out, R.anim.right_enter);
       // super.onBackPressed();
    }
    private void changeTabs(int position) {
        if(position==0){
            created.setTextColor(Color.parseColor("#ffffff"));
            created.setTextSize(20);
            received.setTextColor(Color.parseColor("#d3d3d3"));
            received.setTextSize(18);
        }
        else if(position==1) {
            received.setTextColor(Color.parseColor("#ffffff"));
            received.setTextSize(20);
            created.setTextColor(Color.parseColor("#d3d3d3"));
            created.setTextSize(18);
        }
    }


}
