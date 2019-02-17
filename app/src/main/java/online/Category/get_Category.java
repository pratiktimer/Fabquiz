package online.Category;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Internet.TestApplication;
import google.com.fabquiz.R;
import google.com.fabquiz.database.CategoryC;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import google.com.fabquiz.multiplayer;

import static android.content.Context.MODE_PRIVATE;
import static google.com.fabquiz.SplahScreen.media2;
public class get_Category extends Fragment implements ConnectionReceiver.ConnectionReceiverListener {
    public static SharedPreferences sharedPrefs;
    String catname;
    int p2;
    ProgressDialog progress;
    int max2;
    ImageView image;
    TextView cat_name;
    int randomIndex;
    DataSnapshot dataSnapshot2;
    DatabaseReference ref;
    private static FragmentManager fragmentManager;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    public static TextView t10;
    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    View myFragment;
    RecyclerView  listcategory;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    public static String key2;
    DatabaseReference categories;
    FirebaseRecyclerAdapter<CategoryC,CategoryViewHolder> adapter;
    public static  get_Category newIstance(){
        get_Category getCategory=new get_Category();
        return getCategory;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        database=FirebaseDatabase.getInstance();
        categories=database.getReference("Category");
        categories.keepSynced(true);


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable   Bundle savedInstanceState) {


        myFragment=inflater.inflate(R.layout.fragment_get__category,container,false);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.bgrules));
        }
        listcategory=(RecyclerView) myFragment.findViewById(R.id.listcategory);
        listcategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listcategory.setLayoutManager(layoutManager);
        sharedPrefs = getActivity().getSharedPreferences("google.com.fabquiz0", MODE_PRIVATE);
        image=myFragment.findViewById(R.id.image);
        cat_name=myFragment.findViewById(R.id.cat_name);
        cat_name.setVisibility(TextView.INVISIBLE);
        image.setVisibility(ImageView.INVISIBLE);
        checkConnection();

        t10=(TextView) myFragment.findViewById(R.id.textView10);
        return  myFragment;
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

        if (android.os.Build.VERSION.SDK_INT <= 23) {

            refreshfrag();
        }

    }

    @Override
    public void onClick(View v) {

    }

    private void refreshfrag() {
        boolean isConnected=ConnectionReceiver.isConnected();
        if(!isConnected) {
            no_internet_dialog();
        }
        else
        {
            // Reload current fragment
            Fragment frg = null;
            frg = getActivity().getSupportFragmentManager().findFragmentByTag(Utils.getcategory_Fragment);
            final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.detach(frg);
            ft.attach(frg);
            ft.commitAllowingStateLoss();
        }

    }
    @Override
    public void onResume() {
        super.onResume();

        // register connection status listener
        TestApplication.getInstance().setConnectionListener(this);
    }
    private void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        if(!isConnected) {
            //show a No Internet Alert or Dialog
            laodcategories();
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



                }
            });

            alertDialog.show();
        } catch (Exception e) {
            //  Log.d(SyncStateContract.Constants.TAG, "Show Dialog: "+e.getMessage());
        }
    }
    private void laodcategories() {
        adapter=new FirebaseRecyclerAdapter<CategoryC, CategoryViewHolder>(
                CategoryC.class,
                R.layout.catelayout,
                CategoryViewHolder.class,
                categories
        ){
            @Override
            protected void populateViewHolder(final CategoryViewHolder viewHolder, final CategoryC model, int position) {
                //viewHolder.category_name.setText(model.getCategory_name());

                Picasso.with(getActivity()).load(model.getImage()).transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT)).skipMemoryCache()
                        .resize(size, size)
                        .centerInside().networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.category_image, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(getActivity())
                                .load(model.getImage())
                                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
                                .skipMemoryCache()
                                .resize(size, size)
                                .centerInside()
                                .into(viewHolder.category_image);

                    }
                });


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        boolean isConnected = ConnectionReceiver.isConnected();
                        if(isConnected) {
                            key2 = adapter.getRef(position).getKey();

                            if (model.getNoofsub().equals("0")) {
                                catname = model.getCategory_name();
                                buttonprev();

                            } else {

                                new GetCategory().getsubname();
                            }
                        }
                        else
                            no_internet_dialog();

                    }
                });
            }


        };
        adapter.notifyDataSetChanged();
        listcategory.setAdapter(adapter);
    }
    private void checkConnection2(int max) {
        max2=max;
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
            if(p2==0){
                randomIndex=0;
            }
            else
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
                Bundle bundle = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.right_enter, R.anim.left_out).toBundle();
                Intent start = new Intent(getActivity(), ScrollingActivity2.class);

                start.putExtra("catname",catname);

                ActivityCompat.startActivity(getActivity(), start, bundle);
                getActivity().finish();

            }



        }

        else {

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
    private class BitmapTransform implements Transformation {
        private final int maxWidth;
        private final int maxHeight;
        public BitmapTransform(int maxWidth, int maxHeight) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
        }
        @Override
        public Bitmap transform(Bitmap source) {
            int targetWidth, targetHeight;
            double aspectRatio;

            if (source.getWidth() > source.getHeight()) {
                targetWidth = maxWidth;
                aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                targetHeight = (int) (targetWidth * aspectRatio);
            } else {
                targetHeight = maxHeight;
                aspectRatio = (double) source.getWidth() / (double) source.getHeight();
                targetWidth = (int) (targetHeight * aspectRatio);
            }

            Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, false);
            if (result != source) {
                source.recycle();
            }
            return result;
        }

        @Override
        public String key() {
            return maxWidth + "x" + maxHeight;
        }

    }

}

