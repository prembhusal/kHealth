package org.knoesis.health.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.ibm.icu.util.Calendar;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DatabaseSummaryHelper extends SQLiteOpenHelper {

    public static String DB_PATH = "/data/data/org.knoesis.health/databases/";
    public static String DB_NAME = "sensors_db";
    public static final int DB_VERSION = 1;
    List<Integer> s;
    public static final String TB_USER = "questions_and_answers";

    private SQLiteDatabase myDB;
    private Context context;


    public DatabaseSummaryHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

    @Override
    public synchronized void close() {
        if (myDB != null) {
            myDB.close();
        }
        super.close();
    }

    public Cursor getAllRows() throws SQLException {
        String[] columns = new String[]{"id _id", "answer"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true, TB_USER, columns, null, null, null, null, null, null);

        if (c != null && c.getCount() > 0) {
            c.moveToFirst();

        }

        return c;


    }

    public Cursor getAllIds() throws SQLException {
        String[] columns = new String[]{"question"};
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT question,id _id from questions_ids where id in (select id from questions_and_answers)", null);
        if (c != null && c.getCount() > 0) {

            c.moveToFirst();
        }


        return c;


    }


    //Counts the number of days answered by the patient
    public long getCountOfAnsweredQuestionsPerDay(String user) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TB_USER + " where user = '" + user + "'", null);

        //return DatabaseUtils.queryNumEntries(db, TB_USER);
        int cnt = 0;
        HashSet<String> array = new HashSet<String>();
        if (c != null) {
            while (c.moveToNext()) {
                String uname = c.getString(c.getColumnIndex("timestamp"));
                String arr[] = uname.split(" ");
                array.add(arr[0]);
            }
        }
        c.close();
        return array.size();


    }

    //Counts the number of days answered by the patient
    public String dailyEmailCheck(int condition_number_of_days, String user) {
        long mdiff, convertdifftodays;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TB_USER + " where user = '" + user + "'", null);

        //return DatabaseUtils.queryNumEntries(db, TB_USER);
        int cnt = 0;
        HashSet<String> array = new HashSet<String>();
        if (c != null) {
            while (c.moveToNext()) {
                String uname = c.getString(c.getColumnIndex("timestamp"));
                //String arr[] = uname.split(" ");
                array.add(uname);
            }
        }
        Object[] arrayTemp = array.toArray();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            for (int i = 0; i < array.size(); i++) {
                if ((i + 1) != array.size()) {

                    System.out.println("First date" + arrayTemp[i] + "\n Second date" + arrayTemp[i + 1]);

                    Date d1 = formatter.parse(arrayTemp[i].toString());
                    Date d2 = formatter.parse(arrayTemp[i + 1].toString());

                    //Converting the date to calendar object
                    Calendar md1 = Calendar.getInstance();
                    Calendar md2 = Calendar.getInstance();

                    md1.setTime(d1);
                    md2.setTime(d2);

                    //calculating the difference in milliseconds and converting that into days
                    mdiff = md1.getTimeInMillis() - md2.getTimeInMillis();
                    convertdifftodays = TimeUnit.DAYS.convert(mdiff, TimeUnit.MILLISECONDS);

                    if (Math.abs(convertdifftodays) < 2) {

                        return "success";
                    } else {

                        return "failure";

                    }


                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {

            System.out.println("caught it");


        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        c.close();
        return "null";


    }

    //Counts the number of days answered by the patient
    public String dailyEmailCheck2(int condition_number_of_days, String user) {
        long mdiff, convertdifftodays;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TB_USER + " where user = '" + user + "' order by timestamp DESC LIMIT 0,1", null);

        //return DatabaseUtils.queryNumEntries(db, TB_USER);
        int cnt = 0;
        HashSet<String> array = new HashSet<String>();
        if (c != null) {
            while (c.moveToNext()) {
                String uname = c.getString(c.getColumnIndex("timestamp"));
                //String arr[] = uname.split(" ");
                array.add(uname);
            }
        }
        c.close();

        Object[] arrayTemp = array.toArray();
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        try {
            d1 = formatter.parse(arrayTemp[0].toString());
            Log.d("Date", d1 + "");
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //Converting the date to calendar object
        Calendar md1 = Calendar.getInstance();
        Calendar md2 = Calendar.getInstance();

        md1.setTime(d1);

        System.out.println("latest in table" + formatter.format(md1.getTime()) + " current " + formatter.format(md2.getTime()));


        //calculating the difference in milliseconds and converting that into days
        mdiff = md1.getTimeInMillis() - md2.getTimeInMillis();
        Log.d("md1", md1 + "");
        Log.d("Diff", Math.abs(mdiff) + "");
        convertdifftodays = TimeUnit.DAYS.convert(mdiff, TimeUnit.MILLISECONDS);


        Log.w("number of days", Math.abs(convertdifftodays) + "");
        int hrs = 1000 * 60 * 1;

        if (Math.abs(convertdifftodays) < condition_number_of_days) {
            Log.d("On failure", "failure");
            return "failure";
        } else {
            Log.d("On success", "success");
            return "success";
        }


//        if (hrs <= Math.abs(mdiff))
//
//        {
//            Log.d("On success", "success");
//            return "success";
//        } else
//
//        {
//            Log.d("On failure", "failure");
//            return "failure";
//
//        }

    }


    public int getAll(String user, int id, int answer) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();
        int x = 0;


        Cursor c = db.rawQuery("select count(id) from " + TB_USER + " where user='" + user + "' and id=" + id + " and answer=" + answer + ";", null);

        int y = c.getCount();

        if (y == 0) {
            return 0;
        } else {
            if (c.moveToFirst())

            {
                x = c.getInt(0);
            }
            c.close();
            return x;

        }


    }

    public int getAllBetween(String user, int id, int answer, Timestamp begin, Timestamp end) {
        int count = 0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select count(id) from " + TB_USER + " where user='" + user + "' and id=" + id + " and answer=" + answer + " and timestamp between '" + begin.toString() + "' and '" + end.toString() + "';", null);
        int numOfRows = cursor.getCount();
        if (numOfRows == 0) {
            count = 0;
        } else {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    public int getAll2(String user1, int id, String a, int answer) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();
        int x = 0;

        Cursor c = db.rawQuery("select count(id) from " + TB_USER + " where user='" + user1 + "' and id=" + id + " and answer" + a + answer + ";", null);
        int y = c.getCount();

        if (y == 0) {

            return 0;
        } else {
            if (c.moveToFirst()) {
                x = c.getInt(0);

            }

            c.close();
            return x;
        }


    }

    public int getAll2Between(String user1, int id, String a, int answer, Timestamp begin, Timestamp end) throws SQLException {

        SQLiteDatabase db = this.getReadableDatabase();
        int x = 0;

        Cursor c = db.rawQuery("select count(id) from " + TB_USER + " where user='" + user1 + "' and id=" + id + " and answer" + a + answer + " and timestamp between '" + begin.toString() + "' and '" + end.toString() + "';", null);
        int y = c.getCount();

        if (y == 0) {

            return 0;
        } else {
            if (c.moveToFirst()) {
                x = c.getInt(0);

            }

            c.close();
            return x;
        }


    }


    public String getMinDate(String user) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String date = "";

        Cursor cursor = db.rawQuery("select MIN(timestamp) from " + TB_USER + " where user='" + user + "';", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            date = cursor.getString(0);
        }

        return date;
    }

    public String getMaxDate(String user) throws SQLException {
        SQLiteDatabase db = this.getReadableDatabase();

        String date = "";

        Cursor cursor = db.rawQuery("select MAX(timestamp) from " + TB_USER + " where user='" + user + "';", null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            date = cursor.getString(0);
        }

        return date;
    }


    public boolean isTableExists(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (tableName == null || db == null || !db.isOpen()) {
            return false;
        }
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM sqlite_master WHERE type = ? AND name = ?", new String[]{"table", tableName});
        if (!cursor.moveToFirst()) {
            return false;
        }
        int count = cursor.getInt(0);
        cursor.close();
        return count > 0;
    }


//	public  Cursor gethttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=1139AllUsers(int j){
//		List<String> listQuestions = new ArrayList<String>();
//		
//		SQLiteDatabase db = this.getWritableDatabase();
//		Cursor c = null;
//		
//		try {
//			c = db.rawQuery("SELECT  id _id FROM " + TB_USER , null);
//			
//			if(c == null) return null;
//			
//			String name;
//			c.moveToFirst();
//			
//			
//		} catch (Exception e) {
//			Log.e("tle99", e.getMessage());
//		}
//		
//		
//		db.close();		
//		
//		return c;
//	}
//	

    /***
     * Open database
     *
     * @throws SQLException
     */
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    /***
     * Copy database from source code assets to device
     *
     * @throws IOException
     */
    public void copyDataBase() throws IOException {
        try {

            InputStream myInput = context.getAssets().open(DB_NAME);

            String outputFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outputFileName);

            byte[] buffer = new byte[1024];
            int length;

            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception e) {
            Log.e("tle99 - copyDatabase", e.getMessage());
        }

    }

    /***
     * Check if the database doesn't exist on device, create new one
     *
     * @throws IOException
     */
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();

        if (dbExist) {

        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                Log.e("tle99 - create", e.getMessage());
            }
        }
    }

    // ---------------------------------------------
    // PRIVATE METHODS
    // ---------------------------------------------

    /***
     * Check if the database is exist on device or not
     *
     * @return
     */
    private boolean checkDataBase() {
        SQLiteDatabase tempDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            tempDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        } catch (SQLiteException e) {
            Log.e("tle99 - check", e.getMessage());
        }
        if (tempDB != null)
            tempDB.close();
        return tempDB != null ? true : false;
    }


}
