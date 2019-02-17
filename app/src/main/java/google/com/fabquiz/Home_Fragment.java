package google.com.fabquiz;
import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.Random;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Login.Login;
import google.com.fabquiz.multiplayers.multiplayers;
import online.Category.GetCategory;
import static android.content.Context.MODE_PRIVATE;
public class Home_Fragment extends Fragment implements OnClickListener {
	DatabaseReference users;
	private static View view;
	public boolean once=true;
	FirebaseAuth mAuth;
	public  static  boolean comingfromsettings;
	public static int c=0;
    ImageView multi;
    ImageView play;
    View b8;
	LinearLayout editprofilelinear;
	private static FragmentManager fragmentManager;
	FirebaseDatabase database;
    ImageButton settings,shareit,email,message,whatsapp,canceler;
	public static SharedPreferences sharedPrefs;
	private String getprofile,username;
	int req=90;
	static PermissionManager permission;
	public static String PACKAGE_NAME;
	private LinearLayout share;
	private View mSignInBarView;
	private View mSignOutBarView;
	private View mShowAchievementsButton;
	private View mShowLeaderboardsButton;
	private Listener mListener = null;
	//private boolean mShowSignInButton =true;

	interface Listener {
		// called when the user presses the `Easy` or `Okay` button; will pass in which via `hardMode`
		void onStartGameRequested(boolean hardMode);

		// called when the user presses the `Show Achievements` button
		void onShowAchievementsRequested();

		// called when the user presses the `Show Leaderboards` button
		void onShowLeaderboardsRequested();

		// called when the user presses the `Sign In` button
		void onSignInButtonClicked();

		// called when the user presses the `Sign Out` button
		void onSignOutButtonClicked();
	}
	public void setListener(Listener listener) {
		mListener = listener;
	}


	@Override
	public void onStart() {
		super.onStart();


	}
	public Home_Fragment() {
		database = FirebaseDatabase.getInstance();
		users = database.getReference("Users");

	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState) {
		try {
			view = inflater.inflate(R.layout.home, container, false);
		}catch (OutOfMemoryError e){System.out.print("error"+e);}
		final int[] clickableIds = new int[]{
				R.id.share,
				R.id.email,
				R.id.message,
				R.id.whatsapp,
				R.id.cancel,
				R.id.button,
				R.id.button1,
				R.id.button2,
				R.id.sign_in_button,
				R.id.show_achievements_button,
				R.id.show_leaderboards_button,
				R.id.button8
		};


		for (int clickableId : clickableIds) {
			view.findViewById(clickableId).setOnClickListener(this);
		}
		checkpermission();
		initViews();
		return view;
	}

	private void checkpermission() {
		permission=new PermissionManager() {

			@Override
			public void ifCancelledAndCanRequest(final Activity activity) {

				MainActivity.showDialogOK(getActivity(),"Camera Permission is Required To Take Screenshot  of Your Answers and Storage to " +
								"Store them,Please grant permission for the same",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								switch (which) {
									case DialogInterface.BUTTON_POSITIVE:
										checkAndRequestPermissions(activity);
										break;
									case DialogInterface.BUTTON_NEGATIVE:

										break;
								}
							}
						});

			}



		};
		permission.checkAndRequestPermissions(getActivity());
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
		}

	}

	private void updateUI(final boolean mShowSignInButton) {

		mSignOutBarView.setVisibility(View.INVISIBLE);
		mSignInBarView.setVisibility(View.INVISIBLE);
		if(!mShowSignInButton){
			Animation fadeinmulti = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
			if(once)
			multi.startAnimation(fadeinmulti);
			else{
				multi.setVisibility(mShowSignInButton ? View.INVISIBLE : View.VISIBLE);
				mSignOutBarView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
				mSignInBarView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);
				mShowAchievementsButton.setEnabled(!mShowSignInButton);
				mShowLeaderboardsButton.setEnabled(!mShowSignInButton);
			}

			fadeinmulti.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {
					multi.setVisibility(mShowSignInButton ? View.INVISIBLE : View.VISIBLE);
				}

				@Override
				public void onAnimationEnd(Animation animation) {

					Animation fadeinmulti2 = AnimationUtils.loadAnimation(getActivity(), R.anim.left_enter);
					Animation fadeinmult3 = AnimationUtils.loadAnimation(getActivity(), R.anim.right_enter);
					Animation fadeinmult4 = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);

if(once) {
	mShowAchievementsButton.startAnimation(fadeinmulti2);
	mShowLeaderboardsButton.startAnimation(fadeinmult3);
	b8.startAnimation(fadeinmult4);
	once=false;
}
					b8.setVisibility(Button.VISIBLE);
					mSignOutBarView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
					mSignInBarView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);
					mShowAchievementsButton.setEnabled(!mShowSignInButton);
					mShowLeaderboardsButton.setEnabled(!mShowSignInButton);

				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});
		}
		else{
			multi.setVisibility(mShowSignInButton ? View.INVISIBLE : View.VISIBLE);
			mSignInBarView.setVisibility(!mShowSignInButton ? View.GONE : View.VISIBLE);
		}
	}
