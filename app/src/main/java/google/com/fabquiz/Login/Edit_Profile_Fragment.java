package google.com.fabquiz.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import google.com.fabquiz.MainActivity;
import google.com.fabquiz.R;
import google.com.fabquiz.Settings_Fragment;
import google.com.fabquiz.ToastPackage.CustomToast;
import google.com.fabquiz.ToastPackage.CustomToast2;
import google.com.fabquiz.database.Helper;
import google.com.fabquiz.multiplayers.multiplayers;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

import static android.app.Activity.RESULT_OK;

public class Edit_Profile_Fragment extends Fragment implements OnClickListener {
	private static EditText username;
	private static FragmentManager fragmentManager;
	Animation zoomin, zoomout;
	ImageView image;
	FirebaseFirestore mFirestore;
	private static View view;
	private Button SaveinfoButton;
	Uri photoURI;
	FirebaseDatabase database;
	DatabaseReference users,contacts;
	private FirebaseAuth mAuth;
	private StorageReference mStorage;
	ProgressBar progressBar;
	private static final int GALLERY_INTENT = 2;
	private static final int CAPTURE_INTENT = 1;
	private static int MY_PERMISSIONS_REQUEST_READ_CONTACTS=90;
	String nametheimage, getprofile;
	FirebaseAuth.AuthStateListener mAuthListener;
		@Override
	public void onStart() {
		super.onStart();
		mAuthListener.onAuthStateChanged(mAuth);
	}
	public Edit_Profile_Fragment() {
		database = FirebaseDatabase.getInstance();
		users = database.getReference("Users");
		contacts = database.getReference("Contact");
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.edit_user_profile, container, false);
		((Login)getActivity()).updateStatusBarColor("#202021");
		mAuth = FirebaseAuth.getInstance();
		mAuthListener = new FirebaseAuth.AuthStateListener() {
			@Override
			public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
				if (firebaseAuth.getCurrentUser() == null) {
					startActivity(new Intent(getActivity(), MainActivity.class));
				}
			}
		};
		initViews();
		setListeners();
		getuserinfo();
		getprofile();

		return view;

	}
	private void initViews() {
		fragmentManager = getActivity().getSupportFragmentManager();
		progressBar=(ProgressBar) view.findViewById(R.id.progressBar2);

		mStorage = FirebaseStorage.getInstance().getReference();
		username = (EditText) view.findViewById(R.id.username);
		SaveinfoButton = (Button) view.findViewById(R.id.save);
		if (Settings_Fragment.comingfromsettings = true) {
			SaveinfoButton.setText(R.string.Save);
		} else {
			SaveinfoButton.setText(R.string.Next);
		}
		image = (ImageView) view.findViewById(R.id.profilepic);
		zoomin = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_in);
		zoomout = AnimationUtils.loadAnimation(getActivity(), R.anim.zoom_out);


	}

	private void setListeners() {
		image.setOnClickListener(this);
		SaveinfoButton.setOnClickListener(this);
	}
	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 90: {

				if (grantResults.length > 0
						&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {

				} else {
					Toast.makeText(getActivity(),"Camera featured is Disabled ", Toast.LENGTH_LONG).show();
				}
				super.onRequestPermissionsResult(requestCode,permissions,grantResults);
			}
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.profilepic:
				if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
					selectImage();
				}
				else{
					Helper.hasPermissions2(getActivity(),MY_PERMISSIONS_REQUEST_READ_CONTACTS, android.Manifest.permission.CAMERA,(R.string.camera),(R.string.camera_disabled));
				}
				break;
			case R.id.save:
				updateinformation();


				break;
		}

	}
	private void updateinformation() {
			final String getFullName = username.getText().toString();
		if (getFullName.equals("") || getFullName.length() == 0
				) {
			new CustomToast().Show_Toast(getActivity(), view, "Username field is Required.");
		} else {
			changepassword(getFullName);

		}
	}
	private void changepassword(final String getPassword) {
		mAuth = FirebaseAuth.getInstance();
		FirebaseUser user = mAuth.getCurrentUser();
		updatedb(getPassword);
		if (user != null) {
			updatedb(getPassword);
			if (Login.firstlogin == true) {
				if (getPassword != null) {
					String goto2= String.valueOf(2);
					Intent i = new Intent(getActivity(), multiplayers.class);
					i.putExtra("message", goto2);
					startActivity(i);
					getActivity().overridePendingTransition(R.anim.right_enter, R.anim.left_out);
				}
			}
//			else if(Settings_Fragment.comingfromsettings==false){
//				startActivity(new Intent(getActivity(), multiplayers.class));
//				getActivity().finish();
//			}
//			else if(Settings_Fragment.comingfromsettings==true) {
//						}
//
//		}
		}
	}
	private void updatedb(final String getPassword) {
		final FirebaseUser user = mAuth.getCurrentUser();
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
					if (user.getPhoneNumber() != null && (user.getPhoneNumber().toString().equals(zoneSnapshot.child("phonenumber").getValue(String.class)))) {
						zoneSnapshot.getRef().child("fullname").setValue(getPassword);
						new CustomToast2().Show_Toast(getActivity(), view,
								"Updated Information :(!!!.");
						getuserinfo();
					}
				}

			}
			@Override
			public void onCancelled(DatabaseError databaseError) {
			}
		});

	}
	private void getuserinfo() {
		FirebaseUser user = mAuth.getCurrentUser();
		final String getEmailId = user.getPhoneNumber();
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
					if (getEmailId.equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
						nametheimage = (zoneSnapshot.child("phonenumber").getValue(String.class));
						getprofile = zoneSnapshot.child("getprofile").getValue(String.class);
						username.setText(zoneSnapshot.child("fullname").getValue(String.class));
					}

				}

			}
			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}
	private void dispatchTakePictureIntent() {
		if(Helper.hasPermissions4(getActivity()) == false){
				}
		else {
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
			File photoFile = null;
				try {
					photoFile = createImageFile();
				} catch (IOException e) {
					e.printStackTrace();

				}

				if (photoFile != null) {
					//Toast.makeText(getActivity(),"image not null",Toast.LENGTH_SHORT).show();
//					photoURI = FileProvider.getUriForFile(getActivity(),
//							"com.example.android.fileprovider",
//							photoFile);
					photoURI = Uri.fromFile(photoFile);
					takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
					startActivityForResult(takePictureIntent, CAPTURE_INTENT);
				}
			}
		}

	}
	private void getprofile() {
		final FirebaseUser user = mAuth.getCurrentUser();
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
					if (user.getPhoneNumber().equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
						String Profile=zoneSnapshot.child("getprofile").getValue(String.class);
						if(Profile==null|Profile.equals("")) {
						image.setImageResource(R.drawable.profilepic);
												}
						else{
							Picasso.with(getActivity()).load(zoneSnapshot.child("getprofile").getValue(String.class))
									.transform(new CropCircleTransformation())
									.into(image, new Callback() {
										@Override
										public void onSuccess() {
																}
										@Override
										public void onError() {

										}
									});
						}

					}

				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});
	}	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK && requestCode == CAPTURE_INTENT) {

					FirebaseUser user = mAuth.getCurrentUser();
					final String getEmailId = user.getPhoneNumber();
					StorageReference filepath = mStorage.child("Photos").child(getEmailId);
					filepath.putFile(photoURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

							Uri downloadUri = taskSnapshot.getDownloadUrl();
							setprofile(downloadUri);

						}
					});
				}
				else{

				}
				break;

			case 2:
				if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {

					FirebaseUser user = mAuth.getCurrentUser();
					final String getEmailId = user.getPhoneNumber();
					final Uri uri = data.getData();
					StorageReference filepath = mStorage.child("Photos").child(getEmailId);
					filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
							Uri downloadUri = taskSnapshot.getDownloadUrl();
							if(getActivity()!=null) {
								progressBar.setVisibility(ProgressBar.INVISIBLE);
								Toast.makeText(getActivity(), "Profile Picture Set Successfully", Toast.LENGTH_SHORT).show();
							}
							setprofile(downloadUri);

						}
					}).addOnFailureListener(new OnFailureListener() {
						@Override
						public void onFailure(@NonNull Exception e) {
							if(getActivity()!=null) {
								progressBar.setVisibility(ProgressBar.INVISIBLE);


								Toast.makeText(getActivity(), "Failed To Set Profile ", Toast.LENGTH_SHORT).show();
							}
						}
					}).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
						@Override
						public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
							progressBar.setVisibility(ProgressBar.VISIBLE);
							double progresslength=(100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
							progressBar.setProgress((int)progresslength);
						}
					});
				}
				break;
		}
	}
	private void uploadimage() {
		if(Helper.hasPermissions4(getActivity()) == false){
				}
		else {
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			startActivityForResult(intent, GALLERY_INTENT);
		}

	}
	private void selectImage() {
		final CharSequence[] options = {"Take Photo", "Choose From Gallery"};
		android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getActivity());
		builder.setTitle("Select Option");
		builder.setItems(options, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				if (options[item].equals("Take Photo")) {
					dialog.dismiss();
					dispatchTakePictureIntent();
				} else if (options[item].equals("Choose From Gallery")) {
					dialog.dismiss();
					uploadimage();
				}
						}


				});
		builder.show();


	}
	String mCurrentPhotoPath;
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(
				imageFileName,  /* prefix */
				".jpg",         /* suffix */
				storageDir      /* directory */
		);
		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}
	private void setprofile(final Uri downloadUri) {
		final String durl = String.valueOf(downloadUri);
		FirebaseUser user = mAuth.getCurrentUser();
		final String getEmailId = user.getPhoneNumber();
		users.addListenerForSingleValueEvent(new ValueEventListener() {
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot zoneSnapshot : dataSnapshot.getChildren()) {
					if (getEmailId.equals(zoneSnapshot.child("phonenumber").getValue(String.class))) {
						zoneSnapshot.getRef().child("getprofile").setValue(durl);
						getprofile();
										}
				}

			}

			@Override
			public void onCancelled(DatabaseError databaseError) {

			}
		});


	}
}

