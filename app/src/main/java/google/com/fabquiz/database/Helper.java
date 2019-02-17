package google.com.fabquiz.database;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.regex.Pattern;


import google.com.fabquiz.R;
/**
 * Created by Pratik on 2/23/2018.
 */
public class Helper {
    public static boolean hasPermissions4(final Activity activity){

        //add your permissions here
        String[] AppPermissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.INTERNET,
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED
        };

        //ungranted permissions
        ArrayList<String> ungrantedPerms = new ArrayList<String>();
        //loop

        //lets set a boolean of hasUngrantedPerm to false
        Boolean needsPermRequest = false;

        //permissionGranted
        int permGranted = PackageManager.PERMISSION_GRANTED;

        //permission required content
        String permRequestStr = activity.getString(R.string.the_following_perm_required);

        //loop
        for(String permission : AppPermissions){

            //check if perm is granted
            int checkPerm = ContextCompat.checkSelfPermission(activity,permission);

            //if the permission is not granted
            if(ContextCompat.checkSelfPermission(activity,permission) != permGranted){

                needsPermRequest = true;

                //add the permission to the ungranted permission list
                ungrantedPerms.add(permission);

                //permssion name
                String[] splitPerm = permission.split(Pattern.quote("."));

                String permName = splitPerm[splitPerm.length-1].concat("\n");

                permRequestStr = permRequestStr.concat(permName);
            }//end if

        }//end loop


        //if all permission is granted end exec
        //then continue code exec
        if(!needsPermRequest) {

            return true;
        }//end if

        //convert array list to array string
        final String[]  ungrantedPermsArray = ungrantedPerms.toArray(new String[ungrantedPerms.size()]);

        //show alert Dialog requesting permission


        //return false so that code exec in that script will not be allowed
        //to continue
        return false;

    }//end checkPermissions
    public static void hasPermissions2(FragmentActivity activity, int permission, String camera, int camera1, int camer_disabled) {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                camera)) {
            Toast.makeText(activity, camera1, Toast.LENGTH_SHORT).show();

            ActivityCompat.requestPermissions(activity,
                    new String[]{camera},
                    permission);
            // return  true;

        } else {
            Toast.makeText(activity, camer_disabled, Toast.LENGTH_SHORT).show();
            // return  false;
        }
    }
}
