package google.com.fabquiz.Internet;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;

import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;


import google.com.fabquiz.SplahScreen;

/**
 * Created by Pratik on 2/24/2018.
 */
public class TestApplication extends Application  implements Application.ActivityLifecycleCallbacks {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
      //  MultiDex.install(this);
    }

    private static TestApplication mInstance;
    public static final String TAG = TestApplication.class
            .getSimpleName();
    private RequestQueue mRequestQueue;
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }
    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }
    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        Picasso.Builder builder=new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built=builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
        StrictMode.VmPolicy.Builder builder2 = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder2.build());
        mInstance = this;
        registerActivityLifecycleCallbacks(this);

    }
    public static synchronized TestApplication getInstance() {
        return mInstance;
    }
    private static boolean isInterestingActivityVisible;
    public static boolean isInterestingActivityVisible() {
        return isInterestingActivityVisible;
    }
    @Override
    public void onActivityResumed(Activity activity) {
        if (activity instanceof SplahScreen) {
            isInterestingActivityVisible = true;
        }
    }
    @Override
    public void onActivityStopped(Activity activity) {
        if (activity instanceof SplahScreen) {
            isInterestingActivityVisible = false;
        }
    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }
    @Override
    public void onActivityStarted(Activity activity) {
    }
    @Override
    public void onActivityPaused(Activity activity) {
    }
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
    @Override
    public void onActivityDestroyed(Activity activity) {
    }
    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }

}