package online.Category.subjects;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.R;
import google.com.fabquiz.database.SubjectsC;
import google.com.fabquiz.multiplayer;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import online.Category.CategoryViewHolder;
import online.Category.GetCategory;
import online.Category.ItemClickListener;
import online.Category.ScrollingActivity2;
import online.Category.get_Category;

import static android.content.Context.MODE_PRIVATE;
import static google.com.fabquiz.SplahScreen.media2;
public class get_subjects extends Fragment {
    public static SharedPreferences sharedPrefs;
    String catname;
    int p2;
    ProgressDialog progress;
    int max2;
    ImageView image;
    DatabaseReference ref;
    TextView cat_name;
    int randomIndex;
    DataSnapshot dataSnapshot2;
    View myFragment;
    RecyclerView listcategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    Query categories2;
    FirebaseRecyclerAdapter<SubjectsC, CategoryViewHolder> adapter;
    public static get_subjects newIstance() {
        get_subjects getCategory = new get_subjects();
        return getCategory;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        categories2 = database.getReference("Subjects").orderByChild("categoryid").equalTo(get_Category.key2);

        categories2.keepSynced(true);

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_get__category, container, false);
        listcategory = (RecyclerView) myFragment.findViewById(R.id.listcategory);
        listcategory.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(container.getContext());
        listcategory.setLayoutManager(layoutManager);
        sharedPrefs = getActivity().getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        image = myFragment.findViewById(R.id.image);
        cat_name = myFragment.findViewById(R.id.cat_name);
        cat_name.setVisibility(TextView.INVISIBLE);
        image.setVisibility(ImageView.INVISIBLE);
        checkConnection();
        //laodcategories();
        return myFragment;
    }
    @Override
    public void onResume() {
        super.onResume();

    }
    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected) {
            //show a No Internet Alert or Dialog
            no_internet_dialog();
        }
        else {
            laodcategories();
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
                  //  showNetDisabledAlertToUser(getActivity());
                    //   finish();

                }
            });

            alertDialog.show();
        } catch (Exception e) {
            //  Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
        }
    }
    private void laodcategories() {
        adapter=new FirebaseRecyclerAdapter<SubjectsC,CategoryViewHolder>(
                SubjectsC.class,
                R.layout.catelayout,
                CategoryViewHolder.class,
                categories2
        ){

            @Override
            protected void populateViewHolder(final CategoryViewHolder viewHolder, final SubjectsC model, int position) {


                Picasso.with(getActivity()).load(model.getSubimage()).skipMemoryCache()
                        .networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.category_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity())
                                .load(model.getSubimage())
                                .into(viewHolder.category_image);

                    }
                });

                   viewHolder.category_name.setVisibility(TextView.GONE);


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        boolean isConnected = ConnectionReceiver.isConnected();
                        if(isConnected) {
                            catname = model.getSubname();
                            buttonprev();
                        }
                        else no_internet_dialog();

                    }
                });
            }


        };
        adapter.notifyDataSetChanged();
        listcategory.setAdapter(adapter);
    }
    private void checkConnection2(int max) {
        max2=max;
      //  if(catname.equals("Hollywood")) {
            sharedPrefs = getActivity().getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
            p2 = sharedPrefs.getInt(catname, 0);
            if (p2 == max2) {
                if(GetCategory.multiplayer.equals("2")){
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                    Intent start = new Intent(getActivity(),multiplayer.class);

                    start.putExtra("catname",catname);

                    ActivityCompat.startActivity(getActivity(), start, bundle);
                }
                else{
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                    Intent start = new Intent(getActivity(), ScrollingActivity2.class);

                    start.putExtra("catname",catname);

                    ActivityCompat.startActivity(getActivity(), start, bundle);

                }



            } else {

                progress = new ProgressDialog(getActivity());
                progress.setTitle("Loading");
                progress.setMessage("Wait while loading...");
              //   progress.setIndeterminate(true);
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setMax(max2);
                progress.setProgress(p2);
                progress.setCancelable(true);



                progress.show();

                randomIndex=p2;
                buttonnext();
            }


    }
    private void setQuestionsView(final int questionId) {
        if (questionId ==max2) {
           progress.dismiss();
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
            editor.putInt(catname, max2);
            editor.commit();
            if(GetCategory.multiplayer.equals("2")){
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                Intent start = new Intent(getActivity(),multiplayer.class);

                start.putExtra("catname",catname);

                ActivityCompat.startActivity(getActivity(), start, bundle);

            }
            else{
                if(GetCategory.multiplayer.equals("2")){
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                    Intent start = new Intent(getActivity(),multiplayer.class);

                    start.putExtra("catname",catname);

                    ActivityCompat.startActivity(getActivity(), start, bundle);
                }
                else{
                    Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                    Intent start = new Intent(getActivity(), ScrollingActivity2.class);

                    start.putExtra("catname",catname);

                    ActivityCompat.startActivity(getActivity(), start, bundle);
getActivity().finish();
                }

            }

        }

        else {
         //   image.setVisibility(ImageView.VISIBLE);
            cat_name.setText(dataSnapshot2.child("Questions").getValue(String.class));
            cat_name.setText(dataSnapshot2.child("CorrectAnswer").getValue(String.class));
            cat_name.setText(dataSnapshot2.child("OptA").getValue(String.class));
            cat_name.setText(dataSnapshot2.child("OptB").getValue(String.class));
            try {
                if (dataSnapshot2.child("OptC").getValue(String.class).equals("")) {
                } else
                    cat_name.setText(dataSnapshot2.child("OptC").getValue(String.class));
            }catch (Exception e){}

            try {
                if (dataSnapshot2.child("OptC").getValue(String.class).equals("")) {

                } else
                    cat_name.setText(dataSnapshot2.child("OptD").getValue(String.class));
            }catch (Exception e){}
            progress.setProgress(questionId);
            if(getActivity()==null){
                return;
            }
                         getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if ((dataSnapshot2==null)) {
                        buttonnext();
                    }
                    else {


                        Picasso.with(getActivity()).load(dataSnapshot2.child("IsImageQuestion").getValue(String.class)).transform(new CropCircleTransformation()).into(image, new Callback() {
                            @Override
                            public void onSuccess() {

                                buttonnext();
                            }

                            @Override
                            public void onError() {

                                buttonnext();
                            }

                        });
                    }


                }

            });
            // randomIndex++;

                // progress.dismiss();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("google.com.fabquiz0", getActivity().MODE_PRIVATE).edit();
                editor.putInt(catname, questionId);
                editor.commit();


        }
    }
    private void mytry(final int questionId) {
        media2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataSnapshot2 = dataSnapshot;
                setQuestionsView(questionId);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void buttonnext() {

        randomIndex++;
        ref=database.getReference();
        ref.keepSynced(true);
        media2 =ref.child("Questions").child(catname).child(String.valueOf(randomIndex));
        media2.keepSynced(true);
        mytry(randomIndex);

    }
    private void buttonprev() {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference ref = rootRef.child("Questions").child(catname);
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                checkConnection2((int)dataSnapshot.getChildrenCount());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);

        //Toast.makeText(SplahScreen.this, "max = " + max, Toast.LENGTH_LONG).show();
    }
}
