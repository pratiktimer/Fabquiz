package google.com.fabquiz.database;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import google.com.fabquiz.R;
/**
 * Created by Pratik on 3/19/2018.
 */
public class serv extends Service {
    MediaPlayer mp;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }
    public void onCreate()
    {
        try {
           mp = MediaPlayer.create(this, R.raw.bgmusic);
            mp.setLooping(true);
            mp.setVolume(0.3f,0.3f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onDestroy()
    {
        try {
            if (mp.isPlaying()) {
                mp.stop();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onStart(Intent intent, int startid){

        try {
            mp.start();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}