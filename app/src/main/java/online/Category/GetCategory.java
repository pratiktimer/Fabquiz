package online.Category;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import google.com.fabquiz.MainActivity;
import google.com.fabquiz.R;
import online.Category.subjects.get_subjects;


public class GetCategory extends AppCompatActivity {
    private static FragmentManager fragmentManager;
    public  static  String catname;
    public static String position,multiplayer;
    public static SharedPreferences sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (android.os.Build.VERSION.SDK_INT <=23) {
            setTheme(R.style.Theme_AppCompat_NoActionBar);
        }
        else{
            setTheme(R.style.AppFullScreenTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_category);
        position = getIntent().getExtras().get("position").toString();
        multiplayer = getIntent().getExtras().get("multiplayers").toString();
        fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.frame_layout, get_Category.newIstance(),
                            Utils.getcategory_Fragment).commit();
        }
    }
    public void  getsubname(){
        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.right_enter, R.anim.left_out)
                .replace(R.id.frame_layout,
                        get_subjects.newIstance(),
                        Utils.get_subjects).commit();
    }
    public void replacecategory() {

        fragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.left_enter, R.anim.right_out)
                .replace(R.id.frame_layout, get_Category.newIstance(),
                        Utils.getcategory_Fragment).commit();

    }
    @Override
    public void onBackPressed() {

        // Find the tag of signup and forgot password fragment
        Fragment SignUp_Fragment = fragmentManager
                .findFragmentByTag(Utils.get_subjects);
        Fragment getcat = fragmentManager
                .findFragmentByTag(Utils.getcategory_Fragment);




        if (SignUp_Fragment != null)
            replacecategory();
        else if(getcat!=null)
        {
            if(multiplayer.equals("2")){
                Bundle bundle = ActivityOptions.makeCustomAnimation(GetCategory.this, R.anim.left_enter, R.anim.right_out).toBundle();
                Intent i = new Intent(GetCategory.this, MainActivity.class);
                i.putExtra("position", 2);
                ActivityCompat.startActivity(GetCategory.this, i, bundle);
            }
            else {
                sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
                boolean p2 = sharedPrefs.getBoolean("NameOfThingToSave3", true);
                if (p2) {
                    Bundle bundle = ActivityOptions.makeCustomAnimation(GetCategory.this, R.anim.left_enter, R.anim.right_out).toBundle();
                    Intent i = new Intent(GetCategory.this, MainActivity.class);
                    i.putExtra("position", 3);
                    ActivityCompat.startActivity(GetCategory.this, i, bundle);


                } else {
                    Bundle bundle = ActivityOptions.makeCustomAnimation(GetCategory.this, R.anim.left_enter, R.anim.right_out).toBundle();
                    Intent i = new Intent(GetCategory.this, MainActivity.class);
                    i.putExtra("position", 2);
                    ActivityCompat.startActivity(GetCategory.this, i, bundle);

                }
            }


        }

        else
            super.onBackPressed();
    }

}
