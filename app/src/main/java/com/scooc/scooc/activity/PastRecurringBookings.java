package com.scooc.scooc.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import com.scooc.scooc.R;
import com.scooc.scooc.activity.sample.DatabaseHelper;

import static com.scooc.scooc.activity.sample.DatabaseHelper.COL_1;

public class PastRecurringBookings extends AppCompatActivity {
    ImageView delete;
    DatabaseHelper myDb;
    ListAdapter adapter;
    private AlertDialog.Builder build;
    private ArrayList<String> userId = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_recurring_bookings);
        DatabaseHelper db = new DatabaseHelper(this);
        myDb = new DatabaseHelper(this);
        delete= findViewById(R.id.delete);
        final ArrayList<HashMap<String, String>> userList = db.GetUsers();
        ListView lv = (ListView) findViewById(R.id.user_list);

         adapter = new SimpleAdapter(PastRecurringBookings.this, userList, R.layout.list_row,new String[]{"SOURCE","DESTINATION","STARTDATE","ENDDATE","PICKUPTIME",}, new int[]{R.id.name, R.id.designation, R.id.startDate,R.id.enddate,R.id.location});
        lv.setAdapter(adapter);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

                build = new AlertDialog.Builder(PastRecurringBookings.this);
                build.setTitle("Delete " + userList.get(0));
                build.setMessage("Do you want to delete ?");
                build.setPositiveButton("Yes",new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        Toast.makeText( getApplicationContext(),
                                userList.get(0) + " "
                                        + userList.get(1)
                                        + " is deleted.",Toast.LENGTH_LONG).show();

                     /*   myDb.deleteData(
                                myDb.TABLE_NAME,
                                myDb.c.KEY_ID + "="
                                        + userId.get(arg2), null);*/

                      /*  myDb.deleteData(
                                myDb.TABLE_NAME,
                                myDb.deleteData(COL_1) + "="
                                        + userId.get(arg2), null);*/
                       // displayData();
                        dialog.cancel();
                    }
                });

                build.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = build.create();
                alert.show();

                return true;
            }
        });
    }
/*        Button back = (Button)findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(DetailsActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });*/
    }
/*    public void DeleteData() {
        delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id=adapter.getItemId(adapter );

                        Integer deletedRows = myDb.deleteData();
                        if(deletedRows > 0)
                            Toast.makeText(PastRecurringBookings.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(PastRecurringBookings.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}*/
//long click to delete data
