package com.example.eventmanager.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanager.R;
import com.example.eventmanager.adapter.CustomAdapter;
import com.example.eventmanager.data.DataHelper;
import com.example.eventmanager.util.VerticalSpacingItemDecorator;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Fragment_current extends Fragment {

    View view;

    DataHelper dataHelper;



    ArrayList<String> eventIDs,
            customerNames,
            venues,
            venueTypes,
            startDates,
            endDates,
            startTimes,
            endTimes,
            totalGuests,
            foodItems,
            foodCosts,
            venueCosts,
            totalCosts;

    CustomAdapter customAdapter;


    RecyclerView rvCurrent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_current, container, false);

        rvCurrent = view.findViewById(R.id.rvCurrent);
        dataHelper = new DataHelper(view.getContext());

        eventIDs = new ArrayList<>();
        customerNames = new ArrayList<>();
        venues = new ArrayList<>();
        venueTypes = new ArrayList<>();
        startDates = new ArrayList<>();
        endDates = new ArrayList<>();
        startTimes = new ArrayList<>();
        endTimes = new ArrayList<>();
        totalGuests = new ArrayList<>();
        foodItems = new ArrayList<>();
        foodCosts = new ArrayList<>();
        venueCosts = new ArrayList<>();
        totalCosts = new ArrayList<>();
        setAndGetData();
        return view;
    }

    private void setAndGetData(){
        storeDataInArrays();
        setViewAdapter();
    }
    private void storeDataInArrays(){
        try {
            eventIDs.clear();
            customerNames.clear();
            venues.clear();
            venueTypes.clear();
            startDates.clear();
            endDates.clear();
            startTimes.clear();
            endTimes.clear();
            totalGuests.clear();
            foodItems.clear();
            foodCosts.clear();
            venueCosts.clear();
            totalCosts.clear();
            Cursor cursor = dataHelper.viewAll();
            int count = 0;
            if(cursor.moveToFirst()){
                do{

                    String date = calculateDate(cursor.getString(4));
                    if(date == "current") {
                        count ++;

                        eventIDs.add(cursor.getString(0));
                        customerNames.add(cursor.getString(1));
                        venues.add(cursor.getString(2));
                        venueTypes.add(cursor.getString(3));
                        startDates.add(cursor.getString(4));
                        endDates.add(cursor.getString(5));
                        startTimes.add(cursor.getString(6));
                        endTimes.add(cursor.getString(7));
                        totalGuests.add(cursor.getString(8));
                        foodItems.add(cursor.getString(9));
                        foodCosts.add(cursor.getString(10));
                        venueCosts.add(cursor.getString(11));
                        totalCosts.add(cursor.getString(12));
                    }
                }
                while (cursor.moveToNext());
            }
            else{
                Toast.makeText(view.getContext(), "Your data is empty", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(view.getContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void setViewAdapter(){
        customAdapter = new CustomAdapter(view.getContext(),
                eventIDs, customerNames, venues, venueTypes, startDates, endDates, startTimes,
                endTimes, totalGuests,
                foodItems, foodCosts, venueCosts, totalCosts);
        rvCurrent.setAdapter(customAdapter);
        VerticalSpacingItemDecorator verticalSpacingItemDecorator = new VerticalSpacingItemDecorator(10);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rvCurrent);
        rvCurrent.addItemDecoration(verticalSpacingItemDecorator);
        rvCurrent.addItemDecoration(verticalSpacingItemDecorator);
        rvCurrent.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("System Message");
            builder.setCancelable(false);
            builder.setMessage("Would you like to Delete this Data?");
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    TextView myID = viewHolder.itemView.findViewById(R.id.txtID);
                    dataHelper.deleteEvent(Integer.parseInt(myID.getText().toString()));
                    Toast.makeText(getContext(), "Deleted: " +myID.getText(), Toast.LENGTH_SHORT).show();
                    setAndGetData();
                    customAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    customAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    };

    @SuppressLint("NewApi")
    private int getDifference(String date){
        int diff = 0;
        Date firstDate;
        Date secondDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try{
            firstDate = sdf.parse(String.valueOf(LocalDateTime.now()));
            secondDate = sdf.parse(date);

            long diffInMills = Math.abs(secondDate.getTime()) - firstDate.getTime();
            diff = (int) TimeUnit.DAYS.convert(diffInMills, TimeUnit.MILLISECONDS);
        }
        catch (Exception ex){

        }

        return diff;
    }

    private String calculateDate(String date){
        if(getDifference(date) < 0){
            //return past
            return "past";
        }
        else if(getDifference(date) >0){
            //upcoming
            return "upcoming";
        }
        else if(getDifference(date) == 0){
            //present
            return "current";
        }
        else{
            return "date does not coincide";
        }

    }
}
