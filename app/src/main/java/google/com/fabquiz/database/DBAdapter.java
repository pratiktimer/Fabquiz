package google.com.fabquiz.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public  class DBAdapter extends SQLiteOpenHelper {

    private final Context myContext;
    public int id;

    private static  int DATABASE_VERSION = 1;

    //Question student = new Question();
    // Database Name
    public static final String DATABASE_NAME = "Userdb";


    // Table name
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_QUESTION2 = "question2";
    private static final String TABLE_QUESTION3 = "question3";
    private static final String TABLE_QUESTION4 = "question4";
    private static final String TABLE_QUESTION5 = "question5";

    String DB_PATH = "/data/data/google.com.fabquiz/databases/";




    public SQLiteDatabase myDatabase;

    public DBAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
    }




    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DATABASE_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {

        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DATABASE_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);

    }







    @Override
    public void onCreate(SQLiteDatabase db) {



    }
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        DATABASE_VERSION=1;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        boolean dbExist=checkDataBase();
        if(dbExist) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION2);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION3);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION4);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION5);

        }
        try {
            copyDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        openDataBase();

    }


}






