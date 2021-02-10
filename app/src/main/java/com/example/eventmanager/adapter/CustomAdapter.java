package com.example.eventmanager.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eventmanager.R;
import com.example.eventmanager.data.DataHelper;
import com.example.eventmanager.ui.main.AllDataActivity;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {


    private int lastPosition = -1;
    int row_index = -1;
    Context context;
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
    DataHelper dataHelper;

    public CustomAdapter(Context context,
                         ArrayList eventIDs,
                         ArrayList customerNames,
                         ArrayList venues,
                         ArrayList venueTypes,
                         ArrayList startDates,
                         ArrayList endDates,
                         ArrayList startTimes,
                         ArrayList endTimes,
                         ArrayList totalGuests,
                         ArrayList foodItems,
                         ArrayList foodCosts,
                         ArrayList venueCosts,
                         ArrayList totalCosts){
        this.context = context;
        this.eventIDs = eventIDs;
        this.customerNames = customerNames;
        this.venues = venues;
        this.venueTypes = venueTypes;
        this.startDates = startDates;
        this.endDates = endDates;
        this.startTimes = startTimes;
        this.endTimes = endTimes;
        this.totalGuests = totalGuests;
        this.foodItems = foodItems;
        this.foodCosts = foodCosts;
        this.venueCosts = venueCosts;
        this.totalCosts = totalCosts;
    }
    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.event_row, parent, false);
        dataHelper = new DataHelper(context);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        showData(holder, position);

        holder.cardEventRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO; go to new activity
                int eventID = Integer.parseInt(holder.txtID.getText().toString());
                String customerName = holder.txtCustomerName.getText().toString();
                String venue = holder.txtVenue.getText().toString();
                String venueType = holder.txtVenueType.getText().toString();
                String startDate = holder.txtStartDate.getText().toString();
                String endDate = holder.txtEndDate.getText().toString();
                String startTime = holder.txtStartTime.getText().toString();
                String endTime = holder.txtEndTime.getText().toString();
                String Food = holder.txtFoodItem.getText().toString();
                String Guest = holder.txtTotalGuest.getText().toString();
                String VenueCost = holder.txtVenueCost.getText().toString();
                String FoodCost = holder.txtFoodCost.getText().toString();
                String TotalCost = holder.txtTotalCost.getText().toString();

                Intent i = new Intent(context, AllDataActivity.class);
                i.putExtra("id", eventID);
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
                context.startActivity(i);
            }
        });
    }

    private void showData(MyViewHolder holder, int position) {
        holder.txtID.setText(String.valueOf(eventIDs.get(position)));
        holder.txtCustomerName.setText(String.valueOf(customerNames.get(position)));
        holder.txtVenue.setText(String.valueOf(venues.get(position)));
        holder.txtVenueType.setText(String.valueOf(venueTypes.get(position)));
        holder.txtStartDate.setText(String.valueOf(startDates.get(position)));
        holder.txtEndDate.setText(String.valueOf(endDates.get(position)));
        holder.txtStartTime.setText(String.valueOf(startTimes.get(position)));
        holder.txtEndTime.setText(String.valueOf(endTimes.get(position)));
        holder.txtTotalGuest.setText(String.valueOf(totalGuests.get(position)));
        holder.txtFoodItem.setText(String.valueOf(foodItems.get(position)));
        holder.txtFoodCost.setText(String.valueOf(foodCosts.get(position)));
        holder.txtVenueCost.setText(String.valueOf(venueCosts.get(position)));
        holder.txtTotalCost.setText(String.valueOf(totalCosts.get(position)));
    }

    @Override
    public int getItemCount() {
        return eventIDs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtID,
        txtCustomerName,
        txtVenue,
        txtVenueType,
        txtStartDate,
        txtEndDate,
        txtStartTime,
        txtEndTime,
        txtTotalGuest,
        txtFoodItem,
        txtFoodCost,
        txtVenueCost,
        txtTotalCost;
        CardView cardEventRow;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardEventRow = itemView.findViewById(R.id.cardEventRow);
            txtID = itemView.findViewById(R.id.txtID);
            txtCustomerName = itemView.findViewById(R.id.txtCustomerName);
            txtVenue = itemView.findViewById(R.id.txtVenue);
            txtVenueType = itemView.findViewById(R.id.txtVenueType);
            txtStartDate = itemView.findViewById(R.id.txtStartDate);
            txtEndDate = itemView.findViewById(R.id.txtEndDate);
            txtStartTime = itemView.findViewById(R.id.txtStartTime);
            txtEndTime = itemView.findViewById(R.id.txtEndTime);
            txtTotalGuest = itemView.findViewById(R.id.txtTotalGuest);
            txtFoodItem = itemView.findViewById(R.id.txtFoodItem);
            txtFoodCost = itemView.findViewById(R.id.txtFoodCost);
            txtVenueCost = itemView.findViewById(R.id.txtVenueCost);
            txtTotalCost = itemView.findViewById(R.id.txtTotalCost);
        }
    }



}
