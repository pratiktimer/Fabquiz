package google.com.fabquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import google.com.fabquiz.Login.Login;
import google.com.fabquiz.ToastPackage.CustomToast2;
import google.com.fabquiz.database.serv;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class Settings_Fragment extends Fragment implements View.OnClickListener {
   ImageView profilepic;
   boolean po=false;
	//Spinner s1;
   TextView username;
	private static View view;
	public static ToggleButton t1,t2,t3;
//	FirebaseFirestore mFirestore;
	//ImageButton backButton;
	//Button b5;
   	private static FragmentManager fragmentManager;
   	FirebaseAuth mAuth;
	FirebaseAuth.AuthStateListener mAuthListener;
	LinearLayout editprofilelinear;
	public  static  boolean comingfromsettings;
	FirebaseDatabase database;
	DatabaseReference users;
	public static SharedPreferences sharedPrefs;
	ProgressBar pb;
	//private Listener mListener = null;
	Button mSignOutBarView;
	private GoogleSignInClient mGoogleSignInClient;
	//private ProgressBar pd2;



	public void setShowSignInButton(boolean showSignInButton) {

		mSignOutBarView.setVisibility(showSignInButton ? View.VISIBLE : View.INVISIBLE);
	}
	
  	@Override
	public void onStart() {
		super.onStart();


	}
	@Override
	public void onDestroy() {
		super.onDestroy();

	}
	public Settings_Fragment() {
		database = FirebaseDatabase.getInstance();
		users = database.getReference("Users");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
						 Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_settings, container, false);

		MainActivity.loginLayout.setBackgroundColor(Color.parseColor("#202021"));
		((MainActivity)getActivity()).updateStatusBarColor("#202021");
		mAuth = FirebaseAuth.getInstance();
		initViews();
		//Toast.makeText(getActivity(),""+isSignedIn(),Toast.LENGTH_SHORT).show();
		setShowSignInButton(isSignedIn());

        setListeners();
		return view;
	}
	private boolean isSignedIn() {
		return GoogleSignIn.getLastSignedInAccount(getActivity()) != null;
	}
	// Initiate Views
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		//s1=(Spinner) view.findViewById(R.id.spinner);
		editprofilelinear=(LinearLayout)view.findViewById(R.id.editprofielinear);
		//backButton=(ImageButton) view.findViewById(R.id.backButton);
pb=(ProgressBar) view.findViewById(R.id.Progressbar2);
pb.setVisibility(ProgressBar.INVISIBLE);
mSignOutBarView=(Button) view.findViewById(R.id.sign_out_button);

		t1=(ToggleButton) view.findViewById(R.id.toggleButton1);
		t2=(ToggleButton) view.findViewById(R.id.toggleButton2);
		t3=(ToggleButton) view.findViewById(R.id.toggleButton3);

		if (android.os.Build.VERSION.SDK_INT <= 23) {
			t1.setTextColor(Color.parseColor("#000000"));
			t2.setTextColor(Color.parseColor("#000000"));
			t3.setTextColor(Color.parseColor("#000000"));
		//	t4.setTextColor(Color.parseColor("#000000"));
		}
