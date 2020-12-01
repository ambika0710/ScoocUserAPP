package com.scooc.scooc.activity.sample;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import com.scooc.scooc.R;
import com.scooc.scooc.activity.AutoBooking;
import com.scooc.scooc.activity.AutoBookingAdapter;
import com.scooc.scooc.activity.Employee;

public class ViewAutoBooking extends AppCompatActivity {

    private static final String TAG ="" ;
    List<Employee> employeeList;
    SQLiteDatabase mDatabase;
    ListView listViewEmployees;
    AutoBookingAdapter adapter;
    Button addnewride;
    boolean is_Exist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        // checkForTableExists(mDatabase,"recurringtable");
        // isDbPresent();
       /* checkDataBase();
            if(checkDataBase()== false)
            {
                Intent intent  = new Intent(EmployeeActivity.this,RecurringRidesActivity.class);
                startActivity(intent);
            }*/
        listViewEmployees = (ListView) findViewById(R.id.listViewEmployees);
        addnewride = (Button)findViewById(R.id.buttonnewone);
        addnewride.setText("BOOK NEW AUTO BOOKING");

        addnewride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(ViewAutoBooking.this, AutoBooking.class);
                startActivity(intent);
            }
        });

        employeeList = new ArrayList<>();

        //opening the database
        showEmployeesFromDatabase();
        //this method will display the employees in the list

    }
    /**
     * Check if the database exist and can be read.
     *
     * @return true if it exists and can be read, false if it doesn't
     */


    /* open database, if doesn't exist, create it */
/*    private boolean checkForTableExists(SQLiteDatabase db, String table){
        String sql = "SELECT name FROM recurringdb WHERE type='table' AND name='"+table+"'";
        Cursor mCursor = db.rawQuery(sql, null);
        if (mCursor.getCount() > 0) {
            return true;
        }
        mCursor.close();
        return false;
    }*/


    private boolean isDbPresent() {
        Log.v(TAG, "is DB present Entry!!!");
        boolean checkFlag = true;
        SQLiteDatabase mDatabase;
        Context context = getApplicationContext();
        String testPath = context.getDatabasePath(AutoBooking.DATABASE_NAME) + AutoBooking.DATABASE_NAME;
        try{
            mDatabase = SQLiteDatabase.openDatabase(testPath, null,
                    SQLiteDatabase.OPEN_READWRITE);
        }
        catch(SQLiteException sqlException){
            Log.v(TAG, "DB absent");
            checkFlag=false;
        }
        Log.v(TAG, "is DB present Exit!!!");
        return checkFlag;
    }// End of Method
    /*   private boolean checkDataBase() {
         //  SQLiteDatabase checkDB = null;
           try {
               mDatabase = SQLiteDatabase.openDatabase(RecurringRidesActivity.DATABASE_NAME, null,
                       SQLiteDatabase.OPEN_READONLY);
             //  checkDB.close();
           } catch (SQLiteException e) {
               System.out.println("Not Exist Database");

               // database doesn't exist yet.
           }
           return mDatabase != null;
       }*/
    private void showEmployeesFromDatabase() {
        mDatabase = openOrCreateDatabase(AutoBooking.DATABASE_NAME, MODE_PRIVATE, null);

        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM autobookingtable", null);

        //if the cursor has some data
        if (cursorEmployees.moveToFirst()) {
            //looping through all the records
            do {
                //pushing each record in the employee list
                employeeList.add(new Employee(
                        cursorEmployees.getInt(0),
                        cursorEmployees.getString(1),
                        cursorEmployees.getString(2),
                        cursorEmployees.getString(3),
                        cursorEmployees.getString(4),
                        cursorEmployees.getString(5),
                        cursorEmployees.getString(6)
                ));
            } while (cursorEmployees.moveToNext());
        }
        //closing the cursor
        cursorEmployees.close();
        boolean ans = employeeList.isEmpty();
        if(ans == true){
            Intent intent  = new Intent(ViewAutoBooking.this, AutoBooking.class);
            startActivity(intent);
        }

        //creating the adapter object
        adapter = new AutoBookingAdapter(this, R.layout.list_layout_employee, employeeList, mDatabase);

        //adding the adapter to listview
        listViewEmployees.setAdapter(adapter);

    }

}
