package google.com.fabquiz.multiplayers;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Transformation;

import google.com.fabquiz.Internet.ConnectionReceiver;
import google.com.fabquiz.Internet.TestApplication;
import google.com.fabquiz.R;
import google.com.fabquiz.database.CategoryViewHolder2;
import google.com.fabquiz.database.GamesCreatedByPhoneNumbers;
import google.com.fabquiz.database.GamesReceivedByPhoneNumbers;
import online.Category.ItemClickListener;


public class get_mult_result_frag extends Fragment implements ConnectionReceiver.ConnectionReceiverListener {
    private static FragmentManager fragmentManager;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 768;
    public static TextView t10;
    FirebaseAuth mAuth;

    int size = (int) Math.ceil(Math.sqrt(MAX_WIDTH * MAX_HEIGHT));
    View myFragment;
    RecyclerView listcategory,listcategory2;
    RecyclerView.LayoutManager layoutManager,layoutManager2;
    FirebaseDatabase database;
    public static String key2;
    DatabaseReference categories,categories2;
    FirebaseRecyclerAdapter<GamesReceivedByPhoneNumbers,CategoryViewHolder2> adapter2;
    FirebaseRecyclerAdapter<GamesCreatedByPhoneNumbers,CategoryViewHolder2> adapter;
    public static get_mult_result_frag newIstance(){
        get_mult_result_frag getCategory=new get_mult_result_frag();
        return getCategory;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getActivity().getSupportFragmentManager();
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        categories=database.getReference("GamesCreatedByPhoneNumbers").child(user.getPhoneNumber());
        categories2=database.getReference("GamesReceivedByPhoneNumbers").child(user.getPhoneNumber());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        myFragment=inflater.inflate(R.layout.frag_mult2,container,false);
        listcategory=(RecyclerView) myFragment.findViewById(R.id.listcategory);
        listcategory.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(container.getContext());
        listcategory.setLayoutManager(layoutManager);
//        listcategory2=(RecyclerView) myFragment.findViewById(R.id.listcategory2);
//        listcategory2.setHasFixedSize(true);
//        layoutManager2=new LinearLayoutManager(container.getContext());
//        listcategory2.setLayoutManager(layoutManager2);
        checkConnection();
       // laodcategories();
        t10=(TextView) myFragment.findViewById(R.id.textView10);
        return  myFragment;
    }
    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if(!isConnected) {
           no_internet_dialog();
            //show a No Internet Alert or Dialog

        }else{
           laodcategories();
            // dismiss the dialog or refresh the activity
        }
    }

    @Override
    public void onClick(View v) {
        
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
            no_internet_dialog();
        }
        else {
            if (GetMultiplayerResult.position.equals("") | GetMultiplayerResult.position.equals(null)){
                laodcategories();
          //  myresult();
        }

            else
            {
                laodcategories();
            //    myresult();
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
                    showNetDisabledAlertToUser(getActivity());
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
    private void laodcategories() {
        adapter=new FirebaseRecyclerAdapter<GamesCreatedByPhoneNumbers, CategoryViewHolder2>(
                GamesCreatedByPhoneNumbers.class,
                R.layout.catelayout3,
                CategoryViewHolder2.class,
                categories
        ){

            @Override
            public GamesCreatedByPhoneNumbers getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }
            @Override
            protected void populateViewHolder(final CategoryViewHolder2 viewHolder, final GamesCreatedByPhoneNumbers model, int position) {


                if(model.getScoreofplayer2().equals(-2)){
                    viewHolder.button.setText("Their Turn Waiting");

                }
                else {
                    int score1 = model.getScoreofplayer2();
                    int score2 = model.getScoreofplayer1();
                    if (model.getScoreofplayer2().equals(-1)) {
                        viewHolder.button.setText("Declined");
                    } else {
                        if (score1 > score2) {
                            viewHolder.button.setText("YOU LOST");
                        } else if (score2 > score1) {
                            viewHolder.button.setText("YOU WON");
                        } else if (score1 == score2) {
                            viewHolder.button.setText("MATCH TIED");
                        }
                    }
                }
                viewHolder.category_name.setText(model.getPlayer2());
                Glide.with(getActivity())
                        .load(model.getPlayer2Profile())
                        .apply(RequestOptions.circleCropTransform())
                        .into(viewHolder.category_image);
//                Picasso.with(getActivity()).load(MainActivity.contact2.getGetprofile()).transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT)).skipMemoryCache()
//                        .resize(size, size)
//                        .centerInside().networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.category_image, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        Picasso.with(getActivity())
//                                .load(MainActivity.contact2.getGetprofile())
//                                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
//                                .skipMemoryCache()
//                                .resize(size, size)
//                                .centerInside()
//                                .into(viewHolder.category_image);
//
//                    }
//                });


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                       // Toast.makeText(getActivity(),String.format(
                          //      "%s|%s",adapter.getRef(position).getKey(),model.getCategory_name()),
                        //
                        //                   Toast.LENGTH_SHORT).show();
                      //  key2=adapter.getRef(position).getKey();



                    }
                });
            }


        };

        adapter.notifyDataSetChanged();
        listcategory.setAdapter(adapter);


    }
    public void myresult(){

        adapter2=new FirebaseRecyclerAdapter<GamesReceivedByPhoneNumbers, CategoryViewHolder2>(
                GamesReceivedByPhoneNumbers.class,
                R.layout.catelayout3,
                CategoryViewHolder2.class,
                categories2
        ){
            @Override
            public GamesReceivedByPhoneNumbers getItem(int position) {
                return super.getItem(getItemCount() - 1 - position);
            }
            @Override
            protected void populateViewHolder(final CategoryViewHolder2 viewHolder, final GamesReceivedByPhoneNumbers model, int position) {

                viewHolder.category_name.setText(model.getPlayer1());
                Glide.with(getActivity())
                        .load(model.getPlayer1Profile())
                        .apply(RequestOptions.circleCropTransform())
                        .into(viewHolder.category_image);
//                Picasso.with(getActivity()).load(MainActivity.contact2.getGetprofile()).transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT)).skipMemoryCache()
//                        .resize(size, size)
//                        .centerInside().networkPolicy(NetworkPolicy.OFFLINE).into(viewHolder.category_image, new Callback() {
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//                        Picasso.with(getActivity())
//                                .load(MainActivity.contact2.getGetprofile())
//                                .transform(new BitmapTransform(MAX_WIDTH, MAX_HEIGHT))
//                                .skipMemoryCache()
//                                .resize(size, size)
//                                .centerInside()
//                                .into(viewHolder.category_image);
//
//                    }
//                });


                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(getActivity(),String.format(
                        //      "%s|%s",adapter.getRef(position).getKey(),model.getCategory_name()),
                        //
                        //                   Toast.LENGTH_SHORT).show();
                        //  key2=adapter.getRef(position).getKey();



                    }
                });
            }


        };
        adapter2.notifyDataSetChanged();
        listcategory2.setAdapter(adapter2);
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