public void setShowSignInButton2(boolean mShowSignInButton) {
updateUI(!mShowSignInButton);
//	multi.setVisibility(mShowSignInButton ? View.VISIBLE : View.INVISIBLE);
//	mShowAchievementsButton.setEnabled(mShowSignInButton);
//	mShowLeaderboardsButton.setEnabled(mShowSignInButton);
//	mSignInBarView.setVisibility(mShowSignInButton ? View.GONE : View.VISIBLE);
//	mSignOutBarView.setVisibility(mShowSignInButton ? View.VISIBLE : View.GONE);



}

	private boolean isSignedIn() {
	return GoogleSignIn.getLastSignedInAccount(getActivity()) != null;
}

	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		editprofilelinear=(LinearLayout)view.findViewById(R.id.editprofielinear);
        share=(LinearLayout) view.findViewById(R.id.share);
        share.setVisibility(LinearLayout.INVISIBLE);
		email=(ImageButton) view.findViewById(R.id.email);
		message=(ImageButton) view.findViewById(R.id.message);
		whatsapp=(ImageButton) view.findViewById(R.id.whatsapp);
		canceler=(ImageButton) view.findViewById(R.id.cancel);
		multi=(ImageView) view.findViewById(R.id.multiplayer);
		multi.setOnClickListener(this);
		settings=(ImageButton) view.findViewById(R.id.button);
		shareit=(ImageButton) view.findViewById(R.id.button2);
		play=(ImageView) view.findViewById(R.id.button1);
		mSignInBarView = view.findViewById(R.id.sign_in_bar);
		mSignOutBarView = view.findViewById(R.id.sign_out_bar);
		b8=view.findViewById(R.id.button8);
		//b8.setVisibility(Button.VISIBLE);
		mShowAchievementsButton = view.findViewById(R.id.show_achievements_button);
		mShowLeaderboardsButton = view.findViewById(R.id.show_leaderboards_button);

		//mSignOutBarView = view.findViewById(R.id.sign_out_bar);

		//updateUI();
		setShowSignInButton2(isSignedIn());
		mAuth=FirebaseAuth.getInstance();
		getuserinfo();

		setListeners();
		Animation bounce2 = AnimationUtils.loadAnimation(getActivity(), R.anim.bounce2);
		play.startAnimation(bounce2);
		bounce2.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});

		Random Rand=new Random();
		int randindex=Rand.nextInt(3);
		try{
		if(randindex==0){
			Glide.with(getActivity()).load(R.mipmap.play).into(play);
			Glide.with(getActivity()).load(R.mipmap.multiplayer2).into(multi);

		}
		else if(randindex==1){
			Glide.with(getActivity()).load(R.mipmap.play3).into(play);
			Glide.with(getActivity()).load(R.mipmap.multiplayer).into(multi);}
		else {
			Glide.with(getActivity()).load(R.mipmap.play2).into(play);
			Glide.with(getActivity()).load(R.mipmap.multiplayer).into(multi);
		}}catch (OutOfMemoryError error){System.out.print("error"+error);}




	}

	// Set Listeners
	private void setListeners() {


		play.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						ImageView view = (ImageView) v;
						//overlay is black with transparency of 0x77 (119)
						view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
						view.invalidate();
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						ImageView view = (ImageView) v;
						//clear the overlay
						view.getDrawable().clearColorFilter();
						view.invalidate();
						break;
					}
				}

				return false;
			}
		});
		multi.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: {
						ImageView view = (ImageView) v;
						//overlay is black with transparency of 0x77 (119)
						view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
						view.invalidate();
						break;
					}
					case MotionEvent.ACTION_UP:
					case MotionEvent.ACTION_CANCEL: {
						ImageView view = (ImageView) v;
						//clear the overlay
						view.getDrawable().clearColorFilter();
						view.invalidate();
						break;
					}
				}

				return false;
			}
		});



	}
	private void checkConnection2(){

		boolean isConnected = ConnectionReceiver.isConnected();

		if(!isConnected) {
			no_internet_dialog();
		}
		else {
			if(mAuth.getCurrentUser()!=null) {
				comingfromsettings=true;
				//new Login().replaceuser_profile_fragment();
				Intent goeditprofile = new Intent(getActivity(), Login.class);
				startActivity(goeditprofile);

			}
		}



	}
	private void no_internet_dialog() {
		try {
			AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

			alertDialog.setTitle("Info");
			alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
			alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {

					//    showNetDisabledAlertToUser(ScrollingActivity2.this);
					//   finish();

				}
			});

			alertDialog.show();
		} catch (Exception e) {
			//  Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

			case R.id.editprofielinear:
				checkConnection2();
				break;
			case  R.id.button1:
				sharedPrefs = getActivity().getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
				boolean p2 = sharedPrefs.getBoolean("NameOfThingToSave3",true);
				if (p2) {
					new  MainActivity().replacerulesfragment();
				}
				else {
					Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_up, R.anim.left_out).toBundle();
					Intent goonlinecategories = new Intent(getActivity(), GetCategory.class);
					goonlinecategories.putExtra("position", 1);
					goonlinecategories.putExtra("multiplayers", 3);
					ActivityCompat.startActivity(getActivity(), goonlinecategories, bundle);

				}


				break;
			case R.id.button:
				checkSettingConnection();
				break;
			case R.id.button8:

				checkConnection();
				break;
			case R.id.button2:
				shareit.setVisibility(ImageButton.INVISIBLE);
				share.setVisibility(LinearLayout.VISIBLE);
				Animation RightSwipe = AnimationUtils.loadAnimation(getActivity(), R.anim.right_enter);
				share.startAnimation(RightSwipe);

				break;
			case R.id.email:
			case R.id.message:
				shareingit();
				break;
			case R.id.whatsapp:
				sharebywhatsapp();
				break;
			case R.id.multiplayer:
				if(isSignedIn())
				{
					Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.slide_up, R.anim.left_out).toBundle();
					Intent goonlinecategories = new Intent(getActivity(), GetCategory.class);
					goonlinecategories.putExtra("position", 1);
					goonlinecategories.putExtra("multiplayers",2);
					try{
						startActivity( goonlinecategories, bundle);}
					catch (Exception e){}
					break;
				}
				break;
			case R.id.cancel:
				Animation RightSwipe2 = AnimationUtils.loadAnimation(getActivity(), R.anim.right_out);
				share.startAnimation(RightSwipe2);
				RightSwipe2.setAnimationListener(new Animation.AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {

					}

					@Override
					public void onAnimationEnd(Animation animation) {
						share.setVisibility(LinearLayout.INVISIBLE);
						shareit.setVisibility(ImageButton.VISIBLE);
					}

					@Override
					public void onAnimationRepeat(Animation animation) {

					}
				});

				break;
			case R.id.sign_in_button:
				//Toast.makeText(getActivity(),"yup",Toast.LENGTH_SHORT).show();
				try{
				mListener.onSignInButtonClicked();}catch (Exception e){}

			//
				finally {
				//	setShowSignInButton(isSignedIn());
				}


				break;
			case R.id.show_achievements_button:
