package google.com.fabquiz;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.google.android.gms.ads.internal.gmsg.HttpClient;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karan.churi.PermissionManager.PermissionManager;

import java.util.ArrayList;
import java.util.List;

import google.com.fabquiz.database.serv;
import online.Category.OnlineResult;

public class MainActivity extends AppCompatActivity implements
		Home_Fragment.Listener{
	private int REQUEST_INVITE=90;
	private static final int RC_UNUSED = 5001;
	private static FragmentManager fragmentManager;
	private static final int RC_SIGN_IN = 9001;
	public static RelativeLayout loginLayout;
	private  Home_Fragment mMainMenuFragment;
	private Settings_Fragment mWinFragment;
	// Client variables
	private AchievementsClient mAchievementsClient;
	private LeaderboardsClient mLeaderboardsClient;
	private EventsClient mEventsClient;
	private PlayersClient mPlayersClient;
	// Client used to sign in with Google APIs
	private GoogleSignInClient mGoogleSignInClient;
	public static SharedPreferences sharedPrefs;
	//private final AccomplishmentsOutbox mOutbox = new AccomplishmentsOutbox();
	// achievements and scores we're pending to push to the cloud
	// (waiting for the user to sign in, for instance)


	String position;
	private Rules_Fragment rules_frag;
	private Result_Fragment res_frag;

	public static void showDialogOK(Activity activity, String message, DialogInterface.OnClickListener okListener) {
		new AlertDialog.Builder(activity)
				.setMessage(message)
				.setPositiveButton("OK", okListener)
				.setNegativeButton("Cancel", okListener)
				.create()
				.show();
	}
	private void signOut() {

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		if (Build.VERSION.SDK_INT <23) {
			setTheme(R.style.Theme_AppCompat_NoActionBar);
		}
		else{
			setTheme(R.style.AppTheme);
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
		}
		position = String.valueOf(getIntent().getExtras().get("position"));

		mGoogleSignInClient = GoogleSignIn.getClient(this,
				new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());



		sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(MainActivity.this, serv.class));
		fragmentManager = getSupportFragmentManager();
		mMainMenuFragment = new Home_Fragment();
		mMainMenuFragment.setListener(this);

		loginLayout = (RelativeLayout) findViewById(R.id.Relative1);
		//OnlineResult onlineResult=new OnlineResult();
		//onlineResult.setCallback(this);
		// If savedinstnacestate is null then replace login fragment
		if(position.equals("1")) {
			res_frag = new Result_Fragment();
			//res_frag.setListener(this);

			replacesresult();

		}
		else if(position.equals("3")){
			rules_frag = new Rules_Fragment();
			//rules_frag.setListener(this);
			replacerulesfragment2();
		}
		else {
			//	if (savedInstanceState == null) {
			//loginLayout.setBackgroundResource(R.mipmap.bg2);
			//if(Helper.hasPermissions(MainActivity.this)==false){

			//	}
			//	else {



			fragmentManager
					.beginTransaction()
					.replace(R.id.frameContainer, mMainMenuFragment,
							Utils.Home_fragment).commit();
			//}
			//}
		}
        checkPlaceholderIds();
		// On close icon click finish activity
	}
	private void checkPlaceholderIds() {
		StringBuilder problems = new StringBuilder();

		if (getPackageName().startsWith("com.google.")) {
			problems.append("- Package name start with com.google.*\n");
		}

		for (Integer id : new Integer[]{
				R.string.app_id,
				R.string.achievement_starter,
		R.string.achievement_starter_ii,R.string.achievement_fighter_iii,
		R.string.achievement_starter_iii,R.string.achievement_fighter_i,R.string.achievement_fighter_ii,R.string.achievement_fighter_iii}) {

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

	private void signInSilently() {
        Log.d("TAG", "signInSilently()");

        mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
                new OnCompleteListener<GoogleSignInAccount>() {
                    @Override
                    public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "signInSilently(): success");
                            onConnected(task.getResult());
                        } else {
                            Log.d("TAG", "signInSilently(): failure", task.getException());
                            onDisconnected();
                        }
                    }
                });
    }
	@Override
	public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
		try {
			Home_Fragment.permission.checkResult(requestCode, permissions, grantResults);
			//To get Granted Permission and Denied Permission
			ArrayList<String> granted = Home_Fragment.permission.getStatus().get(0).granted;
			ArrayList<String> denied = Home_Fragment.permission.getStatus().get(0).denied;
		}
		catch (Exception e){
			//Toast.makeText(MainActivity.this,""+e,Toast.LENGTH_SHORT).show();
		}
		//	Toast.makeText(MainActivity.this,""+granted,Toast.LENGTH_SHORT).show();
	}
	// Replace Login Fragment with animation
	public void replaceLoginFragment() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter,R.anim.right_out)
				.replace(R.id.frameContainer, mMainMenuFragment,
						Utils.Home_fragment).commit();



	}
	public void replacerulesfragment() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.fui_slide_in_right,R.anim.fui_slide_out_left)
				.replace(R.id.frameContainer, new Rules_Fragment(),
						Utils.Rules_fragment).commit();

	}
	public void replacerulesfragment2() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter,R.anim.right_out)
				.replace(R.id.frameContainer, rules_frag,
						Utils.Rules_fragment).commit();

	}
	public void replacesettingfragment() {
		mWinFragment=new Settings_Fragment();
		//mWinFragment.setListener();
		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter,R.anim.left_out)
				.replace(R.id.frameContainer,mWinFragment,
						Utils.Settings_Fragment).commit();

	}
	public void replacesubname2() {
	}
	protected void replacesresult() {

		fragmentManager
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter,R.anim.left_out)
				.replace(R.id.frameContainer, res_frag,
						Utils.Result_Fragment).commitAllowingStateLoss();

	}
	public void updateStatusBarColor(String color){
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(Color.parseColor(color));
		}


	}
	@Override
	public void onBackPressed() {

		// Find the tag of signup and forgot password fragment
		Fragment result = fragmentManager
				.findFragmentByTag(Utils.Result_Fragment);
		Fragment Home = fragmentManager
				.findFragmentByTag(Utils.Home_fragment);

		Fragment Home2 = fragmentManager
				.findFragmentByTag(Utils.Rules_fragment);

		Fragment Home3 = fragmentManager
				.findFragmentByTag(Utils.Settings_Fragment);

		Fragment Home4 = fragmentManager
				.findFragmentByTag(Utils.Score_fragment);

		Fragment Home5 = fragmentManager
				.findFragmentByTag(Utils.Subname_Fragment);


		if(Home!=null)
			finishAffinity();
		else if(Home2!=null) {
			replaceLoginFragment();
		}
		else if(result!=null) {
			replaceLoginFragment();
		}
		else if(Home3!=null) {
			Settings_Fragment.comingfromsettings=false;
			replaceLoginFragment();


		}
		else if(Home4!=null)
			replacesubname2();
		else if(Home5!=null){
			sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
			boolean p2 = sharedPrefs.getBoolean("NameOfThingToSave3",true);
			if (p2) {
				replacerulesfragment2();
			}
			else
				new MainActivity().replaceLoginFragment();
		}



		else
			super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		stopService(new Intent(MainActivity.this,serv.class));
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		stopService(new Intent(MainActivity.this,serv.class));
		super.onPause();
	}

	@Override
	protected void onPostResume() {
		sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(MainActivity.this, serv.class));
		super.onPostResume();
	}

	@Override
	protected void onResume() {

		sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(MainActivity.this, serv.class));
		super.onResume();
		signInSilently();
	}
	@Override
	protected void onStart() {
		sharedPrefs = getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if(p)
			startService(new Intent(MainActivity.this, serv.class));
		super.onStart();
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
		else if (requestCode == RC_SIGN_IN) {
			Task<GoogleSignInAccount> task =
					GoogleSignIn.getSignedInAccountFromIntent(data);

			try {
				GoogleSignInAccount account = task.getResult(ApiException.class);
				onConnected(account);
			} catch (ApiException apiException) {
				String message = apiException.getMessage();
				if (message == null || message.isEmpty()) {
					message = getString(R.string.signin_other_error);
				}

				onDisconnected();
				new AlertDialog.Builder(MainActivity.this)
						.setMessage(message)
						.setNeutralButton(android.R.string.ok, null)
						.show();
			}
		}
	}

	private void onConnected(GoogleSignInAccount googleSignInAccount) {
		Log.d("TAG", "onConnected(): connected to Google APIs");

		mAchievementsClient = Games.getAchievementsClient(this, googleSignInAccount);
		mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
		mEventsClient = Games.getEventsClient(this, googleSignInAccount);
		mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);

		// Show sign-out button on main menu
		try {

			mMainMenuFragment.setShowSignInButton(false);
		}
		catch (Exception e){}

		// Show "you are signed in" message on win screen, with no sign in button.
		  // mWinFragment.setShowSignInButton(false);

		// Set the greeting appropriately on main menu
		mPlayersClient.getCurrentPlayer()
				.addOnCompleteListener(new OnCompleteListener<Player>() {
					@Override
					public void onComplete(@NonNull Task<Player> task) {
						String displayName;
						if (task.isSuccessful()) {
							displayName = task.getResult().getDisplayName();

						} else {
							Exception e = task.getException();
							handleException(e, getString(R.string.players_exception));
							displayName = "???";
						}
						//mMainMenuFragment.setGreeting("Hello, " + displayName);
					}
				});


		// if we have accomplishments to push, push them
