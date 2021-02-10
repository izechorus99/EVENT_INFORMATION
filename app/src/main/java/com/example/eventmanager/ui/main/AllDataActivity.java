package com.example.eventmanager.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.eventmanager.R;
import com.example.eventmanager.Secondary_activity;

public class AllDataActivity extends AppCompatActivity {

    TextView txtID, txtVenue, txtVenueType, txtStartDate, txtEndDate, txtStartTime, txtEndTime, txtFood, txtGuest, txtVenueCost, txtFoodCost, txtTotalCost;
    Button btnUpdate;
    int id;
    String customerName;
    String venue;
    String venueType;
    String startDate;
    String endDate;
    String startTime;
    String endTime;
    String Food;
    String Guest;
    String VenueCost;
    String FoodCost;
    String TotalCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_data);
        txtID = findViewById(R.id.txtAllID);
        txtVenue = findViewById(R.id.txtVenue);
        txtVenueType = findViewById(R.id.txtVenueType);
        txtStartDate = findViewById(R.id.txtStartDate);
        txtEndDate = findViewById(R.id.txtEndDate);
        txtStartTime = findViewById(R.id.txtStartTime);
        txtEndTime = findViewById(R.id.txtEndTime);
        txtFood = findViewById(R.id.txtFood);
        txtGuest = findViewById(R.id.txtGuest);
        txtVenueCost = findViewById(R.id.txtVenueCost);
        txtFoodCost = findViewById(R.id.txtFoodCost);
        txtTotalCost = findViewById(R.id.txtTotalCost);

        btnUpdate = findViewById(R.id.btnUpdate);

        getExtraValues(savedInstanceState);

        btnUpdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(), Secondary_activity.class);
                i.putExtra("id", id);
                i.putExtra("customerName", customerName);
                i.putExtra("venue", venue);
                i.putExtra("venueType", venueType);
                i.putExtra("startDate", startDate);
                i.putExtra("endDate", endDate);
                i.putExtra("startTime", startTime);
                i.putExtra("endTime", endTime);
                i.putExtra("Food", Food);
                i.putExtra("Guest", Guest);
                i.putExtra("VenueCost", VenueCost);
                i.putExtra("FoodCost", FoodCost);
                i.putExtra("TotalCost", TotalCost);
                startActivity(i);
            }
        });


    }

    private void getExtraValues(Bundle savedInstanceState){
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = 0;
                customerName = "";
                venue = "";
                venueType = "";
                startDate = "";
                endDate = "";
                startTime = "";
                endTime = "";
                Food = "";
                Guest = "";
                VenueCost = "";
                FoodCost = "";
                TotalCost = "";
            }
            else{
                id = extras.getInt("id");
                customerName = extras.getString("customerName");
                venue = extras.getString("venue");
                venueType = extras.getString("venueType");
                startDate = extras.getString("startDate");
                endDate = extras.getString("endDate");
                startTime = extras.getString("startTime");
                endTime = extras.getString("endTime");
                Food = extras.getString("Food");
                Guest = extras.getString("Guest");
                VenueCost = extras.getString("VenueCost");
                FoodCost = extras.getString("FoodCost");
                TotalCost = extras.getString("TotalCost");
            }
        }
        else{
            id = (int) savedInstanceState.getSerializable("id");
            customerName = (String) savedInstanceState.getSerializable("customerName");
            venue = (String) savedInstanceState.getSerializable("venue");
            venueType = (String) savedInstanceState.getSerializable("venueType");
            startDate = (String) savedInstanceState.getSerializable("startDate");
            endDate = (String) savedInstanceState.getSerializable("endDate");
            startTime = (String) savedInstanceState.getSerializable("startTime");
            endTime = (String) savedInstanceState.getSerializable("endTime");
            Food = (String) savedInstanceState.getSerializable("Food");
            Guest = (String) savedInstanceState.getSerializable("Guest");
            VenueCost = (String) savedInstanceState.getSerializable("VenueCost");
            FoodCost = (String) savedInstanceState.getSerializable("FoodCost");
            TotalCost = (String) savedInstanceState.getSerializable("TotalCost");
        }

        if(id != 0) {
            txtID.setText(id + "");
            txtVenue.setText(venue);
            txtVenueType.setText(venueType);
            txtStartDate.setText(startDate);
            txtEndDate.setText(endDate);
            txtStartTime.setText(startTime);
            txtEndTime.setText(endTime);
            txtFood.setText(Food);
            txtGuest.setText(Guest);
            txtVenueCost.setText(VenueCost);
            txtFoodCost.setText(FoodCost);
            txtTotalCost.setText(TotalCost);
        }
    }

}