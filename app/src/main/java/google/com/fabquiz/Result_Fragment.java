package google.com.fabquiz;
import android.Manifest;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;



import google.com.fabquiz.ScreenshotAnswer.CheckAnswer;
import online.Category.GetCategory;
public class Result_Fragment extends Fragment implements OnClickListener {
	private static View view;
	TextView t1,t8,t9;
	Button b6,b7;
	String position,total,highscore;
	ImageButton imagebutton2;
	ImageView imageView;
	private String ansqno;
	Boolean change;
   	private static FragmentManager fragmentManager;
  	@Override
	public void onStart() {
		super.onStart();


	}
	public Result_Fragment() {

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_result, container, false);
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
		}

		initViews();

        setListeners();
		return view;
	}
	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713

		t9=(TextView) view.findViewById(R.id.textView9);
		if (android.os.Build.VERSION.SDK_INT <23) {
			t9.setVisibility(TextView.GONE);
		}
		else if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			t9.setVisibility(TextView.GONE);
		}
		else if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			t9.setVisibility(TextView.GONE);
		}
		t8=(TextView) view.findViewById(R.id.textView8);
		imageView=(ImageView) view.findViewById(R.id.profilepic);
		imagebutton2=(ImageButton) view.findViewById(R.id.imageButton2);
		b6=(Button) view.findViewById(R.id.button6);
		b7=(Button) view.findViewById(R.id.button7);
		t1=(TextView)view.findViewById(R.id.textView1);

     //t9.setVisibility(TextView.GONE);

	}
	// Set Listeners
	private void setListeners() {
		t9.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {

				Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_up, R.anim.left_out).toBundle();


					Intent tomy = new Intent(getActivity(), CheckAnswer.class);
					tomy.putExtra("scroll1", 1);
			    	ActivityCompat.startActivity(getActivity(), tomy, bundle);


			}
		});
		imagebutton2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				clearbitmaps();
				Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_up, R.anim.left_out).toBundle();
				Intent i =new Intent(getActivity(),MainActivity.class);
				i.putExtra("position", 2);
				ActivityCompat.startActivity(getActivity(), i, bundle);
				getActivity().finish();
			}
		});
		b6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			clearbitmaps();



			}
		});
		b7.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			clearbitmaps();


			}
		});
		if(change==true) {

			if(highscore.equals("100")){

				t1.setText("HIGH SCORE : "  +highscore);
				imageView.animate().rotation(180).start();
			}
			else{
				t8.setText("Score :"+highscore);
				imageView.animate().rotation(360).start();
				t1.setText("New Record  : " + position.toString() + " Correct Out Of" + "  " + ansqno);
			}
		}

		else {
			if (Integer.parseInt(position.toString()) > 0) {
				t8.setText("Score :" + highscore);
				t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);
			} else {
				t8.setText("Score :" + highscore);
				imageView.animate().rotation(180).start();
				t1.setText("You Have Answered " + position.toString() + " Correct Out Of" + "  " + ansqno);
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {


		}

	}
	private void clearbitmaps() {
		if (android.os.Build.VERSION.SDK_INT <23) {

		}
		else {

		}
	}
}
