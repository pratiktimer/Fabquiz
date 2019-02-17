package google.com.fabquiz.multiplayers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.google.android.gms.appinvite.AppInviteInvitation;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.MainActivity;
import google.com.fabquiz.R;
import google.com.fabquiz.Rules_Fragment;
//import google.com.fabquiz.Score_Fragment;
import google.com.fabquiz.Settings_Fragment;
//import google.com.fabquiz.Subname_Fragment;
import google.com.fabquiz.Utils;
import google.com.fabquiz.database.serv;

public class multiplayers extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener{

	private static FragmentManager fragmentManager;
	String goto2;
	public static RelativeLayout loginLayout;
	private int REQUEST_INVITE=90;
	public static SharedPreferences sharedPrefs;
	@Override
	public void onNetworkConnectionChanged(boolean isConnected) {
		if(!isConnected) {
			no_internet_dialog();
			//show a No Internet Alert or Dialog

		}else{
			//	laodcategories();
			// dismiss the dialog or refresh the activity
		}
	}

	@Override
	public void onClick(View v) {

	}

	private void checkConnection() {
		boolean isConnected = ConnectionReceiver.isConnected();
		if(!isConnected) {
			//show a No Internet Alert or Dialog
			no_internet_dialog();
		}
		else {
			//	laodcategories();
		}
	}

	private void no_internet_dialog() {
		try {
			AlertDialog alertDialog = new AlertDialog.Builder(getApplicationContext()).create();

			alertDialog.setTitle("Info");
			alertDialog.setMessage("Internet not available, Cross check your internet connectivity and try again");
			alertDialog.setIcon(android.R.drawable.ic_dialog_alert);
			alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					showNetDisabledAlertToUser(getApplicationContext());
					//   finish();

				}
			});

			alertDialog.show();
		} catch (Exception e) {
			//  Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
		}
	}

	public static void showNetDisabledAlertToUser(final Context context){
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context, AlertDialog.THEME_TRADITIONAL);
		alertDialogBuilder.setMessage("Would you like to enable it?")
				.setTitle("No Internet Connection")
				.setPositiveButton(" Enable Internet ", new DialogInterface.OnClickListener(){
					public void onClick(DialogInterface dialog, int id){
						Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
						dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(dialogIntent);
					}
				});

		alertDialogBuilder.setNegativeButton(" Cancel ", new DialogInterface.OnClickListener(){
			public void onClick(DialogInterface dialog, int id){
				dialog.cancel();
			}
		});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (android.os.Build.VERSION.SDK_INT <= 23) {
			setTheme(R.style.Theme_AppCompat_NoActionBar);

		}
		else{
			setTheme(R.style.AppTheme);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_multiplayer);
		goto2 = getIntent().getStringExtra("message");
		checkConnection();
		sharedPrefs = getSharedPreferences("google.com.fabquiz", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(multiplayers.this, serv.class));

		// If savedinstnacestate is null then replace login fragment
		//if (savedInstanceState == null) {
		//	if (TextUtils.isEmpty(FirebaseMessagingService.goto2)) {
			//	loginLayout.setBackgroundResource(R.mipmap.bg);
		fragmentManager = getSupportFragmentManager();
		loginLayout = (RelativeLayout) findViewById(R.id.Relative1);

		 if(goto2.equals("1"))
		 	replaceseechallenge();
		 else {
			 fragmentManager
					 .beginTransaction()
					// .setCustomAnimations(R.anim.right_enter,R.anim.left_out)
					 .replace(R.id.frameContainer, new multiplayer_fragment(),
							 Utils.multiplayer_fragment).commit();
		 }
		//		return; // or break, continue, throw
		//	}

			//	replaceseechallenge();
				//	online_multilayer_result();

	//	}

		// On close icon click finish activity
	}

	// Replace Login Fragment with animation
//	public void online_multilayer_result() {
//
//		fragmentManager
//				.beginTransaction()
//				.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
//				.replace(R.id.frameContainer, new Home_Fragment(),
//						Utils.online_multiplayer_result).commit();
//
//		//	position= (int) getIntent().getExtras().get("position");
//		//	email=(String) getIntent().getExtras().get("email");
//		//	if(position==1){
//		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
//		//	}
//
//	}
//	protected void replaceScore() {
//
//		fragmentManager
//				.beginTransaction()
//				.setCustomAnimations(R.anim.left_enter, R.anim.right_out)
//				.replace(R.id.frameContainer, new Score_Fragment(),
//						Utils.Score_fragment).commit();
//		//	position= (int) getIntent().getExtras().get("position");
//		//	email=(String) getIntent().getExtras().get("email");
//		//	if(position==1){
//		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
//		//	}
//	}



	protected void replacerulesfragment() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
				.replace(R.id.frameContainer, new Rules_Fragment(),
						Utils.Rules_fragment).commit();
		//	position= (int) getIntent().getExtras().get("position");
		//	email=(String) getIntent().getExtras().get("email");
		//	if(position==1){
		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
		//	}
	}
	public void clearBackStackInclusive(String tag) {
		getSupportFragmentManager().popBackStack(tag, FragmentManager.POP_BACK_STACK_INCLUSIVE);
	}
	public void replacesettingfragment() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
				.replace(R.id.frameContainer, new Settings_Fragment(),
						Utils.Settings_Fragment).commit();
		//loginLayout.setBackgroundResource(R.color.seta);
		//	position= (int) getIntent().getExtras().get("position");
		//	email=(String) getIntent().getExtras().get("email");
		//	if(position==1){
		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
		//	}
	}
