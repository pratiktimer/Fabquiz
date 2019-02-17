package google.com.fabquiz.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public  class DBAdapter2 extends SQLiteOpenHelper {

    private final Context myContext;
    public int id;

    private static  int DATABASE_VERSION = 1;

    Question student = new Question();
    // Database Name
    public static final String DATABASE_NAME = "pratiksha3";


    // Table name
    private static final String TABLE_QUESTION = "question";
    private static final String TABLE_QUESTION2 = "question2";
    private static final String TABLE_QUESTION3 = "question3";
    private static final String TABLE_QUESTION4 = "question4";
    private static final String TABLE_QUESTION5 = "question5";
    private static final String TABLE_QUESTION6 = "question6";
    private static final String TABLE_QUESTION7 = "question7";
    private static final String TABLE_QUESTION8 = "question8";

    String DB_PATH = "/data/data/google.com.fabquiz/databases/";

    // Table Columns names
    private static final String KEY_ID = "_id";
    private static final String KEY_QUESION = "question";
    private static final String subject1="subject1";
    private static final String subject2="subject2";
    private static final String subject3="subject3";
    private static final String subject4="subject4";
    private static final String KEY_ANSWER = "answer"; //correct option
    private static final String KEY_OPTA = "opta"; //option a
    private static final String KEY_OPTB = "optb"; //option b
    private static final String KEY_OPTC = "optc"; //option c
    private static final String KEY_OPTD = "optd"; //option d


    public SQLiteDatabase myDatabase;

    public DBAdapter2(Context context) {
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

    public int rowCount(String position) {
        int row = 0;
        String selectQuery = null;
        if (position.equals("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        } else if (position.equals("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        } else if (position.equals("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION3;
        } else if (position.equals("4")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION4;
        }
        else if (position.equals("6")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION6;
        }
        else if (position.equals("7")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION7;
        }
        else if (position.equals("8")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION8;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        row = cursor.getCount();
        return row;
    }

    public Boolean rowCount2(String position) {
        int row = 0;
        if (position.equals("1")) {
            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        } else if (position.equals("2")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        } else if (position.equals("3")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION3;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        } else if (position.equals("4")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION4;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        }
        else if (position.equals("6")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION6;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        }
        else if (position.equals("7")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION7;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        }
        else if (position.equals("8")) {

            String selectQuery = "SELECT  * FROM " + TABLE_QUESTION8;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            row = cursor.getCount();
        }
        if (row == 0) {
            return false;
        } else return true;

    }


    public List<Question> getAllQuestions(String position) {

        List<Question> quesList = new ArrayList<Question>();
        String selectQuery = null;
        // Select All Query
        if (position.equals("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        } else if (position.equals("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        } else if (position.equals("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION3;
        } else if (position.equals("4")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION4;
        }
        else if (position.equals("6")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION6;
        }
        else if (position.equals("7")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION7;
        }
        else if (position.equals("8")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION8;
        }
        myDatabase = this.getWritableDatabase();

        Cursor cursor = myDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQUESTION(cursor.getString(1));
                quest.setANSWER(cursor.getString(2));
                quest.setOptionA(cursor.getString(3));
                quest.setOptionB(cursor.getString(4));
                quest.setOptionC(cursor.getString(5));
                quest.setOptionD(cursor.getString(6));


                quesList.add(quest);

            } while (cursor.moveToNext());
        }
        // return quest list
        return quesList;
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

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public Boolean insertdata(String kEY_QUESION, String ans, String kEY_a, String kEY_b, String kEY_c, String kEY_d, String position) {
        long result = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_QUESION, kEY_QUESION);
        contentValues.put(KEY_ANSWER, ans);
        contentValues.put(KEY_OPTA, kEY_a);
        contentValues.put(KEY_OPTB, kEY_b);
        contentValues.put(KEY_OPTC, kEY_c);
        contentValues.put(KEY_OPTD, kEY_d);
        if (position.equals("1")) {
            result = db.insert(TABLE_QUESTION, null, contentValues);

        } else if (position.equals("2")) {
            result = db.insert(TABLE_QUESTION2, null, contentValues);

        }
        if (position.equals("3")) {
            result = db.insert(TABLE_QUESTION3, null, contentValues);

        } else if (position.equals("4")) {
            result = db.insert(TABLE_QUESTION4, null, contentValues);

        }
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public ArrayList<String> getAllLabels(String position) {
        ArrayList<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = null;
        // Select All Query
        if (position.equals("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        } else if (position.equals("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        } else if (position.equals("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION3;
        } else if (position.equals("4")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION4;
        }

        myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        myDatabase.close();

        // returning lables
        return labels;
    }
    public ArrayList<String> getAllLabels2(String position) {
        ArrayList<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = null;
        // Select All Query
        if (position.equals("1")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION;
        } else if (position.equals("2")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION2;
        } else if (position.equals("3")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION3;
        } else if (position.equals("4")) {
            selectQuery = "SELECT  * FROM " + TABLE_QUESTION4;
        }

        myDatabase = this.getWritableDatabase();
        Cursor cursor = myDatabase.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                labels.add(cursor.getString(1));
                //labels.add(cursor.getString(2));
                // labels.add(cursor.getString(2));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        myDatabase.close();

        // returning lables
        return labels;
    }

    public Question getEmployeeByProj(int value, String position) {


        myDatabase = this.getWritableDatabase();
        Cursor cursor = null;

        if (position.equals("1")) {
            cursor = myDatabase.query(
                    TABLE_QUESTION,
                    new String[]{KEY_QUESION, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC, KEY_OPTD},
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(value)},
                    null,
                    null,
                    null);
        } else if (position.equals("2")) {
            cursor = myDatabase.query(
                    TABLE_QUESTION2,
                    new String[]{KEY_QUESION, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC, KEY_OPTD},
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(value)},
                    null,
                    null,
                    null);
        }
        if (position.equals("3")) {
            cursor = myDatabase.query(
                    TABLE_QUESTION3,
                    new String[]{KEY_QUESION, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC, KEY_OPTD},
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(value)},
                    null,
                    null,
                    null);
        } else if (position.equals("4")) {
            cursor = myDatabase.query(
                    TABLE_QUESTION4,
                    new String[]{KEY_QUESION, KEY_ANSWER, KEY_OPTA, KEY_OPTB, KEY_OPTC, KEY_OPTD},
                    KEY_ID + " = ?",
                    new String[]{String.valueOf(value)},
                    null,
                    null,
                    null);
        }
        if (cursor != null && cursor.moveToFirst()) {
            student.QUESTION = cursor.getString(cursor.getColumnIndex(KEY_QUESION));
            student.ANSWER = cursor.getString(cursor.getColumnIndex(KEY_ANSWER));
            student.OptionA = cursor.getString(cursor.getColumnIndex(KEY_OPTA));
            student.OptionB = cursor.getString(cursor.getColumnIndex(KEY_OPTB));
            student.OptionC = cursor.getString(cursor.getColumnIndex(KEY_OPTC));
            student.OptionD = cursor.getString(cursor.getColumnIndex(KEY_OPTD));

        }
        return new Question(student.QUESTION, student.ANSWER, student.OptionA, student.OptionB, student.OptionC, student.OptionD);

    }



    public Boolean updatedata(int kEY_ID, String kEY_QUESION, String ans, String kEY_a, String kEY_b, String kEY_c, String kEY_d, String position) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID, kEY_ID);
        contentValues.put(KEY_QUESION, kEY_QUESION);
        contentValues.put(KEY_ANSWER, ans);
        contentValues.put(KEY_OPTA, kEY_a);
        contentValues.put(KEY_OPTB, kEY_b);
        contentValues.put(KEY_OPTC, kEY_c);
        contentValues.put(KEY_OPTD, kEY_d);

        if (position.equals("1")) {
            db.update(TABLE_QUESTION, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("2")) {
            db.update(TABLE_QUESTION2, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("3")) {
            db.update(TABLE_QUESTION3, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("4")) {
            db.update(TABLE_QUESTION4, contentValues, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        }
        return true;
    }

    public Integer deletedata(int kEY_ID, String position) {
        int c = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        if (position.equals("1")) {
            c = db.delete(TABLE_QUESTION, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("2")) {
            c = db.delete(TABLE_QUESTION2, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("3")) {
            c = db.delete(TABLE_QUESTION3, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        } else if (position.equals("4")) {
            c = db.delete(TABLE_QUESTION4, KEY_ID + "= ?", new String[]{String.valueOf(kEY_ID)});
        }

        return c;
    }



    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
            // this.getWritableDatabase();
        } else {
            this.getWritableDatabase();

            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
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






