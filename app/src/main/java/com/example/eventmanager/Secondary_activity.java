package com.example.eventmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventmanager.data.DataHelper;

import org.w3c.dom.Text;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Secondary_activity extends AppCompatActivity {

    private static final String TAG = "Secondary_activity";
    DataHelper eventDB;
    EditText etCustomerName,
            etStartDate,
            etEndDate,
            etStartTime,
            etEndTime,
            etTotalGuest,
            etVenueCost,
            etFoodCost,
            etTotalCost;
    Spinner spVenue,
            spVenueType,
            spFoodItem;
    Button btnADD;

    int mYear,mMonth,mDay;
    int hour, minute;
    Calendar calendar = Calendar.getInstance();


    String val, to;


    String[] foodArr = {"Select Food", "food1", "food2","food3","food4","food5","food6"};
    String[] foodCostArr = {"","5000", "5500", "6700", "7000", "7200", "10000"};

    String[] venueArr = {"Select Venue", "Taipan", "Susana","AMA"};
    String[] venueCostArr = {"", "3000", "5000","20000"};

    String[] venueTypeArr = {"Select Venue Type", "BIRTHDAY", "WEDDING", "MEETING", "HAYA"};


    ArrayAdapter<String> foodAdapter;
    ArrayAdapter<String> venueAdapter;
    ArrayAdapter<String> venueTypeAdapter;



    private int id;
    private String customerNameOld;
    private String venueOld;
    private String venueTypeOld;
    private String startDateOld;
    private String endDateOld;
    private String startTimeOld;
    private String endTimeOld;
    private String FoodOld;
    private String GuestOld;
    private String VenueCostOld;
    private String FoodCostOld;
    private String TotalCostOld;

    //Title
    TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondary_activity);
        eventDB = new DataHelper(Secondary_activity.this);


        etCustomerName = findViewById(R.id.etCustomerName);
        etStartDate = findViewById(R.id.etStartDate);
        etEndDate = findViewById(R.id.etEndDate);
        etStartTime = findViewById(R.id.etStartTime);
        etEndTime = findViewById(R.id.etEndTime);
        etTotalGuest = findViewById(R.id.etTotalGuest);
        etVenueCost = findViewById(R.id.etVenueCost);
        etFoodCost = findViewById(R.id.etFoodCost);
        etTotalCost = findViewById(R.id.etTotalCost);

        spVenue = findViewById(R.id.spVenue);
        spVenueType =findViewById(R.id.spVenueType);
        spFoodItem = findViewById(R.id.spFoodItem);


        btnADD = (Button) findViewById(R.id.btnAdd);

        venueAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, venueArr);
        spVenue.setAdapter(venueAdapter);
        spVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                etVenueCost.setText("" + venueCostArr[position]);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        venueTypeAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, venueTypeArr);
        spVenueType.setAdapter(venueTypeAdapter);


        foodAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, foodArr);
        spFoodItem.setAdapter(foodAdapter);

        spFoodItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                etFoodCost.setText("" + foodCostArr[position]);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddDATA();

        title = findViewById(R.id.txtTitle);
        setExtraValues(savedInstanceState);
        if(id == 0){
            title.setText("Add Information");
        }
        else{
            title.setText("Update Information");
            btnADD.setText("Update");
        }


    }



    //for update
    private void setExtraValues(Bundle savedInstanceState){
        if(savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if(extras == null){
                id = 0;
                customerNameOld = "";
                venueOld = "";
                venueTypeOld = "";
                startDateOld = "";
                endDateOld = "";
                startTimeOld = "";
                endTimeOld = "";
                FoodOld = "";
                GuestOld = "";
                VenueCostOld = "";
                FoodCostOld = "";
                TotalCostOld = "";
            }
            else{
                id = extras.getInt("id");
                customerNameOld = extras.getString("customerName");
                venueOld = extras.getString("venue");
                venueTypeOld = extras.getString("venueType");
                startDateOld = extras.getString("startDate");
                endDateOld = extras.getString("endDate");
                startTimeOld = extras.getString("startTime");
                endTimeOld = extras.getString("endTime");
                FoodOld = extras.getString("Food");
                GuestOld = extras.getString("Guest");
                VenueCostOld = extras.getString("VenueCost");
                FoodCostOld = extras.getString("FoodCost");
                TotalCostOld = extras.getString("TotalCost");
            }
        }
        else{
            id = (int) savedInstanceState.getSerializable("id");
            customerNameOld = (String) savedInstanceState.getSerializable("customerName");
            venueOld = (String) savedInstanceState.getSerializable("venue");
            venueTypeOld = (String) savedInstanceState.getSerializable("venueType");
            startDateOld = (String) savedInstanceState.getSerializable("startDate");
            endDateOld = (String) savedInstanceState.getSerializable("endDate");
            startTimeOld = (String) savedInstanceState.getSerializable("startTime");
            endTimeOld = (String) savedInstanceState.getSerializable("endTime");
            FoodOld = (String) savedInstanceState.getSerializable("Food");
            GuestOld = (String) savedInstanceState.getSerializable("Guest");
            VenueCostOld = (String) savedInstanceState.getSerializable("VenueCost");
            FoodCostOld = (String) savedInstanceState.getSerializable("FoodCost");
            TotalCostOld = (String) savedInstanceState.getSerializable("TotalCost");
        }
        etCustomerName.setText(customerNameOld);
        spVenue.setSelection(venueAdapter.getPosition(venueOld));
        spVenueType.setSelection(venueTypeAdapter.getPosition(venueTypeOld));
        etStartDate.setText(startDateOld);
        etEndDate.setText(endDateOld);
        etStartTime.setText(startTimeOld);
        etEndTime.setText(endTimeOld);
        spFoodItem.setSelection(foodAdapter.getPosition(FoodOld));
        etTotalGuest.setText(GuestOld);
        etVenueCost.setText(VenueCostOld);
        etFoodCost.setText(FoodCostOld);
        etTotalCost.setText(TotalCostOld);
    }










    private void calculateTotal(){
        try {
            Double venueCost = Double.parseDouble(etVenueCost.getText().toString());
            Double foodCost = Double.parseDouble(etFoodCost.getText().toString());
            Double total = venueCost + foodCost;
            etTotalCost.setText(total + "");
        }catch (Exception ex){
            Toast.makeText(Secondary_activity.this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    //String outputs for fields that are empty
    private boolean areInputsEmpty(){
        boolean result = false;
        if(TextUtils.isEmpty(etCustomerName.getText())
            && TextUtils.isEmpty(etFoodCost.getText())
            && TextUtils.isEmpty(etVenueCost.getText())
            && TextUtils.isEmpty(etTotalCost.getText())
            && TextUtils.isEmpty(etTotalGuest.getText())
            && TextUtils.isEmpty(etStartDate.getText())
            && TextUtils.isEmpty(etStartTime.getText())
            && TextUtils.isEmpty(etEndDate.getText())
            && TextUtils.isEmpty(etEndTime.getText())
        ){
            result = true;
        }
        else{
            result = false;
        }
        return result;
    }
    private String foodSpinnerItemSelected(){
        String result = "";
        if(TextUtils.isEmpty(etFoodCost.getText())){
            result = "Please select a food item.";
        }
        else{
            result = "";
        }
        return result;
    }
    private String venueSpinnerItemSelected(){
        String result = "";
        if(TextUtils.isEmpty(etVenueCost.getText())){
            result = "Please select a food item.";
        }
        else{
            result = "";
        }
        return result;
    }
    private String venueTypeSpinnerItemSelected(){
        String result = "";
        if(spVenueType.getSelectedItem().toString() == "Select Venue Type"){
            result = "Please select a venue type.";
        }
        else{
            result = "";
        }
        return result;
    }

    //String outputs for the fields that are not valid
    private boolean areInputsValid(){
        String message = "";
        boolean result = false;
        //Strings
        String startDate = etStartDate.getText().toString();
        String endDate = etEndDate.getText().toString();
        String startTime = etStartTime.getText().toString();
        String endTime = etEndTime.getText().toString();

        //Dates
        Date sTime = null;
        Date eTime = null;
        Date sDate = null;
        Date eDate = null;
        try {
            sTime = new SimpleDateFormat("HH:mm").parse(startTime);
            eTime = new SimpleDateFormat("HH:mm").parse(endTime);
            eDate = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);
            sDate = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        } catch (ParseException e) {
            result = false;
            e.printStackTrace();
        }


        if(startDate == endDate){
            if(sTime.getTime() >= eTime.getTime()){
                message += "\nPlease make sure your times are right or you can change your End Date.";
                Toast.makeText(Secondary_activity.this, message, Toast.LENGTH_SHORT).show();
                return false;
            }
            else{
                result = true;
            }
        }
        else if(sDate.getTime() > eDate.getTime()){
            //this means the date started event is greater than the end date
            message += "\nPlease make sure your dates are right";
            Toast.makeText(Secondary_activity.this, message, Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            result = true;
        }

        return result;
    }
    private boolean isValidForInsertOrUpdate(){
        if(areInputsEmpty()){
            //inputs empty
            String noData = "Please make sure you complete the fields.\n" +
                    foodSpinnerItemSelected() + "\n" +
                    venueSpinnerItemSelected() + "\n" +
                    venueTypeSpinnerItemSelected();
            Toast.makeText(Secondary_activity.this, noData, Toast.LENGTH_LONG).show();
            return false;
        }
        else{
            //inputs are right
            //now check the validity
            if(areInputsValid()){
                return true;
            }
        }
        return false;
    }

    public void AddDATA() {
        etStartDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    etStartDate.requestFocus();
                    getDatepicker("start");
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(getApplication().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etStartDate.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        etEndDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    etEndDate.requestFocus();
                    getDatepicker("end");
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(getApplication().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etEndDate.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        etStartTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    etStartTime.requestFocus();
                    timePicker("start");
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(getApplication().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etStartTime.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
        etEndTime.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    etEndTime.requestFocus();
                    timePicker("end");
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP){
                    InputMethodManager inputMethodManager = (InputMethodManager) getApplication().getSystemService(getApplication().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(etEndTime.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });



        btnADD.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(isValidForInsertOrUpdate()) {
                            calculateTotal();
                            String customerName = etCustomerName.getText().toString();
                            String venue = spVenue.getSelectedItem().toString();
                            String venueType = spVenueType.getSelectedItem().toString();
                            String startDate = etStartDate.getText().toString();
                            String endDate = etEndDate.getText().toString();
                            String startTime = etStartTime.getText().toString();
                            String endTime = etEndTime.getText().toString();
                            int totalGuest = Integer.parseInt(etTotalGuest.getText().toString());
                            String foodItem = spFoodItem.getSelectedItem().toString();
                            String foodCost = etFoodCost.getText().toString();
                            String venueCost = etVenueCost.getText().toString();
                            String totalCost = etTotalCost.getText().toString();

                            if(id == 0) {
                                String insertValidity = null;
                                    insertValidity = eventDB.insertValidity(venue, startDate, endDate, startTime, endTime);

                                if (insertValidity == "Venue is empty") {
                                    Toast.makeText(Secondary_activity.this, "Venue is Empty", Toast.LENGTH_SHORT).show();

                                    boolean isInserted = eventDB.insertData(customerName,
                                            venue, venueType, startDate, endDate, startTime, endTime, totalGuest,
                                            foodItem, foodCost, venueCost, totalCost);
                                    if (isInserted) {
                                        Toast.makeText(Secondary_activity.this, "New Data Added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Secondary_activity.this, "Cannot insert data", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (insertValidity == "Date and Time is Valid") {
                                    Toast.makeText(Secondary_activity.this, "Date and Time is valid", Toast.LENGTH_SHORT).show();

                                    boolean isInserted = eventDB.insertData(customerName,
                                            venue, venueType, startDate, endDate, startTime, endTime, totalGuest,
                                            foodItem, foodCost, venueCost, totalCost);
                                    if (isInserted) {
                                        Toast.makeText(Secondary_activity.this, "New Data Added", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Secondary_activity.this, "Cannot insert data", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (insertValidity == "Date and Time is Taken") {
                                    Toast.makeText(Secondary_activity.this,
                                            "Venue is taken and your date and time is also taken.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Secondary_activity.this,
                                            "BUG? FUCK? FUCCCKKKK!", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else{
                               //TODO: different in update
                                //check date same as insert but do not check the old date
                                //then update
                                //private String updateValidity(String venue, String startDate, String startTime, String endDate, String endTime,
                                //                                   String startDateOld, String startTimeOld, String endDateOld, String endTimeOld){

                                String updateValidity = eventDB.updateValidity(venue, startDate, startTime, endDate, endTime,
                                        startDateOld, startTimeOld, endDateOld, endTimeOld);
                                if (updateValidity == "Venue is empty") {
                                    boolean updated = eventDB.updateEvent(id+"", customerName,
                                            venue, venueType, startDate, endDate, startTime, endTime, totalGuest,
                                            foodItem, foodCost, venueCost, totalCost);
                                    if (updated) {
                                        Toast.makeText(Secondary_activity.this, "The data is updated.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Secondary_activity.this, "Cannot update data.", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (updateValidity == "Date and Time is Valid") {
                                    boolean updated = eventDB.updateEvent(id+"", customerName,
                                            venue, venueType, startDate, endDate, startTime, endTime, totalGuest,
                                            foodItem, foodCost, venueCost, totalCost);
                                    if (updated) {
                                        Toast.makeText(Secondary_activity.this, "The data is updated.", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getBaseContext(), MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        Toast.makeText(Secondary_activity.this, "Cannot updated data.", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (updateValidity == "Date and Time is Taken") {
                                    Toast.makeText(Secondary_activity.this,
                                            "Venue is taken and your date and time is also taken.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Secondary_activity.this,
                                            "BUG? FUCK? FUCCCKKKK!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                        else{
                            return;
                        }


                    }
                });

    }
    public  void getDatepicker(String startEndDate){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String dateString = format.format(calendar.getTime());
                        if(startEndDate == "start"){
                            etStartDate.setText(dateString);
                        }
                        else if(startEndDate == "end"){
                            etEndDate.setText(dateString);
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }
    public void timePicker(String timeStartEnd){
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String mins = selectedMinute +"";
                String hrs = selectedHour +"";
                if(mins.length() == 1){
                    mins = "0"+mins;
                }
                if(hrs.length() == 1){
                    hrs = "0" + hrs;
                }
                if(hrs == "" || TextUtils.isEmpty(hrs)){
                    hrs = "00";
                }
                if(mins == "" || TextUtils.isEmpty(mins)){
                    mins = "00";
                }
                if(timeStartEnd == "start") {
                    try {
                        String time = hrs + ":" + mins;
                        etStartTime.setText(time);
                    } catch (Exception ex) {
                        etStartTime.setText(getcurrenttime());
                    }
                }
                else if(timeStartEnd == "end"){
                    try {
                        String time = hrs + ":" + mins;
                        etEndTime.setText(time);
                    } catch (Exception ex) {
                        etEndTime.setText(getcurrenttime());
                    }
                }
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.show();
    }
    public String getcurrenttime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        String timeString = format.format(calendar.getTime());
        return timeString;

    }
}
