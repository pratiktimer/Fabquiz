package google.com.fabquiz.database;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 13-12-2017.
 */

public class DatabaseHelper2 extends SQLiteOpenHelper {
    private static final String KEY_ID = "_id";
    public static final String DATABASE_NAME = "pratiksha3";
    private static  int DATABASE_VERSION =1;
    private static final String SP_KEY_DB_VER = "db_ver";
    private final Context mContext;
    private static final String subject1="subject1";
    private static final String subject2="subject2";
    private static final String subject3="subject3";
    private static final String subject4="subject4";
    private static final String subject6="subject5";
    private static final String subject7="subject6";
    private static final String subject8="subject7";
    private static final String TABLE_QUESTION6="highscore";

    private static final String TABLE_QUESTION5 = "question5";
    public DatabaseHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
        initialize();
    }
    public List<String> getAllLabels10(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = null;
        // Select All Query
        selectQuery = "SELECT * FROM " + TABLE_QUESTION5;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(1));
                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
                labels.add(cursor.getString(5));
                labels.add(cursor.getString(6));
                labels.add(cursor.getString(7));
                labels.add(cursor.getString(8));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    public List<String> getAllLabels12(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = null;
        // Select All Query
        selectQuery = "SELECT * FROM " + TABLE_QUESTION5;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
                labels.add(cursor.getString(5));
                labels.add(cursor.getString(6));
                labels.add(cursor.getString(7));
                labels.add(cursor.getString(8));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    public List<String> getAllLabelsscore(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = null;
        // Select All Query
        selectQuery = "SELECT * FROM " + TABLE_QUESTION6;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                labels.add(cursor.getString(1));
                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
                labels.add(cursor.getString(5));
                labels.add(cursor.getString(6));
                labels.add(cursor.getString(7));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    public List<String> getAllLabels11(){
        List<String> labels = new ArrayList<String>();
        String selectQuery = null;
        // Select All Query
        selectQuery = "SELECT * FROM " + TABLE_QUESTION6 ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(1));
                labels.add(cursor.getString(2));
                labels.add(cursor.getString(3));
                labels.add(cursor.getString(4));
                labels.add(cursor.getString(5));
                labels.add(cursor.getString(6));
                labels.add(cursor.getString(7));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
    /**
     * Initializes database. Creates database if doesn't exist.
     */
    private void initialize() {
        if (databaseExists()) {
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
            if (DATABASE_VERSION != dbVersion) {
                File dbFile = mContext.getDatabasePath(DATABASE_NAME);
                if (!dbFile.delete()) {
                    Log.w("TAG", "Unable to update database");
                }
            }
        }
        if (!databaseExists()) {
            createDatabase();
        }
    }

    /**
     * Returns true if database file exists, false otherwise.
     * @return
     */
    private boolean databaseExists() {
        File dbFile = mContext.getDatabasePath(DATABASE_NAME);
        return dbFile.exists();
    }

    /**
     * Creates database by copying it from assets directory.
     */
    private void createDatabase() {
        String parentPath = mContext.getDatabasePath(DATABASE_NAME).getParent();
        String path = mContext.getDatabasePath(DATABASE_NAME).getPath();

        File file = new File(parentPath);
        if (!file.exists()) {
            if (!file.mkdir()) {
                Log.w("TAG", "Unable to create database directory");
                return;
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = mContext.getAssets().open(DATABASE_NAME);
            os = new FileOutputStream(path);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            SharedPreferences prefs = PreferenceManager
                    .getDefaultSharedPreferences(mContext);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(SP_KEY_DB_VER, DATABASE_VERSION);
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DATABASE_VERSION=1;
    }
    public Boolean updatescore(int score, String pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

         if(pos.equals("1")) {
             contentValues.put(subject1, String.valueOf(score));
             db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});
             return true;
         }
        else if(pos.equals("2")){
            contentValues.put(subject2, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}
        else if(pos.equals("3")){
            contentValues.put(subject3, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}
        else if(pos.equals("4")){
            contentValues.put(subject4, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}
         else if(pos.equals("5")){
             contentValues.put(subject6, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}

         else if(pos.equals("6")){
             contentValues.put(subject7, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}

         else if(pos.equals("7")){
             contentValues.put(subject8, String.valueOf(score));
        db.update(TABLE_QUESTION6, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(1)});return true;}
else
        return false;
    }

}