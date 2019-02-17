package google.com.fabquiz.multiplayers;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
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

import com.google.android.gms.appinvite.AppInviteInvitation;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Internet.TestApplication;
import google.com.fabquiz.R;
import google.com.fabquiz.database.Helper;


public class multiplayer_fragment extends Fragment implements ConnectionReceiver.ConnectionReceiverListener, OnClickListener {

	private static View view;

    Button result,see_challenge,challenge_friend,invite_contact;

	private static FragmentManager fragmentManager;
int req=90;
   ImageButton settings;
	private int MY_PERMISSIONS_REQUEST_READ_CONTACTS=91;

	@Override
	public void onNetworkConnectionChanged(boolean isConnected) {
		if(!isConnected) {
			initViews();
			//show a No Internet Alert or Dialog
			//hideeverything();
			no_internet_dialog();

			//show a No Internet Alert or Dialog

		}else{
		//	initViews();
			checkConnection();
		//	laodcategories();
			// dismiss the dialog or refresh the activity
		}
	}

	@Override
	public void onResume() {
		super.onResume();

		// register connection status listener
		TestApplication.getInstance().setConnectionListener(this);
	}

	private void hideeverything() {
		result.setVisibility(Button.GONE);
		see_challenge.setVisibility(Button.GONE);
		challenge_friend.setVisibility(Button.GONE);
		invite_contact.setVisibility(Button.GONE);
	}
	private void showeverything() {
		result.setVisibility(Button.VISIBLE);
		see_challenge.setVisibility(Button.VISIBLE);
		challenge_friend.setVisibility(Button.VISIBLE);
		invite_contact.setVisibility(Button.VISIBLE);
	}

	private void checkConnection() {
		initViews();
		setListeners();
		boolean isConnected = ConnectionReceiver.isConnected();
		if(!isConnected) {
		hideeverything();
			no_internet_dialog();

		}
		else {
showeverything();
		//	laodcategories();
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
				//	showNetDisabledAlertToUser(getActivity());
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

  // TextView t11;
	@Override
	public void onStart() {
		super.onStart();


	}



	public multiplayer_fragment() {



	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.multiplayer_home, container, false);
//		MainActivity.loginLayout.setBackgroundResource(R.mipmap.bg2);
//		((MainActivity)getActivity()).updateStatusBarColor("#070E42");
		if (android.os.Build.VERSION.SDK_INT >= 21) {
			Window window = getActivity().getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
		}
		checkConnection();

		return view;
	}

	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		result=(Button) view.findViewById(R.id.results);
		see_challenge=(Button) view.findViewById(R.id.see_challenges);
		challenge_friend=(Button) view.findViewById(R.id.challenge_friend);
		invite_contact=(Button) view.findViewById(R.id.invite);
		//if (android.os.Build.VERSION.SDK_INT < 23) {
//			result.setTextColor(Color.parseColor("#000000"));
//			see_challenge.setTextColor(Color.parseColor("#000000"));
//			challenge_friend.setTextColor(Color.parseColor("#000000"));
//			invite_contact.setTextColor(Color.parseColor("#000000"));
	//	}
//         play=(Button) view.findViewById(R.id.results);
//          multiplayers=(Button) view.findViewById(R.id.multiplayers);
//          settings=(ImageButton) view.findViewById(R.id.button);
       //   t11=(TextView) view.findViewById(R.id.textView11);




	}

	// Set Listeners
	private void setListeners() {
//		play.setOnClickListener(this);
		invite_contact.setOnClickListener(this);
		challenge_friend.setOnClickListener(this);
		result.setOnClickListener(this);
		see_challenge.setOnClickListener(this);



	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case  R.id.challenge_friend:
				if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {


					startActivity(new Intent(getActivity(), google.com.fabquiz.recyclerviewsearch.MainActivity.class));
					getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);

				}
				else{
					Helper.hasPermissions2(getActivity(),MY_PERMISSIONS_REQUEST_READ_CONTACTS, Manifest.permission.READ_CONTACTS,(R.string.contact_toast),(R.string.contact_disabled));
				}


				break;
			case R.id.invite:
//				try {
//					Intent i = new Intent(Intent.ACTION_SEND);
//					i.setType("text/plain");
//					i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
//					String sAux = "\nLet me recommend you this application\n\n";
//					sAux = sAux + R.string.invitation_deep_link;
//					i.putExtra(Intent.EXTRA_TEXT, sAux);
//					startActivity(Intent.createChooser(i, "choose one"));
//				} catch(Exception e) {
//					//e.toString();
//				}
				//				Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
//				whatsappIntent.setType("text/plain");
//				whatsappIntent.setPackage("com.whatsapp");
//				whatsappIntent.putExtra(Intent.EXTRA_TEXT, Uri.parse(getString(R.string.invitation_deep_link)));
//				try {
//					startActivity(whatsappIntent);
//				} catch (android.content.ActivityNotFoundException ex) {
//					//Toast.MakeShortText("Whatsapp have not been installed.");
//				}
//				Intent myintent=new Intent(Intent.ACTION_SEND);
//				myintent.setType("text/plain");
//				String sharebody= String.valueOf(Uri.parse(getString(R.string.invitation_deep_link)));
//				String sharesub= String.valueOf(Uri.parse(getString(R.string.invitation_deep_link)));
//				myintent.putExtra(Intent.EXTRA_SUBJECT,sharebody);
//				myintent.putExtra(Intent.EXTRA_TEXT,sharebody);
//				Intent.createChooser(myintent,"Share Using");
				onInviteClicked();
				break;
			case  R.id.results:
				Intent gotoonlineresult=new Intent(getActivity(), GetMultiplayerResult.class);
				gotoonlineresult.putExtra("highscore","");
				startActivity(gotoonlineresult);
				getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);
				getActivity().finish();
				break;
			case R.id.see_challenges:
				new multiplayers().replaceseechallenge();
				break;
//				break;
//			case R.id.button:
//				new  MainActivity().replacesettingfragment();
//				break;
//		//	case  R.id.textView11:
//
//		//		new  MainActivity().replacesettingfragment();
//		//		break;
//
//			case  R.id.multiplayers:
//             // new MainActivity().replacelogin();
//				Intent i=new Intent(getActivity(), Login.class);
//				startActivity(i);
//				break;
		}

	}
	@Override
	public void onDestroy() {
		super.onDestroy();
		//((MainActivity)getActivity()).clearBackStackInclusive("tag"); // tag (addToBackStack tag) should be the same which was used while transacting the F2 fragment
	}

	private void onInviteClicked() {
		Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
				.setMessage(getString(R.string.invitation_message))
				.setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))
				//.setCustomImage(Uri.parse(getString(R.string.invitation_custom_image)))
				//.setCallToActionText(getString(R.string.invitation_cta))
				.build();
		startActivityForResult(intent, req);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