try {
	mListener.onShowAchievementsRequested();
}catch (Exception e){}

				break;
			case R.id.show_leaderboards_button:
				try{
				mListener.onShowLeaderboardsRequested();}
				catch (Exception e){}
				break;
//			case R.id.sign_out_button:
//				mListener.onSignOutButtonClicked();
//				break;
		}
	}

	private void sharebywhatsapp() {
		PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();
		try {
			Intent i = new Intent(Intent.ACTION_SEND);
			i.setType("text/plain");
			i.setPackage("com.whatsapp");
			i.putExtra(Intent.EXTRA_SUBJECT, R.string.invitation_title);
			String sAux = "\nCheck out FabQuiz,I use it to play quiz.Get it for free at\n\n";
			sAux = sAux + getString(R.string.invitation_deep_link);

			i.putExtra(Intent.EXTRA_TEXT, sAux);

			startActivity(Intent.createChooser(i, "Share Using"));

		} catch(Exception e) {
			//e.toString();
		}
	}

	private void shareingit() {
		try {
			PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();
			Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
					.setMessage(getString(R.string.invitation_message))
					.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
					.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
					.build();
			startActivityForResult(intent, req);
		}
		catch(Exception e){}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	private void checkSettingConnection() {
			new  MainActivity().replacesettingfragment();

	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void setShowSignInButton(boolean showSignInButton) {

		updateUI(showSignInButton);
	}
	private void getuserinfo() {
		FirebaseUser user = mAuth.getCurrentUser();
		if(user!=null) {
			final String getEmailId = user.getPhoneNumber();
			users.addListenerForSingleValueEvent(new ValueEventListener() {
				@Override
				public void onDataChange(DataSnapshot dataSnapshot) {
					for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
						if (getEmailId.equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
							//	nametheimage = (zoneSnapshot.child("phonenumber").getValue(String.class));
							getprofile = zoneSnapshot.child("getprofile").getValue(String.class);
							username=(zoneSnapshot.child("fullname").getValue(String.class));
						}

					}

				}

				@Override
				public void onCancelled(DatabaseError databaseError) {

				}
			});
		}
	}
	private void checkConnection() {

		boolean isConnected = ConnectionReceiver.isConnected();

		if(!isConnected) {
			//no_internet_dialog();
		}
		else {
			if(username!=null&&!username.equals("")) {
				String goto2= String.valueOf(2);
				Intent i = new Intent(getActivity(), multiplayers.class);
				i.putExtra("message", goto2);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);
			}
			else{
				Intent i = new Intent(getActivity(), Login.class);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);

			}
		}

	}
}