//		if (!mOutbox.isEmpty()) {
//			pushAccomplishments();
//			Toast.makeText(this, getString(R.string.your_progress_will_be_uploaded),
//					Toast.LENGTH_LONG).show();
//		}

		//loadAndPrintEvents();
	}

	private void onDisconnected() {
		Log.d("TAG", "onDisconnected()");

		mAchievementsClient = null;
		mLeaderboardsClient = null;
		mPlayersClient = null;

		// Show sign-in button on main menu
		try {
			mMainMenuFragment.setShowSignInButton(true);
		}
		catch (Exception e){}

		// Show sign-in button on win screen
	//	mWinFragment.setShowSignInButton(true);

		//mMainMenuFragment.setGreeting(getString(R.string.signed_out_greeting));
	}

	private boolean isSignedIn() {

			return GoogleSignIn.getLastSignedInAccount(getApplicationContext()) != null;


	}
//	private void pushAccomplishments() {
//		if (!isSignedIn()) {
//			// can't push to the cloud, try again later
//			return;
//		}
//// Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
////		        .increment(getString(R.string.achievement_beginner), 1);
////		mLeaderboardsClient.submitScore(getString(R.string.leaderboard_english),
////				mOutbox.mEasyModeScore);
//
//	}
	private void handleException(Exception e, String details) {
		int status = 0;

		if (e instanceof ApiException) {
			ApiException apiException = (ApiException) e;
			status = apiException.getStatusCode();
		}

		String message = getString(R.string.status_exception_error, details, status, e);

		new AlertDialog.Builder(MainActivity.this)
				.setMessage(message)
				.setNeutralButton(android.R.string.ok, null)
				.show();
	}


	@Override
	public void onStartGameRequested(boolean hardMode) {
		//startGame(hardMode);
	}

	@Override
	public void onShowAchievementsRequested() {
		mAchievementsClient.getAchievementsIntent()
				.addOnSuccessListener(new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						startActivityForResult(intent, RC_UNUSED);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						handleException(e, getString(R.string.achievements_exception));
					}
				});
	}

	@Override
	public void onShowLeaderboardsRequested() {
		mLeaderboardsClient.getAllLeaderboardsIntent()
				.addOnSuccessListener(new OnSuccessListener<Intent>() {
					@Override
					public void onSuccess(Intent intent) {
						startActivityForResult(intent, RC_UNUSED);
					}
				})
				.addOnFailureListener(new OnFailureListener() {
					@Override
					public void onFailure(@NonNull Exception e) {
						handleException(e, getString(R.string.leaderboards_exception));
					}
				});
	}

	@Override
	public void onSignInButtonClicked() {
		startSignInIntent();
	}

	@Override
	public void onSignOutButtonClicked() {
		signOut();

	}
	private void startSignInIntent() {
		startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
	}

//	private class AccomplishmentsOutbox {
//
//		int mBoredSteps = 0;
//		int mEasyModeScore = -1;
//
//
//		boolean isEmpty() {
//			return mBoredSteps == 0 && mEasyModeScore < 0;
//		}
//
//	}


}