//	protected void replacesubname() {
//
//		fragmentManager
//				.beginTransaction()
//				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
//				.replace(R.id.frameContainer, new Subname_Fragment(),
//						Utils.Subname_Fragment).commit();
//		//	position= (int) getIntent().getExtras().get("position");
//		//	email=(String) getIntent().getExtras().get("email");
//		//	if(position==1){
//		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
//		//	}
//	}
//	public void replacesubname2() {
//
//		fragmentManager
//				.beginTransaction()
//
//				.replace(R.id.frameContainer, new Subname_Fragment(),
//						Utils.Subname_Fragment).commitAllowingStateLoss();
//		//	position= (int) getIntent().getExtras().get("position");
//		//	email=(String) getIntent().getExtras().get("email");
//		//	if(position==1){
//		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
//		//	}
//	}
	public void replaceseechallenge() {
//setContentView(R.layout.activity_see_challenges);
	//	setContentView(R.layout.multiplayer_result);
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.left_out)
				.replace(R.id.frameContainer, new get_mult_seechallenge_frag(),
						Utils.see_challenge).commitAllowingStateLoss();
		//	position= (int) getIntent().getExtras().get("position");
		//	email=(String) getIntent().getExtras().get("email");
		//	if(position==1){
		//		Toast.makeText(MainActivity.this,"verify email",Toast.LENGTH_SHORT).show();
		//	}
	}
	public void updateStatusBarColor(String color){
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(Color.parseColor(color));
		}
    	// Color must be in hexadecimal fromat
		//if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
		//	Window window = getWindow();
		//	window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
		//	window.setStatusBarColor(Color.parseColor(color));

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("TAG", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

		if (requestCode == REQUEST_INVITE) {
			if (resultCode == RESULT_OK) {
				// Get the invitation IDs of all sent messages
				String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
				for (String id : ids) {
					Log.d("TAG", "onActivityResult: sent invitation " + id);
				}
			} else {
				// Sending failed or it was canceled, show failure message to the user
				// ...
			}
		}
	}

	@Override
	public void onBackPressed() {

		// Find the tag of signup and forgot password fragment
//		Fragment result = fragmentManager
//				.findFragmentByTag(Utils.Result_Fragment);
		Fragment Home = fragmentManager
				.findFragmentByTag(Utils.multiplayer_fragment);
//
		Fragment Home2 = fragmentManager
				.findFragmentByTag(Utils.see_challenge);
//
//		Fragment Home3 = fragmentManager
//				.findFragmentByTag(Utils.Settings_Fragment);
//
//		Fragment Home4 = fragmentManager
//				.findFragmentByTag(Utils.Score_fragment);
//
//		Fragment Home5 = fragmentManager
//				.findFragmentByTag(Utils.Subname_Fragment);


		// Check if both are null or not
		// If both are not null then replace login fragment else do backpressed
		// task
  if(Home!=null) {

	  Intent i =new Intent(multiplayers.this, MainActivity.class);
	  i.putExtra("position", 2);
	  startActivity(i);
	  overridePendingTransition(R.anim.left_enter, R.anim.right_out);
	  finish();
  }
  else if(Home2!=null){
	  String goto2= String.valueOf(2);
	  Intent i = new Intent(multiplayers.this, multiplayers.class);
	  i.putExtra("message", goto2);
	  startActivity(i);
	  overridePendingTransition(R.anim.left_enter, R.anim.right_out);
	  finish();
  }

//  else if(result!=null) {
//	 replaceLoginFragment();
//  }
//		else if(Home3!=null) {
//			replaceLoginFragment();
//
//		}
//		else if(Home4!=null)
//			replacesubname();
//		else if(Home5!=null)
//			replacerulesfragment();
//
//
//		else
			super.onBackPressed();
	}
	@Override
	protected void onDestroy() {
		stopService(new Intent(multiplayers.this, serv.class));
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		stopService(new Intent(multiplayers.this, serv.class));
		super.onPause();
	}

	@Override
	protected void onResume() {
		sharedPrefs = getSharedPreferences("google.com.fabquiz", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(multiplayers.this, serv.class));
		super.onResume();
	}
	@Override
	protected void onStart() {
		sharedPrefs = getSharedPreferences("google.com.fabquiz", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(multiplayers.this, serv.class));
		super.onStart();
	}
}




