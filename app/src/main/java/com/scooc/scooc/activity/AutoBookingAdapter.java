package com.scooc.scooc.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import com.scooc.scooc.R;

/**
 * Created by Belal on 9/30/2017.
 */

public class AutoBookingAdapter extends ArrayAdapter<Employee> {

    Context mCtx;
    int listLayoutRes;
    List<Employee> employeeList;
    SQLiteDatabase mDatabase;

    public AutoBookingAdapter(Context mCtx, int listLayoutRes, List<Employee> employeeList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, employeeList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.employeeList = employeeList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Employee employee = employeeList.get(position);


        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewSalary = view.findViewById(R.id.textViewSalary);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);
        TextView textViewEndDate = view.findViewById(R.id.textViewEndDate);


        textViewName.setText(employee.getSource());
        textViewDept.setText(employee.getDest());
        textViewSalary.setText("PickupTime:"+String.valueOf(employee.getPickuptime()));
        textViewJoiningDate.setText("StartDate is"+employee.getStartdate());
        textViewEndDate.setText("EndDate is "+employee.getEnddate());


        Button buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        Button buttonEdit = view.findViewById(R.id.buttonEditEmployee);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateEmployee(employee);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM autobookingtable WHERE id = ?";
                        mDatabase.execSQL(sql, new Integer[]{employee.getId()});
                        reloadEmployeesFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateEmployee(final Employee employee) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);


        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
        final EditText editTextPicktime = view.findViewById(R.id.editTextPicktime);
        final EditText editTextStartdate = view.findViewById(R.id.editTextStartdate);
        final EditText editTextEnddate = view.findViewById(R.id.editTextenddate);


        // final Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(employee.getSource());
        editTextSalary.setText(String.valueOf(employee.getDest()));
        editTextPicktime.setText(String.valueOf(employee.getPickuptime()));
        editTextStartdate.setText(String.valueOf(employee.getStartdate()));
        editTextEnddate.setText(String.valueOf(employee.getEnddate()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String source = editTextName.getText().toString().trim();
                String des = editTextSalary.getText().toString().trim();
                String picktime = editTextPicktime.getText().toString().trim();
                String startdatetime = editTextStartdate.getText().toString().trim();
                String enddate = editTextEnddate.getText().toString().trim();

                //String dept = spinnerDepartment.getSelectedItem().toString();

                if (source.isEmpty()) {
                    editTextName.setError("Source can't be blank");
                    editTextName.requestFocus();
                    return;
                }

                if (des.isEmpty()) {
                    editTextSalary.setError("Destination can't be blank");
                    editTextSalary.requestFocus();
                    return;
                }
                if (picktime.isEmpty()) {
                    editTextPicktime.setError("Pickuptime can't be blank");
                    editTextPicktime.requestFocus();
                    return;
                }   if (startdatetime.isEmpty()) {
                    editTextStartdate.setError("StartDate can't be blank");
                    editTextStartdate.requestFocus();
                    return;
                }   if (enddate.isEmpty()) {
                    editTextEnddate.setError("Enddate can't be blank");
                    editTextEnddate.requestFocus();
                    return;
                }
                //   (source, destination, pickuptime, startdate,enddate,mulintent
                String sql = "UPDATE autobookingtable \n" +
                        "SET source = ?, \n" +
                        "destination = ?, \n" +
                        "pickuptime = ?, \n" +
                        "startdate = ?, \n" +
                        "enddate = ?\n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{source, des,picktime,startdatetime,enddate, String.valueOf(employee.getId())});
                Toast.makeText(mCtx, "Ride Updated", Toast.LENGTH_SHORT).show();
                reloadEmployeesFromDatabase();

                dialog.dismiss();
            }
        });
    }

    private void reloadEmployeesFromDatabase() {
        Cursor cursorEmployees = mDatabase.rawQuery("SELECT * FROM autobookingtable", null);
        if (cursorEmployees.moveToFirst()) {
            employeeList.clear();
            do {
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
        cursorEmployees.close();
        notifyDataSetChanged();
    }

}