//		b5=(Button) view.findViewById(R.id.button5);
	//	if(mAuth.getCurrentUser()==null)
		//	b5.setVisibility(Button.INVISIBLE);
		profilepic=(ImageView) view.findViewById(R.id.profilepic);
		username=(TextView) view.findViewById(R.id.username);
	//	pd2 =(ProgressBar) view.findViewById(R.id.pd0);
		restoredata();


	}
	private void saveprefences() {
		if (t1.isChecked())
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0",                 getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave", true);
			editor.commit();
		}
		else
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave", false);
			editor.commit();
		}
	}
	private void saveprefences2() {


		if (t2.isChecked())
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave2", true);
			editor.commit();
			getActivity().startService(new Intent(getActivity(), serv.class));
		}
		else
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave2", false);
			editor.commit();
			getActivity().stopService(new Intent(getActivity(),serv.class));

		}
	}
	private void saveprefences3() {
		if (t3.isChecked())
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave3", true);
			editor.commit();
		}
		else
		{
			SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
			editor.putBoolean("NameOfThingToSave3", false);
			editor.commit();
		}
	}
	private void restoredata() {


//		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
//				R.array.Default,
//				android.R.layout.simple_spinner_item);
//		// Drop down layout style - list view with radio button
//		adapter2
//				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		// attaching data adapter to spinner
//		s1.setAdapter(adapter2);
  		if(mAuth.getCurrentUser()!=null){
  			getprofile();
		}
		else{
  			editprofilelinear.setVisibility(LinearLayout.INVISIBLE);
		}
		sharedPrefs = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE);
		t1.setChecked(sharedPrefs.getBoolean("NameOfThingToSave", true));
		t2.setChecked(sharedPrefs.getBoolean("NameOfThingToSave2", true));
		boolean p=sharedPrefs.getBoolean("NameOfThingToSave2", true);
		if (p == false) {
			serv p2 = new serv();
			p2.onDestroy();

				}
				else{
			getActivity().startService(new Intent(getActivity(), serv.class));
		}

		t3.setChecked(sharedPrefs.getBoolean("NameOfThingToSave3", true));

	}
	private void setListeners() {
		t1.setOnClickListener(this);
		t2.setOnClickListener(this);
		t3.setOnClickListener(this);
		mSignOutBarView.setOnClickListener(this);
        //backButton.setOnClickListener(this);
		editprofilelinear.setOnClickListener(this);

	}
	private void getprofile() {
    pb.setVisibility(ProgressBar.VISIBLE);

		final FirebaseUser user = mAuth.getCurrentUser();
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {

				for (final DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
					if (user.getPhoneNumber().equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
						username.setText(zoneSnapshot.child("fullname").getValue(String.class).toString());
			    	if(zoneSnapshot.child("getprofile").getValue(String.class).equals("")) {
						profilepic.setImageResource(R.drawable.profilepic);
						pb.setVisibility(ProgressBar.INVISIBLE);
						}
						else{
						Picasso.with(getActivity()).load(zoneSnapshot.child("getprofile").getValue(String.class))
								.transform(new CropCircleTransformation())
								.into(profilepic, new Callback() {
									@Override
									public void onSuccess() {
										pb.setVisibility(ProgressBar.INVISIBLE);

									}

									@Override
									public void onError() {
										pb.setVisibility(ProgressBar.INVISIBLE);
									po=true;

									}
								});
						if(po) {
							Glide.with(getActivity())
									.load(zoneSnapshot.child("getprofile").getValue(String.class))
									.apply(RequestOptions.circleCropTransform())
									.into(profilepic);
						}

						}



					}

				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case  R.id.backButton:
           getActivity().onBackPressed();
				break;
			case R.id.editprofielinear:
				checkConnection2();
				break;
			case R.id.toggleButton1:
				saveprefences();
				break;
			case R.id.toggleButton2:

				saveprefences2();
				break;
			case R.id.toggleButton3:
				saveprefences3();
				break;
		//	case R.id.toggleButton4:
		//		saveprefences4();
		//		break;
			case R.id.sign_out_button:

				onSignOutButtonClicked();
  checkConnection();
	//mAuth.addAuthStateListener(mAuthListener);
	break;

}

	}

	private void onSignOutButtonClicked() {
		Log.d("TAG", "signOut()");

		if (!isSignedIn()) {
			Toast.makeText(getActivity(), "signOut() called, but was not signed in!",Toast.LENGTH_SHORT).show();
			return;
		}
		try{	mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),
				new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());

				mGoogleSignInClient.signOut().addOnCompleteListener(getActivity(),
						new OnCompleteListener<Void>() {
							@Override
							public void onComplete(@NonNull Task<Void> task) {
								boolean successful = task.isSuccessful();
								Log.d("TAG", "signOut(): " + (successful ? "success" : "failed"));
								CustomToast2 p=new CustomToast2();
								p.Show_Toast(getActivity(),view,"Sign Out Successfully!!!!");
								setShowSignInButton(!successful);

							}
						});}catch (Exception e){}
	}

	private void checkConnection() {


			if(mAuth.getCurrentUser()!=null) {
				//mAuth.signOut();
				String user_id=mAuth.getCurrentUser().getUid();
				Map<String,Object> tokenMapRemove=new HashMap<>();
				tokenMapRemove.put("token_id","");
				FirebaseFirestore mFirestore;
				mFirestore= FirebaseFirestore.getInstance();
				mFirestore.collection("Users").document(user_id).update(tokenMapRemove).addOnSuccessListener(new OnSuccessListener<Void>() {
					@Override
					public void onSuccess(Void aVoid) {
						mAuth.signOut();
						new CustomToast2().Show_Toast(getActivity(), view,
								"Log Out Successfully :)");

					}
				});
			}

		}
	private void checkConnection2(){
			if(mAuth.getCurrentUser()!=null) {
				comingfromsettings=true;
				Intent goeditprofile = new Intent(getActivity(), Login.class);
				startActivity(goeditprofile);
			}
	}
}
